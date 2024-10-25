package entity;

import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.io.IOException;

import assets.Asset;
import assets.Texture;
import main.Input;
import math.Vector2;
import renderer.Renderer;
import renderer.UserInterface;

public class CraftingTable implements Entity {

	public Scene HierarchyScene = null;
	
	Vector2 Position = new Vector2();
	Player PlayerEntity = null;
	
	Texture CraftingTableTexture = null;
	
	@Override
	public void OnInitialize(Scene hierarchyScene) {
		this.HierarchyScene = hierarchyScene;
		
		this.Position = new Vector2(28 * Renderer.TILE_SIZE, 62 * Renderer.TILE_SIZE);
		this.PlayerEntity = (Player)hierarchyScene.GetEntityByName("Player");
		
		try {
			this.CraftingTableTexture = Asset.Load("/tiles/craftingtable.png").<Texture>As();
		} catch (IOException | FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void OnUpdate(double timeStep) {
		Vector2 centerPositionObject = new Vector2(this.Position.X + Renderer.TILE_SIZE / 2, this.Position.Y + Renderer.TILE_SIZE / 2);
		Vector2 playerWorldPos = this.PlayerEntity.GetWorldPosition();
		double distance = Vector2.Distance(centerPositionObject, this.PlayerEntity.GetWorldPosition());
		
		if(distance < 12.0) {

			centerPositionObject = new Vector2(centerPositionObject.X - 112, centerPositionObject.Y - 54);
			playerWorldPos = this.PlayerEntity.Position;
			UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 24, "Not enough materials");

			// Dont delete!!!!!!!!!!!
//			centerPositionObject = new Vector2(centerPositionObject.X - 108, centerPositionObject.Y - 60);
//			playerWorldPos = this.PlayerEntity.Position;
//			UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 32, "Craft Shovel");
//
//			centerPositionObject = new Vector2(centerPositionObject.X + 56, centerPositionObject.Y + 24);
//			playerWorldPos = this.PlayerEntity.Position;
//			UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 24, "(PRESS C)");

		}
		
	}

	@Override
	public void OnPhysicsUpdate(double timeStep) {
		
	}

	@Override
	public void OnDraw() {
		Renderer.Submit(this.Position, new Vector2(Renderer.TILE_SIZE), this.CraftingTableTexture);
	}

	@Override
	public String GetEntityName() {
		return "CraftingTable";
	}

}
