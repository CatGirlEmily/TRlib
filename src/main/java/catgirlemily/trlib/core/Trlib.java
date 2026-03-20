package catgirlemily.trlib.core;

import catgirlemily.trlib.TREngine;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Trlib - The main framework class.
 * Handles the game loop, delta time calculation, JLine3 Raw Mode, and keyboard input.
 */
public abstract class Trlib {
    private final TREngine renderer;
    private boolean running = true;

    // System sterowania
    private Terminal terminal;
    private NonBlockingReader reader;
    private final Map<Character, Long> keyStates = new HashMap<>();
    private static final long KEY_TIMEOUT_MS = 100; // Po jakim czasie klawisz "wygaśnie" (dla Windows)

    public Trlib(int width, int height, int fps) {
        this.renderer = new TREngine(width, height, fps);

        try {
            // 1. Inicjalizacja JLine3 i Raw Mode
            this.terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();
            this.terminal.enterRawMode();
            this.reader = terminal.reader();

            // 2. Ukrycie kursora (ANSI)
            System.out.print("\033[?25l");
            System.out.flush();

            // 3. Wątek nasłuchujący klawiatury
            startInputThread();

        } catch (IOException e) {
            System.err.println("[Trlib] Failed to initialize terminal Raw Mode.");
            e.printStackTrace();
        }

        // Safety: ensure the cursor is restored and terminal is fixed even if the app crashes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            stop();
            System.out.print("\033[?25h"); // Pokaż kursor
            System.out.flush();
            System.out.println("\n[Trlib] Engine stopped safely.");
        }));
    }

    /** Wątek czytający wejście z terminala w sposób nieblokujący. */
    private void startInputThread() {
        Thread inputThread = new Thread(() -> {
            while (running) {
                try {
                    int c = reader.read(5L); // Czytaj z timeoutem 5ms
                    if (c != -1) {
                        char key = Character.toUpperCase((char) c);
                        synchronized (keyStates) {
                            keyStates.put(key, System.currentTimeMillis());
                        }
                    }
                } catch (IOException ignored) {}
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();
    }

    /** * Sprawdza, czy dany klawisz jest wciśnięty.
     * @param c Znak klawisza (np. 'W', 'A', 'S', 'D').
     */
    public boolean isKeyPressed(char c) {
        char key = Character.toUpperCase(c);
        synchronized (keyStates) {
            if (!keyStates.containsKey(key)) return false;
            
            // Jeśli klawisz był widziany niedawno, uznajemy go za wciśnięty
            boolean active = (System.currentTimeMillis() - keyStates.get(key)) < KEY_TIMEOUT_MS;
            if (!active) keyStates.remove(key);
            return active;
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Abstract Hooks \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    public abstract void onUpdate(double delta);
    public abstract void onRender(TREngine renderer);

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Core Loop \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    public void start() {
        long lastFrameTime = System.nanoTime();

        while (running) {
            long currentTime = System.nanoTime();
            double delta = (currentTime - lastFrameTime) / 1_000_000_000.0;
            lastFrameTime = currentTime;
            
            onUpdate(delta);

            renderer.clear();
            onRender(renderer);
            renderer.display();

            try {
                // Precyzyjne sterowanie FPS
                long sleepTime = 1000 / renderer.getFPS();
                Thread.sleep(Math.max(sleepTime, 1));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    public void stop() {
        this.running = false;
        try {
            if (terminal != null) terminal.close();
        } catch (IOException ignored) {}
    }

    public TREngine getRenderer() {
        return renderer;
    }
}