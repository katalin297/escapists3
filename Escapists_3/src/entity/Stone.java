package entity;

import java.awt.event.KeyEvent;

import main.Input;
import math.Vector2;
import renderer.Renderer;
import renderer.UserInterface;

public class Stone implements Entity {

	public Scene HierarchyScene = null;
	
	Vector2 Position = new Vector2();
	double Radius = 0.0;
	
	Player PlayerEntity = null;
	
	public Stone(Vector2 position, double radius) {
		this.Position = position;
		this.Radius = radius;
	}
	
	@Override
	public void OnInitialize(Scene hierarchyScene) {
		this.HierarchyScene = hierarchyScene;
		
		//this.Position = new Vector2(28 * Renderer.TILE_SIZE, 62 * Renderer.TILE_SIZE);
		this.PlayerEntity = (Player)hierarchyScene.GetEntityByName("Player");
		
	}
	
	public boolean PressedOnce = true;

	@Override
	public void OnUpdate(double timeStep) {
		Vector2 centerPositionObject = new Vector2(this.Position.X, this.Position.Y);
		Vector2 playerWorldPos = this.PlayerEntity.GetWorldPosition();
		double distance = Vector2.Distance(centerPositionObject, playerWorldPos);
		
		if(distance < this.Radius) {

			if(this.PlayerEntity.Inventory.HasItem("pickaxe")) {
				centerPositionObject = new Vector2(centerPositionObject.X, centerPositionObject.Y);
				playerWorldPos = this.PlayerEntity.Position;
				UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 32, "GET STONE");
				
				centerPositionObject = new Vector2(centerPositionObject.X + 20, centerPositionObject.Y + 20);
				UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 24, "(PRESS T)");
				
				if(Input.IsKeyPressed(KeyEvent.VK_T)) {
					
					if(!this.PressedOnce) {
						
						this.PlayerEntity.AddItem("stone");
						
					}
					this.PressedOnce = true;
				}
				else {
					this.PressedOnce = false;
				}
				
			}
			else {
				centerPositionObject = new Vector2(centerPositionObject.X - 56, centerPositionObject.Y);
				playerWorldPos = this.PlayerEntity.Position;
				UserInterface.DrawText(centerPositionObject.minus(playerWorldPos), 28, "YOU CANT GET STONE");				
			}

			// Dont delete!!!!!!!!!!!
//			centerPositionObject = new Vector2(centerPositionObject.X - 76, centerPositionObject.Y - 32);
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
		
	}

	@Override
	public String GetEntityName() {
		return "Stone";
	}

}
