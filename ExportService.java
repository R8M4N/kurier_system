import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportService implements IExportService {
    
    @Override
    public void exportuj(List<Paczka> paczki, String nazwaPliku) throws ExportException {
        if (paczki == null) {
            throw new ExportException("Lista paczek nie może być null");
        }
        
        if (nazwaPliku == null || nazwaPliku.trim().isEmpty()) {
            throw new ExportException("Nazwa pliku nie może być pusta");
        }
        
        try (FileWriter writer = new FileWriter(nazwaPliku)) {
            writer.write("=== RAPORT PACZEK ===\n");
            writer.write("Łączna liczba paczek: " + paczki.size() + "\n\n");
            
            for (Paczka paczka : paczki) {
                writer.write(paczka.toString() + "\n");
            }
            
            writer.write("\n=== KONIEC RAPORTU ===\n");
        } catch (IOException e) {
            throw new ExportException("Nie można zapisać pliku: " + nazwaPliku, e);
        }
    }
}
