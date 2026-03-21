package catgirlemily.trlib.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.Pixel;

public class Texture {
    private static Texture instance;
    private final Map<String, Pixel[][]> cache = new HashMap<>();
    
    // Znaki od najciemniejszego do najjaśniejszego
    private static final String RAMP = "@";

    public static Texture get() {
        if (instance == null) instance = new Texture();
        return instance;
    }

    public Pixel[][] load(String path, int w, int h) {
        String key = path + "_" + w + "_" + h;
        if (cache.containsKey(key)) return cache.get(key);

        try {
            BufferedImage img = ImageIO.read(new File(path));
            
            // Jeśli w/h to 0, użyj oryginalnego rozmiaru
            int targetW = (w > 0) ? w : img.getWidth();
            int targetH = (h > 0) ? h : img.getHeight();

            BufferedImage scaled = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = scaled.createGraphics();
            g.drawImage(img, 0, 0, targetW, targetH, null);
            g.dispose();

            Pixel[][] grid = new Pixel[targetH][targetW];
            for (int y = 0; y < targetH; y++) {
                for (int x = 0; x < targetW; x++) {
                    int argb = scaled.getRGB(x, y);
                    int alpha = (argb >> 24) & 0xff;

                    if (alpha < 128) {
                        grid[y][x] = Pixel.EMPTY;
                    } else {
                        int r = (argb >> 16) & 0xff;
                        int gCol = (argb >> 8) & 0xff;
                        int b = argb & 0xff;

                        // Wybór znaku na podstawie jasności
                        double lum = (0.2126 * r + 0.7152 * gCol + 0.0722 * b);
                        char c = RAMP.charAt((int)(lum / 256.0 * RAMP.length()));
                        
                        // Używamy Twojego nowego Color.rgb()!
                        grid[y][x] = new Pixel(c, Color.rgb(r, gCol, b));
                    }
                }
            }
            cache.put(key, grid);
            return grid;
        } catch (IOException e) {
            e.printStackTrace();
            return new Pixel[][]{{Pixel.EMPTY}};
        }
    }
}