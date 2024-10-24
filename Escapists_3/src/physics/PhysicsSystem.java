package physics;

import java.util.ArrayList;

import math.BoundingBox;
import math.Vector2;

public class PhysicsSystem {
	
	// Static objects are a list of bounding boxes that are set up from the initalization of the app
	// and remain unchanged throw the whole lifetime of the game (for example tiles - walls, fences, etc.)
	static ArrayList<BoundingBox> StaticObjects = new ArrayList<BoundingBox>();
	
	// Dynamic objects area list of bounding boxes that need to be updated everyframe in order to collide
	// correctly with the player (for example NPCs, trees - which need to be destroyed, etc.)
	static ArrayList<BoundingBox> DynamicObjects = new ArrayList<BoundingBox>();
	
	public static void Initialize() {
		StaticObjects.clear();
		DynamicObjects.clear();
	}
	
	public static void AddStaticObject(Vector2 position, Vector2 scale) {
		StaticObjects.add(new BoundingBox(position, scale));
	}
	
	public static void AddDynamicObject(Vector2 position, Vector2 scale) {
		DynamicObjects.add(new BoundingBox(position, scale));
	}
	
	public static void ResetDynamicObjects() {
		DynamicObjects.clear();
	}
	
	public static boolean IsInCollision(BoundingBox object) {
		
		
		// Check all the dynamic objects firstly
		for(int i = 0; i < DynamicObjects.size(); i++) {
			if(DynamicObjects.get(i).IsInBoundingBox(object)) { return true; }
		}
		
		
		// Then if nothing collided, then check all the static ones
		for(int i = 0; i < StaticObjects.size(); i++) {
			if(StaticObjects.get(i).IsInBoundingBox(object)) { return true; }
		}
		
		// If nothing collided at all, then return false
		return false;
	}
}
