package ghosttest;

import file.FileHandler;
import file.Settings;
import gui.events.UI;
import model.Map;
import model.objects.field.Field;
import model.objects.field.FieldType;
import model.objects.ghosts.Clyde;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.Direction;
import util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GhostTest {

    private Clyde ghost;
    private final String filePath =new File("").getAbsolutePath() +
            "/src/main/resources/maps/3.txt";
    @Mock
    UI destructionMock;
    @Mock
    Settings settingsMock;

    @Before
    public void prepare(){
        MockitoAnnotations.openMocks(this);
        ghost = new Clyde(new Field(1,1, FieldType.Clyde));
    }

    @Test
    @RepeatedTest(1)
    public void testRandomGenerator(){
        int j = 0;
        Pair<Integer,Integer> testDirection = new Pair<>(1,0);
        for(int i = 0; i<20; i++){
            Pair<Integer,Integer>point = ghost.getRandomDirection(testDirection);
            System.out.println(point);
            if(point.equals(testDirection))
                j++;
        }
        System.out.println(j);
    }

    @Test
    public void testRoadGenerator() {
        Map map = new Map.Builder()
                .withMapFile(FileHandler.fileLoader(filePath))
                .onFrame(destructionMock)
                .withSettings(settingsMock)
                .build();

        for(int i = 0; i < 5 ; i++){
            System.out.println("LAP " +(i+1));
            List<Field> road = ghost.getRoute(map.getMap(),map.getPacman(), Direction.None);
            for(Field field : road)
                System.out.println(field.getX() + " " + field.getY());
        }

    }
}
