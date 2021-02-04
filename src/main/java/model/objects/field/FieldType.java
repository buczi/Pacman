package model.objects.field;

/**
 * Type of each field possible in the game
 */
@SuppressWarnings("SpellCheckingInspection")
public enum FieldType {
    Empty('-'),
    EmptyWall('|'),
    Wall('W'),
    Fruit('f'),
    BigFruit('u'),
    PacMan('M'),
    Blinky('B'),
    Pinky('P'),
    Inky('I'),
    Clyde('C');

    private final char type;

    FieldType(char type){
        this.type = type;
    }

    public char getType() {
        return type;
    }

    public static FieldType getType(char c){
        for(FieldType type : FieldType.values())
            if(type.getType() == c)
                return type;

        return FieldType.Empty;
    }
}
