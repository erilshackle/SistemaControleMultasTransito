/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.condutor;

import java.util.ArrayList;
import models.Categoria;
import models.Genero;
import models.Ilha;
import models.licenca.Licenca;

/**
 * Builder Pattern Design
 * @author eril.carvalho
 */
public class CondutorBuilder {

    private String nome;
    private Genero sexo;
    private int idade;
    private Ilha ilha;
    private String endereco;
    private Licenca licenca;
    private Condutor driver;

    /**
     * 
     * @param nome
     * @param sexo 
     */
    public CondutorBuilder(String nome, Genero sexo) {
        this.idade = 18;
        this.nome = nome;
        this.sexo = sexo;
    }

    public CondutorBuilder setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public CondutorBuilder setSexo(Genero sexo) {
        this.sexo = sexo;
        return this;
    }

    public CondutorBuilder setIdade(int idade) {
        this.idade = idade;
        return this;
    }

    public CondutorBuilder setIlha(Ilha ilha) {
        this.ilha = ilha;
        return this;
    }

    public CondutorBuilder setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }
    
    public CondutorBuilder setLicenca(long numero, ArrayList<Categoria> categorias){
        this.licenca = new Licenca(driver, numero, categorias);
        return this;
    }
    
    public CondutorBuilder setLicenca(long numero, Categoria categoria){
        this.licenca = new Licenca(driver, numero, categoria);
        return this;
    }

    public Condutor build() {
        driver =  new Condutor(nome, sexo, idade, ilha, endereco, licenca);
        return driver;
    }
    
}
