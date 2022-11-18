package WebVisuClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunJavaWebVisu {

    private static final Logger LOGGER = Logger.getLogger(RunJavaWebVisu.class.getName());

    public static void runJavaWebVisu(String sAddress) {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String[] _asCommand;
                _asCommand = new String[] {"java", "-classpath", ".;rt.jar;minml.jar;webvisu.jar", "webvisu.WebVisuFrame"};
                // Run a java app in a separate system process
                Process _pProc = null;
                try {
                    String sFilename = "./log_" + sAddress + ".txt";
                    File _fLogFile = null;
                    _fLogFile = new File(sFilename);
                    if (_fLogFile.exists())
                        _fLogFile.delete();
                    _fLogFile.createNewFile();
                    _pProc = Runtime.getRuntime().exec(_asCommand);
                    ReadStream _rsReadInput = new ReadStream("stdin", _pProc.getInputStream (), sFilename);
                    ReadStream _rsReadError = new ReadStream("stderr", _pProc.getErrorStream ());
                    _rsReadInput.start ();
                    _rsReadError.start ();
                    _pProc.waitFor();
                } catch (IOException | InterruptedException ex) {  
                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                } finally {
                    if(_pProc != null)
                        _pProc.destroy();
                }
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        runJavaWebVisu("192.168.1.1");
    }
}
