// Make the immutable class final (So that we can't extend mutable subclasses from it)
public final class ImmutableTwoDVector {
    private final Float x, y;
    //By boxing the values we make them immutable, but with the additional final keyword, we make sure that we cant change them from the class either

    private static final ImmutableTwoDVector NULL_VECTOR = new ImmutableTwoDVector(0,0);

    public ImmutableTwoDVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Since Float is immutable in itself, we can return the actual reference instead of copying.
    // If it was a mutable field, we would need to copy it, and return the copy.
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getMagnitude(){
        return (float) Math.sqrt(x*x + y*y);
    }

    // Make the normalize and add methods return new instances instead of changing the original.

    public ImmutableTwoDVector normalize(){
        float length = this.getMagnitude();
        if(length != 0){
            return (new ImmutableTwoDVector(x/ length, y/length));
        }
        else{
            return NULL_VECTOR;
        }
    }

    public ImmutableTwoDVector add(TwoDVector other){
        return new ImmutableTwoDVector(x+other.getX(), y+other.getY());
    }

    public float dot(TwoDVector other){
        return x*other.getX() + y* other.getY();
    }

    /*
    C)
    public void add(Vector2d v):
    This method changes the instance (since it does not return anything)
    So, this can only be a member of a mutable class.

    public Vector2D add(Vector2d v):
    This method return a potentially new object, thus we can use it in both implementations

    public Vector2D add(Vector2D v1, Vector2D v2):
    Similarly, this method can be a member of both the immutable and mutable classes, but we could make it final, since it does not use the state
    (As suggested in the last point.)

    D)
    We convey immutability of the class by
    - naming the class immutable
    - not providing setter methods
    - making the fields private final
     */
}
