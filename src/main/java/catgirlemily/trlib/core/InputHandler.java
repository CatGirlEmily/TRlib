package catgirlemily.trlib.core;

import com.sun.jna.platform.win32.User32;

/**
 * InputHandler - Direct Windows API key polling.
 * Allows for flawless multi-key detection.
 */
public class InputHandler {
    
    /**
     * Sprawdza czy klawisz jest obecnie wciśnięty.
     * @param vKey Kod wirtualny klawisza (np. 0x44 dla 'D', 0x20 dla Spacji).
     */
    public static boolean isKeyDown(int vKey) {
        // GetAsyncKeyState zwraca wartość, gdzie najwyższy bit jest ustawiony,
        // jeśli klawisz jest aktualnie trzymany.
        short state = User32.INSTANCE.GetAsyncKeyState(vKey);
        return (state & 0x8000) != 0;
    }

    // Pomocnicze kody klawiszy (Virtual Key Codes)
    public static final int KEY_W = 0x57;
    public static final int KEY_A = 0x41;
    public static final int KEY_S = 0x53;
    public static final int KEY_D = 0x44;
    public static final int KEY_SPACE = 0x20;
    public static final int KEY_SHIFT = 0x10;
    public static final int KEY_ESCAPE = 0x1B;
}