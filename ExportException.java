public class ExportException extends PaczkaException {
    public ExportException(String message) {
        super("Błąd eksportu: " + message);
    }
    
    public ExportException(String message, Throwable cause) {
        super("Błąd eksportu: " + message, cause);
    }
}