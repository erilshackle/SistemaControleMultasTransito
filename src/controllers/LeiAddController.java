/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.PoliciaTransitoCV;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author lenil
 */
public class LeiAddController implements Initializable {

    @FXML
    private TextField txtArtigo;
    @FXML
    private TextField txtSeccao;
    @FXML
    private TextField txtDescricao;
    @FXML
    private TextField txtValor;
    @FXML
    private Spinner<Integer> spnPontos;
    @FXML
    private Label lblMessaging;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnCancelar;
    
    private boolean artOK, secOK, valOK; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spnPontos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 36));
        
        txtArtigo.textProperty().addListener((observable, oldValue, newValue) -> {
            
            try{
                if(Integer.valueOf(newValue) > 0)
                    artOK = true;
            }catch(NumberFormatException ex){
                artOK = false;
            }
        });
        
        txtSeccao.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if(Integer.valueOf(newValue) > 0)
                    secOK = true;
            }catch(NumberFormatException ex){
                secOK = false;
            }
        });
        
        txtValor.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if(Integer.valueOf(newValue) > 0)
                    valOK = true;
            }catch(NumberFormatException ex){
                valOK = false;
            }
        });
        
        btnCancelar.setOnAction(event -> {
            closeStage(event);
        });
        
    }

    @FXML
    private void adicionarLei(ActionEvent event) {
        PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
        
        if(validateData()){
            int lei_art = Integer.valueOf(txtArtigo.getText());
            int lei_sec = Integer.valueOf(txtSeccao.getText());
            float lei_val = Float.valueOf(txtValor.getText());
            String lei_desc = txtDescricao.getText();
            int lei_pt = spnPontos.getValue();

            if (ptcv.getLei(lei_art, lei_sec) != null) {
                StagesManipulator.alertWarrn("Lei Já Registrada",
                        "Este artigo e seccao já foi registrada");
            } else if (ptcv.criarLei(lei_art, lei_sec, lei_desc, lei_val, lei_pt)) {
                StagesManipulator.alertInfo("Lei Registrada",
                        "Lei Registrada com sucesso");
                closeStage(event);
            }
        }
    }
    
    private void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private boolean validateData() {
        
        if(!artOK || txtArtigo.getText().isBlank()){
            lblMessaging.setText("Verifique o artigo");
        } else
        if(!secOK || txtSeccao.getText().isBlank()){
            lblMessaging.setText("Verifique a seccao");
        } else
        if(!valOK || txtValor.getText().isBlank()){
            lblMessaging.setText("Verifique o valor");
        } else{
            return true;
        }
        return false;
    }

}