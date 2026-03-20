package catgirlemily.demo;
 
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
    private final double speed = 30.0;
 
    public testerasci() {
        // Zwiększyłeś okno do 140 w super(), więc Rect też powinien to uwzględniać
        super(150, 40, 30);
 
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
        new Rect(new Vector2(60, 20), new Vector2(70, 25), 2, "X").draw(renderer);
        new Sprite("src/main/resources/sss.png", new Vector2(10, 10), 32, 64).draw(renderer);
 
 
    }
 
    public static void main(String[] args) {
        new testerasci().start();
    }
}