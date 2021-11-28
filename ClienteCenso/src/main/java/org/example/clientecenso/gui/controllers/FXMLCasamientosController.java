package org.example.clientecenso.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.example.clientecenso.service.CasamientosService;
import org.example.common.modelos.Persona;

import javax.inject.Inject;

public class FXMLCasamientosController {
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private FXMLPrincipalController principal;
    @Inject
    private CasamientosService casamientosService;
    @FXML
    private ListView<Persona> listHombres;
    @FXML
    private ListView<Persona> listMujeres;

    public void setPrincipal(FXMLPrincipalController fxmlPrincipalController) {
        this.principal = fxmlPrincipalController;
    }

    public void loadHombres() {
    }

    public void loadMujeres() {
    }
}
