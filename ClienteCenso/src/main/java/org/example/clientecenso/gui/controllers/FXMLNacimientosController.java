package org.example.clientecenso.gui.controllers;

import io.vavr.control.Either;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.clientecenso.gui.utils.ConstantesGui;
import org.example.clientecenso.service.CasamientosService;
import org.example.clientecenso.service.NacimientosService;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.Persona;
import org.example.common.modelos.Sexo;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

public class FXMLNacimientosController {
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private FXMLPrincipalController principal;
    @Inject
    private CasamientosService casamientosService;

    @Inject
    private NacimientosService nacimientosService;
    @FXML
    private ListView<Persona> listHombres;
    @FXML
    private ListView<Persona> listMujeres;
    @FXML
    private TextField tfNombre;
    @FXML
    private ComboBox<Sexo> cbSexo;

    public void setPrincipal(FXMLPrincipalController fxmlPrincipalController) {
        this.principal = fxmlPrincipalController;
    }

    public void loadSexo() {
        cbSexo.getItems().setAll(Sexo.HOMBRE, Sexo.MUJER);
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

    public void nacimiento() {
        Persona hombre = listHombres.getSelectionModel().getSelectedItem();
        Persona mujer = listMujeres.getSelectionModel().getSelectedItem();
        Persona persona = new Persona();
        persona.setNombre(tfNombre.getText());
        persona.setSexo(cbSexo.getValue());
        persona.setNacimiento(LocalDate.now());
        persona.setLugarNacimiento(ConstantesGui.NASHVILLE);

        if (hombre != null && mujer != null) {
            var tarea = new Task<Either<ApiError, ApiRespuesta>>() {

                @Override
                protected Either<ApiError, ApiRespuesta> call() {
                    return nacimientosService.nacimiento(hombre.getId(), mujer.getId(), persona);
                }
            };

            tarea.setOnSucceeded(workerStateEvent -> {
                tarea.getValue().peek(apiRespuesta -> {
                            loadHombres();
                            loadMujeres();
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
            alert.setContentText(ConstantesGui.SELECCIONA_PADRE_MADRE);
            alert.showAndWait();
        }


    }
}
