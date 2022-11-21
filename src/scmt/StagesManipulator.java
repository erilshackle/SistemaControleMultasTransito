/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scmt;

import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import models.condutor.Condutor;

/**
 *
 * @author eril.carvalho
 */
public class StagesManipulator {
    
     public Stage loadTela(String tela) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/" + tela + ".fxml"));
        Parent root = loader.load();
        Stage mainstage = new Stage();
        Scene scene = new Scene(root);
        mainstage.setScene(scene);
        mainstage.setResizable(false);
        return mainstage;
    }
    
    public Stage loadTelaModal(String tela, Stage parent) throws IOException{
        Stage stage = loadTela(tela);
        stage.initOwner(parent);
        stage.initModality(Modality.APPLICATION_MODAL); 
        stage.centerOnScreen();
        stage.setUserData(tela);
        stage.getUserData();
        return stage;
    }
    
    /**
     * 
     * @param root to set scene
     * @param parentStage to hide, null if no need to hide
     * @return  a readyStage to show
     */
    public static Stage loadNewStage(Parent root, Stage parent) {
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        if(parent != null){
            stage.initOwner(parent);
            parent.hide();
        }
        stage.centerOnScreen();
        stage.setResizable(false);
        return stage;
    }
    
    
    private static Alert alert(AlertType type, String title, String message){
        Alert alert = new Alert(type);
        //alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        return alert;
    }
    
    public static void alertInfo(String title, String message){
        Alert alert = alert(AlertType.INFORMATION, title, message);
        alert.showAndWait();
    }
    
    public static void alertError(String title, String message){
        Alert alert = alert(AlertType.ERROR, title, message);
        alert.setTitle("Erro");
        alert.showAndWait();
    }
    
    public static void alertWarrn(String title, String message){
        Alert alert = alert(AlertType.WARNING, title, message);
        alert.setTitle("Aviso");
        alert.showAndWait();
    }
    
    public static boolean alertConfirm(String title, String message){
        Alert alert = alert(AlertType.CONFIRMATION, title, message);
        alert.setTitle("Atenção");
        Optional<ButtonType> res = alert.showAndWait();
        return (res.isPresent() && res.get() == ButtonType.OK);
    }
     
    public static void closeStage(ActionEvent event) {
        Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
    
}
