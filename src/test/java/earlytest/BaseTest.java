package earlytest;

import file.logger.Logger;
import model.*;
import file.Settings;
import model.objects.pacman.PowerUp;
import org.junit.Test;
import util.Direction;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BaseTest {
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
        Map map = new Map(fileContent,settings);
        map.showMap();
        map.getPacman().addPowerUp(new PowerUp(1,true,10));
        map.movePacman(Direction.Left);
        //map.moveGhost(map.getGhosts().get(0),Direction.Right);
        map.showMap();
        //map.moveGhost(map.getGhosts().get(0),Direction.Right);
        map.movePacman(Direction.Left);
        map.showMap();


    }
}
