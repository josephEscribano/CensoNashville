package org.example.clientecenso.gui.controllers;

import io.vavr.control.Either;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import org.example.clientecenso.gui.utils.ConstantesGui;
import org.example.clientecenso.service.CasamientosService;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;

import javax.inject.Inject;
import java.util.List;

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
        var tarea = new Task<Either<ApiError, List<Persona>>>() {

            @Override
            protected Either<ApiError, List<Persona>> call() {
                return casamientosService.getHombres();
            }
        };

        tarea.setOnSucceeded(workerStateEvent -> {
            tarea.getValue().peek(hombres -> listHombres.getItems().setAll(hombres))
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

    public void loadMujeres() {

        var tarea = new Task<Either<ApiError, List<Persona>>>() {

            @Override
            protected Either<ApiError, List<Persona>> call() {
                return casamientosService.getMujeres();
            }
        };

        tarea.setOnSucceeded(workerStateEvent -> {
            tarea.getValue().peek(mujeres -> listMujeres.getItems().setAll(mujeres))
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

    public void boda() {
        Persona hombre = listHombres.getSelectionModel().getSelectedItem();
        Persona mujer = listMujeres.getSelectionModel().getSelectedItem();
        if (hombre != null && mujer != null) {
            var tarea = new Task<Either<ApiError, ApiRespuesta>>() {

                @Override
                protected Either<ApiError, ApiRespuesta> call() {
                    return casamientosService.boda(hombre.getId(), mujer.getId());
                }
            };

            tarea.setOnSucceeded(workerStateEvent -> {
                tarea.getValue().peek(apiRespuesta -> {
                            alert.setContentText(apiRespuesta.getMessage());
                            alert.showAndWait();
                        })
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
            alert.setContentText(ConstantesGui.DEBES_SELECCIONAR_UN_HOMBRE_Y_UNA_MUJER);
            alert.showAndWait();
        }
    }
}
