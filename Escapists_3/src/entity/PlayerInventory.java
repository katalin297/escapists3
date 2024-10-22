package entity;

import java.util.ArrayList;

import math.Vector2;
import math.Vector4;
import renderer.UserInterface;

public class PlayerInventory {
	
	ArrayList<String> Items = new ArrayList<String>();
	
	public PlayerInventory() {
		
	}
	
	public void OnDraw() {
		UserInterface.DrawRectangle(
		   new Vector2(1400, 700), new Vector2(140, 140), new Vector4(24, 12, 0, 250), 32,
		   5, new Vector4(120, 60, 0, 255), 22
		);
		
		UserInterface.DrawRectangle(
		   new Vector2(1400 - (140 + 20), 700), new Vector2(140, 140), new Vector4(24, 12, 0, 250), 32,
		   5, new Vector4(120, 60, 0, 255), 22
		);
		
		UserInterface.DrawRectangle(
		   new Vector2(1400 - 2 * (140 + 20), 700), new Vector2(140, 140), new Vector4(24, 12, 0, 250), 32,
		   5, new Vector4(120, 60, 0, 255), 22
		);
		   
	}
	
}
