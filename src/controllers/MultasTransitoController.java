/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.condutor.Condutor;
import models.PoliciaTransitoCV;
import scmt.MultasTransito;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author eril.carvalho
 */
public class MultasTransitoController implements Initializable {
    
    PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
    
    Stage stage, parentStage;
    
    @FXML
    private Button btnAutores;
    @FXML
    private Button btnCondutores;
    @FXML
    private Button btnLeis;
    @FXML
    private Button btnHistoricos;
    @FXML
    private TextField txtPesquisa;
    @FXML
    private Button btnLicenca;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stage = new Stage();
    }    

    @FXML
    private void telaAutores(ActionEvent event) throws IOException {
        Stage parent = (Stage)((Node) event.getSource()).getScene().getWindow();
        Stage stage = new StagesManipulator().loadTelaModal("Autores", parent);
        stage.showAndWait();
    }

    @FXML
    private void telaCondutor(ActionEvent event) throws IOException {
        stage = new StagesManipulator().loadTela("Condutores");
        Stage parent = (Stage)((Node) event.getSource()).getScene().getWindow();
        prepararStage(stage,"Condutores", "driver.png", parent);
        stage.showAndWait();
        parent.show();
    }
    
    @FXML
    private void telaLicenca(ActionEvent event) throws IOException {
        stage = new StagesManipulator().loadTela("Licencas");
        Stage parent = (Stage)((Node) event.getSource()).getScene().getWindow();
        prepararStage(stage,"Licenças", "driver_license.png", parent);
        stage.showAndWait();
        parent.show();
    }
    
    @FXML
    private void telaLeis(ActionEvent event) throws IOException {
        stage = new StagesManipulator().loadTela("Leis");
        Stage parent = (Stage)((Node) event.getSource()).getScene().getWindow();
        prepararStage(stage, "Leis", "law.png", parent);
        stage.showAndWait();
        parent.show();
    }

    @FXML
    private void telaHisorico(ActionEvent event) throws IOException {
        stage = new StagesManipulator().loadTela("Historico");
        Stage parent = (Stage)((Node) event.getSource()).getScene().getWindow();
        prepararStage(stage,"Historico", "order_history.png", parent);
        stage.showAndWait();
        parent.show();
    }
    
    private void prepararStage(Stage stage, String titulo, String icone, Stage hideStage){
        if(hideStage != null){
            hideStage.hide();
        }
        stage.getIcons().add(new Image("/img/icons8-"+ icone));
        stage.setTitle(titulo);
    }

    @FXML
    private void pesquisarRapida(ActionEvent event) throws IOException {
        String search = txtPesquisa.getText();
        Condutor driver = ptcv.getCondutor(Integer.parseInt(search));
        
        if(driver != null){
            InspecionarController.setInspectCondutor(driver);
            Parent root = FXMLLoader.load(getClass().getResource("/views/Inspecionar.fxml"));
            Stage parentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            Stage stage = StagesManipulator.loadNewStage(root, parentStage);
            stage.setResizable(false);
            parentStage.hide();
            stage.showAndWait();
            parentStage.show();
        } else {
            StagesManipulator.alertWarrn("Pesquisar Condutor", "Nenhum condutor encontrado");
        }
    }

    
    
}
