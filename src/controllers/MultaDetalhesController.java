/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.multa.Multa;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author eril.carvalho
 */
public class MultaDetalhesController implements Initializable {

    private static Multa multa;

    @FXML
    private Button btnPagar;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtPagar;
    @FXML
    private TextField txtEscritorio;
    @FXML
    private TextField txtInfracao;

    public static void setSelectedMulta(Multa detailMulta){
        multa = detailMulta;
    }
    @FXML
    private TextField txtLei;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        multa.updateData();

        txtLei.setText(multa.getInfracaoArtigo());
        txtInfracao.setText(multa.getInfracao());
        txtData.setText(multa.getData().toString());
        txtEstado.setText(multa.getEstado().toString());
        txtValor.setText(String.valueOf(multa.getValorPagar()));
        txtPagar.setText(txtValor.getText());
        
        if(multa.isPago()){
            txtValor.setDisable(true);
            txtEscritorio.setDisable(true);
        }
        
    }

    @FXML
    private void pagarMulta(ActionEvent event) {
        try {
            String escritorio = txtEscritorio.getText();
            float valor = Float.valueOf(txtPagar.getText());
            if (multa.pagar(valor, escritorio)) {
                StagesManipulator.alertInfo("Pagamento", "Pagamento efectuado com sucesso");
                closeStage(event);
            } else {
                StagesManipulator.alertWarrn("Valor", "Valor insuficiente");
            }
                
        } catch (NumberFormatException ex) {
            StagesManipulator.alertWarrn("Valor", "Entre um valor válido");
            ex.printStackTrace();
        }
    }

    public void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
