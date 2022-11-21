package models;

public enum Ilha {
    
    SANTO_ANTAO("Santo Antão"),
    SAO_VICENTE("São Vicente"),
    SAO_NICOLAU("São Nicolau"),
    SAL("Sal"),
    BOAVISTA("Boa Vista"),
    MAIO("Maio"),
    SANTIAGO("Santiago"),
    FOGO("Fogo"),
    BRAVA("Brava");
    
    
    private final String name;
    
    private Ilha(String islandName) { name = islandName; }
    @Override
    public String toString(){ return name; }
    public static Ilha fromvalue(String v) { return valueOf(v); }
    
}
