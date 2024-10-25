package entity;

import java.awt.FontFormatException;
import java.io.IOException;

import assets.Asset;
import assets.Texture;
import math.Vector2;
import renderer.Renderer;

public class BasketBallField implements Entity {

	Vector2 Position;
	Texture BasketBallFieldTexture = null;
	
	@Override
	public void OnInitialize(Scene hierarchyScene) {
		try {
			this.BasketBallFieldTexture = Asset.Load("/tiles/basketball.png").<Texture>As();
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		
		this.Position = new Vector2(60 * Renderer.TILE_SIZE, 55 * Renderer.TILE_SIZE);
	}

	@Override
	public void OnUpdate(double timeStep) {
		
	}

	@Override
	public void OnPhysicsUpdate(double timeStep) {
		
	}

	@Override
	public void OnDraw() {
		Renderer.Submit(this.Position, new Vector2(912, 640), this.BasketBallFieldTexture);
	}

	@Override
	public String GetEntityName() {
		return "BasketBallField";
	}

}
