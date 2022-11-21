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
import models.PoliciaTransitoCV;
import models.condutor.Condutor;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author eril.carvalho
 */
public class HabilitarLicencaController implements Initializable {

    private static Condutor condutor;

    static void setSelectedCondutor(Condutor driver) {
        condutor = driver;
    }

    @FXML
    private Button btnHabiltar;
    @FXML
    private TextField txtLicenca;
    @FXML
    private TextField txtPontos;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtPagamento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtLicenca.setText(condutor.getLicenca().toString());
        txtPontos.setText(String.valueOf(condutor.getPontosTotalMultas()));
        txtValor.setText(String.valueOf(condutor.getValorTotalMultas()));
    }

    @FXML
    private void pagarMulta(ActionEvent event) {
        try {
            float pagar = Float.parseFloat(txtPagamento.getText());
            if (pagar < 0) {
                StagesManipulator.alertError("Pagamento", "Valor inválido");
            } else if (condutor.reabilitarLicenca(pagar)) {
                PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
                ptcv.tirarDaJustica(condutor);
                StagesManipulator.alertInfo("Pagamento",
                        "Licenca do condutor foi habilitada com sucesso e suas multas foram resetadas");
                closeStage(event);
            } else {
                StagesManipulator.alertWarrn("Pagamento", "Valor insuficiente!");

            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }

    private void closeStage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
