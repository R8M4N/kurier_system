public class InvalidPaczkaDataException extends PaczkaException {
    public InvalidPaczkaDataException(String message) {
        super("Nieprawidłowe dane paczki: " + message);
    }
}