package entity;

import java.util.ArrayList;

import main.Camera;
import math.Vector2;
import physics.PhysicsSystem;

public class Scene {
	
	Camera SceneCamera;
	ArrayList<Entity> Entities;
	
	public Scene() {
		this.Entities = new ArrayList<Entity>();
		this.SceneCamera = new Camera();
	}
	
	public void OnInitalize() {
		for(int i = 0; i < this.Entities.size(); i++) {
			Entity currentEntity = this.Entities.get(i);
			currentEntity.OnInitialize(this);
		}
	}
	
	public void OnUpdate(double timeStep) {
		for(int i = 0; i < this.Entities.size(); i++) {
			Entity currentEntity = this.Entities.get(i);
			currentEntity.OnUpdate(timeStep);
		}
	}
	
	public void OnPhysicsUpdate(double timeStep) {
		PhysicsSystem.ResetDynamicObjects();
		for(int i = 0; i < this.Entities.size(); i++) {
			Entity currentEntity = this.Entities.get(i);
			currentEntity.OnPhysicsUpdate(timeStep);
		}
	}
	
	public void OnDraw() {
		for(int i = 0; i < this.Entities.size(); i++) {
			Entity currentEntity = this.Entities.get(i);
			currentEntity.OnDraw();
		}
	}
	
	public void AddEntity(Entity ent) {
		this.Entities.add(ent);
	}
	
	public Camera GetPrimaryCamera() { return SceneCamera; }
	
	public void SetPrimaryCameraPosition(Vector2 position) { 
		SceneCamera.UpdateCameraPosition(position);
	}
	
}
