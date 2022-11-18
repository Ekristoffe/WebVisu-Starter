package WebVisuClass;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalFiles {

    private static final Logger LOGGER = Logger.getLogger(LocalFiles.class.getName());

    public static boolean checkFile(String sFilename) {
        File _fFile = new File(sFilename);
        return (_fFile.exists() && !_fFile.isDirectory());
    }

    public static void cleanFiles(String sFilename) {
        File _fFile = new File(sFilename);
        if (_fFile.exists() && !_fFile.isDirectory()) {
            LOGGER.log(Level.FINE, "Delete: {0}", sFilename);
            _fFile.delete();
        }
    }

    public static void main(String[] args) {

        Boolean _xWebvisuhtm;
        Boolean _xWebvisujar;
        Boolean _xMinmljar;
        String _sWebvisuhtm = "webvisu.htm"; //needs to be replaced with local file path
        String _sWebvisujar = "webvisu.jar"; //needs to be replaced with local file path
        String _sMinmljar = "minml.jar"; //needs to be replaced with local file path
        _xWebvisuhtm = checkFile(_sWebvisuhtm);
        LOGGER.log(Level.INFO, "Is file {0} exist: {1}", new Object[]{_sWebvisuhtm, _xWebvisuhtm});
        if (_xWebvisuhtm) {
            cleanFiles(_sWebvisuhtm);
            LOGGER.log(Level.INFO, "Delete: {0}", new Object[]{_sWebvisuhtm});
        }
        _xWebvisujar = checkFile(_sWebvisujar);
        LOGGER.log(Level.INFO, "Is file {0} exist: {1}", new Object[]{_sWebvisujar, _xWebvisujar});
        if (_xWebvisujar) {
            cleanFiles(_sWebvisujar);
            LOGGER.log(Level.INFO, "Delete: {0}", new Object[]{_sWebvisujar});
        }
        _xMinmljar = checkFile(_sMinmljar);
        LOGGER.log(Level.INFO, "Is file {0} exist: {1}", new Object[]{_sMinmljar, _xMinmljar});
        if (_xMinmljar) {
            cleanFiles(_sMinmljar);
            LOGGER.log(Level.INFO, "Delete: {0}", new Object[]{_sMinmljar});
        }
    }
}
