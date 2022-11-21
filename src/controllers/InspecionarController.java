/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Categoria;
import models.condutor.Condutor;
import models.licenca.EstadoLicenca;
import models.Genero;
import models.PoliciaTransitoCV;
import models.multa.Multa;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author eril.carvalho
 */
public class InspecionarController implements Initializable {

    private Stage stage;
    
    private static Condutor condutor;

    private static Multa multa;

    @FXML
    private Label lblName;
    @FXML
    private Label lblGender;
    @FXML
    private Label lblAge;
    @FXML
    private Label lblIsland;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblPoints;
    @FXML
    private Button btnEditar;
    @FXML
    private Label lblState;
    @FXML
    private Label lblNumber;
    @FXML
    private Label lblCategories;
    @FXML
    private TableView<Multa> tblMultas;
    @FXML
    private TableColumn<Multa, String> colData;
    @FXML
    private TableColumn<Multa, Integer> colPontos;
    @FXML
    private TableColumn<Multa, Float> colValor;
    @FXML
    private TableColumn<Multa, Integer> colAtraso;
    @FXML
    private TableColumn<Multa, Boolean> colEstado;
    @FXML
    private Label lblPayment;
    @FXML
    private Button btnAdicionarMulta;
    @FXML
    private ImageView gphProfile;
    @FXML
    private ImageView gphGender;
    @FXML
    private ImageView gphLicenceState;
    @FXML
    private Button btnAtivarLicenca;
    @FXML
    private Button btnDetalhesMulta;
    
    public static void setInspectCondutor(Condutor driver) {
        condutor = driver;
    }
    
    PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        try {
            updateData();
        } catch (IOException ex) {
            Logger.getLogger(InspecionarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void telaAdicionarMulta(ActionEvent event) throws IOException {
        MultaAddController.setSelectedCondutor(condutor);
        Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage = new StagesManipulator().loadTelaModal("MultaAdd", parentStage);
        stage.showAndWait();
        updateMultaData();
        updateLicencaData();
        updateGraphsState();
    }

    @FXML
    private void telaEditarCondutor(ActionEvent event) throws IOException {
        CondutorEditController.setSelectedCondutor(condutor);
        Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage = new StagesManipulator().loadTelaModal("CondutorEdit", parentStage);
        stage.showAndWait();
        updateData();
    }
    
    @FXML
    private void telaDetalhesMulta(ActionEvent event) throws IOException {
        MultaDetalhesController.setSelectedMulta(multa);
        Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage = new StagesManipulator().loadTelaModal("MultaDetalhes", parentStage);
        stage.setTitle("Detalhes e Pagamento de Multa");
        stage.showAndWait();
        updateMultaData();
        updateLicencaData();
    }
    
    @FXML
    private void ativarLicenca(ActionEvent event) throws IOException {
        HabilitarLicencaController.setSelectedCondutor(condutor);
        Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage = new StagesManipulator().loadTelaModal("HabilitarLicenca", parentStage);
        stage.showAndWait();
    }
    
    private void updateData() throws IOException {
        updateMultaData();
        updatePessoalData();
        updateLicencaData();
        updateGraphsState();
    }

    private void updatePessoalData() {
        lblName.setText(condutor.getNome());
        lblGender.setText(condutor.getSexo().toString());
        lblAge.setText(String.valueOf(condutor.getIdade()) + " anos");
        lblIsland.setText(condutor.getIlha().toString());
        lblAddress.setText(condutor.getEndereco());
    }

    private void updateLicencaData() {
        String cat = "";
        lblState.setText(condutor.getLicenca().getEstado().toString());
        lblNumber.setText(String.valueOf(condutor.getLicenca().getNumeroLicenca()));
        for (Categoria c : condutor.getLicenca().getCategorias()) {
            cat += c.name() + " ";
        }
        lblCategories.setText(cat);
        
        switch (condutor.getLicenca().getEstado()) {
            case ATIVO -> {
                enableAtivarLicenca(false);
            }
            case SUSPENSO -> {
                enableAtivarLicenca(true);
                ptcv.levarAJustica(condutor);
            }
            case RETIRADA -> {
                enableAtivarLicenca(true);
                ptcv.levarAJustica(condutor);
            }
        }
        
    }

    private void updateMultaData() {
        ObservableList<Multa> multas = FXCollections.observableArrayList();
        multas.addAll(condutor.getMultas());

        colData.setCellValueFactory(new PropertyValueFactory<>("data"));
        colPontos.setCellValueFactory(new PropertyValueFactory<>("pontos"));
        colAtraso.setCellValueFactory((CellDataFeatures<Multa, Integer> mlt)
                -> new ReadOnlyObjectWrapper(mlt.getValue().getDiasHabeis())
        );
        colValor.setCellValueFactory(new PropertyValueFactory<>("valorPagar"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblMultas.setItems(multas);
        lblPoints.setText(String.valueOf(condutor.getPontosTotalMultas()));
        lblPayment.setText(String.valueOf(condutor.getValorTotalMultas()));
    }

    private void updateGraphsState() throws IOException {
        
        if (condutor.getSexo() == Genero.MASCULINO) {
            gphGender.setImage(new Image("/img/icons8-male.png"));
        } else if (condutor.getSexo() == Genero.FEMININO) {
            gphGender.setImage(new Image("/img/icons8-female.png"));
        }
        
        if(condutor.aJustica()){
            gphProfile.setImage(new Image("/img/icons8-handcuffs.png"));
        }
//
        //Funcção switch case sugerido pelo IDE, muit facil de endender ;) 
        switch (condutor.getLicenca().getEstado()) {
            case ATIVO -> {
                gphLicenceState.setImage(new Image("/img/icons8-check_file.png"));
            }
            case SUSPENSO -> {
                gphLicenceState.setImage(new Image("/img/icons8-law_file.png"));
            }
            case RETIRADA -> {
                gphLicenceState.setImage(new Image("/img/icons8-delete_file.png"));
                gphProfile.setImage(new Image("/img/icons8-handcuffs.png"));
            }
        }
    }

    @FXML
    private void selectMulta(MouseEvent event) {
        multa = tblMultas.getSelectionModel().getSelectedItem();
        if (multa != null) {
            disableMultaButtons(false);
        } else {
            disableMultaButtons(true);
        }
    }


    private void disableMultaButtons(boolean state) {
        btnDetalhesMulta.setDisable(state);
    }

    private void enableAtivarLicenca(boolean state) {
        disableMultaButtons(!state);
        btnAtivarLicenca.setDisable(!state);
    }


}
