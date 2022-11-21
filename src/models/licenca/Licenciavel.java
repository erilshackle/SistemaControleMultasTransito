/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.licenca;

import java.util.ArrayList;
import models.multa.Multa;

/**
 *
 * @author eril.carvalho
 */
public interface Licenciavel {
    
    public abstract boolean removerLicenca();
    public abstract boolean suspenderLicenca();
    public abstract boolean ativarLicenca();
    public abstract boolean reabilitarLicenca(float valorTotalMultas);
}
