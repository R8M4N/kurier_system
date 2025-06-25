public enum StatusPaczki {
    NADANA("Nadana"),
    W_DRODZE("W drodze"),
    W_DORECZENIU("W doręczeniu"),
    DORECZONA("Doręczona"),
    ZWROCONA("Zwrócona");
    
    private String nazwa;
    
    StatusPaczki(String nazwa) {
        this.nazwa = nazwa;
    }
    
    public String getNazwa() {
        return nazwa;
    }
    
    @Override
    public String toString() {
        return nazwa;
    }
}