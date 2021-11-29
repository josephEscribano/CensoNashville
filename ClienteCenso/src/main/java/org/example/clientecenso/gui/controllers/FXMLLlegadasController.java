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
import org.example.common.utils.ConstantesCommon;

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
    @FXML
    private TextField tfNhijos;
    @FXML
    private TextField tfFiltroFecha;

    public void setPrincipal(FXMLPrincipalController fxmlPrincipalController) {
        this.principal = fxmlPrincipalController;
    }

    public void loadSexo() {
        cbSexo.getItems().setAll(Sexo.HOMBRE, Sexo.MUJER);
    }

    public void loadEstadoCivil() {
        cbEcivil.getItems().setAll(null, EstadoCivil.CASADO, EstadoCivil.SOLTERO, EstadoCivil.VIUDO);
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

    public void insertPersona() {

        Persona persona = new Persona();

        if (cbEcivil.getValue() != null) {
            persona.setNombre(tfNombre.getText());
            persona.setEstadoCivil(cbEcivil.getValue());
            persona.setSexo(cbSexo.getValue());
            persona.setIdPersonaCasada(0);
            persona.setNacimiento(dpNacimiento.getValue());

            persona.setLugarNacimiento(tfProcedencia.getText());
            var tarea = new Task<Either<ApiError, Boolean>>() {

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
        } else {
            alert.setContentText(ConstantesGui.SELECCIONA_UN_ESTADO_CIVIL);
            alert.showAndWait();
        }


    }

    public void deletePersona() {
        Persona persona = listPersonas.getSelectionModel().getSelectedItem();
        if (persona != null) {
            var tarea = new Task<Either<ApiError, ApiRespuesta>>() {

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

        } else {
            alert.setContentText(ConstantesGui.SELECCIONA_UNA_PERSONA);
            alert.showAndWait();
        }

    }

    public void showData() {
        Persona persona = listPersonas.getSelectionModel().getSelectedItem();
        tfNombre.setText(persona.getNombre());
        cbEcivil.setValue(persona.getEstadoCivil());
        cbSexo.setValue(persona.getSexo());
        dpNacimiento.setValue(persona.getNacimiento());
        tfProcedencia.setText(persona.getLugarNacimiento());
    }

    //ten en cuenta que las personas que sean hijos he puesto su estado civil como null hasta que muere su padre, que pasa a ser soltero.
    //por esa razon los hijos no se muestra su estado civil al seleccionarlos
    public void updatePersona() {
        Persona persona = listPersonas.getSelectionModel().getSelectedItem();
        if (persona != null) {
            //He puesto que se puedan actualizar tanto el año como la procedencia, para que no sea un unico campo nombre el que se actualice
            persona.setNombre(tfNombre.getText());
            persona.setNacimiento(dpNacimiento.getValue());
            persona.setLugarNacimiento(tfProcedencia.getText());

            int index = listPersonas.getItems().indexOf(persona);
            var tarea = new Task<Either<ApiError, ApiRespuesta>>() {

                @Override
                protected Either<ApiError, ApiRespuesta> call() {
                    return personasService.updatePersona(persona);
                }
            };

            tarea.setOnSucceeded(workerStateEvent -> {
                tarea.getValue().peek(correcto -> {
                            if (correcto.getMessage().equals(ConstantesCommon.PERSONA_ACTUALIZADA)) {
                                listPersonas.getItems().set(index, persona);
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


        } else {
            alert.setContentText(ConstantesGui.SELECCIONA_UNA_PERSONA);
            alert.showAndWait();
        }

    }

    public void filtrado() {
        String filtroFecha = tfFiltroFecha.getText();
        int filtroNhijos = -1;
        String filtroEcivil = "";
        String filtroLugar = tfProcedencia.getText();

        if (cbEcivil.getValue() != null) {
            filtroEcivil = cbEcivil.getValue().toString();
        }

        if (!tfNhijos.getText().isEmpty()) {
            filtroNhijos = Integer.parseInt(tfNhijos.getText());
        }
        // He tenidoque poner las varibles de abajo porque sino no me dejaba pasar los parametros en caso de que estos varien.
        int finalFiltroNhijos = filtroNhijos;
        String finalFiltroEcivil = filtroEcivil;
        var tarea = new Task<Either<ApiError, List<Persona>>>() {

            @Override
            protected Either<ApiError, List<Persona>> call() {
                return personasService.filtrar(filtroLugar, filtroFecha, finalFiltroNhijos, finalFiltroEcivil);
            }
        };

        tarea.setOnSucceeded(workerStateEvent -> {
            tarea.getValue().peek(personas -> listPersonas.getItems().setAll(personas))
                    .peekLeft(apiError -> {
                        loadPersonas();
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
}
