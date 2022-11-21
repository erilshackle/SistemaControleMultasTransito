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
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Lei;
import models.PoliciaTransitoCV;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author eril.carvalho
 */
public class LeiEditController implements Initializable {

    PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
    private static Lei updateLei = null;

    @FXML
    private TextField txtArtigo;
    @FXML
    private TextField txtSeccao;
    @FXML
    private Spinner<Integer> spnPontos;
    @FXML
    private TextField txtValor;
    @FXML
    private TextField txtDescricao;
    @FXML
    private Button btnAplicar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Label lblMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateLei = LeisController.getSelectedLei();
        int spnInitial = updateLei.getPontos();

        txtArtigo.setText(String.valueOf(updateLei.getArtigo()));
        txtArtigo.setText(String.valueOf(updateLei.getArtigo()));
        txtSeccao.setText(String.valueOf(updateLei.getSeccao()));
        spnPontos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 36, spnInitial));
        txtValor.setText(String.valueOf(updateLei.getValorMulta()));
        txtValor.setPromptText(String.valueOf(updateLei.getValorMulta()));
        txtDescricao.setText(String.valueOf(updateLei.getDescricao()));
        txtDescricao.setPromptText(String.valueOf(updateLei.getDescricao()));

        btnCancelar.setOnAction(event -> {
            closeStage(event);
        });
        
        validateDatas();
        
    }

    public static void setUpdateLei(Lei lei) {
        if (updateLei == null) {
            updateLei = lei;
        }
    }

    @FXML
    private void aplicarAtualizacao(ActionEvent event) {
        String descricao = txtDescricao.getText();
        float valor = Float.valueOf(txtValor.getText());
        int pontos = spnPontos.getValue();
        if(ptcv.modificarLei(updateLei, descricao, valor, pontos)){
            StagesManipulator.alertInfo("Modificar Lei", "Lei atualizada com sucesso");
            closeStage(event);
        }
    }

    private void validateDatas() {
        // validade Valor input
        txtValor.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (Float.valueOf(newValue) > 0) {
                    lblMessage.setText("");
                    btnAplicar.setDisable(false);
                } else {
                    lblMessage.setText("Valor Negativo Invávido");
                    btnAplicar.setDisable(true);
                }
            } catch (NumberFormatException ex) {
                lblMessage.setText("Verifique a entrada do valor");
                    btnAplicar.setDisable(true);
            }
        });
    }

    
    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
