package catgirlemily.trlib.drawable;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.Drawable;
import catgirlemily.trlib.type.Pixel;
import catgirlemily.trlib.type.Vector2;
import catgirlemily.trlib.util.Texture;

public class Sprite implements Drawable {
    private final Pixel[][] data;
    private Vector2 position;

    public Sprite(String path, Vector2 pos, int w, int h) {
        this.position = pos;
        this.data = Texture.get().load(path, w, h);
    }

    // TEJ METODY BRAKOWAŁO:
    public void setPosition(Vector2 pos) {
        this.position = pos;
    }

    @Override
    public void draw(TREngine renderer) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                Pixel p = data[y][x];
                if (p == Pixel.EMPTY) continue;
                renderer.drawPoint(position.x() + x, position.y() + y, p.character(), p.color());
            }
        }
    }
}