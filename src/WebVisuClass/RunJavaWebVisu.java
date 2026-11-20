package WebVisuClass;

import java.io.File;
//import java.io.FileWriter;
import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RunJavaWebVisu {

    //private static final String KEY_STORE_PASS = "changeit";
    //private static final String KEY_STORE_FILE = "webVisuKeyStore.jks";
    
    private static final Logger LOGGER = Logger.getLogger(RunJavaWebVisu.class.getName());

    public static void runJavaWebVisu(Boolean xHttps, String sAddress) {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                String[] _asCommand;
                /*
                if (xHttps) {
                    String _sKeyStore = Paths.get(KEY_STORE_FILE).toAbsolutePath().toString();
                    LOGGER.log(Level.INFO, "KeyStore full path: {0}", _sKeyStore);
                    /*
                    // Path to your client identity certificate store (JKS or PKCS12 format)
                    System.setProperty("javax.net.ssl.keyStore", _sKeyStore);
                    // The password required to open the KeyStore file
                    System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASS);
                    // Optional: Defaults to "JKS". Set to "PKCS12" if using a .p12 or .pfx file
                    System.setProperty("javax.net.ssl.keyStoreType", "JKS");
                    // Define the path to your custom JKS or PKCS12 file
                    System.setProperty("javax.net.ssl.trustStore", _sKeyStore);
                    // Define the password required to open/verify the truststore integrity
                    System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASS);
                    * /
                    _asCommand = new String[] {"java", "-classpath", ".;rt.jar;minml.jar;webvisu.jar", "webvisu.WebVisuFrame", "-Dcom.sun.jndi.ldap.object.disableEndpointIdentification=true", "-Djavax.net.ssl.keyStore="+_sKeyStore, "-Djavax.net.ssl.keyStorePassword="+KEY_STORE_PASS, "-Djavax.net.ssl.trustStore="+_sKeyStore, "-Djavax.net.ssl.trustStorePassword="+KEY_STORE_PASS};
                } else {
                    _asCommand = new String[] {"java", "-classpath", ".;rt.jar;minml.jar;webvisu.jar", "webvisu.WebVisuFrame"};
                }
                */
                _asCommand = new String[] {"java", "-classpath", ".;rt.jar;minml.jar;webvisu.jar", "webvisu.WebVisuFrame"};
                // Run a java app in a separate system process
                Process _pProc = null;
                try {
                    String sFilename = "./log_" + sAddress + ".txt";
                    //File _fLogFile = null;
                    //_fLogFile = new File(sFilename);
                    File _fLogFile = new File(sFilename);
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
        runJavaWebVisu(false,"192.168.1.1");
    }
}
