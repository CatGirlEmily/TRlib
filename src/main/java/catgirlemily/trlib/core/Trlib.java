package catgirlemily.trlib.core;

import catgirlemily.trlib.TREngine;
import catgirlemily.trlib.type.KeyCode;

import com.sun.jna.platform.win32.User32;

import java.util.HashSet;
import java.util.Set;

/**
 * Trlib - The core engine class.
 * Manages the high-precision game loop, Delta Time calculations, 
 * and low-level Windows API input handling via JNA.
 */
public abstract class Trlib {
    private final TREngine renderer; // Your custom character-based rendering engine
    private boolean running = true;  // Main loop control flag
    
    /** * Stores keys that were pressed in the PREVIOUS frame. 
     * Essential for the "onKeyPress" logic to distinguish between holding a key 
     * and a single initial strike.
     */
    private final Set<Integer> lastFrameKeys = new HashSet<>();

    /**
     * Initializes the engine.
     * @param width Terminal width in characters.
     * @param height Terminal height in characters.
     * @param fps Target frames per second.
     */
    public Trlib(int width, int height, int fps) {
        this.renderer = new TREngine(width, height, fps);
        
        // Shutdown Hook: Runs when the game is closed (even via Ctrl+C or crashing)
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            stop();
            // ANSI Sequence: Restore the cursor so the user's terminal isn't "broken" after exiting
            System.out.print("\033[?25h"); 
            System.out.flush();
            System.out.println("\n[Trlib] Engine stopped safely.");
        }));
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Abstract Hooks \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Main Logic Hook. Called every frame.
     * @param renderer Access to the rendering engine.
     * @param delta Time elapsed since the last frame in seconds (use for frame-independent movement).
     */
    public abstract void onUpdate(TREngine renderer, double delta);

    /** * Main Render Hook. Called after onUpdate.
     * @param renderer Object used to draw points, rectangles, and sprites.
     */
    public abstract void onRender(TREngine renderer);
    
    /** * Single Strike Event. Triggered once when a key is first pressed.
     * @param keyCode The Windows Virtual Key code (e.g., 0x20 for Space).
     */
    public abstract void onKeyPress(int keyCode);

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
            // Calculate how many seconds have passed since the last render
            double delta = (currentTime - lastFrameTime) / 1_000_000_000.0;
            lastFrameTime = currentTime;
            
            // 1. Process keyboard state via WinAPI
            processInput();

            // 2. Execute game logic and physics
            onUpdate(renderer, delta);

            // 3. Clear the buffer, draw new objects, and flush to terminal
            renderer.clear();
            onRender(renderer);
            renderer.display();

            // 4. Frame Rate Control (FPS Limiter)
            try {
                long sleepTime = 1000 / renderer.getFPS();
                // Math.max prevents negative sleep times during heavy CPU lag
                Thread.sleep(Math.max(sleepTime, 1));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    /** Stops the game loop and cleans up terminal resources. */
    public void stop() {
        this.running = false;
        if (renderer != null) renderer.stop();
    }

/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////// Input Handling \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Scans the state of keys using GetAsyncKeyState.
     * Compares current state with the previous frame to trigger onKeyPress events.
     */
    private void processInput() {
        // Iterate through standard Virtual Key codes (from Backspace to Scroll Lock)
        for (int vk = 0x08; vk <= 0x91; vk++) {
            boolean isDown = isKeyPressed(vk);
            
            // "Just Pressed" Logic: key is down NOW, but was NOT down BEFORE
            if (isDown && !lastFrameKeys.contains(vk)) {
                onKeyPress(vk);
                lastFrameKeys.add(vk);
            } 
            // "Released" Logic: remove from the set once the user lets go
            else if (!isDown && lastFrameKeys.contains(vk)) {
                lastFrameKeys.remove(vk);
            }
        }
    }


    /**
     * Polling: Direct query to Windows regarding the physical state of a key.
     * @param vKey Virtual Key code.
     * @return true if the key is currently being held down.
     */
    public boolean isKeyPressed(int vKey) {
        // 0x8000 is a bitmask checking the high-order bit (current physical state)
        short state = User32.INSTANCE.GetAsyncKeyState(vKey);
        return (state & 0x8000) != 0;
    }

    public boolean isKeyPressed(KeyCode key) {
        return isKeyPressed(key.getCode());
    }




/////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////// Getters \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
/////////////////////////////////////////////////////////////////////////////////////////////

    public TREngine getRenderer() { return renderer; }

}