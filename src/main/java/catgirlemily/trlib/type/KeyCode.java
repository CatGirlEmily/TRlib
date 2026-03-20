package catgirlemily.trlib.type;

/**
 * Maps Windows Virtual-Key codes to readable enum constants.
 * Reference: https://learn.microsoft.com/en-us/windows/win32/inputdev/virtual-key-codes
 */
public enum KeyCode {
	// Letters
	A(0x41), B(0x42), C(0x43), D(0x44), E(0x45), F(0x46), G(0x47), H(0x48), 
	I(0x49), J(0x4A), K(0x4B), L(0x4C), M(0x4D), N(0x4E), O(0x4F), P(0x50), 
	Q(0x51), R(0x52), S(0x53), T(0x54), U(0x55), V(0x56), W(0x57), X(0x58), 
	Y(0x59), Z(0x5A),

	// Arrows
	LEFT(0x25), UP(0x26), RIGHT(0x27), DOWN(0x28),

	// Functional
	SPACE(0x20), ENTER(0x0D), ESCAPE(0x1B), SHIFT(0x10), 
	CONTROL(0x11), BACKSPACE(0x08), TAB(0x09),

	// Numbers (Top Row)
	N0(0x30), N1(0x31), N2(0x32), N3(0x33), N4(0x34), 
	N5(0x35), N6(0x36), N7(0x37), N8(0x38), N9(0x39),

	// Unknown/Placeholder
	UNKNOWN(-1);

	private final int keyCode;

	KeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public int getCode() {
		return keyCode;
	}

	/**
	 * Finds the matching Enum constant for a raw integer code.
	 */
	public static KeyCode fromCode(int code) {
		for (KeyCode key : values()) {
			if (key.keyCode == code) return key;
		}
		return UNKNOWN;
	}
}
