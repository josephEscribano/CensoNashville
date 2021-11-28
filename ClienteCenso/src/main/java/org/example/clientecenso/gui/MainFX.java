package org.example.clientecenso.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.example.clientecenso.gui.utils.ConstantesGui;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

@Log4j2
public class MainFX {

    @Inject
    FXMLLoader fxmlLoader;

    public void start(@Observes @StartupScene Stage stage) {
        try {
            Parent fxmlparent = fxmlLoader.load(getClass().getResourceAsStream(ConstantesGui.FXML_FXMLPRINCIPAL_FXML));
            stage.setScene(new Scene(fxmlparent));
            stage.setTitle(ConstantesGui.TITULO_PRINCIPAL);
            stage.show();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

    }
}
