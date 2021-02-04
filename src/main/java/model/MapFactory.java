package model;

import exception.CriticalException;
import exception.ExceptionType;
import file.Settings;
import gui.events.Update;
import model.objects.field.Field;
import model.objects.field.FieldType;
import model.objects.fruits.BigFruit;
import model.objects.fruits.SmallFruit;
import model.objects.ghosts.*;
import model.objects.pacman.Pacman;
import model.objects.pacman.PowerUp;
import util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class MapFactory
{
    /**
     * Load map from array of strings
     * @param fileMap content of map
     * @param settings settings used to regulate map objects
     * @return parsed map and list of ghosts
     */
    public static Pair<Field[][], List<Ghost>> createMap(String[] fileMap, Settings settings, Update update){
        int y = fileMap.length;
        int x = fileMap[0].length();
        Field[][] map = new Field[y][x];
        List<Ghost>ghosts = new ArrayList<>();
        for(int j = 0; j < y; j++)
            for(int i = 0; i < x; i++)
                map[j][i] = new Field(j,i, FieldType.Empty);

        for(int j = 0; j < fileMap.length; j++)
            for(int i = 0; i < fileMap[j].length(); i++){
                FieldType current = FieldType.getType(fileMap[j].charAt(i));

                //Load all characters and objects to map
                if(current == FieldType.PacMan)
                    map[j][i] = new Pacman(new Field(i,j,current),settings.getSpeed(),settings.getLives(), update);
                else if(current == FieldType.BigFruit)
                    map[j][i] = new BigFruit(new Field(i,j,current),settings.getBPoints(),
                            new PowerUp(settings.getEnhancedSpeed(),settings.isDread(),settings.getTime()));
                else if(current == FieldType.Fruit)
                    map[j][i] = new SmallFruit(new Field(i,j,current),settings.getPoints());
                else if(current == FieldType.Blinky){
                    Blinky blinky = new Blinky(new Field(i,j,current));
                    blinky.setSpeed(settings.getBSpeed());
                    map[j][i] = blinky;
                    ghosts.add(blinky);
                }
                else if(current == FieldType.Clyde){
                    Clyde clyde = new Clyde(new Field(i,j,current));
                    map[j][i] = clyde;
                    clyde.setSpeed(settings.getCSpeed());
                    ghosts.add(clyde);
                }
                else if(current == FieldType.Inky){
                    Inky inky = new Inky(new Field(i,j,current));
                    map[j][i] = inky;
                    inky.setSpeed(settings.getISpeed());
                    ghosts.add(inky);
                }
                else if(current == FieldType.Pinky){
                    Pinky pinky = new Pinky(new Field(i,j,current));
                    map[j][i] = pinky;
                    pinky.setSpeed(settings.getPSpeed());
                    ghosts.add(pinky);
                }
                else
                    map[j][i] = new Field(i,j,current);
            }
        return  new Pair<>(map,ghosts);
    }

    /**
     * Finds presents of Pacman on the map
     * @return instance of Pacman
     */
    public static Pacman getPacman(Field[][] map) throws CriticalException {
        for(Field[] fields : map)
            for(Field field : fields)
                if(field.getType().equals(FieldType.PacMan))
                    return (Pacman)field;
        throw new CriticalException("[ERROR] UNABLE TO FIND PACMAN", MapFactory.class.getName(), ExceptionType.PACMAN_NOT_FOUND);
    }
}
