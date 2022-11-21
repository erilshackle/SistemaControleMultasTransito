/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.PoliciaTransitoCV;
import models.condutor.Condutor;
import models.multa.Multa;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author eril.carvalho
 */
public class HistoricoController implements Initializable {

    PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
    
    private Condutor condutor;
    
    @FXML
    private TableView<Condutor> tblHistorico;
    @FXML
    private TableColumn<Condutor, String> colCondutor;
    @FXML
    private TableColumn<Condutor, String> colLicenca;
    @FXML
    private TableColumn<Condutor, Integer> colPontos;
    @FXML
    private TableColumn<Condutor, Float> colDivida;
    @FXML
    private TableColumn<Condutor, String> colEstado;
    @FXML
    private Button btnInspecionar;
    @FXML
    private Button btnVoltar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateData();
        
        btnVoltar.setOnAction(event -> {
            closeStage(event);
        });
        
    }    

    @FXML
    private void InspecionarCondutor(ActionEvent event) throws IOException {
        condutor = tblHistorico.getSelectionModel().getSelectedItem();
    
        if(condutor != null){
            
            InspecionarController.setInspectCondutor(condutor);
            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage loadTela = new StagesManipulator().loadTelaModal("Inspecionar", parentStage);
            loadTela.setTitle("Inspecionar Condutor");
            loadTela.showAndWait();
            updateData();
            
        } else {
            StagesManipulator.alertWarrn("Inspecionar", "Selecione um condutor");
        }
        
    }
    
    private void updateData(){
        
        ObservableList<Condutor> condutores = FXCollections.observableArrayList();
        condutores.addAll(ptcv.getCondutoresJustica());

        colCondutor.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colLicenca.setCellValueFactory(new PropertyValueFactory<>("licenca"));
        colPontos.setCellValueFactory((CellDataFeatures<Condutor, Integer> d)
                -> new ReadOnlyObjectWrapper(d.getValue().getPontosTotalMultas())
        );
        colDivida.setCellValueFactory((CellDataFeatures<Condutor, Float> d)
                -> new ReadOnlyObjectWrapper(d.getValue().getValorTotalMultas())
        );
        colEstado.setCellValueFactory((CellDataFeatures<Condutor, String> d)
                -> new ReadOnlyObjectWrapper(d.getValue().getLicenca().getEstado().toString())
        );
        tblHistorico.setItems(condutores);
        
    }
    
    public void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
