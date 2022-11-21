package models.multa;

import java.time.LocalDate;
import java.util.ArrayList;
import models.Lei;

public interface Multavel {

    public abstract boolean aplicarMulta(LocalDate data, Lei lei);

    /**
     * Para o condutor pagar uma multa
     * @param multa
     * @param pagamento
     * @param escritorio
     * @return 
     */
    public abstract boolean pagarMulta(Multa multa, float pagamento, String escritorio);
    
    /**
     * Para Saber se o condutor foi ou não à Justiça
     * @return 
     */
    public abstract boolean aJustica();
    
    /**
     * 
     * @return o valor a pagar do condutor pelas multas
     */
    public abstract float getValorTotalMultas();
    
    /**
     * 
     * @return os pontos totais acomulados nas multas
     */
    public abstract int getPontosTotalMultas();
    
}
