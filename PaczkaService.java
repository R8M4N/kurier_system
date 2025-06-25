import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class PaczkaService implements IPaczkaService {
    private Map<String, Paczka> paczki;
    private static final String PLIK_DANYCH = "dane.bin";
    public PaczkaService() {
        this.paczki = new HashMap<>();
        wczytajDane();
    }
    
    @Override
    public void dodajPaczke(Paczka paczka) throws PaczkaException {
        if (paczka == null) {
            throw new InvalidPaczkaDataException("Paczka nie może być null");
        }
        
        if (paczka.getId() == null || paczka.getId().trim().isEmpty()) {
            throw new InvalidPaczkaDataException("ID paczki nie może być puste");
        }
        
        if (paczki.containsKey(paczka.getId())) {
            throw new DuplicatePaczkaException(paczka.getId());
        }
        
        if (paczka.getWaga() <= 0) {
            throw new InvalidPaczkaDataException("Waga musi być większa od 0");
        }
        
        paczki.put(paczka.getId(), paczka);
    }
    
    @Override
    public void usunPaczke(String id) throws PaczkaNotFoundException {
        if (!paczki.containsKey(id)) {
            throw new PaczkaNotFoundException(id);
        }
        paczki.remove(id);
    }
    
    @Override
    public void edytujPaczke(String id, Paczka nowaPaczka) throws PaczkaNotFoundException, PaczkaException {
        if (!paczki.containsKey(id)) {
            throw new PaczkaNotFoundException(id);
        }
        
        if (nowaPaczka == null) {
            throw new InvalidPaczkaDataException("Nowa paczka nie może być null");
        }
        
        if (nowaPaczka.getWaga() <= 0) {
            throw new InvalidPaczkaDataException("Waga musi być większa od 0");
        }
        
        if (!id.equals(nowaPaczka.getId()) && paczki.containsKey(nowaPaczka.getId())) {
            throw new DuplicatePaczkaException(nowaPaczka.getId());
        }
        
        paczki.remove(id);
        paczki.put(nowaPaczka.getId(), nowaPaczka);
    }
    
    @Override
    public void zmienStatus(String id, StatusPaczki nowyStatus) throws PaczkaNotFoundException {
        if (!paczki.containsKey(id)) {
            throw new PaczkaNotFoundException(id);
        }
        paczki.get(id).setStatus(nowyStatus);
    }
    
    @Override
    public Paczka znajdzPaczke(String id) throws PaczkaNotFoundException {
        if (!paczki.containsKey(id)) {
            throw new PaczkaNotFoundException(id);
        }
        return paczki.get(id);
    }
    
    @Override
    public List<Paczka> pobierzWszystkiePaczki() {
        return new ArrayList<>(paczki.values());
    }
    
    @Override
    public List<Paczka> pobierzPaczkiPoStatusie(StatusPaczki status) {
        return paczki.values().stream()
                .filter(paczka -> paczka.getStatus() == status)
                .collect(Collectors.toList());
    }


    public void zapiszDane() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PLIK_DANYCH))) {
            oos.writeObject(paczki);
        } catch (IOException e) {
            System.err.println("Błąd zapisu danych: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    private void wczytajDane() {
        File plik = new File(PLIK_DANYCH);
        if (!plik.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(plik))) {
            Object wczytaneObiekty = ois.readObject();
            
            if (wczytaneObiekty instanceof Map) {
                this.paczki = (Map<String, Paczka>) wczytaneObiekty;
                System.out.println("Wczytano " + paczki.size() + " paczek z pliku.");
            } else {
                System.err.println("Nieprawidłowy format danych w pliku.");
                this.paczki = new HashMap<>();
            }
            
        } catch (IOException e) {
            System.err.println("Błąd odczytu pliku: " + e.getMessage());
            this.paczki = new HashMap<>();
        } catch (ClassNotFoundException e) {
            System.err.println("Nie można odnaleźć klasy: " + e.getMessage());
            this.paczki = new HashMap<>();
        } catch (ClassCastException e) {
            System.err.println("Nieprawidłowy typ danych w pliku: " + e.getMessage());
            this.paczki = new HashMap<>();
        }
    }

    @Override
    public int pobierzNajwiekszeId() {
        if (paczki.isEmpty()) {
            return 0; 
        }
        
        int maxId = 0;
        for (String idStr : paczki.keySet()) {
            try {
                int id = Integer.parseInt(idStr);
                if (id > maxId) {
                    maxId = id;
                }
            } catch (NumberFormatException e) {
                System.err.println("ID nie jest liczbą: " + idStr);
            }
        }
        return maxId;
    }

}