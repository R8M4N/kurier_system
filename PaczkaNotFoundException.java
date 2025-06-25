public class PaczkaNotFoundException extends PaczkaException {
    public PaczkaNotFoundException(String id) {
        super("Paczka o ID: " + id + " nie została znaleziona");
    }
}