package censusanalyser;

public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM, UNABLE_TO_PARSE,NO_SUCH_FILE_ERROR;

    }

    public ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(ExceptionType type,String message,Throwable cause) {
        super(message, cause);
        this.type = type;
    }
}
