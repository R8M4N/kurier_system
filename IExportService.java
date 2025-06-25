import java.util.List;

public interface IExportService {
    void exportuj(List<Paczka> paczki, String nazwaPliku) throws ExportException;
}