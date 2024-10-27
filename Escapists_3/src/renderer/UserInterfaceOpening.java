package renderer;

import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;

import assets.Asset;
import assets.Texture;
import main.Input;
import math.Vector2;

public class UserInterfaceOpening {
	
	static Texture PrologueTexture = null;
	static Texture EpilogueTexture = null;
	static public boolean StartGame = false;
	static public boolean FinishedGame = false;
	
	public static void OnInitialize() { 
		try {
			PrologueTexture = Asset.Load("/ui/prologue.png").<Texture>As();
			EpilogueTexture = Asset.Load("/ui/epilogue.png").<Texture>As();
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
	
	static boolean PressedOnce = false;
	
	public static void OnUpdate() {
		if(Input.IsKeyPressed(KeyEvent.VK_ENTER)) {
			
			if(!PressedOnce) {
				StartGame = true;
			}
			PressedOnce = true;
		}
		else {
			PressedOnce = false;
		}
	}
	
	public static void OnDraw(Graphics2D graphicsAPI) {
		if(!StartGame) {
			graphicsAPI.drawImage(
					PrologueTexture.InternalImage,
					0, 0,
					1600, 900,
					null
			);
		} else if(FinishedGame) {
			graphicsAPI.drawImage(
					EpilogueTexture.InternalImage,
					0, 0,
					1600, 900,
					null
			);
		}
		
	}
	
	public static void SetGameStateAsFinished() {
		FinishedGame = true;
	}
	
}
