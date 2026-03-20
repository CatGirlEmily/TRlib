package catgirlemily.demo;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Trlib;
import catgirlemily.trlib.drawable.Line;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.Vector2;


//
// shoutout chatgpt
//
public class ClockDemo extends Trlib {
    private double angle = 0; // Kąt w radianach
    private final double rotationSpeed = 1; // Prędkość obrotu

    // Parametry ekranu zgodnie z prośbą: 150x55, 60 FPS
    public ClockDemo() { 
        super(210, 55, 30);
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
    // Definiujemy pozycję i rozmiar kwadratu
    Vector2 topLeft = new Vector2(10, 10);
    Vector2 bottomRight = new Vector2(40, 25); // Szerokość 30, Wysokość 15

    // Obliczamy pozostałe narożniki
    Vector2 topRight = new Vector2(bottomRight.x(), topLeft.y());
    Vector2 bottomLeft = new Vector2(topLeft.x(), bottomRight.y());

    // Definiujemy wzór linii (np. pełny blok)
    String pattern = "█";
    int thickness = 1; // Grubość 1

    // Rysujemy 4 linie, każda w innym kolorze (Tęcza)
    // 1. Góra (Czerwona)
    new Line(topLeft, topRight, pattern, thickness)
        .withColor(Color.RED)
        .draw(renderer);

    // 2. Prawy bok (Żółty)
    new Line(topRight, bottomRight, pattern, thickness)
        .withColor(Color.YELLOW)
        .draw(renderer);

    // 3. Dół (Zielony)
    new Line(bottomRight, bottomLeft, pattern, thickness)
        .withColor(Color.GREEN)
        .draw(renderer);

    // 4. Lewy bok (Niebieski)
    new Line(bottomLeft, topLeft, pattern, thickness)
        .withColor(Color.BLUE)
        .draw(renderer);
    }

    public static void main(String[] args) {
        new ClockDemo().start();
    }
}