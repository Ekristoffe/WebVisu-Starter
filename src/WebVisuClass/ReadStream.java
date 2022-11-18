package WebVisuClass;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReadStream implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(CreateConf.class.getName());
    
    private static final String NEW_LINE_SEPARATOR = "\r\n";
    
    String sName;
    InputStream isInputStream;
    String sFilename;
    Thread tThread;      
    public ReadStream(String sName, InputStream isInputStream, String sFilename) {
        this.sName = sName;
        this.isInputStream = isInputStream;
        this.sFilename = sFilename;
    }       
    public ReadStream(String sName, InputStream isInputStream) {
        this.sName = sName;
        this.isInputStream = isInputStream;
        this.sFilename = null;
    }  
    public void start () {
        tThread = new Thread (this);
        tThread.start ();
    }       
    @Override
    public void run () {
        FileWriter _fwFileWriter = null;
        try {
            InputStreamReader _isrInputStreamReader = new InputStreamReader (isInputStream);
            BufferedReader _brBufferedReader = new BufferedReader (_isrInputStreamReader);
            if (sFilename != null) {
                _fwFileWriter = new FileWriter(sFilename, true);
            }
            while (true) {
                String _sLine = _brBufferedReader.readLine();
                if (_sLine == null) break;
                System.out.println ("[" + sName + "] " + _sLine);
                if (_fwFileWriter != null) {
                    _fwFileWriter.append(_sLine);
                    _fwFileWriter.append(NEW_LINE_SEPARATOR);
                    _fwFileWriter.flush();
                }
            }
            isInputStream.close ();    
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
}
