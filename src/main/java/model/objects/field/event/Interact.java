package model.objects.field.event;

import gui.events.Destruction;
import model.objects.pacman.Pacman;

@SuppressWarnings("unused")
public interface Interact {
    /** Called when Pacman enter the field
     */
    void interact(Pacman pacman, Destruction destruction);
    void delete();
}
