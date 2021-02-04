package earlytest;

import body.event.Restart;
import com.google.gson.Gson;
import file.Settings;
import file.logger.Logger;
import gui.Frame;
import model.Map;
import model.objects.pacman.PowerUp;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.Direction;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BaseTest {
    @Mock
    Restart restartMock;

    @Before
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void baseTest(){
        Settings settings = new Settings(0,0,0,0,0,
                0,0,0,0,0,false,0);
        String[] fileContent;
        List<String> data = new LinkedList<>();
        try{
            File file = new File(new File("").getAbsolutePath() +
                    "/src/main/resources/maps/1.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
                data.add(scanner.nextLine());
            scanner.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        fileContent = data.toArray(new String[0]);
        Logger.log("Start\n");
        Frame frame = new Frame(restartMock);
        Map map = null;
        try{
            map = new Map.Builder()
                    .withMapFile(fileContent)
                    .onFrame(frame)
                    .withSettings(settings)
                    .build();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        frame.init(map);
        map.showMap();
        map.getPacman().addPowerUp(new PowerUp(1,true,10));
        map.movePacman(Direction.Left);
        //map.moveGhost(map.getGhosts().get(0),Direction.Right);
        map.showMap();
        //map.moveGhost(map.getGhosts().get(0),Direction.Right);
        map.movePacman(Direction.Left);
        map.showMap();
    }

    @Test
    public void jsonTest() throws IOException, ParseException {
        FileReader json = new FileReader(new File("")
                .getAbsolutePath() + "/src/test/resources/test.json");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(json);
        Settings settings = new Gson().fromJson(obj.toString(),Settings.class);
        System.out.println(settings.toString());


    }
}
