public class TwoDVector {
    private float x, y;

    public TwoDVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getMagnitude(){
        return (float) Math.sqrt(x*x + y*y);
    }

    public void normalize(){
        float length = this.getMagnitude();
        if(length != 0){
            x = x/ length;
            y = y/ length;
        }
    }

    public void add(TwoDVector other){
        x += other.getX();
        y += other.getY();
    }

    public float dot(TwoDVector other){
        return x*other.getX() + y* other.getY();
    }
}
