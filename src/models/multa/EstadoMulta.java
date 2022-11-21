/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.multa;

/**
 *
 * @author eril.carvalho
 */
public enum EstadoMulta {
    PAGO("Pago"),
    PENDENTE("Pendente"),
    JUSTICA("Justiça"),
    ATRAZADO("Atrazado");
    
    private final String estado;
    
    private EstadoMulta(String e){
        estado = e;
    }
    
    @Override
    public String toString(){
        return estado;
    }
    
}
