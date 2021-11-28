module ClienteCenso {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires lombok;
    requires io.vavr;
    requires retrofit2;
    requires okhttp3;
    requires retrofit2.adapter.rxjava2;
    requires retrofit2.converter.gson;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires javafx.graphics;
    requires jakarta.enterprise.cdi.api;
    requires jakarta.inject.api;
    requires annotations;
    requires Common;

    opens org.example.clientecenso.gui;
    opens org.example.clientecenso.dao;
    opens org.example.clientecenso.gui.controllers;
    opens org.example.clientecenso.service;
    opens org.example.clientecenso.config;
    opens org.example.clientecenso.dao.retrofit;
    opens  org.example.clientecenso.dao.utils;

    exports org.example.clientecenso.gui;
    exports org.example.clientecenso.gui.controllers;
    exports org.example.clientecenso.dao;
    exports org.example.clientecenso.service;
    exports org.example.clientecenso.config;
    exports org.example.clientecenso.gui.utils;
//    exports org.example.clientecenso.dao.utils;
    exports org.example.clientecenso.dao.retrofit;

}