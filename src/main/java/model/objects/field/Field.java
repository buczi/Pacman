package model.objects.field;

import gui.events.Destruction;
import model.objects.field.event.Interact;
import model.objects.pacman.Pacman;

public class Field implements Interact {
    private int x;
    private int y;
    private boolean immovable;
    FieldType type;

    public Field(int x, int y, FieldType type){
        this.x = x;
        this.y = y;
        this.type = type;
        this.immovable = type == FieldType.Empty || type == FieldType.Wall
                || type == FieldType.Fruit || type == FieldType.BigFruit;
    }

    public Field(Field field){
        this.x = field.x;
        this.y = field.y;
        this.type = field.type;
        this.immovable = field.immovable;
    }

    public Field() {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isImmovable() {
        return immovable;
    }

    public FieldType getType() {
        return type;
    }

    public void changeToEmpty(){
        this.type = FieldType.Empty;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void interact(Pacman pacman, Destruction destruction) {
    }

    @Override
    public void delete() {

    }
}
