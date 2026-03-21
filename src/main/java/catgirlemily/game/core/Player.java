package catgirlemily.game.core;

import catgirlemily.game.Game;
import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.type.KeyCode;
import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.util.Clamp;

public class Player {
    public Vector2 pos;
    public boolean canMove = true;

    private double localPosX;
    private double localPosY;

    private final String[] texture = {"src/main/resources/sprites/taxi.png"};
    
    private final Vector2 hitboxStraight = new Vector2(8, 6);
    private final Vector2 hitboxSide     = new Vector2(12, 4);
    
    private Vector2 currentHitbox;
    private final Sprite spriteStraight;
    private final Sprite spriteSide;
    private Sprite currentSprite;

    public Player(int startX, int startY) {
        this.localPosX = startX;
        this.localPosY = startY;
        this.pos = new Vector2(startX, startY);

        this.spriteStraight = new Sprite(texture[0], pos, hitboxStraight.x(), hitboxStraight.y());
        this.spriteSide     = new Sprite(texture[0], pos, hitboxSide.x(), hitboxSide.y());
        
        this.currentSprite = spriteSide;
        this.currentHitbox = hitboxSide;
    }

    public void handleInput(Game game, int width, int height) {
        if (!canMove) return;

        double speed = Game.debug && game.isKeyDown(KeyCode.SHIFT.getCode()) ? 5 : 2;
        if ((game.isKeyDown(KeyCode.W.getCode()) || game.isKeyDown(KeyCode.S.getCode())) && 
            (game.isKeyDown(KeyCode.A.getCode()) || game.isKeyDown(KeyCode.D.getCode()))) {
            speed = speed / 1.5;
        }

        if (game.isKeyDown(KeyCode.W.getCode())) {
            localPosY -= speed / 2; // match the screen ratio (2:1)
            this.currentSprite = spriteStraight;
            this.currentHitbox = hitboxStraight;
        }
        if (game.isKeyDown(KeyCode.S.getCode())) {
            localPosY += speed / 2;
            this.currentSprite = spriteStraight;
            this.currentHitbox = hitboxStraight;
        }
        
        if (game.isKeyDown(KeyCode.A.getCode())) {
            localPosX -= speed;
            this.currentSprite = spriteSide;
            this.currentHitbox = hitboxSide;
        } 
        else if (game.isKeyDown(KeyCode.D.getCode())) {
            localPosX += speed;
            this.currentSprite = spriteSide;
            this.currentHitbox = hitboxSide;
        } 

        // dziala tak sobie ale ok
        // na drugim pikselu out of bounds przeniesienie do nastepnej sceny
        localPosX = Clamp.clamp(localPosX, 4.0, width - 4.0);
        localPosY = Clamp.clamp(localPosY, 1.0, height - 1.0);
        
        this.pos = new Vector2((int) Math.round(localPosX), (int) Math.round(localPosY));
    }

    public Vector2 getHitbox() {
        return currentHitbox;
    }

    public void render(TREngine renderer) {
        if (Game.debug) {
            renderer.drawString(0, 0, String.format("Local X: %.1f Y: %.1f | Render X: %d", localPosX, localPosY, pos.x()), null);
        }
        
        int currentW = currentHitbox.x();
        int currentH = currentHitbox.y();
        
        int renderX = pos.x() - (currentW / 2);
        int renderY = pos.y() - (currentH / 2);

        currentSprite.setPosition(new Vector2(renderX, renderY));
        currentSprite.draw(renderer);
    }

    public void setAllX(int newX) {
        pos = new Vector2(newX, pos.y());
        localPosX = newX;
    }

    public void setAllY(int newY) {
        pos = new Vector2(pos.x(), newY);
        localPosY = newY;
    }
}