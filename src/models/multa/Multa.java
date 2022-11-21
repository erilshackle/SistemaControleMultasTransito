package models.multa;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import models.Lei;
import models.PoliciaTransitoCV;

public class Multa {
    
    private EstadoMulta estado;
    
    private boolean pago;

    private final LocalDate data;
    
    private LocalDate dataPago;
    
    private String escritorio;
    
    private float valorPagar;

    private final Lei lei;
    
    /**
     *
     * @param data
     * @param lei
     */
    public Multa(LocalDate data, Lei lei) {
        this.pago  = false;
        this.data = data;
        this.lei = lei;
        this.estado = EstadoMulta.PENDENTE;
        this.valorPagar = lei.getValorMulta();
        getDiasHabeis();
    }
    
    public void updateData(){
        this.valorPagar = getValorPagar();
    }
    
    public String getInfracao(){
        return lei.getDescricao();
    }
    
    public String getInfracaoArtigo() {
        return lei.toString();
    }
    
    
    public long getDiasHabeis() {
        LocalDate today = LocalDate.now();
        
        LocalDate begin = LocalDate.of(data.getYear(), data.getMonth(), data.getDayOfMonth());
        LocalDate end;
        if(isPago()){
             end = LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth());
        } else {
            end = LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth());
        }
        long days = ChronoUnit.DAYS.between(begin, end); // dias passados
        updateEstado((int) days);
        return days;
    }

    public boolean pagar(float valor, String escritorio) {
        atualizarValorPagar();
        if( valor >= valorPagar){
            this.escritorio = escritorio;
            this.pago = true;
            this.dataPago = LocalDate.now();
            this.estado = EstadoMulta.PAGO;
        }
        return pago;
    }
    
    public float getValorPagar(){
        return valorPagar;
    }
    
    public int getPontos() {
        return lei.getPontos();
    }
    
    public boolean isPago() {
        return pago;
    }

    public LocalDate getDataPago() {
        return dataPago;
    }

    public String getEscritorio() {
        return escritorio;
    }

    public LocalDate getData() {
        return data;
    }
    
    public EstadoMulta getEstado(){
        return estado;
    }
    
    
    private void updateEstado(int dias){
        if(isPago()){
            this.estado =EstadoMulta.PAGO;
        } else
        if(dias >= PoliciaTransitoCV.DIAS_JUSTICA){
            this.estado = EstadoMulta.JUSTICA;
        } else 
        if(dias >= PoliciaTransitoCV.DIAS_LIMITE){
            this.estado =EstadoMulta.ATRAZADO;
        } else {
            this.estado = EstadoMulta.PENDENTE;
        }
    }
    
    private void atualizarValorPagar(){
        if(!isPago() && getEstado() != EstadoMulta.ATRAZADO
                && getDiasHabeis() >= PoliciaTransitoCV.DIAS_LIMITE ){
            this.valorPagar = lei.getValorMulta() *2;
        }
    }

    
}
