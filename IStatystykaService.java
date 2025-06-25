public interface IStatystykaService {
    int policzPaczkiDoDoręczenia();
    int policzPaczkiDoręczone();
    int policzWszystkiePaczki();
    double obliczCalkowityPrzychod();
    StatystykiRaport generujRaport();
}