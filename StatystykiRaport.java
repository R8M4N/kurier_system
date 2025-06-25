public class StatystykiRaport {
    private int paczkiDoDoręczenia;
    private int paczkiDoręczone;
    private int wszystkiePaczki;
    private double calkowityPrzychod;
    
    public StatystykiRaport(int paczkiDoDoręczenia, int paczkiDoręczone, int wszystkiePaczki, double calkowityPrzychod) {
        this.paczkiDoDoręczenia = paczkiDoDoręczenia;
        this.paczkiDoręczone = paczkiDoręczone;
        this.wszystkiePaczki = wszystkiePaczki;
        this.calkowityPrzychod = calkowityPrzychod;
    }
    
    public int getPaczkiDoDoręczenia() { return paczkiDoDoręczenia; }
    public int getPaczkiDoręczone() { return paczkiDoręczone; }
    public int getWszystkiePaczki() { return wszystkiePaczki; }
    public double getCalkowityPrzychod() { return calkowityPrzychod; }
    
    @Override
    public String toString() {
        return String.format("Statystyki:\nPaczki do doręczenia: %d\nPaczki doręczone: %d\nWszystkie paczki: %d\nCałkowity przychód: %.2f zł",
                paczkiDoDoręczenia, paczkiDoręczone, wszystkiePaczki, calkowityPrzychod);
    }
}