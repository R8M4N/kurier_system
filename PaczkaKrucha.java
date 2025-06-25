import java.io.Serializable;

public class PaczkaKrucha extends Paczka implements Serializable {
    private static final double CENA_ZA_KG = 6.0;
    private static final double DODATEK_KRUCHA = 10.0;
    
    public PaczkaKrucha(String id, String adresNadawcy, String adresOdbiorcy, double waga, String dataNadania) {
        super(id, adresNadawcy, adresOdbiorcy, waga, dataNadania);
    }
    
    @Override
    public double obliczKoszt() {
        return (waga * CENA_ZA_KG) + DODATEK_KRUCHA;
    }
    
    @Override
    public String getTypPaczki() {
        return "Krucha";
    }
}