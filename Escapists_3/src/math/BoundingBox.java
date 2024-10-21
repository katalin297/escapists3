package math;

public class BoundingBox {
	
	// Example of bounding box:
	// A--B
	// |  |
	// C--D
	
	public Vector2 UpLeft = new Vector2();    // A
	public Vector2 UpRight = new Vector2();   // B
	public Vector2 DownLeft = new Vector2();  // C
	public Vector2 DownRight = new Vector2(); // D
	
	public BoundingBox(Vector2 position, Vector2 scale) {
		UpLeft =    new Vector2(position.X          , position.Y          );
		UpRight =   new Vector2(position.X + scale.X, position.Y          );
		DownLeft =  new Vector2(position.X          , position.Y + scale.Y);
		DownRight = new Vector2(position.X + scale.X, position.Y + scale.Y);
	}
	
	public boolean IsInBoudingBox(Vector2 point) {
		int xMin = UpLeft.X;
		int xMax = DownRight.X;
		int yMin = UpLeft.Y;
		int yMax = DownRight.Y;

	    // Check if the point is within the bounds
	    return (point.X >= xMin && point.X <= xMax) && (point.Y >= yMin && point.Y <= yMax);

	}
	
	public boolean IsInBoundingBox(BoundingBox bb) {
		return IsInBoudingBox(bb.UpLeft) ||
			   IsInBoudingBox(bb.UpRight) || 
			   IsInBoudingBox(bb.DownLeft) || 
			   IsInBoudingBox(bb.DownRight); 
	}
}
