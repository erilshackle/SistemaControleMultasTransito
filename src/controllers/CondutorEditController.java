/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.Genero;
import models.Ilha;
import models.condutor.Condutor;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author eril.carvalho
 */
public class CondutorEditController implements Initializable {

    private static Condutor selectedCondutor;

    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnVoltar;
    @FXML
    private TextField txtNome;
    @FXML
    private RadioButton rbHomem;
    @FXML
    private ToggleGroup groupSexo;
    @FXML
    private RadioButton rbMulher;
    @FXML
    private Spinner<Integer> spnIdade;
    @FXML
    private ComboBox<Ilha> cbxIlha;
    @FXML
    private TextField txtMorada;
    @FXML
    private Label lblMessage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtNome.setText(selectedCondutor.getNome());
        txtNome.setText(selectedCondutor.getNome());
        spnIdade.setValueFactory(new SpinnerValueFactory.
                IntegerSpinnerValueFactory(18, 100, selectedCondutor.getIdade()));
        cbxIlha.setItems(FXCollections.observableArrayList(Ilha.values()));
        cbxIlha.getSelectionModel().select(selectedCondutor.getIlha());
        txtMorada.setText(selectedCondutor.getEndereco());
        if(selectedCondutor.getSexo() == Genero.MASCULINO){
            rbHomem.setSelected(true);
        } else {
            rbMulher.setSelected(true);
        }
        
        btnVoltar.setOnAction(event -> {
            closeStage(event);
        });
        
    }    

    @FXML
    private void atualizar(ActionEvent event) {
        String nome = txtNome.getText();
        String endereco = txtMorada.getText();
        int idade = spnIdade.getValue();
        Ilha ilha = cbxIlha.getValue();
        Genero sexo =  rbHomem.isSelected() ? Genero.MASCULINO : Genero.FEMININO;
        
        if(StagesManipulator.alertConfirm("Atualizar Dados do condutor",
                "Confirma que pretende atualizar os dados deste condutor?"))
        {
            if(!nome.isBlank()){
                selectedCondutor.setNome(nome);
            }
            selectedCondutor.setSexo(sexo);
            selectedCondutor.setIdade(idade);
            selectedCondutor.setEndereco(endereco);
            selectedCondutor.setIlha(ilha);
            closeStage(event);
        }
    }
    
    public static void setSelectedCondutor(Condutor condutor){
        selectedCondutor = condutor;
    }
    
    public void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
