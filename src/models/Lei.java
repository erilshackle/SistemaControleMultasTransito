package models;

public class Lei {

    private final int artigo;

    private final int seccao;

    private String descricao;

    private float valorMulta;

    private int pontos;

    public Lei(int artigo, int seccao, float valorMulta, int pontos) {
        if(valorMulta <= 0){
            throw new Illegal­Argument­Exception("Valor da multa não pode ser  menor ou igual a zero");
        } else if(pontos <= 0){
            throw new Illegal­Argument­Exception("pontos não pode ser  menor ou igual a zero");
        }
        this.artigo = artigo;
        this.seccao = seccao;
        this.valorMulta = valorMulta;
        this.pontos = pontos;
    }

    public Lei(int artigo, int seccao, String descricao, float valorMulta, int pontos) {
        this(artigo,seccao,valorMulta,pontos);
        this.descricao = descricao;
    }

    public int getArtigo() {
        return artigo;
    }
    
    public int getSeccao() {
        return seccao;
    }
    
    public float getValorMulta() {
        return valorMulta;
    }

    public boolean setValorMulta(float valorMulta) {
        if (valorMulta <= 0)return false;
        this.valorMulta = valorMulta;
        return true;
    }

    public int getPontos() {
        return pontos;
    }

    public boolean setPontos(int pontos) {
        if (pontos <= 0)return false;
        this.pontos = pontos;
        return true;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Art. " + artigo + ", Seção  " + seccao;
    }

}
