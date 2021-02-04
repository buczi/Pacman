package pacmantest;

import gui.events.Destruction;
import gui.events.Update;
import model.objects.field.Field;
import model.objects.field.FieldType;
import model.objects.fruits.*;
import model.objects.ghosts.Ghost;
import model.objects.pacman.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.Pair;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PacmanTest {
    private Ghost ghostField;
    private SmallFruit fruitField;
    private BigFruit bigFruitField;
    private Field emptyField;
    private final Pair<Integer,Integer> defPosition = new Pair<>(1,0);
    private Pacman pacman;
    @Mock
    private Destruction destructionMock;

    @Mock
    private Update updateMock;

    @Before
    public void initFields(){
        MockitoAnnotations.openMocks(this);
        ghostField = new Ghost(new Field(defPosition.getX(),defPosition.getY(), FieldType.Pinky));
        fruitField = new SmallFruit(new Field(defPosition.getX(),defPosition.getY(), FieldType.Fruit),10);
        bigFruitField = new BigFruit(new Field(defPosition.getX(),defPosition.getY(), FieldType.BigFruit),5,
                new PowerUp(1,true,1f));
        emptyField = new Field(defPosition.getX(),defPosition.getY(), FieldType.Empty);
        pacman = new Pacman(new Field(0,0, FieldType.PacMan),1,1,updateMock);
    }

    @Test
    public void moveToEmptyTest(){
        Pacman pacman = new Pacman(this.pacman);
        pacman.move(emptyField,destructionMock);
        assertEquals(defPosition, new Pair<>(pacman.getX(),pacman.getY()));
    }

    @Test
    public void testFruitInteractions(){
        Pacman pacman = new Pacman(this.pacman);
        pacman.move(bigFruitField,destructionMock);
        int points = pacman.getPoints().getPoints();
        pacman.move(fruitField,destructionMock);
        assertAll(
                () -> assertNotEquals(new PowerUp(), pacman.getPower()),
                () -> assertNotEquals(points,pacman.getPoints().getPoints())
        );
    }


    @Test
    public void testGhostInteraction(){
        Pacman pacman = new Pacman(this.pacman);
        int livesBef = pacman.getLives();
        pacman.move(ghostField,destructionMock);
        int livesAft = pacman.getLives();
        pacman.move(bigFruitField,destructionMock);
        pacman.move(ghostField,destructionMock);
        assertAll(
                () -> assertNotEquals(livesBef, livesAft),
                () -> assertEquals(livesAft, pacman.getLives())
        );
    }

}
