/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Categoria;
import models.PoliciaTransitoCV;
import models.condutor.Condutor;
import models.licenca.Licenca;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author eril.carvalho
 */
public class LicencasController implements Initializable {

    @FXML
    private TextField txtPesquisa;
    @FXML
    private TableView<Licenca> tblLicenca;
    @FXML
    private TableColumn<Licenca, Long> colLicenca;
    @FXML
    private TableColumn<Licenca, String> colCondutor;
    @FXML
    private TableColumn<Licenca, String> colCategorias;
    @FXML
    private TableColumn<Licenca, String> colEstado;
    @FXML
    private Button btnVoltar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
        updateTable(ptcv.getLicencas());
        
        btnVoltar.setOnAction(event -> {
            StagesManipulator.closeStage(event);
        });
        
    }    

    @FXML
    private void pesquisarLicenca(ActionEvent event) {
        PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
        try{
        String num = txtPesquisa.getText();
        updateTable(ptcv.pesquisarLicencas(Integer.valueOf(num)));
        }catch(NumberFormatException ex){
            ex.printStackTrace();
        }
    }
    
    private void updateTable(ArrayList<Licenca> licencas){
        ObservableList<Licenca> licenses = FXCollections.observableArrayList();
        licenses.addAll(licencas);

        colLicenca.setCellValueFactory(new PropertyValueFactory<>("numeroLicenca"));
        colCondutor.setCellValueFactory(new PropertyValueFactory<>("condutor"));
        colCategorias.setCellValueFactory((TableColumn.CellDataFeatures<Licenca, String> lic)
                -> new ReadOnlyObjectWrapper(lic.getValue().getCategoriasNome())
        );
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblLicenca.setItems(licenses);
        
    }
    
}
