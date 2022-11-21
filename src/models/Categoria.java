package models;

public enum Categoria {
    A,  //
    B,  //
    C,  //
    D,  //
    E;  //
    
    private Categoria() {}
    public String value() { return name(); }
    public static Categoria fromvalue(String v) { return valueOf(v); }
}
