package org.example.servidorCenso.dao;

import io.vavr.control.Either;
import org.example.common.errores.ApiError;
import org.example.common.modelos.*;
import org.example.servidorCenso.EE.utils.ConstantesRest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DaoPersonas {
    private static final List<Persona> personas = new ArrayList<>();

    static {

        Persona hijo1 = new Persona(3, "Roberto", Sexo.HOMBRE, LocalDate.of(2000, 8, 14), "Nashville");
        Persona hijo2 = new Persona(4, "Maria", Sexo.MUJER, LocalDate.of(2010, 10, 20), "Nashville");
        Persona hijo3 = new Persona(7, "Paco", Sexo.HOMBRE, LocalDate.of(1995, 8, 14), "Nashville");
        Persona hijo4 = new Persona(9, "Laura", Sexo.MUJER, LocalDate.of(1998, 10, 20), "Nashville");
        //Añadir hijos a la lista de personas
        personas.add(hijo1);
        personas.add(hijo2);
        personas.add(hijo3);
        personas.add(hijo4);
        List<RelacionPadreHijo> hijos1 = new ArrayList<>();
        hijos1.add(new RelacionPadreHijo(hijo1.getId()));
        hijos1.add(new RelacionPadreHijo(hijo2.getId()));

        List<RelacionPadreHijo> hijos2 = new ArrayList<>();
        hijos2.add(new RelacionPadreHijo(hijo1.getId()));
        hijos2.add(new RelacionPadreHijo(hijo2.getId()));
        personas.add(new Persona(1, "Jose", EstadoCivil.CASADO, Sexo.HOMBRE, LocalDate.of(1980, 6, 19), "Madrid", 2, hijos1));
        personas.add(new Persona(2, "Ana", EstadoCivil.CASADO, Sexo.MUJER, LocalDate.of(1979, 1, 10), "Madrid", 1, hijos2));


        List<RelacionPadreHijo> hijos3 = new ArrayList<>();
        hijos3.add(new RelacionPadreHijo(hijo3.getId()));
        hijos3.add(new RelacionPadreHijo(hijo4.getId()));

        List<RelacionPadreHijo> hijos4 = new ArrayList<>();
        hijos4.add(new RelacionPadreHijo(hijo3.getId()));
        hijos4.add(new RelacionPadreHijo(hijo4.getId()));


        personas.add(new Persona(5, "Alberto", EstadoCivil.CASADO, Sexo.HOMBRE, LocalDate.of(1972, 11, 15), "Barcelona", 6, hijos3));
        personas.add(new Persona(6, "Sara", EstadoCivil.CASADO, Sexo.MUJER, LocalDate.of(1973, 12, 26), "Barcelona", 5, hijos4));

        personas.add(new Persona(10, "Mario", EstadoCivil.SOLTERO, Sexo.HOMBRE, LocalDate.of(2003, 8, 25), "Madrid"));
        personas.add(new Persona(11, "Diana", EstadoCivil.SOLTERO, Sexo.MUJER, LocalDate.of(1990, 8, 25), "Sevilla"));
    }

    public Either<ApiError, List<Persona>> getAll() {
        Either<ApiError, List<Persona>> resultado;

        if (!personas.isEmpty()) {
            resultado = Either.right(personas);
        } else {
            resultado = Either.left(new ApiError(ConstantesDao.NO_HAY_PERSONAS_REGISTRADAS, LocalDate.now()));
        }
        return resultado;
    }

    public boolean insertPersona(Persona persona) {
        boolean confirmacion = false;
        Persona pers = personas.stream().reduce(((persona1, persona2) -> persona1.getId() >= persona2.getId() ? persona1 : persona2)).orElse(null);
        if (persona.getEstadoCivil() == EstadoCivil.SOLTERO) {
            if (pers != null) {
                persona.setId(pers.getId() + 1);

            } else {
                persona.setId(1);
            }

            personas.add(persona);
            persona.setIdPersonaCasada(0);
            persona.setHijos(new ArrayList<>());
            confirmacion = true;
        }

        return confirmacion;
    }

    public void insertHijos(Persona persona) {
        Persona pers = personas.stream().reduce(((persona1, persona2) -> persona1.getId() >= persona2.getId() ? persona1 : persona2)).orElse(null);
        if (pers != null) {
            persona.setId(pers.getId() + 1);

        } else {
            persona.setId(1);
        }

        personas.add(persona);
        persona.setIdPersonaCasada(0);
        persona.setEstadoCivil(null);
        persona.setHijos(new ArrayList<>());

    }


    public Either<ApiError, ApiRespuesta> deletePersona(int id) {

        Either<ApiError, ApiRespuesta> resultado;
        Persona persona = personas.stream().filter(persona1 -> persona1.getId() == id).findFirst().orElse(null);
        int contador = 0;
        if (persona != null) {
            boolean hijo = false;
            for (Persona persona1 : personas) {
                for (RelacionPadreHijo relacionPadreHijo : persona1.getHijos()) {
                    if (relacionPadreHijo.getIdhijo() == id) {
                        hijo = true;
                    }
                }
            }
            // si es false es que no es hijo de nadie y entro para que pueda hacer las comprobaciones
            if (!hijo) {
                if (!persona.getHijos().isEmpty()) {
                    for (RelacionPadreHijo relacionPadreHijo : persona.getHijos()) {
                        personas.removeIf(persona3 -> persona3.getId() == relacionPadreHijo.getIdhijo());
                        contador++;
                    }
                    if (persona.getEstadoCivil() == EstadoCivil.CASADO) {
                        personas.removeIf(persona1 -> persona1.getId() == persona.getIdPersonaCasada());
                        personas.removeIf(persona1 -> persona1.getId() == persona.getId());
                        contador += 2;
                    }
                } else {
                    personas.removeIf(persona1 -> persona1.getId() == persona.getId());
                    contador++;
                }
                resultado = Either.right(new ApiRespuesta(ConstantesDao.PERSONAS_BORRADA + contador));
            } else {
                resultado = Either.left(new ApiError(ConstantesDao.NO_PUEDES_BORRAR_UN_HIJO, LocalDate.now()));
            }
        } else {
            resultado = Either.left(new ApiError(ConstantesDao.LA_PERSONA_NO_EXISTE, LocalDate.now()));
        }

        return resultado;

    }


    public boolean updatePersona(Persona persona) {
        boolean confirmacion = false;

        Persona persona1 = personas.stream().filter(persona2 -> persona2.getId() == persona.getId()).findFirst().orElse(null);

        if (persona1 != null) {
            int index = personas.indexOf(persona1);
            personas.set(index, persona);
            confirmacion = true;
        }

        return confirmacion;
    }


    // nacimientos

    public Either<ApiError, List<Persona>> getSoloPersonas() {
        Either<ApiError, List<Persona>> resultado;

        List<Persona> listPersonas = personas.stream().filter(persona -> persona.getEstadoCivil() != null).collect(Collectors.toList());

        if (!listPersonas.isEmpty()) {
            resultado = Either.right(listPersonas);
        } else {
            resultado = Either.left(new ApiError(ConstantesDao.NO_EXISTEN_PERSONAS_QUE_PUEDAN_TENER_HIJOS, LocalDate.now()));
        }

        return resultado;
    }

    public void exiliarPersona(Persona persona) {
        AtomicBoolean tieneMujer = new AtomicBoolean(false);
        if (persona.getEstadoCivil() == EstadoCivil.CASADO) {
            personas.forEach(persona1 -> {
                if (persona1.getId() == persona.getIdPersonaCasada()) {
                    persona1.setIdPersonaCasada(0);
                    tieneMujer.set(true);
                }
            });
            if (!tieneMujer.get() || persona.getIdPersonaCasada() == 0) {
                if (!persona.getHijos().isEmpty()) {
                    for (RelacionPadreHijo relacionPadreHijo : persona.getHijos()) {
                        personas.forEach(persona1 -> {
                            if (persona1.getId() == relacionPadreHijo.getIdhijo()) {
                                persona1.setEstadoCivil(EstadoCivil.SOLTERO);
                            }
                        });
                    }
                    personas.remove(persona);
                } else {
                    personas.remove(persona);
                }
            }
            personas.remove(persona);
        } else {
            personas.remove(persona);
        }


    }

    public Either<ApiError, ApiRespuesta> naceHijo(int idpadre, int idmadre, Persona persona) {
        Either<ApiError, ApiRespuesta> resultado;
        Persona padre = personas.stream().filter(persona1 -> persona1.getId() == idpadre).findFirst().orElse(null);

        Persona madre = personas.stream().filter(persona2 -> persona2.getId() == idmadre).findFirst().orElse(null);

        if (padre != null && madre != null) {
            boolean hijo = false;
            for (Persona persona1 : personas) {
                for (RelacionPadreHijo relacionPadreHijo : persona1.getHijos()) {
                    if (relacionPadreHijo.getIdhijo() == idpadre || relacionPadreHijo.getIdhijo() == idmadre) {
                        hijo = true;
                    }
                }
            }
            if (!hijo) {
                if (padre.getEstadoCivil() != EstadoCivil.SOLTERO && madre.getEstadoCivil() != EstadoCivil.SOLTERO) {
                    if (padre.getIdPersonaCasada() == madre.getId() && madre.getIdPersonaCasada() == padre.getId()) {
                        insertHijos(persona);
                        personas.get(personas.indexOf(padre)).getHijos().add(new RelacionPadreHijo(personas.get(personas.size() - 1).getId()));
                        personas.get(personas.indexOf(madre)).getHijos().add(new RelacionPadreHijo(personas.get(personas.size() - 1).getId()));

                        resultado = Either.right(new ApiRespuesta(ConstantesDao.EL_HIJO_HA_SIDO_AÑADIDO));
                    } else {
                        exiliarPersona(padre);
                        exiliarPersona(madre);
                        resultado = Either.left(new ApiError(ConstantesDao.LAS_PERSONAS_NO_ESTAN_CASADAS_Y_HAN_SIDO_EXILIADAS, LocalDate.now()));
                    }
                } else {
                    exiliarPersona(padre);
                    exiliarPersona(madre);
                    resultado = Either.left(new ApiError(ConstantesDao.LAS_PERSONAS_NO_ESTAN_CASADAS_Y_HAN_SIDO_EXILIADAS, LocalDate.now()));
                }
            } else {
                resultado = Either.left(new ApiError(ConstantesDao.LOS_HIJOS_DE_OTRAS_PERSONAS_NO_PUEDEN_TENER_HIJOS, LocalDate.now()));
            }
        } else {
            resultado = Either.left(new ApiError(ConstantesDao.NO_EXISTEN_ESAS_PERSONAS, LocalDate.now()));
        }


        return resultado;
    }

    public Either<ApiError, ApiRespuesta> boda(int idhombre, int idmujer) {
        Either<ApiError, ApiRespuesta> resultado;
        Persona hombre = personas.stream().filter(persona1 -> persona1.getId() == idhombre).findFirst().orElse(null);

        Persona mujer = personas.stream().filter(persona2 -> persona2.getId() == idmujer).findFirst().orElse(null);

        if (hombre != null && mujer != null) {
            boolean hijo = false;
            for (Persona persona1 : personas) {
                for (RelacionPadreHijo relacionPadreHijo : persona1.getHijos()) {
                    if (relacionPadreHijo.getIdhijo() == idhombre || relacionPadreHijo.getIdhijo() == idmujer) {
                        hijo = true;
                    }
                }
            }
            if (hombre.getSexo() == Sexo.HOMBRE && mujer.getSexo() == Sexo.MUJER) {
                if (!hijo) {
                    if (hombre.getEstadoCivil() == EstadoCivil.SOLTERO && mujer.getEstadoCivil() == EstadoCivil.SOLTERO) {
                        personas.get(personas.indexOf(hombre)).setEstadoCivil(EstadoCivil.CASADO);
                        personas.get(personas.indexOf(mujer)).setEstadoCivil(EstadoCivil.CASADO);
                        personas.get(personas.indexOf(hombre)).setIdPersonaCasada(mujer.getId());
                        personas.get(personas.indexOf(mujer)).setIdPersonaCasada(hombre.getId());
                        resultado = Either.right(new ApiRespuesta(ConstantesDao.LAS_PERSONAS_SE_HAN_CASADO));
                    } else {
                        resultado = Either.left(new ApiError(ConstantesDao.NO_SE_PUEDEN_CASAR_PERSONAS_QUE_NO_SEAN_SOLTERAS, LocalDate.now()));
                    }
                } else {
                    resultado = Either.left(new ApiError(ConstantesDao.NO_SE_PUEDEN_CASAR_NIÑOS, LocalDate.now()));
                }
            } else {
                resultado = Either.left(new ApiError(ConstantesDao.SEXO_OPUESTO, LocalDate.now()));
            }
        } else {
            resultado = Either.left(new ApiError(ConstantesDao.NO_EXISTEN_ESAS_PERSONAS, LocalDate.now()));
        }


        return resultado;

    }

    public Either<ApiError, ApiRespuesta> muerePersona(int id) {

        Either<ApiError, ApiRespuesta> resultado;
        Persona persona = personas.stream().filter(persona1 -> persona1.getId() == id).findFirst().orElse(null);

        if (persona != null) {
            boolean hijo = false;
            for (Persona persona1 : personas) {
                for (RelacionPadreHijo relacionPadreHijo : persona1.getHijos()) {
                    if (relacionPadreHijo.getIdhijo() == id) {
                        hijo = true;
                    }
                }
            }
            // falso si el id no pertenece al hijo de alguien y si pertence sera verdadero y por tanto no se borrará
            if (!hijo) {
                AtomicBoolean tieneMujer = new AtomicBoolean(false);
                if (persona.getEstadoCivil() == EstadoCivil.CASADO || persona.getEstadoCivil() == EstadoCivil.VIUDO) {
                    personas.forEach(persona1 -> {
                        if (persona1.getId() == persona.getIdPersonaCasada()) {
                            persona1.setIdPersonaCasada(0);
                            persona1.setEstadoCivil(EstadoCivil.VIUDO);
                            tieneMujer.set(true);
                        }
                    });
                    if (!tieneMujer.get() || persona.getIdPersonaCasada() == 0) {
                        if (!persona.getHijos().isEmpty()) {
                            for (RelacionPadreHijo relacionPadreHijo : persona.getHijos()) {
                                personas.forEach(persona1 -> {
                                    if (persona1.getId() == relacionPadreHijo.getIdhijo()) {
                                        persona1.setEstadoCivil(EstadoCivil.SOLTERO);
                                    }
                                });
                            }
                            personas.remove(persona);
                        } else {
                            personas.remove(persona);
                        }
                    }

                    personas.remove(persona);
                } else {
                    personas.remove(persona);
                }
                resultado = Either.right(new ApiRespuesta(ConstantesDao.LA_PERSONA_HA_MUERTO));
            } else {
                resultado = Either.left(new ApiError(ConstantesRest.EL_NIÑO_HA_RESUCITADO, LocalDate.now()));
            }
        } else {
            resultado = Either.left(new ApiError(ConstantesDao.NO_EXISTEN_ESAS_PERSONAS, LocalDate.now()));
        }

        return resultado;
    }

    public Either<ApiError, List<Persona>> filtrado(String lugar, String nacimiento, int nhijos, String ecivil) {
        Either<ApiError, List<Persona>> resultado;
        boolean confirmacion = false;
        List<Persona> personasFiltradas = personas;
        if (!lugar.isEmpty()) {
            personasFiltradas = personasFiltradas.stream().filter(persona -> persona.getLugarNacimiento().equals(lugar)).collect(Collectors.toList());
            confirmacion = true;
        }
        if (!nacimiento.isEmpty()) {
            personasFiltradas = personasFiltradas.stream().filter(persona -> String.valueOf(persona.getNacimiento().getYear()).equals(nacimiento)).collect(Collectors.toList());
            confirmacion = true;
        }
        if (nhijos > -1) {
            personasFiltradas = personasFiltradas.stream().filter(persona -> persona.getHijos().size() == nhijos).collect(Collectors.toList());
            confirmacion = true;
        }

        if (!ecivil.isEmpty()) {
            personasFiltradas = personasFiltradas.stream().filter(persona -> String.valueOf(persona.getEstadoCivil()).equals(ecivil)).collect(Collectors.toList());
            confirmacion = true;
        }

        if (confirmacion) {
            resultado = Either.right(personasFiltradas);
        } else {
            resultado = Either.left(new ApiError(ConstantesDao.TODOS_LOS_CAMPOS_ESTAN_VACIOS, LocalDate.now()));
        }

        return resultado;
    }
}
