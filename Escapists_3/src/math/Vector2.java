package math;

public class Vector2 {
    public int X;
    public int Y;

    // Default constructor
    public Vector2() {
        this.X = 0;
        this.Y = 0;
    }

    // Constructor with a single integer, initializing both X and Y to the same value
    public Vector2(int integer) {
        this.X = integer;
        this.Y = integer;
    }

    // Constructor with two integers (X and Y)
    public Vector2(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    // Vector addition
    public Vector2 plus(Vector2 other) {
        return new Vector2(this.X + other.X, this.Y + other.Y);
    }

    // Vector subtraction
    public Vector2 minus(Vector2 other) {
        return new Vector2(this.X - other.X, this.Y - other.Y);
    }

    // Scalar multiplication
    public Vector2 times(int scalar) {
        return new Vector2(this.X * scalar, this.Y * scalar);
    }

    // Element-wise multiplication with another vector
    public Vector2 times(Vector2 other) {
        return new Vector2(this.X * other.X, this.Y * other.Y);
    }

    // Scalar division
    public Vector2 div(int scalar) {
        return new Vector2(this.X / scalar, this.Y / scalar);
    }

    // Element-wise division with another vector
    public Vector2 div(Vector2 other) {
        return new Vector2(this.X / other.X, this.Y / other.Y);
    }
    
    public static double Distance(Vector2 other1, Vector2 other2) {
    	return Math.sqrt( (double)Math.abs(other1.X - other2.X) + (double)Math.abs(other1.Y - other2.Y) );
    }

    // Overriding the toString method for better readability
    @Override
    public String toString() {
        return "(" + this.X + ", " + this.Y + ")";
    }
}
