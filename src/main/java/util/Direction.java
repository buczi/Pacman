package util;

public enum Direction {
    Up,
    Down,
    Right,
    Left,
    None;

    public static Pair<Integer,Integer>getNext(Direction direction){
        switch (direction){
            case Up:
                return new Pair<>(0,-1);
            case Down:
                return new Pair<>(0,1);
            case Right:
                return new Pair<>(1,0);
            case Left:
                return new Pair<>(-1,0);
        }
        return new Pair<>(0,0);
    }

    public static Direction getDirection(int x, int y){
        if(x == 0 && y == -1)
            return Up;
        else if (x == 0 && y == 1)
            return Down;
        else if ((x == -1 || x > 10) && y == 0)
            return Left;
        else if((x == 1 || x < -10) && y == 0)
            return Right;
        return None;
    }
}
