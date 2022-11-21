/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.Categoria;
import models.condutor.Condutor;
import models.condutor.CondutorBuilder;
import models.Genero;
import models.Ilha;
import models.PoliciaTransitoCV;
import scmt.MultasTransito;
import scmt.StagesManipulator;

/**
 * FXML Controller class
 *
 * @author eril.carvalho
 */
public class CondutoresRegisterController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtApelido;
    @FXML
    private RadioButton rbSexoMasc;
    @FXML
    private ToggleGroup sexo;
    @FXML
    private RadioButton rbSexoFem;
    @FXML
    private Spinner<Integer> spIdade;
    @FXML
    private TextField txtLicenca;
    @FXML
    private Button btnAdicionar;
    @FXML
    private CheckBox ckbCatA, ckbCatB, ckbCatC, ckbCatD, ckbCatE;
    @FXML
    private Label lblMessage;
    @FXML
    private ComboBox<Ilha> cbxIlha;
    @FXML
    private TextField txtEndereco;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set spiner values
        spIdade.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(18, 90));
        // popilate combobox 
        cbxIlha.setItems(FXCollections.observableArrayList(Ilha.values()));
        cbxIlha.getSelectionModel().select(Ilha.SANTIAGO);
    }    

    @FXML
    public void adcionarCondutor(ActionEvent event) {
        ArrayList<Categoria> cat = new ArrayList<>();
        PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
        
        if(checkAllFields()){
           String name = txtNome.getText() + " " + txtApelido.getText();
           Genero gender = (rbSexoMasc.isSelected()) ? Genero.MASCULINO : Genero.FEMININO;
           int age = spIdade.getValue();
           Ilha island = (Ilha)cbxIlha.getValue();
           long numlicense = Integer.parseInt(txtLicenca.getText());
           String address = txtEndereco.getText();
           // add categorias
           if(ckbCatA.isSelected()) cat.add(Categoria.A);
           if(ckbCatB.isSelected()) cat.add(Categoria.B);
           if(ckbCatC.isSelected()) cat.add(Categoria.C);
           if(ckbCatD.isSelected()) cat.add(Categoria.D);
           if(ckbCatE.isSelected()) cat.add(Categoria.E);
           
           /**
            * Construindo um condutor pela classe CondutorBuilder
            */
           Condutor driver = new CondutorBuilder(name, gender).setIdade(age)
                    .setIlha(island).setEndereco(address)
                    .setLicenca(numlicense, cat).build();
            
           if(ptcv.addCondutor(driver)){
               StagesManipulator.alertInfo("Registro", "Condutor Adicionado com sucesso");
               // close
               Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               stage.close();
           } else {
               StagesManipulator.alertError("Registro", 
                       "Condutor Não pode ser adicionado, devido a licensa já registrada");
           }
        }
        
    }
    
    private boolean checkAllFields(){
        String err = null;
        try{
            if(txtNome.getText().isEmpty()){
                err = "Preencha o campo Nome.";
            } else
            if(txtApelido.getText().isEmpty()){
                err = "Preencha o campo Apelido";
            } else
            if (cbxIlha.getSelectionModel().getSelectedItem() == null){
                err ="Selecione uma Ilha";
            } else
            if (txtLicenca.getText().isEmpty()){
                err ="Preencha o campo Numero de Licença";
            } else 
            if( Integer. parseInt(txtLicenca.getText()) <= 0  ){
                err = "Número de Licença inválido";
            } else
            if (!ckbCatC.isSelected() && !ckbCatA.isSelected() && !ckbCatB.isSelected() 
                && !ckbCatD.isSelected() && !ckbCatE.isSelected()){
                err = "Selecione pelo menos uma categoria";
            } else {
                lblMessage.setText(err);
                return true;
            }
        } catch (NumberFormatException nfe){
            err = "Número de Licença inválido";
        }
        lblMessage.setText(err);
        return false;
    }
    
}
