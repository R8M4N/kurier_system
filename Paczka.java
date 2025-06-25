import java.io.Serializable;

public abstract class Paczka implements Serializable{
    protected String id;
    protected String adresNadawcy;
    protected String adresOdbiorcy;
    protected double waga;
    protected StatusPaczki status;
    protected String dataNadania;

    private static final long serialVersionUID = 1L;

    public Paczka(String id, String adresNadawcy, String adresOdbiorcy, double waga, String dataNadania) {
        this.id = id;
        this.adresNadawcy = adresNadawcy;
        this.adresOdbiorcy = adresOdbiorcy;
        this.waga = waga;
        this.dataNadania = dataNadania;
        this.status = StatusPaczki.NADANA;
    }

    public Paczka() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAdresNadawcy() { return adresNadawcy; }
    public void setAdresNadawcy(String adresNadawcy) { this.adresNadawcy = adresNadawcy; }

    public String getAdresOdbiorcy() { return adresOdbiorcy; }
    public void setAdresOdbiorcy(String adresOdbiorcy) { this.adresOdbiorcy = adresOdbiorcy; }

    public double getWaga() { return waga; }
    public void setWaga(double waga) { this.waga = waga; }

    public StatusPaczki getStatus() { return status; }
    public void setStatus(StatusPaczki status) { this.status = status; }

    public String getDataNadania() { return dataNadania; }
    public void setDataNadania(String dataNadania) { this.dataNadania = dataNadania; }

    public abstract double obliczKoszt();

    public abstract String getTypPaczki();

    @Override
    public String toString() {
        return String.format("ID: %s, Od: %s, Do: %s, Waga: %.2f kg, Status: %s, Data: %s, Typ: %s, Koszt: %.2f zł",
                id, adresNadawcy, adresOdbiorcy, waga, status, dataNadania, getTypPaczki(), obliczKoszt());
    }
}