/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Lei;
import models.PoliciaTransitoCV;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author lenil
 */
public class LeisController implements Initializable {
    
    PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
    private static Lei selectedLei; 
    
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Lei> tblLeis;
    @FXML
    private TableColumn<Lei, Integer> colArtigo;
    @FXML
    private TableColumn<Lei, Integer> colSeccao;
    @FXML
    private TableColumn<Lei, String> colDescricao;
    @FXML
    private TableColumn<Lei, Float> colValor;
    @FXML
    private TableColumn<Lei, Integer> colPontos;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAtualizar;
    @FXML
    private TextField txtPesquisa;
    
    public static Lei getSelectedLei(){
        return selectedLei;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTable(ptcv.getLeis());
    }    
    
    private void updateTable(ArrayList<Lei> leis){
       ObservableList<Lei> laws = FXCollections.observableArrayList();
       laws.addAll(leis);
        
       colArtigo.setCellValueFactory(new PropertyValueFactory<Lei, Integer>("artigo"));
       colSeccao.setCellValueFactory(new PropertyValueFactory<Lei, Integer>("seccao"));
       colDescricao.setCellValueFactory(new PropertyValueFactory<Lei, String>("descricao"));
       colValor.setCellValueFactory(new PropertyValueFactory<Lei, Float>("valorMulta"));
       colPontos.setCellValueFactory(new PropertyValueFactory<Lei, Integer>("pontos"));
       
       tblLeis.setItems(laws);
       
        
    }


    @FXML
    private void adicionarLei(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/LeiAdd.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        Stage parentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.setResizable(false);
        stage.showAndWait();
        updateTable(ptcv.getLeis());
    }

    @FXML
    private void eliminarLei(ActionEvent event) {
        String title = "Eliminar Lei " + selectedLei.toString();
        if(StagesManipulator.alertConfirm(title, "Deseja realmente eliminar essa lei?")){
            if( ptcv.eliminarLei(selectedLei) ){
                StagesManipulator.alertInfo(title, "Lei eliminada com sucesso.");
            } else {
                StagesManipulator.alertError(title, "Não foi possivel eliinar essa lei.");   
            }
        }
    }

    @FXML
    private void AtualizarLei(ActionEvent event) throws IOException {
        Stage parentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Stage stage = new StagesManipulator().loadTelaModal("leiEdit", parentStage);
        stage.showAndWait();
        updateTable(ptcv.getLeis());
        updateTable(ptcv.getLeis());
    }

    @FXML
    private void selectLei(MouseEvent event) {
        selectedLei = null;
        selectedLei = tblLeis.getSelectionModel().getSelectedItem();
        if(selectedLei != null){
            enableButton(true);
        } else {
            enableButton(false);
        }
    }

    @FXML
    private void pesquisarArtigo(ActionEvent event) {
        try{
            String val = txtPesquisa.getText();
            ArrayList<Lei> leis = ptcv.pesquisarLeis(Long.valueOf(val));
            updateTable(leis);
            
        }catch(NumberFormatException ex){
            ex.printStackTrace();
        }
    }
    
    private void enableButton(boolean state){
        btnAtualizar.setDisable(!state);
        btnEliminar.setDisable(!state);
    }
    
}
