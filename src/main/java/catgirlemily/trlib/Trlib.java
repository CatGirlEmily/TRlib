package catgirlemily.trlib;

/**
 * Trlib - The main framework class.
 * Handles the game loop, delta time calculation, and lifecycle management.
 */
public abstract class Trlib {
    private final TREngine renderer;
    private boolean running = true;

    /**
     * Initializes the library and sets up a safety hook to restore terminal state on exit.
     */
    public Trlib(int width, int height, int fps) {
        this.renderer = new TREngine(width, height, fps);

        // Safety: ensure the cursor is restored even if the app crashes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            stop();
            System.out.println("\n[Trlib] Engine stopped safely.");
        }));
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Abstract Hooks \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Called once per frame to update game logic.
     * @param delta Time since last frame in seconds.
     */
    public abstract void onUpdate(double delta);

    /** 
     * Called once per frame to draw objects into the buffer.
     * @param renderer The active engine.
    */
    public abstract void onRender(TREngine renderer);

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Core Loop \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Starts the main game loop. This method is blocking.
     */
    public void start() {
        long lastFrameTime = System.nanoTime();

        while (running) {
            long currentTime = System.nanoTime();
            
            // Delta time in seconds (e.g. 0.016 for 60 FPS)
            double delta = (currentTime - lastFrameTime) / 1_000_000_000.0;
            lastFrameTime = currentTime;
            
            // 1. Logic Update
            onUpdate(delta);

            // 2. Rendering Pipeline
            renderer.clear();
            onRender(renderer);
            renderer.display();

            // 3. Frame Rate Control
            try {
                // Calculate sleep time to maintain target FPS
                long sleepTime = 1000 / renderer.getFPS();
                Thread.sleep(Math.max(sleepTime, 1));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    /**
     * Stops the game loop and cleans up terminal resources.
     */
    public void stop() {
        this.running = false;
        if (renderer != null) {
            renderer.stop();
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Getters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    public TREngine getRenderer() {
        return renderer;
    }
}