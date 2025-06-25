import java.util.List;

public class StatystykaService implements IStatystykaService {
    private IPaczkaService paczkaService;
    
    public StatystykaService(IPaczkaService paczkaService) {
        this.paczkaService = paczkaService;
    }
    
    @Override
    public int policzPaczkiDoDoręczenia() {
        return paczkaService.pobierzWszystkiePaczki().stream()
                .mapToInt(paczka -> (paczka.getStatus() != StatusPaczki.DORECZONA && paczka.getStatus() != StatusPaczki.ZWROCONA) ? 1 : 0)
                .sum();
    }

    @Override
    public int policzPaczkiDoręczone() {
        return (int) paczkaService.pobierzWszystkiePaczki().stream()
                .filter(paczka -> paczka.getStatus() == StatusPaczki.DORECZONA 
                            || paczka.getStatus() == StatusPaczki.ZWROCONA)
                .count();
    }
    
    @Override
    public int policzWszystkiePaczki() {
        return paczkaService.pobierzWszystkiePaczki().size();
    }
    
    @Override
    public double obliczCalkowityPrzychod() {
        return paczkaService.pobierzWszystkiePaczki().stream()
                .filter(paczka -> paczka.getStatus() == StatusPaczki.DORECZONA)
                .mapToDouble(Paczka::obliczKoszt)
                .sum();
    }
    
    @Override
    public StatystykiRaport generujRaport() {
        return new StatystykiRaport(
            policzPaczkiDoDoręczenia(),
            policzPaczkiDoręczone(),
            policzWszystkiePaczki(),
            obliczCalkowityPrzychod()
        );
    }
}