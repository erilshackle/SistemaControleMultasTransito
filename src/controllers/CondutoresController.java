/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.condutor.Condutor;
import models.licenca.Licenca;
import models.PoliciaTransitoCV;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author eril.carvalho
 */
public class CondutoresController implements Initializable {

    PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
    private static Condutor selectedDriver;

    @FXML
    private TextField txtCondutor;
    @FXML
    private TableView<Condutor> tblCondutores;
    @FXML
    private TableColumn<Condutor, String> colNome;
    @FXML
    private TableColumn<Condutor, Character> colSexo;
    @FXML
    private TableColumn<Condutor, Integer> colIdade;
    @FXML
    private TableColumn<Condutor, String> colIlha;
    @FXML
    private TableColumn<Condutor, String> colLicenca;
    @FXML
    private TableColumn<Condutor, Integer> colMultas;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnInspecionar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateTable(ptcv.getCondutores());
    }

    @FXML
    private void pesquisarCondutor(ActionEvent event) {
        updateTable(ptcv.pesquisarCondutores(txtCondutor.getText()));
    }

    @FXML
    private void adicionarCondutor(ActionEvent event) throws IOException {
        Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Stage loadTela = new StagesManipulator().loadTelaModal("CondutoresRegister", parentStage);
        loadTela.setTitle("Adicionar Condutores");
        loadTela.showAndWait();
        updateTable(ptcv.getCondutores());
    }

    @FXML
    private void eliminarCondutor(ActionEvent event) {
        selectedDriver = tblCondutores.getSelectionModel().getSelectedItem();
        if (selectedDriver != null) {
            if(StagesManipulator.alertConfirm("Eliminar Condutor", 
                    "Tem certeza que quer eliminar este condutor?") ){
                ptcv.eliminarCondutor(selectedDriver);
                updateTable(ptcv.getCondutores());
            }
        } else {
            StagesManipulator.alertWarrn("Eliminar", "Por Favor, selecione um condutor na tabela");
        }
    }

    @FXML
    private void InspecionarCondutor(ActionEvent event) throws IOException {
        
        selectedDriver = tblCondutores.getSelectionModel().getSelectedItem();
        
        if (selectedDriver != null) {
            InspecionarController.setInspectCondutor(selectedDriver);
            Stage loadTela = new StagesManipulator().loadTela("Inspecionar");
            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            parentStage.hide();
            loadTela.setTitle("Inspecionar Condutor");
            loadTela.showAndWait();
            loadTela.close();
            updateTable(ptcv.getCondutores());
            parentStage.showAndWait();    
        
        } else {
            StagesManipulator.alertWarrn("Inspecionar", "Por Favor, selecione um condutor na tabela");
        }
    }

    @FXML
    private void selectCondutor(MouseEvent event) {
//        selectedDriver = tblCondutores.getSelectionModel().getSelectedItem();
    }

    private void updateTable(ArrayList<Condutor> condutores) {
        ObservableList<Condutor> drivers = FXCollections.observableArrayList();
        drivers.addAll(condutores);
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colIlha.setCellValueFactory(new PropertyValueFactory<>("ilha"));
        //colLicenca.setCellValueFactory(new PropertyValueFactory<>("licenca"));
        colLicenca.setCellValueFactory((CellDataFeatures<Condutor, String> c)
                -> new ReadOnlyObjectWrapper(c.getValue().getLicenca().getEstado().toString())
        );
        colMultas.setCellValueFactory((CellDataFeatures<Condutor, Integer> c)
                -> new ReadOnlyObjectWrapper(c.getValue().getPontosTotalMultas())
        );
        tblCondutores.setItems(drivers);
    }

}
