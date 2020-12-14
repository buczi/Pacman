package util;

public enum Direction {
    Up,
    Down,
    Right,
    Left;

    public static Direction opposite(Direction direction) {
        switch (direction) {
            case Up:
                return Down;

            case Down:
                return Up;

            case Right:
                return Left;

            case Left:
                return Right;
        }
        return Up;
    }

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
        return new Pair<>();
    }
}
