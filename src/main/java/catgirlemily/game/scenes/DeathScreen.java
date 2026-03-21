package catgirlemily.game.scenes;

import catgirlemily.game.Game;
import catgirlemily.game.util.Scene;
import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.core.SoundManager;
import catgirlemily.trlib.drawable.Sprite;
import catgirlemily.trlib.type.Color;
import catgirlemily.trlib.type.Vector2;

public class DeathScreen implements Scene {
    private final Game game;

	private Sprite ha1;
	private Sprite ha2;
	private int currentFrame = 0;

	private double timer = 0.0;

	private final int spriteWidth = 20;
	private final int spriteHeight = 10;

	private int windowX = 0;
	private int windowY = 0;

    public DeathScreen(Game game) {
        this.game = game;
    }

    @Override
    public void init() {
		windowX = game.getRenderer().getWidth();
		windowY = game.getRenderer().getHeight();

		int centerX = (windowX / 2) - (spriteWidth / 2);
		int centerY = (windowY / 2) - (spriteHeight / 2);

		Vector2 centerPosition = new Vector2(centerX, centerY);

		// For now placeholders.
		// TODO: Miki. Narysujesz spritea czegoś co się śmieje w 2 kratkach?
		ha1 = new Sprite("src/main/resources/sprites/deathscreen/ha1.png", centerPosition, spriteWidth, spriteHeight);
        ha2 = new Sprite("src/main/resources/sprites/deathscreen/ha2.png", centerPosition, spriteWidth, spriteHeight);

		SoundManager.play("src/main/resources/sounds/deathscreen/ha.wav", false);
	}

    @Override
    public void update(TREngine renderer, double delta) {
		timer += delta;

		if(timer >= 1.0) {
			timer -= 1.0;

			currentFrame = (currentFrame == 0) ? 1 : 0;

			SoundManager.play("src/main/resources/sounds/deathscreen/ha.wav", false);
		}
	}

    @Override
    public void render(TREngine renderer) {
		renderer.clear();

		if(currentFrame == 0) {
			ha1.draw(renderer);
		}
		else {
			ha2.draw(renderer);
		}

		renderer.drawString(windowX / 2 - 6, windowY / 4, "You are", Color.CYAN);
		renderer.drawString(windowX / 2 + 2, windowY / 4, "DEAD", Color.RED);
		renderer.drawString(windowX / 2 - 12, windowY - (windowY / 4), "Press any key to", Color.CYAN);
		renderer.drawString(windowX / 2 + 5, windowY - (windowY / 4), "continue", Color.GREEN);
	}

    @Override
    public void onKeyPress(int vKey) {
	}
}
