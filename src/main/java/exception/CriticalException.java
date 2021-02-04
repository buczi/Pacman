package exception;

import file.logger.LogException;

/**
 * Unrecoverable exception. Stops application from running
 */
public class CriticalException extends  Exception implements LogException {
    private final String message;
    private final String className;
    private final ExceptionType type;

    public CriticalException(String message, String className, ExceptionType type){
        super();
        this.message = message;
        this.className = className;
        this.type = type;
    }

    public String getCriticalExceptionTrace(){
        return message + "\nCLASS:\n" + className + "\nEXCEPTION TYPE:\n" + type + "\n" + type.getDebugMessage();
    }

    @Override
    public String getExceptionMessage() {
        return getCriticalExceptionTrace();
    }
}
