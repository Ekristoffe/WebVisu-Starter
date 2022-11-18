package WebVisuClass;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateConf {

    private static final Logger LOGGER = Logger.getLogger(CreateConf.class.getName());

    private static final String EQUAL_DELIMITER = " = ";
    private static final String NEW_LINE_SEPARATOR = "\r\n";

    public static void writeConfFile(Boolean xHttps, String sIP, String sPath, String sVisu, ArrayList<AppletParameter> alAppletParameter) {

        String _sFileName;
        FileWriter _fwFileWriter = null;

        _sFileName = "webclient_conf.ini";

        try {
            _fwFileWriter = new FileWriter(_sFileName, false);  // true to append / false to overwrite.
            _fwFileWriter.append("URL");
            _fwFileWriter.append(EQUAL_DELIMITER);
            if (xHttps) {
                _fwFileWriter.append("https://");
            } else {
                _fwFileWriter.append("http://");
            }
            _fwFileWriter.append(sIP);
            _fwFileWriter.append(sPath);
            _fwFileWriter.append(NEW_LINE_SEPARATOR);

            if (sVisu != null) {
                _fwFileWriter.append("STARTVISU");
                _fwFileWriter.append(EQUAL_DELIMITER);
                _fwFileWriter.append(sVisu);
                _fwFileWriter.append(NEW_LINE_SEPARATOR);
            }

            //Write a new AppletParameter object list to the Configuration file
            for (AppletParameter _AppletParameter : alAppletParameter) {
                if ((!_AppletParameter.getName().equals("STARTVISU")) || (sVisu == null)) {
                    _fwFileWriter.append(_AppletParameter.getName());
                    _fwFileWriter.append(EQUAL_DELIMITER);
                    _fwFileWriter.append(_AppletParameter.getValue());
                    _fwFileWriter.append(NEW_LINE_SEPARATOR);
                }
            }

            LOGGER.log(Level.FINE, "Configuration file was created successfully !!!");

        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } finally {
            if (_fwFileWriter != null) {
                try {
                    _fwFileWriter.flush();
                    try {
                        _fwFileWriter.close();
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                    }
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                }
            }
        }
    }

    public static void main(String[] args) {
        //Create a new list of station to be filled by CSV file data 
        ArrayList<AppletParameter> alAppletParameter;

        String _sIP = "192.168.0.4"; //needs to be replaced with local file path

        alAppletParameter = ParseApplet.parseHtmFile();

        writeConfFile(false, _sIP, "/PLC/", null, alAppletParameter);
    }

}
