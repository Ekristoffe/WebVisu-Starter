package WebVisuClass;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunHtmlWebVisu {

    private static final Logger LOGGER = Logger.getLogger(RunHtmlWebVisu.class.getName());

    public static void runWebVisuURI(String sWebVisu) {

        URI _uriWebVisu;

        try {
            _uriWebVisu = new URI(sWebVisu);
            try {
                Desktop.getDesktop().browse(_uriWebVisu); // Launches the associated application to open the URI.
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        } catch (URISyntaxException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }

    }

    public static void main(String[] args) {
        String _sWebVisu;
        _sWebVisu = "http://192.168.1.18";
        runWebVisuURI(_sWebVisu);
    }
}
