public enum User {
    ADMIN("admin"),
    KURIER("kurier");
    
    private String rola;
    
    User(String rola) {
        this.rola = rola;
    }
    
    public String getRola() {
        return rola;
    }
}