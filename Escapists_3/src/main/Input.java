package main;

public class Input {
	
	static KeyHandler KeyEventHandler = new KeyHandler();
	
	public static boolean IsKeyPressed(int keyCode) {
		return KeyEventHandler.PressedKeys.contains(Integer.valueOf(keyCode));
	}
	
	public static boolean IsAnyKeyPressed(int keyCode) {
		return KeyEventHandler.PressedKeys.size() > 0;
	}
	
	public static KeyHandler GetInternalKeyHandler() { return KeyEventHandler; }
		
}
