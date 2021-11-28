package org.example.clientecenso.service;

import org.example.clientecenso.dao.DaoPersonas;

import javax.inject.Inject;

public class DefuncionesService {
    private final DaoPersonas dao;

    @Inject
    public DefuncionesService(DaoPersonas dao) {
        this.dao = dao;
    }
}
