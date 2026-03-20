package catgirlemily.demo;

import java.util.Random;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Trlib;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.drawable.Rect;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.Vector2;

public class MyGame extends Trlib {
    private Sprite player;
    private Vector2 playerPos;

    private double preciseX = 10.0;
    private double preciseY = 10.0;
    private final double speed = 30.0;

    public MyGame() {
        // Zwiększyłeś okno do 140 w super(), więc Rect też powinien to uwzględniać
        super(210, 55, 60);

        playerPos = new Vector2((int)preciseX, (int)preciseY);
        player = new Sprite("src/main/resources/sss.png", playerPos, 16, 8);
        
        System.out.println("--- Gra wystartowała (WinAPI Input) ---");
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Game Logic \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onUpdate(TREngine renderer, double delta) {
        renderer.setWindowName("x: " + preciseX);
        //renderer.setWidth(new Random().nextInt(150));
        //renderer.setHeight(new Random().nextInt(40));
        //renderer.setBackgroundColor(5);
        //renderer.setForegroundColor(1);
        
        boolean moved = false;

        // Używamy Virtual Key Codes (WinAPI) dla stabilności
        // 0x57 = W, 0x53 = S, 0x41 = A, 0x44 = D
        if (isKeyPressed(0x57)) { preciseY -= speed * delta; moved = true; }
        if (isKeyPressed(0x53)) { preciseY += speed * delta; moved = true; }
        if (isKeyPressed(0x41)) { preciseX -= speed * delta; moved = true; }
        if (isKeyPressed(0x44)) { preciseX += speed * delta; moved = true; }

        // Kolizje (zakładając rozmiar okna 140x40 i sprite 16x8); 

            playerPos.setX((int) Math.round(preciseX));
            playerPos.setY((int) Math.round(preciseY));
            player.setPosition(playerPos);
        };


    /**
     * Nowa metoda wywoływana JEDNORAZOWO przy naciśnięciu.
     */
    @Override
    public void onKeyPress(int keyCode) {
        // Przykład: Spacja (0x20) zmienia pozycję na środek
        if (keyCode == 0x20) {
            preciseX = 60;
            preciseY = 15;
            System.out.println("Teleportacja na środek!");
        }

        // Escape (0x1B) zamyka grę
        if (keyCode == 0x1B) {
            stop();
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Rendering \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onRender(TREngine renderer) {
        // Rysujemy tło dopasowane do 140x40
        player.draw(renderer);
        renderer.drawPoint(new Vector2(10,10), '⡻', Color.BLUE);
    }

    public static void main(String[] args) {
        new MyGame().start();
    }
}