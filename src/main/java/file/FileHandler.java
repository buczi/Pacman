package file;

import com.google.gson.Gson;
import exception.CriticalException;
import exception.ExceptionType;
import file.logger.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    /**
     * Loads file
     * @param path absolute path to given file
     * @return content of file as string array
     * @throws CriticalException throws exception when file doesn't exist
     */
    public static String[] fileLoader(String path) throws CriticalException {
        List<String> data = new LinkedList<>();

        try{
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine())
                data.add(scanner.nextLine());
            scanner.close();
        }
        catch (IOException exception){
            CriticalException cex = new CriticalException("[ERROR] UNABLE TO LOAD FILE at path:" + path, FileHandler.class.getName(), ExceptionType.LOAD_FILE_EXCEPTION);
            System.out.println("[USER] CRITICAL EXCEPTION CHECK CONFIGURATION FILES OR DOWNLOAD AGAIN APP");
            Logger.generateLog(FileHandler.class.getName(),"FAIL TO LOAD FILE",true,cex);
            throw cex;
        }

        Logger.generateLog(FileHandler.class.getName(),"SUCCESSFULLY LOADED FILE " + path,false, null);

        return  data.toArray(new String[0]);
    }

    /**
     * Loads settings from json file
     * @param jPath absolute path to settings file
     * @return created settings object
     * @throws CriticalException when file is not found or it's content is incorrect
     */
    public static Settings loadSettings(String jPath) throws CriticalException{
        try{
            FileReader json = new FileReader(jPath);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            Logger.generateLog(FileHandler.class.getName(),"SUCCESSFULLY LOADED SETTINGS " + jPath,false, null);
            return new Gson().fromJson(obj.toString(),Settings.class);
        }
        catch (FileNotFoundException exception){
            CriticalException cex = new CriticalException("[ERROR] UNABLE TO LOAD FILE at path:" + jPath, FileHandler.class.getName(), ExceptionType.JSON_CREATE_EXCEPTION);
            System.out.println("[USER] CRITICAL EXCEPTION CHECK CONFIGURATION FILES OR DOWNLOAD AGAIN APP");
            Logger.generateLog(FileHandler.class.getName(),"FAIL TO LOAD JSON FILE",true,cex);
            throw cex;
        }
        catch (ParseException | IOException exception){
            CriticalException cex = new CriticalException("[ERROR] UNABLE TO LOAD FILE at path:" + jPath, FileHandler.class.getName(), ExceptionType.JSON_PARSE_EXCEPTION);
            System.out.println("[USER] CRITICAL EXCEPTION CHECK DIFFICULTY FILES OR DOWNLOAD AGAIN APP");
            Logger.generateLog(FileHandler.class.getName(),"FAIL TO LOAD JSON FILE",true,cex);
            throw cex;
        }

    }

}
