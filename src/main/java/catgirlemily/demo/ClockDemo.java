package catgirlemily.demo;

import catgirlemily.trlib.Trlib;
import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.drawable.Line;
import catgirlemily.trlib.types.Vector2;


//
// shoutout chatgpt
//
public class ClockDemo extends Trlib {
    private double angle = 0; // Kąt w radianach
    private final double rotationSpeed = 1; // Prędkość obrotu

    // Parametry ekranu zgodnie z prośbą: 150x55, 60 FPS
    public ClockDemo() { 
        super(210, 55, 60); 
    }

    @Override
    public void onUpdate(double delta) {
        // Zwiększamy kąt o prędkość pomnożoną przez delta (czas klatki)
        angle += rotationSpeed * 0.1;

        // Resetujemy kąt po pełnym obrocie (2 * PI), żeby uniknąć ogromnych liczb
        if (angle > Math.PI * 2) {
            angle -= Math.PI * 2;
        }
    }

    @Override
    public void onRender(TREngine renderer) {
        // Środek ekranu
        int centerX = 150 / 2;
        int centerY = 55 / 2;
        
        // Długość wskazówki (promień)
        int radius = 38;

        // Obliczamy koniec linii
        // Używamy cos dla X i sin dla Y
        int endX = centerX + (int) (Math.cos(angle) * radius * 2.0); // *2.0 bo znaki są wąskie
        int endY = centerY + (int) (Math.sin(angle) * radius);

        Vector2 start = new Vector2(centerX, centerY);
        Vector2 end = new Vector2(endX, endY);

        // Rysujemy linię algorytmem Bresenhama (który masz w klasie Line)
        new Line(start, end, "test", 2).draw(renderer);
    }

    public static void main(String[] args) {
        new ClockDemo().start();
    }
}