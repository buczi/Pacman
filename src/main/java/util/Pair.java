package util;

@SuppressWarnings("ALL")
public class Pair<T, S> {
    private T x;
    private S y;

    public Pair(T x, S y) {
        this.x = x;
        this.y = y;
    }

    public Pair() {
        this.x = null;
        this.y = null;
    }

    public T getX() {
        return x;
    }

    public S getY() {
        return y;
    }

    public void setX(T x) {
        this.x = x;
    }

    public void setY(S y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o){
        Pair<T,S>pair = (Pair<T,S>)o;
        return pair.getX() == this.x && pair.getY() == this.y;
    }
    @Override
    public String toString() {
        return "Value x:" + this.x.toString() + "\tValue y:" + this.y.toString();
    }
}

