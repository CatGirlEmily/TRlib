package catgirlemily.game.scenes;

import catgirlemily.game.Game;
import catgirlemily.game.util.ColorPallete;
import catgirlemily.game.util.Scene;
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
        Color mainColor = Color.GRAY;
        
        renderer.drawString(10, 20, selected == 0 ? ">>> Play" : "    Play", selected == 0 ? Color.WHITE : mainColor);
        renderer.drawString(10, 23, selected == 1 ? ">>> Config" : "    Config", selected == 1 ? Color.WHITE : mainColor);
        renderer.drawString(10, 26, selected == 2 ? ">>> Piwo" : "    Piwo", selected == 2 ? Color.WHITE : mainColor);
        renderer.drawString(10, 29, selected == 3 ? ">>> Exit" : "    Exit", selected == 3 ? Color.WHITE : mainColor);

        // --- 2. RENDEROWANIE PANELU BOCZNEGO (Dla Config) ---
        if (selected == 1) {
            renderConfigSidePanel(renderer);
        }
    }

    private void renderConfigSidePanel(TREngine renderer) {
        // Panel od 120 do 200
        new StyledRect(new Vector2(120, 10), new Vector2(200, 50))
            .withColor(inConfigMode ? ColorPallete.THIRD : ColorPallete.FIFTH)
            .withEdges("@", "@", "@", "@")
            .withCorners("@", "@", "@", "@")
            .draw(renderer);

        int centerX = 160; // Środek panelu

        // --- CONFIGURATION --- (długość 21 znaków) -> 160 - (21/2) = 149.5 -> 150
        renderer.drawString(149, 12, "---[ CONFIGURATION ]---", ColorPallete.EIGHT);

        // Opcje wewnątrz configu
        String[] configOptions = {"Volume: 100%", "Resolution: 210x55", "Fullsreen: OFF"};
        for (int i = 0; i < configOptions.length; i++) {
            Color c = (inConfigMode && configSelected == i) ? ColorPallete.SECOND : Color.WHITE;
            String prefix = (inConfigMode && configSelected == i) ? "> " : "  ";
            String fullLine = prefix + configOptions[i];

            // Dynamiczne centrowanie opcji (opcjonalne, ale wygląda lepiej):
            int startX = centerX - (fullLine.length() / 2);
            renderer.drawString(startX, 18 + (i * 2), fullLine, c);
        }

        // align to center
        if (!inConfigMode) {
            String msg = "[Press ENTER to Edit]"; // 21
            int startX = centerX - (msg.length() / 2); // 160 - 10 = 150
            renderer.drawString(startX, 48, msg, Color.GRAY);
        } else {
            String msg = "[ESC to Back]"; // 13 znaków
            int startX = centerX - (msg.length() / 2); // 160 - 6 = 154
            renderer.drawString(startX, 48, msg, Color.GREEN);
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
                    case 0 -> game.setScene(new TestStreet(game));
                    case 1 -> inConfigMode = true; // Wchodzimy w edycję
                    case 3 -> System.exit(0);
                }
            }
        }
    }
}