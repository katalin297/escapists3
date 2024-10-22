package math;

public class Vector4 {
    public int X;
    public int Y;
    public int Z;
    public int W;

    // Default constructor
    public Vector4() {
        this.X = 0;
        this.Y = 0;
        this.Z = 0;
        this.W = 0;
    }

    // Constructor with a single integer, initializing all components to the same value
    public Vector4(int integer) {
        this.X = integer;
        this.Y = integer;
        this.Z = integer;
        this.W = integer;
    }

    // Constructor with four integers
    public Vector4(int x, int y, int z, int w) {
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.W = w;
    }

    // Vector addition
    public Vector4 plus(Vector4 other) {
        return new Vector4(this.X + other.X, this.Y + other.Y, this.Z + other.Z, this.W + other.W);
    }

    // Vector subtraction
    public Vector4 minus(Vector4 other) {
        return new Vector4(this.X - other.X, this.Y - other.Y, this.Z - other.Z, this.W - other.W);
    }

    // Scalar multiplication
    public Vector4 times(int scalar) {
        return new Vector4(this.X * scalar, this.Y * scalar, this.Z * scalar, this.W * scalar);
    }

    // Element-wise multiplication with another vector
    public Vector4 times(Vector4 other) {
        return new Vector4(this.X * other.X, this.Y * other.Y, this.Z * other.Z, this.W * other.W);
    }

    // Scalar division
    public Vector4 div(int scalar) {
        return new Vector4(this.X / scalar, this.Y / scalar, this.Z / scalar, this.W / scalar);
    }

    // Element-wise division with another vector
    public Vector4 div(Vector4 other) {
        return new Vector4(this.X / other.X, this.Y / other.Y, this.Z / other.Z, this.W / other.W);
    }

    // Overriding the toString method for better readability
    @Override
    public String toString() {
        return "(" + this.X + ", " + this.Y + ", " + this.Z + ", " + this.W + ")";
    }
}