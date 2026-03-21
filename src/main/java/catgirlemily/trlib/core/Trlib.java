package catgirlemily.trlib.core;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.type.KeyCode;
import com.sun.jna.platform.win32.User32;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Trlib - Rdzeń silnika.
 * Obsługuje pętlę gry, Delta Time oraz hybrydowe wejście (WinAPI / Linux Raw Terminal).
 */
public abstract class Trlib {
    private final TREngine renderer;
    private boolean running = true;
    private final Set<Integer> lastFrameKeys = new HashSet<>();
    private int currentLinuxKey = -1;

    public Trlib(int width, int height, int fps) {
        this.renderer = new TREngine(width, height, fps);
        
        //Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        //    stop();
        //    System.out.print("\033[?25h\033[0m"); 
        //    System.out.flush();
        //    System.out.println("\n[Trlib] Engine stopped safely.");
        //}));
    }

    public abstract void onUpdate(TREngine renderer, double delta);
    public abstract void onRender(TREngine renderer);
    public abstract void onKeyPress(int keyCode);

    public void start() {
        long lastFrameTime = System.nanoTime();

        while (running) {
            long currentTime = System.nanoTime();
            double delta = (currentTime - lastFrameTime) / 1_000_000_000.0;
            lastFrameTime = currentTime;
            
            processInput();
            onUpdate(renderer, delta);

            renderer.clear();
            onRender(renderer);
            renderer.display();

            try {
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
        if (renderer != null) renderer.stop();
    }

    /**
     * Główna metoda obsługi wejścia. 
     * Na Linuxie czyta surowe bajty, na Windowsie używa GetAsyncKeyState.
     */

    private long lastKeyTime = 0;
    private static final long KEY_TIMEOUT_MS = 100; // Czas (ms), po którym klawisz "puszcza" na Linuxie

    /**
     * Główna pętla obsługi wejścia z mechanizmem auto-release dla Linuxa.
     */
    private void processInput() {
        if (TREngine.IsOnWindows) {
            // Logika dla Windows (Polling przez WinAPI)
            for (int vk = 0x08; vk <= 0x91; vk++) {
                boolean isDown = isKeyDown(vk);
                if (isDown && !lastFrameKeys.contains(vk)) {
                    onKeyPress(vk);
                    lastFrameKeys.add(vk);
                } else if (!isDown && lastFrameKeys.contains(vk)) {
                    lastFrameKeys.remove(vk);
                }
            }
        } else {
            // Logika dla Linux (Raw Mode Terminal)
            try {
                if (System.in.available() > 0) {
                    while (System.in.available() > 0) {
                        int key = System.in.read();
                        int processedKey = -1;

                        // 1. Obsługa ESC i Strzałek
                        if (key == 0x1B) { 
                            if (System.in.available() >= 2) {
                                System.in.read(); // pomiń '['
                                int arrow = System.in.read();
                                if (arrow == 65) processedKey = KeyCode.UP.getCode();
                                else if (arrow == 66) processedKey = KeyCode.DOWN.getCode();
                                else if (arrow == 67) processedKey = KeyCode.RIGHT.getCode();
                                else if (arrow == 68) processedKey = KeyCode.LEFT.getCode();
                            } else {
                                processedKey = KeyCode.ESCAPE.getCode();
                            }
                        } 
                        // 2. Enter
                        else if (key == 10 || key == 13) {
                            processedKey = KeyCode.ENTER.getCode();
                        }
                        // 3. Backspace
                        else if (key == 127 || key == 8) {
                            processedKey = KeyCode.BACKSPACE.getCode();
                        }
                        // 4. Litery i reszta
                        else {
                            processedKey = key;
                            if (key >= 97 && key <= 122) { // a-z -> A-Z
                                processedKey = key - 32;
                            }
                        }

                        // Jeśli rozpoznaliśmy klawisz, aktualizujemy stan
                        if (processedKey != -1) {
                            currentLinuxKey = processedKey;
                            lastKeyTime = System.currentTimeMillis();
                            
                            // Wywołujemy zdarzenie pojedynczego kliknięcia tylko raz
                            if (!lastFrameKeys.contains(processedKey)) {
                                onKeyPress(processedKey);
                                lastFrameKeys.add(processedKey);
                            }
                        }
                    }
                } else {
                    // Mechanizm puszczania klawisza (Key Release Simulator)
                    if (System.currentTimeMillis() - lastKeyTime > KEY_TIMEOUT_MS) {
                        currentLinuxKey = -1;
                        lastFrameKeys.clear(); // Czyścimy historię "wciśnięć"
                    }
                }
            } catch (IOException e) {
                stop();
            }
        }
    }

    public boolean isKeyDown(int vKey) {
        if (TREngine.IsOnWindows) {
            short state = User32.INSTANCE.GetAsyncKeyState(vKey);
            return (state & 0x8000) != 0;
        } else {
            // Na Linuxie w terminalu "isKeyDown" jest trudne, 
            // zwracamy true tylko jeśli to ostatni wciśnięty klawisz w tej klatce.
            return currentLinuxKey == vKey;
        }
    }

    public boolean isKeyPressed(KeyCode key) {
        return isKeyDown(key.getCode());
    }

    public TREngine getRenderer() { return renderer; }
}