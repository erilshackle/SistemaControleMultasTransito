package models.licenca;

import models.condutor.Condutor;
import java.util.ArrayList;
import models.Categoria;

public class Licenca {

    private Condutor condutor;
    
    private final long numeroLicenca;

    private EstadoLicenca estado;

    private final ArrayList<Categoria> categorias;
    
    private Licenca(Condutor driver, long numeroLicenca) {
        this.condutor = driver;
        this.numeroLicenca = numeroLicenca;
        this.categorias = new ArrayList<>();
        this.estado = EstadoLicenca.ATIVO;
    }
    
    public Licenca(Condutor driver, long numeroLicenca, ArrayList<Categoria> categorias) {
        this(driver, numeroLicenca);
        this.categorias.addAll(categorias);
    }
    
    public Licenca(Condutor driver, long numeroLicenca, Categoria categoria) {
        this(driver, numeroLicenca);
        this.categorias.add(categoria);
    }

    /**
     * @return the numeroLicenca
     */
    public long getNumeroLicenca() {
        return numeroLicenca;
    }
    
    public Condutor getCondutor() {
        return condutor;
    }
    
    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }
    
    public EstadoLicenca getEstado() {
        return estado;
    }

    public void setEstado(EstadoLicenca estado) {
        this.estado = estado;
    }

    /**
     * @return the categorias
     */
    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }
    
    public String getCategoriasNome() {
        String categories = "";
        for(Categoria cat : categorias){
            categories += cat.toString() + " ";
        }
        return categories;
    }
    
    @Override
    public String toString() {
        return String.valueOf(numeroLicenca);
    }

}
