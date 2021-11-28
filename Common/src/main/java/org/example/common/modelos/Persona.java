package org.example.common.modelos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Persona {
    private int id;
    private String nombre;
    private EstadoCivil estadoCivil;
    private Sexo sexo;
    private LocalDate nacimiento;
    private String lugarNacimiento;
    private int idPersonaCasada;
    private List<RelacionPadreHijo> hijos;

    public Persona(int id, String nombre, EstadoCivil estadoCivil, Sexo sexo, LocalDate nacimiento, String lugarNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
        this.nacimiento = nacimiento;
        this.lugarNacimiento = lugarNacimiento;
        this.idPersonaCasada = 0;
        this.hijos = new ArrayList<>();
    }

    public Persona(int id, String nombre, Sexo sexo, LocalDate nacimiento, String lugarNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.sexo = sexo;
        this.nacimiento = nacimiento;
        this.lugarNacimiento = lugarNacimiento;
        this.estadoCivil = null;
        this.idPersonaCasada = 0;
        this.hijos = new ArrayList<>();
    }

    public Persona(int id, String nombre, EstadoCivil estadoCivil, Sexo sexo, LocalDate nacimiento, String lugarNacimiento, int idPersonaCasada, List<RelacionPadreHijo> hijos) {
        this.id = id;
        this.nombre = nombre;
        this.estadoCivil = estadoCivil;
        this.sexo = sexo;
        this.nacimiento = nacimiento;
        this.lugarNacimiento = lugarNacimiento;
        this.idPersonaCasada = idPersonaCasada;
        this.hijos = hijos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return id == persona.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", nombre='" + nombre + '\n' +
                ", estadoCivil=" + estadoCivil + '\n' +
                ", sexo=" + sexo + '\n'+
                ", nacimiento=" + nacimiento + '\n' +
                ", lugarNacimiento='" + lugarNacimiento + '\n' +
                ", idPersonaCasada=" + idPersonaCasada + '\n' +
                ", hijos " + hijos;
    }
}
