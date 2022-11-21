package models.condutor;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import models.Categoria;
import models.Genero;
import models.Ilha;
import models.Lei;
import models.licenca.Licenca;
import models.multa.Multa;
import models.multa.Multavel;
import models.PoliciaTransitoCV;
import models.licenca.EstadoLicenca;
import models.licenca.Licenciavel;
import models.multa.EstadoMulta;

public class Condutor implements Multavel, Licenciavel {

    private StringProperty nome;

    private IntegerProperty idade;

    private Genero sexo;

    private Ilha ilha;

    private StringProperty endereco;

    private Licenca licenca;

    private ArrayList<Multa> multas;

    /**
     * COntrutor Padrão, inicializador
     */
    private Condutor() {
        this.nome = new SimpleStringProperty();
        this.idade = new SimpleIntegerProperty(18); // idade padrão
        this.endereco = new SimpleStringProperty();
        this.multas = new ArrayList<>();
        this.ilha = Ilha.SANTIAGO;
    }

    /**
     * Contrutor Padrão com nome
     *
     * @param nome
     */
    private Condutor(String nome) {
        this();
        this.nome.set(nome);
    }

    /**
     * Construtor simples
     *
     * @param nome
     * @param sexo
     */
    public Condutor(String nome, Genero sexo) {
        this(nome);
        this.sexo = sexo;
    }

    /**
     * Contrutor simples com licença
     *
     * @param nome
     * @param sexo
     * @param numLicenca
     * @param categoria
     */
    public Condutor(String nome, Genero sexo, long numLicenca, Categoria categoria) {
        this(nome, sexo);
        this.licenca = new Licenca(this, numLicenca, categoria);
    }
    public Condutor(String nome, Genero sexo, long numLicenca, ArrayList<Categoria> categorias) {
        this(nome, sexo);
        this.licenca = new Licenca(this, numLicenca, categorias);
    }
    
    /**< Para os contrutores mais detalhados, usar classe CondutorBuild >*/
    
    /**
     * 
     * @param nm nome
     * @param gn genero (MASCULINO, FEMININO)
     * @param id idade (> 18)
     * @param il Ilha
     * @param end endereçø
     * @param lic objecto Licença
     */
    public Condutor(String nme, Genero gnr, int idd, Ilha ilh, String end, Licenca lic){
        this(nme, gnr, lic.getNumeroLicenca(), lic.getCategorias());
        this.idade.set(idd);
        this.ilha = ilh;
        this.endereco.set(end);
    }
    
    public StringProperty getNomeProperty() {
        return nome;
    }

    public String getNome() {
        return this.nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    /**
     * @return driver's idade
     */
    public IntegerProperty getIdadeProperty() {
        return idade;
    }

    /**
     * @return driver's idade
     */
    public int getIdade() {
        return idade.get();
    }

    /**
     * @param idade the idade to set
     * @return
     */
    public boolean setIdade(int idade) {
        if (idade < 16) {
            return false;
        }
        this.idade.set(idade);
        return true;
    }

    /**
     * @return the sexo
     */
    public Genero getSexo() {
        return sexo;
    }

    /**
     * @return the sexo name
     */
    public String getSexoName() {
        return sexo.name();
    }

    /**
     *
     * @param sexo
     */
    public void setSexo(Genero sexo) {
        this.sexo = sexo;
    }

    /**
     *
     * @param sexo
     * @return
     */
    public boolean setSexo(String sexo) {
        if (Genero.fromvalue(sexo) == null) {
            return false;
        }
        this.sexo = Genero.fromvalue(sexo);
        return true;
    }

    /**
     * @return the ilha
     */
    public Ilha getIlha() {
        return ilha;
    }

    /**
     * @param ilha the ilha to set
     */
    public void setIlha(Ilha ilha) {
        this.ilha = ilha;
    }

    public StringProperty getEnderecoProperty() {
        return endereco;
    }

    public String getEndereco() {
        return this.endereco.get();
    }

    public void setEndereco(String nome) {
        this.endereco.set(nome);
    }

    /**
     * @return the licenca
     */
    public Licenca getLicenca() {
        return licenca;
    }

    /**
     * @return the multas
     */
    public ArrayList<Multa> getMultas() {
        return multas;
    }

    @Override
    public boolean pagarMulta(Multa multa, float pagamento, String escritorio) {
        boolean ret = false;
        return multa.pagar(pagamento, escritorio);
    }

    @Override
    public boolean aplicarMulta(LocalDate data, Lei lei) {
        Multa trafficTicket = new Multa(data, lei);
        multas.add(trafficTicket);
        aJustica();
        return true;
    }

    @Override
    public boolean aJustica() {
        if(this.getPontosTotalMultas() >= PoliciaTransitoCV.PONTOS_LIMITE ){
            this.suspenderLicenca();
            return true;
        } else {
            for (Multa multa : multas) {
                if (multa.getEstado() == EstadoMulta.JUSTICA) {
                    this.suspenderLicenca();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public float getValorTotalMultas() {
        float valor = 0;
        for (Multa mlt : multas) {
            valor += mlt.getValorPagar();
        }
        return valor;
    }

    @Override
    public int getPontosTotalMultas() {
        int pts = 0;
        for (Multa mlt : multas) {
            pts += mlt.getPontos();
        }
        return pts;
    }

    @Override
    public boolean removerLicenca() {
        if(!aJustica()){
            return false;
        }
        this.licenca.setEstado(EstadoLicenca.RETIRADA);
        return true;
    }
    
    @Override
    public boolean suspenderLicenca() {
        this.licenca.setEstado(EstadoLicenca.SUSPENSO);
        return true;
    }
    
    @Override
    public boolean ativarLicenca() {
        this.licenca.setEstado(EstadoLicenca.ATIVO);
        return true;
    }
    
    @Override
    public boolean reabilitarLicenca(float valorTotalMultas) {
        if(valorTotalMultas < getValorTotalMultas() ){
            return false;
        } else {
            multas.forEach(multa -> multa.pagar(valorTotalMultas, "Tribunal"));
        }
        multas.clear();
        ativarLicenca();
        return false;
    }
    
    public String toString(){
        return nome.get();
    }

    
}
