package org.example.clientecenso.gui.controllers;

import io.vavr.control.Either;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import org.example.clientecenso.gui.utils.ConstantesGui;
import org.example.clientecenso.service.PersonasService;
import org.example.common.errores.ApiError;
import org.example.common.modelos.ApiRespuesta;
import org.example.common.modelos.EstadoCivil;
import org.example.common.modelos.Persona;
import org.example.common.modelos.Sexo;

import javax.inject.Inject;
import java.util.List;

public class FXMLLlegadasController {
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private FXMLPrincipalController principal;
    @Inject
    private PersonasService personasService;

    @FXML
    private ListView<Persona> listPersonas;
    @FXML
    private TextField tfNombre;
    @FXML
    private ComboBox<EstadoCivil> cbEcivil;
    @FXML
    private ComboBox<Sexo> cbSexo;
    @FXML
    private DatePicker dpNacimiento;
    @FXML
    private TextField tfProcedencia;

    public void setPrincipal(FXMLPrincipalController fxmlPrincipalController) {
        this.principal = fxmlPrincipalController;
    }

    public void loadSexo(){
        cbSexo.getItems().addAll(Sexo.HOMBRE,Sexo.MUJER);
    }
    public void loadEstadoCivil(){
        cbEcivil.getItems().addAll(EstadoCivil.CASADO,EstadoCivil.SOLTERO,EstadoCivil.VIUDO);
    }
    public void loadPersonas() {
        var tarea = new Task<Either<ApiError, List<Persona>>>(){

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

    public void insertPersona(){

        Persona persona = new Persona();
        persona.setNombre(tfNombre.getText());
        persona.setEstadoCivil(cbEcivil.getValue());
        persona.setIdPersonaCasada(0);
        persona.setNacimiento(dpNacimiento.getValue());
        persona.setSexo(cbSexo.getValue());
        persona.setLugarNacimiento(tfProcedencia.getText());
        var tarea = new Task<Either<ApiError,Boolean>>(){

            @Override
            protected Either<ApiError, Boolean> call() {
                return personasService.insertPersona(persona);
            }
        };

        tarea.setOnSucceeded(workerStateEvent -> {
            tarea.getValue().peek(confirmacion -> {
                        if (confirmacion) {
                            loadPersonas();
                        }
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
    }

    public void deletePersona(){
        Persona persona = listPersonas.getSelectionModel().getSelectedItem();
        if (persona != null){
            var tarea = new Task<Either<ApiError, ApiRespuesta>>(){

                @Override
                protected Either<ApiError, ApiRespuesta> call() {
                    return personasService.deletePersona(persona.getId());
                }
            };

            tarea.setOnSucceeded(workerStateEvent -> {
                tarea.getValue().peek(apirespuesta -> {
                            loadPersonas();
                            alert.setContentText(apirespuesta.getMessage());
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

        }else{
            alert.setContentText(ConstantesGui.SELECCIONA_UNA_PERSONA);
            alert.showAndWait();
        }

    }
    //continuar update y filtro
    public void updatePersona(){
        Persona persona = listPersonas.getSelectionModel().getSelectedItem();
    }
}
