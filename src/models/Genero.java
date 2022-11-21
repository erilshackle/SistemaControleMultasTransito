/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author eril.carvalho
 */
public enum Genero {
    
    MASCULINO("Masculino"),
    FEMININO("Feminino");
    
    private final String name;
    
    private Genero (String gender){
        name = gender;
    }
    @Override
    public String toString(){
        return name;
    }
    public static Genero fromvalue(String v) { return valueOf(v); }
    
}
