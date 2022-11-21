/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scmt;

import models.PoliciaTransitoCV;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.random.RandomGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Categoria;
import models.condutor.Condutor;
import models.Genero;
import models.Ilha;
import models.licenca.Licenca;

/**
 *
 * @author eril.carvalho
 */
public class MultasTransito extends Application {
    
    PoliciaTransitoCV ptcv = PoliciaTransitoCV.getInstance();
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        // set scene configuration
        Parent root = FXMLLoader.load(getClass().getResource("/views/MultasTransito.fxml"));
        Scene scene = new Scene(root);
        // configure stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sistema de Controle de Multas de Transito");
        primaryStage.getIcons().add(new Image("/img/logo-icon.png"));
        primaryStage.centerOnScreen();
        primaryStage.setMinWidth(330);
        primaryStage.setMinHeight(490);
        // show stage
        primaryStage.show();
        
        // add drivers
        ptcv.addCondutor( new Condutor("Eliane Mendes", Genero.FEMININO, 1000, Categoria.A));
        ptcv.addCondutor(new Condutor("Junior Silva", Genero.MASCULINO, 1001, Categoria.B));
        ptcv.addCondutor(new Condutor("Patick Lima", Genero.MASCULINO, 1002, Categoria.C));
        ptcv.addCondutor(new Condutor("Patricia Correia", Genero.FEMININO, 1003, Categoria.D));
        ptcv.addCondutor(new Condutor("Isadora Pinto", Genero.FEMININO, 1004, Categoria.A));
        ptcv.addCondutor(new Condutor("Shakle Sousa", Genero.MASCULINO, 1700, Categoria.A));
        
        // add Laws
        ptcv.criarLei(1, 1, "Pagamento de Estacionamento", 500, 1);
        ptcv.criarLei(1, 2, "Horario de Proibido", 100, 1);
        ptcv.criarLei(2, 1, "Sentido Proebido", 300, 3);
        ptcv.criarLei(2, 2, "Invasão de faixa", 400, 3);
        ptcv.criarLei(2, 3, "Ré em zona proibida", 100, 2);
        ptcv.criarLei(3, 1, "Velocidade escessiva", 1000, 3);
        ptcv.criarLei(3, 2, "Atropelamento", 1200, 20);
        ptcv.criarLei(3, 3, "perseguição policial", 1200, 10);
        ptcv.criarLei(4, 1, "Lotação excessiva", 900, 3);
        ptcv.criarLei(4, 2, "Peso excessivo", 800, 3);
        ptcv.criarLei(4, 3, "Licença não permitida", 5000, 5);
        ptcv.criarLei(5, 1, "Atropelamento e fuga", 15000, 36);
        
        Condutor etsc = ptcv.getCondutor(1700);
        etsc.aplicarMulta(LocalDate.of(2022, 2, 15), ptcv.getLei(1, 2));
        ptcv.levarAJustica(etsc);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
}
