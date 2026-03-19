package catgirlemily.trlib.core;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class InputHandler implements Runnable {
    private Terminal terminal;
    private NonBlockingReader reader;
    private final Set<Integer> pressedKeys = new HashSet<>();
    private boolean running = true;

    public InputHandler() {
        try {
            // TerminalBuilder automatycznie wykrywa system (Windows/Linux)
            // i używa JNA (które już masz w pom.xml) do obsługi konsoli.
            this.terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();
            
            // Wchodzimy w tryb RAW - to wyłącza echo i czekanie na Enter
            this.terminal.enterRawMode();
            this.reader = terminal.reader();
        } catch (IOException e) {
            System.err.println("Nie udało się zainicjować JLine Terminal: " + e.getMessage());
        }
    }

    /**
     * Sprawdza, czy dany klawisz (kod ASCII) jest aktualnie naciśnięty.
     */
    public boolean isKeyPressed(int keyCode) {
        synchronized (pressedKeys) {
            return pressedKeys.contains(keyCode);
        }
    }

    @Override
    public void run() {
        try {
            while (running) {
                // Czytamy znak z timeoutem 10ms. 
                // Jeśli nic nie zostanie naciśnięte, zwraca -2.
                int c = reader.read(10);

                synchronized (pressedKeys) {
                    if (c >= 0) {
                        // Dodajemy klawisz do zbioru naciśniętych
                        pressedKeys.add(c);
                    } else if (c == -2) {
                        // Timeout - w terminalu to nasz sposób na "Key Release"
                        // Czyścimy, bo terminal nie wysyła zdarzenia puszczenia klawisza.
                        pressedKeys.clear();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        this.running = false;
        try {
            if (terminal != null) terminal.close();
        } catch (IOException ignored) {}
    }
}