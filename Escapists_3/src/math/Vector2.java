package math;

public class Vector2 {
	public int X;
	public int Y;
	
	public Vector2() {
		this.X = 0;
		this.Y = 0;
	}
	
	public Vector2(int integer) {
		this.X = integer;
		this.Y = integer;
	}
	
	public Vector2(int x, int y) {
		this.X = x;
		this.Y = y;
	}
	
	public Vector2 plus(Vector2 other) {
		return new Vector2(this.X + other.X, this.Y + other.Y);
	}
	
	public Vector2 minus(Vector2 other) {
		return new Vector2(this.X - other.X, this.Y - other.Y);
	}
	
	public Vector2 times(int otherInt) {
		return new Vector2(this.X * otherInt, this.Y * otherInt);
	}
	
	public Vector2 times(Vector2 other) {
		return new Vector2(this.X * other.X, this.Y * other.Y);
	}
	
	public Vector2 div(int otherInt) {
		return new Vector2(this.X / otherInt, this.Y / otherInt);
	}
	
	public Vector2 div(Vector2 other) {
		return new Vector2(this.X / other.X, this.Y / other.Y);
	}
	
	public String toString() {
		return "(" + this.X + ", " + this.Y + ")";
	}
	
}
