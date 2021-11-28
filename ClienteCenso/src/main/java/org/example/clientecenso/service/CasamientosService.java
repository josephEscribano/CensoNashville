package org.example.clientecenso.service;

import org.example.clientecenso.dao.DaoPersonas;

import javax.inject.Inject;

public class CasamientosService {
    private final DaoPersonas dao;

    @Inject
    public CasamientosService(DaoPersonas dao) {
        this.dao = dao;
    }
}
