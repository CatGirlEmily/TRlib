package catgirlemily.trlib.drawable;

import catgirlemily.trlib.type.Color;

public class TypeWriter extends Text {
	private final String fullText;
	private double elapsed = 0;
	private double charTime; // sekundy na jeden znak

	public TypeWriter(int x, int y, String text, Color color, double speed) {
		super(x, y, "", color);
		this.fullText = text;
		this.charTime = speed;
	}
	
	public void update(double delta) {
		elapsed += delta;
		int charsToShow = (int)(elapsed / charTime);
		
		if (charsToShow <= fullText.length()) {
			this.text = fullText.substring(0, charsToShow);
		} else {
			this.text = fullText;
		}
	}
}
