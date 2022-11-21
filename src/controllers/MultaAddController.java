/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.condutor.Condutor;
import models.Lei;
import models.PoliciaTransitoCV;
import scmt.StagesManipulator;

/**
 *
 * @author lenil
 */
public class MultaAddController implements Initializable {

    private static Condutor condutor;
    
    @FXML
    private Button btnAplicar;
    @FXML
    private DatePicker dtpData;
    @FXML
    private Label lblMessage;
    @FXML
    private ComboBox<Lei> cbxLei;
    @FXML
    private Label lblDescricao;
    @FXML
    private TextField txtArtigo;

    private boolean dataOK;
    private boolean leiOK;

    public static void setSelectedCondutor(Condutor driver){
        condutor = driver;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
        cbxLei.setItems(FXCollections.observableArrayList(ptcv.getLeis()));
        dtpData.setValue(LocalDate.now());
        dataOK = true;
        
    }
    
    @FXML
    void aplicarMulta(ActionEvent event) {
        PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();

        LocalDate date = dtpData.getValue();
        Lei lei = cbxLei.getValue();
  
        if (condutor.aplicarMulta(date, lei)) {
            StagesManipulator.alertInfo("Aplicar Multa", "Multa aplicada ao condutor com sucesso");
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            StagesManipulator.alertWarrn("Aplicar Multa", "Não foi possivel aplicar a multa ao condutor");
        }
    }

    @FXML
    private void selecionarLei(ActionEvent event) {
        leiOK = true;
        Lei lei = cbxLei.getValue();
        lblDescricao.setText(lei.getDescricao());
        tryEnableButton();
    }

    @FXML
    private void selecionarData(ActionEvent event) {
        LocalDate ld = dtpData.getValue();
        if (Period.between(LocalDate.now(), ld).getDays() > 0) {
            lblMessage.setText("Data Inválida. escolha uma data até hoje");
        } else {
            dataOK = true;
            lblMessage.setText("");
        }
        tryEnableButton();
    }

    private void tryEnableButton() {
        if (dataOK && leiOK) {
            btnAplicar.setDisable(false);
        } else {
            btnAplicar.setDisable(true);
        }
    }

    

    @FXML
    private void filtrarLei(ActionEvent event) {
        PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
        cbxLei.setItems(null);
        String filter = txtArtigo.getText();
        if(filter.isBlank()){
            cbxLei.setItems(FXCollections.observableArrayList(ptcv.getLeis()));
        } else {
            int art = Integer.valueOf(filter);
            cbxLei.setItems(FXCollections.observableArrayList(ptcv.pesquisarLeis(art)));
        }
    }

}
