import java.util.List;

public interface IPaczkaService {
    void dodajPaczke(Paczka paczka) throws PaczkaException;
    void usunPaczke(String id) throws PaczkaNotFoundException;
    void edytujPaczke(String id, Paczka nowaPaczka) throws PaczkaNotFoundException, PaczkaException;
    void zmienStatus(String id, StatusPaczki nowyStatus) throws PaczkaNotFoundException;
    Paczka znajdzPaczke(String id) throws PaczkaNotFoundException;
    List<Paczka> pobierzWszystkiePaczki();
    List<Paczka> pobierzPaczkiPoStatusie(StatusPaczki status);
    void zapiszDane();
    int pobierzNajwiekszeId();
}