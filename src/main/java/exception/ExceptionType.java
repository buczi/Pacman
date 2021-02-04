package exception;

import file.logger.Logger;

/**
 * All known exceptions with probable causes and suggested solutions
 */
public enum ExceptionType {
    LOAD_FILE_EXCEPTION("Error connected with reading from file. Check if file is not missing or if the path to it is correct."),
    CREATE_MAP_EXCEPTION("Missing arguments while map was created. Error may be caused by incorrect files like settings or map file"),
    JSON_CREATE_EXCEPTION("Missing file with settings, check resources/difficulty folder "),
    JSON_PARSE_EXCEPTION("Failed to parse json file. Check file to make sure that the file is wrote correctly"),
    PACMAN_NOT_FOUND("Unable to find pacman in map file. Try to add 'M' to /resources/map/2.txt");
    private final String content;

    ExceptionType(String string){
        this.content = string;
    }

    public String getDebugMessage(){
        if(Logger.getDebug())
            return content;
        return "";
    }

}
