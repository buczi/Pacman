package model.objects;

public enum FieldType {
    Empty('-'),
    Wall('W'),
    Fruit('f'),
    BigFruit('u'),
    PacMan('M'),
    Blinky('B'),
    Pinky('P'),
    Inky('I'),
    Clyde('C');

    private char type;

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
