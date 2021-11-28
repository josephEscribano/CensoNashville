package org.example.clientecenso.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXMLPrincipalController implements Initializable {

    private final FXMLLoader fxmlLoaderLlegadas;
    private final FXMLLoader fxmlLoaderDefunciones;
    private final FXMLLoader fxmlLoaderNacimientos;
    private final FXMLLoader fxmlLoaderCasamientos;

    @FXML
    private BorderPane fxRoot;


    private AnchorPane llegada;
    private FXMLLlegadasController fxmlLlegadasController;

    private AnchorPane nacimientos;
    private FXMLNacimientosController fxmlNacimientosController;

    private AnchorPane casamientos;
    private FXMLCasamientosController fxmlCasamientosController;

    private AnchorPane defunciones;
    private FXMLDefuncionesController fxmlDefuncionesController;

    @Inject
    public FXMLPrincipalController(FXMLLoader fxmlLoaderLlegadas,
                                   FXMLLoader fxmlLoaderDefunciones,
                                   FXMLLoader fxmlLoaderNacimientos,
                                   FXMLLoader fxmlLoaderCasamientos) {
        this.fxmlLoaderLlegadas = fxmlLoaderLlegadas;
        this.fxmlLoaderDefunciones = fxmlLoaderDefunciones;
        this.fxmlLoaderNacimientos = fxmlLoaderNacimientos;
        this.fxmlLoaderCasamientos = fxmlLoaderCasamientos;
    }

    public BorderPane getFxRoot() {
        return fxRoot;
    }

    public void preloadLlegada() {
        try {
            if (llegada == null) {
                llegada = fxmlLoaderLlegadas.load(getClass().getResourceAsStream("/fxml/FXMLLlegadas.fxml"));
                fxmlLlegadasController = fxmlLoaderLlegadas.getController();
                fxmlLlegadasController.setPrincipal(this);
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadNacimientos() {
        try {
            if (nacimientos == null) {
                nacimientos = fxmlLoaderNacimientos.load(getClass().getResourceAsStream("/fxml/FXMLNacimientos.fxml"));
                fxmlNacimientosController = fxmlLoaderNacimientos.getController();
                fxmlNacimientosController.setPrincipal(this);
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadCasamientos() {
        try {
            if (casamientos == null) {
                casamientos = fxmlLoaderCasamientos.load(getClass().getResourceAsStream("/fxml/FXMLCasamientos.fxml"));
                fxmlCasamientosController = fxmlLoaderCasamientos.getController();
                fxmlCasamientosController.setPrincipal(this);
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void preloadDefunciones() {
        try {
            if (defunciones == null) {
                defunciones = fxmlLoaderDefunciones.load(getClass().getResourceAsStream("/fxml/FXMLDefunciones.fxml"));
                fxmlDefuncionesController = fxmlLoaderDefunciones.getController();
                fxmlDefuncionesController.setPrincipal(this);
            }

        } catch (IOException ex) {
            Logger.getLogger(FXMLPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chargeLllegada() {
        fxmlLlegadasController.loadPersonas();
        fxmlLlegadasController.loadEstadoCivil();
        fxmlLlegadasController.loadSexo();
        fxRoot.setCenter(llegada);
    }

    public void chargeNacimiento() {
        fxmlNacimientosController.loadHombres();
        fxmlNacimientosController.loadMujeres();
        fxRoot.setCenter(nacimientos);
    }
    public void chargeCasamiento() {
        fxmlCasamientosController.loadHombres();
        fxmlCasamientosController.loadMujeres();
        fxRoot.setCenter(casamientos);
    }

    public void chargeDefunciones() {
        fxmlDefuncionesController.loadPersonas();
        fxRoot.setCenter(defunciones);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preloadLlegada();
        preloadNacimientos();
        preloadCasamientos();
        preloadDefunciones();
    }
}
