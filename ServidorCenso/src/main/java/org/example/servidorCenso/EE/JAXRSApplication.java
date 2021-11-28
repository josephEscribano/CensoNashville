package org.example.servidorCenso.EE;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.example.servidorCenso.EE.utils.ConstantesRest;

@ApplicationPath(ConstantesRest.PATH_API)
public class JAXRSApplication extends Application {
}
