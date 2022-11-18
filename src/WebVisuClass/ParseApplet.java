package WebVisuClass;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParseApplet {

    private static final Logger LOGGER = Logger.getLogger(ParseApplet.class.getName());

    private static final String APPLET_START = "<APPLET";
    private static final String APPLET_NAME = "name=";
    private static final String APPLET_VALUE = "value=";
    private static final String APPLET_SEPARATOR = "\"";

    public static ArrayList<AppletParameter> parseHtmFile() {

        BufferedReader _brFileReader = null;

        //Create a new list of station to be filled by CSV file data 
        ArrayList<AppletParameter> alAppletParameter = new ArrayList<>();

        try {
            String _sLine;

            //Create the file reader
            _brFileReader = new BufferedReader(new FileReader("webvisu.htm"));

            Integer _iAppletStart;
            Integer _iLineIndex;
            Integer _iObjectIndex;
            Integer _iStart;
            Integer _iEnd;
            String _sName;
            String _sValue;
            Boolean _xAppletFound;
            Boolean _xLineScanned;
            _xAppletFound = false;
            _sName = "";

            //Read the file line by line starting from the second line
            _sLine = _brFileReader.readLine();
            while (_sLine != null) {
                _iAppletStart = _sLine.indexOf(APPLET_START);
                if (_iAppletStart != -1) {
                    _xAppletFound = true;
                }
                if (_xAppletFound) {
                    _iLineIndex = 0;
                    _xLineScanned = false;
                    while (!_xLineScanned) {
                        _xLineScanned = true;
                        _iObjectIndex = _sLine.indexOf(APPLET_NAME, _iLineIndex);
                        if (_iObjectIndex != -1) {
                            _xLineScanned = false;
                            _iStart = _sLine.indexOf(APPLET_SEPARATOR, _iObjectIndex) + 1;
                            _iEnd = _sLine.indexOf(APPLET_SEPARATOR, _iStart);
                            _sName = _sLine.substring(_iStart, _iEnd);
                            _iLineIndex = _iEnd + 1;
                        }
                        _iObjectIndex = _sLine.indexOf(APPLET_VALUE, _iLineIndex);
                        if (_iObjectIndex != -1) {
                            _xLineScanned = false;
                            _iStart = _sLine.indexOf(APPLET_SEPARATOR, _iObjectIndex) + 1;
                            _iEnd = _sLine.indexOf(APPLET_SEPARATOR, _iStart);
                            _sValue = _sLine.substring(_iStart, _iEnd);
                            _iLineIndex = _iEnd + 1;
                            if (!_sValue.contains(",")) {
                                //Create a new student object and fill his  data
                                AppletParameter _station = new AppletParameter(_sName, _sValue);
                                alAppletParameter.add(_station);
                            }
                        }
                    }
                }
                _sLine = _brFileReader.readLine();
            }
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } finally {
            if (_brFileReader != null) {
                try {
                    _brFileReader.close();
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                }
            }
        }

        return alAppletParameter;

    }

    public static void main(String[] args) {
        parseHtmFile();
    }
}
