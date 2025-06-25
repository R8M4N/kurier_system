import java.io.Serializable;

public class PaczkaEkspresowa extends Paczka implements Serializable {
    private static final double CENA_ZA_KG = 8.0;
    private static final double DODATEK_EKSPRESOWY = 15.0;
    
    public PaczkaEkspresowa(String id, String adresNadawcy, String adresOdbiorcy, double waga, String dataNadania) {
        super(id, adresNadawcy, adresOdbiorcy, waga, dataNadania);
    }
    
    @Override
    public double obliczKoszt() {
        return (waga * CENA_ZA_KG) + DODATEK_EKSPRESOWY;
    }
    
    @Override
    public String getTypPaczki() {
        return "Ekspresowa";
    }
}