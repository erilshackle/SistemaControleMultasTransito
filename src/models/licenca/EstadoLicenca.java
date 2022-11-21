/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.licenca;

/**
 *
 * @author eril.carvalho
 */
public enum EstadoLicenca {
    
    ATIVO("Ativo"),
    SUSPENSO("Suspenso"),
    RETIRADA("Retirada");
    
    private final String estado;
    
    private EstadoLicenca(String state){
        estado = state;
    }
    
    @Override
    public String toString(){
        return estado;
    }
    
}
