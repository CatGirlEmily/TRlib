package catgirlemily.trlib;

public abstract class Trlib {
    private final TREngine renderer;
    private boolean running = true;

    public Trlib(int width, int height, int fps) {
        this.renderer = new TREngine(width, height, fps);
    }

    public abstract void onUpdate(double delta);
    public abstract void onRender(TREngine renderer);

    public void start() {
        // instantiate lastFrameTime used for calculating delta
        long lastFrameTime = System.nanoTime();

        while (running) {
            double delta = (System.nanoTime() - lastFrameTime) / 1_000_000_000.0;
            lastFrameTime = System.nanoTime();
            
            onUpdate(delta);
            renderer.clear();
            onRender(renderer);
            renderer.display();

            try {
                Thread.sleep(1000 / this.renderer.getFPS());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

