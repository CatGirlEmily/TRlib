package mikowy001;

import java.util.Random;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Trlib;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.drawable.Rect;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.Vector2;

public class testerasci extends Trlib {
    private Sprite player;
    private Vector2 playerPos;

    private double preciseX = 10.0;
    private double preciseY = 10.0;
 
    private Sprite drzewaLewo = new Sprite("src/main/resources/sprites/drzewa_lewo.png", new Vector2(1, 1), 180, 80);
    private Sprite drzewaPrawo = new Sprite("src/main/resources/sprites/drzewa_prawo.png", new Vector2(1, 1), 180, 80);

    public testerasci() {
        // Zwiększyłeś okno do 140 w super(), więc Rect też powinien to uwzględniać
        super(210, 40,1);

        playerPos = new Vector2((int)preciseX, (int)preciseY);
        
        
        System.out.println("--- Gra wystartowała (WinAPI Input) ---");
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Game Logic \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onUpdate(TREngine renderer, double delta) {
    
    }

    /**
     * Nowa metoda wywoływana JEDNORAZOWO przy naciśnięciu.
     */
    @Override
    public void onKeyPress(int keyCode) {
        
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Rendering \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onRender(TREngine renderer) {
        
        drzewaLewo.draw(renderer);
        drzewaPrawo.draw(renderer);
        //new Sprite("src/main/resources/sprites/drzewa_lewo.png", new Vector2(10, 10), 40, 20).draw(renderer);
        //renderer.drawString(10, 10, "ssdaadsda", Color.RED);
    }

    public static void main(String[] args) {
        new testerasci().start();
    }
}