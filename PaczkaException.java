public class PaczkaException extends Exception {
    public PaczkaException(String message) {
        super(message);
    }
    
    public PaczkaException(String message, Throwable cause) {
        super(message, cause);
    }
}