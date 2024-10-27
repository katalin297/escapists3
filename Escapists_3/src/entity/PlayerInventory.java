package entity;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import assets.Asset;
import assets.Texture;
import math.Vector2;
import math.Vector4;
import renderer.UserInterface;

public class PlayerInventory {
	
	ArrayList<String> Items = new ArrayList<String>();
	HashMap<String, Texture> ItemTextures = new HashMap<>();
	
	public PlayerInventory() {
		try {
			this.ItemTextures.put("wood",    Asset.Load("/items/wood.png").<Texture>As());
			this.ItemTextures.put("pickaxe", Asset.Load("/items/pickaxe.png").<Texture>As());
			this.ItemTextures.put("stone",   Asset.Load("/items/stone.png").<Texture>As());
			this.ItemTextures.put("shovel",  Asset.Load("/items/shovel.png").<Texture>As());
			
			
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}
	
	public boolean HasItem(String itemName) {
		for(int i = 0; i < this.Items.size(); i++) {
			if(this.Items.get(i) == itemName) { 
				return true;
			}
		}
		return false;
	}
	
	public boolean HasAnyItems() {
		return this.Items.size() > 0; 
	}
	
	public void AddItemName(String itemName) {
		for(int i = 0; i < this.Items.size(); i++) {
			if(this.Items.get(i) == itemName) { 
				return;
			}
		}
		
		this.Items.add(itemName);
	}
	
	public void RemoveItemName(String itemName) {
		for(int i = 0; i < this.Items.size(); i++) {
			if(this.Items.get(i) == itemName) { 
				this.Items.remove(i);
			}
		}
	}
	
	public void Clear() {
		this.Items = new ArrayList<String>();
	}
	
	public void OnDraw() {
		
		
		for(int i = 0; i < 3; i++) {
			UserInterface.DrawRectangle(
			   new Vector2(1400 - i * (140 + 20), 700), new Vector2(140, 140), new Vector4(24, 12, 0, 250), 32,
			   5, new Vector4(120, 60, 0, 255), 22
			);
			
			if(this.Items.size() >= (3-i)) {
				UserInterface.DrawImage(new Vector2(1420 - i * (140 + 20), 720), new Vector2(100, 100), this.ItemTextures.get(this.Items.get((2-i))));
			}
		}
		   
	}
}
