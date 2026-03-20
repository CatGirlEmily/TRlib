package catgirlemily.trlib.core;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class WindowsAnsi {
    private interface Kernel32 extends Library {
        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class);
        Pointer GetStdHandle(int nStdHandle);
        boolean GetConsoleMode(Pointer hConsoleHandle, IntByReference lpMode);
        boolean SetConsoleMode(Pointer hConsoleHandle, int dwxMode);
    }

    public static void enable() {
        // windows exclusive
        if (!System.getProperty("os.name").toLowerCase().contains("win")) return;

        int STD_OUTPUT_HANDLE = -11;
        int ENABLE_VIRTUAL_TERMINAL_PROCESSING = 0x0004;

        Kernel32 k32 = Kernel32.INSTANCE;
        Pointer hConsole = k32.GetStdHandle(STD_OUTPUT_HANDLE);
        IntByReference lpMode = new IntByReference();

        if (k32.GetConsoleMode(hConsole, lpMode)) {
            k32.SetConsoleMode(hConsole, lpMode.getValue() | ENABLE_VIRTUAL_TERMINAL_PROCESSING);
        }
    }
}