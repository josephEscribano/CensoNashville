package org.example.clientecenso.gui.controllers;

import io.vavr.control.Either;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.example.clientecenso.gui.utils.ConstantesGui;
import org.example.clientecenso.service.DefuncionesService;
import org.example.clientecenso.service.PersonasService;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;

import javax.inject.Inject;
import java.util.List;

public class FXMLDefuncionesController {
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private FXMLPrincipalController principal;
    @Inject
    private DefuncionesService defuncionesService;
    @Inject
    private PersonasService personasService;

    @FXML
    private ListView<Persona> listPersonas;

    public void setPrincipal(FXMLPrincipalController fxmlPrincipalController) {
        this.principal = fxmlPrincipalController;
    }

    public void loadPersonas() {

        var tarea = new Task<Either<ApiError, List<Persona>>>() {

            @Override
            protected Either<ApiError, List<Persona>> call() {
                return personasService.getAll();
            }
        };

        tarea.setOnSucceeded(workerStateEvent -> {
            tarea.getValue().peek(personas -> listPersonas.getItems().setAll(personas))
                    .peekLeft(apiError -> {
                        alert.setContentText(apiError.getMessage());
                        alert.showAndWait();
                    });
            this.principal.getFxRoot().setCursor(Cursor.DEFAULT);
        });

        tarea.setOnFailed(workerStateEvent -> {
            alert.setContentText(workerStateEvent.getSource().getException().getMessage());
            alert.showAndWait();
            this.principal.getFxRoot().setCursor(Cursor.DEFAULT);
        });

        new Thread(tarea).start();
        this.principal.getFxRoot().setCursor(Cursor.WAIT);

    }

    public void muerePersona() {
        Persona persona = listPersonas.getSelectionModel().getSelectedItem();

        if (persona != null) {
            var tarea = new Task<Either<ApiError, ApiRespuesta>>() {

                @Override
                protected Either<ApiError, ApiRespuesta> call() {
                    return defuncionesService.muerePersona(persona.getId());
                }
            };
            tarea.setOnSucceeded(workerStateEvent -> {
                tarea.getValue().peek(apiRespuesta -> loadPersonas())
                        .peekLeft(apiError -> {
                            alert.setContentText(apiError.getMessage());
                            alert.showAndWait();
                        });
                this.principal.getFxRoot().setCursor(Cursor.DEFAULT);
            });

            tarea.setOnFailed(workerStateEvent -> {
                alert.setContentText(workerStateEvent.getSource().getException().getMessage());
                alert.showAndWait();
                this.principal.getFxRoot().setCursor(Cursor.DEFAULT);
            });

            new Thread(tarea).start();
            this.principal.getFxRoot().setCursor(Cursor.WAIT);

        } else {
            alert.setContentText(ConstantesGui.SELECCIONA_UNA_PERSONA);
            alert.showAndWait();
        }
    }
}
