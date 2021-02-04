package model.objects.field.event;

import gui.events.Destruction;
import model.objects.field.Field;

public interface Movement {
    void move(Field field, Destruction destruction);
}
