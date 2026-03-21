package catgirlemily.game.core;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.type.Vector2;

public class CarEntity {
    private double x;
    private double y;
    private double speed;
    private int direction; // 1 dla prawo, -1 dla lewo

    private final Sprite sprite;
    private final Vector2 size = new Vector2(12, 4);

    /**
     * @param x Pozycja startowa X
     * @param y Pozycja startowa Y
     * @param speed Prędkość (np. 0.5 lub 1.2)
     * @param direction 1 dla jazdy w prawo, -1 dla jazdy w lewo
     */
    public CarEntity(int x, int y, double speed, int direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;

        this.sprite = new Sprite("src/main/resources/sprites/car1_0.png", new Vector2(x, y), size.x(), size.y());
    }

    public void update(double delta) {
        // Ruch poziomy: pozycja += kierunek * prędkość
        // Jeśli chcesz używać delty: x += direction * speed * delta * 60;
        x += direction * speed;
        if(x < -12) x = 420;
    }

    public void render(TREngine renderer, int cameraX) {
        // Rysujemy z uwzględnieniem kamery, tak jak gracza
        int renderX = (int)Math.round(x) - cameraX;
        int renderY = (int)Math.round(y);

        sprite.setPosition(new Vector2(renderX, renderY));
        sprite.draw(renderer);
    }

    // Gettery do kolizji
    public double getX() { return x; }
    public double getY() { return y; }
    public Vector2 getSize() { return size; }

    public void setPos(int x, int y) {
        sprite.setPosition(new Vector2(x, y));
    }
}