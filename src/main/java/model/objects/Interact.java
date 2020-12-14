package model.objects;

import model.objects.pacman.Pacman;

public interface Interact {
    void interact(Pacman pacman);
    void delete();
}
