package org.example.clientecenso.service;

import org.example.clientecenso.dao.DaoPersonas;

import javax.inject.Inject;

public class NacimientosService {
    private final DaoPersonas dao;

    @Inject
    public NacimientosService(DaoPersonas dao) {
        this.dao = dao;
    }
}
