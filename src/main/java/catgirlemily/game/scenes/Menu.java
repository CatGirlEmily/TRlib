package catgirlemily.game.scenes;

import catgirlemily.game.Game;
import catgirlemily.game.Scene;
import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.drawable.StyledRect;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.KeyCode;
import catgirlemily.trlib.type.Vector2;

public class Menu implements Scene {
    private final Game game;
    private int selected = 0;           // Główne menu
    private int configSelected = 0;     // Menu wewnątrz Configu
    private boolean inConfigMode = false; // Czy jesteśmy "wewnątrz" edycji?

    public Menu(Game game) {
        this.game = game;
    }

    @Override
    public void init() {
        selected = 0;
        inConfigMode = false;
    }

    @Override
    public void update(TREngine renderer, double delta) {}

    @Override
    public void render(TREngine renderer) {
        // --- 1. RENDEROWANIE GŁÓWNYCH OPCJI ---
        // Używamy Color.GRAY dla nieaktywnych opcji, gdy gracz edytuje Config
        Color mainColor = inConfigMode ? Color.MAGENTA : Color.WHITE;
        
        renderer.drawString(10, 20, selected == 0 ? ">>> PLAY" : "    Play", selected == 0 ? Color.CYAN : mainColor);
        renderer.drawString(10, 23, selected == 1 ? ">>> CONFIG" : "    Config", selected == 1 ? Color.CYAN : mainColor);
        renderer.drawString(10, 26, selected == 2 ? ">>> PIWO" : "    Piwo", selected == 2 ? Color.CYAN : mainColor);
        renderer.drawString(10, 29, selected == 3 ? ">>> UWU" : "    Uwu", selected == 3 ? Color.CYAN : mainColor);

        // --- 2. RENDEROWANIE PANELU BOCZNEGO (Dla Config) ---
        if (selected == 1) {
            renderConfigSidePanel(renderer);
        }
    }

    private void renderConfigSidePanel(TREngine renderer) {
        // Rysujemy ramkę po prawej stronie (x od 60 do 120)
        new StyledRect(new Vector2(120, 10), new Vector2(200, 50))
            .withColor(inConfigMode ? Color.YELLOW : Color.WHITE)
            .withEdges("@", "@", "@", "@")
            .withCorners("@", "@", "@", "@")
            .draw(renderer);

        renderer.drawString(65, 17, "--- CONFIGURATION ---", Color.YELLOW);

        // Opcje wewnątrz configu
        String[] configOptions = {"Volume: 100%", "Resolution: 210x55", "Fullsreen: OFF"};
        for (int i = 0; i < configOptions.length; i++) {
            Color c = (inConfigMode && configSelected == i) ? Color.CYAN : Color.WHITE;
            String prefix = (inConfigMode && configSelected == i) ? "> " : "  ";
            renderer.drawString(65, 20 + (i * 2), prefix + configOptions[i], c);
        }

        if (!inConfigMode) {
            renderer.drawString(65, 33, "[Press ENTER to Edit]", Color.MAGENTA);
        } else {
            renderer.drawString(65, 33, "[ESC to Back]", Color.GREEN);
        }
    }

    @Override
    public void onKeyPress(int vKey) {
        KeyCode key = KeyCode.fromCode(vKey);

        if (inConfigMode) {
            // LOGIKA GDY EDYTUJEMY CONFIG
            if (key == KeyCode.UP || key == KeyCode.W) configSelected = Math.max(configSelected - 1, 0);
            if (key == KeyCode.DOWN || key == KeyCode.S) configSelected = Math.min(configSelected + 1, 2);
            
            if (key == KeyCode.ESCAPE) {
                inConfigMode = false; // Powrót do głównego menu
            }
        } else {
            // LOGIKA GŁÓWNEGO MENU
            if (key == KeyCode.UP || key == KeyCode.W) selected = Math.max(selected - 1, 0);
            if (key == KeyCode.DOWN || key == KeyCode.S) selected = Math.min(selected + 1, 3);

            if (key == KeyCode.ENTER || key == KeyCode.SPACE) {
                switch (selected) {
                    case 0 -> game.setScene(new template(game));
                    case 1 -> inConfigMode = true; // Wchodzimy w edycję
                    case 3 -> System.exit(0);
                }
            }
        }
    }
}