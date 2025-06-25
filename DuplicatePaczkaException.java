public class DuplicatePaczkaException extends PaczkaException {
    public DuplicatePaczkaException(String id) {
        super("Paczka o ID: " + id + " już istnieje w systemie");
    }
}