package catgirlemily.demo;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Trlib;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.drawable.Rect;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.Vector2;

/**
 * MyGame - Finalna implementacja Twojej gry w terminalu.
 */
public class MyGame extends Trlib {
    private Sprite player;
    private Vector2 playerPos;

    // Współrzędne double dla płynnego ruchu
    private double preciseX = 10.0;
    private double preciseY = 10.0;
    
    // Prędkość: 30 znaków na sekundę
    private final double speed = 30.0;

    public MyGame() {
        // Okno 120x40, 60 FPS
        super(140, 40, 60);

        // Inicjalizacja pozycji
        playerPos = new Vector2((int)preciseX, (int)preciseY);

        // Ładowanie obrazka (Resource path)
        player = new Sprite("src/main/resources/sss.png", playerPos, 16, 8);
        
        System.out.println("--- Gra wystartowała ---");
        System.out.println("Sterowanie: WSAD | Wyjście: Ctrl+C");
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Game Logic \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onUpdate(double delta) {
        boolean moved = false;

        // Sprawdzanie klawiszy przez Raw Mode (z klasy Trlib)
        if (isKeyPressed('W')) { preciseY -= speed * delta; moved = true; }
        if (isKeyPressed('S')) { preciseY += speed * delta; moved = true; }
        if (isKeyPressed('A')) { preciseX -= speed * delta; moved = true; }
        if (isKeyPressed('D')) { preciseX += speed * delta; moved = true; }

        // Ograniczenie gracza do granic ekranu (Collision Window)
        if (preciseX < 1) preciseX = 1;
        if (preciseY < 1) preciseY = 1;
        if (preciseX > 120 - 16) preciseX = 120 - 16; // Szerokość okna - szerokość sprite
        if (preciseY > 40 - 8) preciseY = 40 - 8;  // Wysokość okna - wysokość sprite

        // Jeśli nastąpił ruch, aktualizujemy Vector2 dla renderera
        if (moved) {
            playerPos.setX((int) Math.round(preciseX));
            playerPos.setY((int) Math.round(preciseY));
            player.setPosition(playerPos);
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Rendering \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onRender(TREngine renderer) {
        // 1. Rysujemy ramkę świata (StyledRect/Rect)
        // Używamy CYAN do obramowania, spacji jako wzoru
        new Rect(new Vector2(0, 0), new Vector2(119, 39), 1, "█")
                .withColor(Color.CYAN)
                .draw(renderer);

        // 2. Rysujemy gracza (Sprite)
        player.draw(renderer);
        
        // 3. Prosty Debug Info (opcjonalnie)
        // Możesz dodać rysowanie punktu z kolorem białym jako prymitywny tekst:
        // renderer.drawPoint(2, 1, 'X', Color.WHITE);
        // renderer.drawPoint(3, 1, ':', Color.WHITE);
        // renderer.drawPoint(5, 1, (char)('0' + (playerPos.x()/10)), Color.WHITE);
    }

    public static void main(String[] args) {
        // Uruchomienie pętli start() z Trlib
        new MyGame().start();
    }
}