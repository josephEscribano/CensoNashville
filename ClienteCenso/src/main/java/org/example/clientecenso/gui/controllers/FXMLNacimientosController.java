package org.example.clientecenso.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.common.modelos.Persona;
import org.example.common.modelos.Sexo;

public class FXMLNacimientosController {

    @FXML
    private ListView<Persona> listHombres;
    @FXML
    private ListView<Persona> listMujeres;
    @FXML
    private TextField tfNombre;
    @FXML
    private ComboBox<Sexo> cbSexo;

    public void setPrincipal(FXMLPrincipalController fxmlPrincipalController) {
    }

    public void loadHombres() {
    }

    public void loadMujeres() {
    }
}
