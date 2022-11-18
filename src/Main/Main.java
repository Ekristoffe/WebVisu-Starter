package Main;

import WebVisuClass.AppletParameter;
import WebVisuClass.CreateConf;
import WebVisuClass.HTTP_HTTPs;
import WebVisuClass.NetworkAddress;
import WebVisuClass.LocalFiles;
import WebVisuClass.ParseApplet;
import WebVisuClass.Ping;
import WebVisuClass.RunJavaWebVisu;
import WebVisuClass.Version;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String WEBVISU_JAR = "webvisu.jar";
    private static final String MINML_JAR = "minml.jar";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                LocalFiles.cleanFiles("webclient_conf.ini");
              }
        });
        
        if (args != null) {
            Integer _iLength = args.length;
            if (_iLength == 0) {
                /* Java Swing GUI */
                MainSwing _MainSwing = new MainSwing();
                _MainSwing.setVisible(true);
            } else {
                List<String> argslist = Arrays.asList(args);
                if (argslist.contains("-h") || argslist.contains("--h")) {
                    Version.help();
                } else if (argslist.contains("-v")) {
                    Version.version();
                } else if (argslist.contains("-c")) {
                    Version.changeLog();
                } else if (argslist.contains("-ip")) {
                    Integer _iIP = argslist.indexOf("-ip") + 1;
                    if (_iLength > _iIP) {
                        Boolean _xNoPing = false;
                        if (argslist.contains("-np")) {
                            _xNoPing = true;
                        }
                        if (argslist.contains("-visu")) {
                            Integer _iVISU = argslist.indexOf("-visu") + 1;
                            if (_iLength > _iVISU) {
                                run(argslist.get(argslist.indexOf("-ip") + 1), argslist.get(argslist.indexOf("-visu") + 1), _xNoPing);
                            } else {
                                Version.help();
                            }
                        } else {
                            run(argslist.get(argslist.indexOf("-ip") + 1), null, _xNoPing);
                        }
                    } else {
                        Version.help();
                    }
                } else {
                    Version.help();
                }
                System.out.println("Press any key + \"Enter\" to exit.");
                try {
                    System.in.read();
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                Runtime.getRuntime().exit(0);
            }
        } else {
            Runtime.getRuntime().exit(0);    
        }
    }
    
    /**
     * @param sAddress the Network address
     * @param sVisu the WebVisu to call
     * @param xNoPing disable the automatic ping function
     */
    public static void run(String sAddress, String sVisu, Boolean xNoPing) {

        String _sPath = "/PLC/";
        String _sIP;
        _sIP = NetworkAddress.addressResolver(sAddress);
        if (NetworkAddress.addressChecker(_sIP)) {
            Integer _iTimeout = 0;
            if (xNoPing || Ping.isReachable(_sIP, _iTimeout)) {
                Boolean _xHTTPs;
                _xHTTPs = HTTP_HTTPs.checkHTTPs(_sIP);
                if (_xHTTPs != null) {
                    Integer _iWebvisu;
                    _iWebvisu = HTTP_HTTPs.downloadWebVisu(_xHTTPs, _sIP);
                    switch (_iWebvisu) {
                        case -1: // the webvisu is a HTML5 webvisu)
                            LOGGER.log(Level.WARNING, "HTML5 webvisu detected at ''{0}'' ({1})!", new Object[]{sAddress, _sIP});
                            return;
                        case 0: // the webvisu is direct (port 80, path "/")
                            LOGGER.log(Level.WARNING, "No webvisu at ''{0}'' ({1})!", new Object[]{sAddress, _sIP});
                            return;
                        case 1: // the webvisu is direct (path "/")
                            _sPath = "/";
                            break;
                        case 2: // the webvisu is for a 750-88x (path "/plc/")
                            _sPath = "/PLC/";
                            break;
                        case 3: // the webvisu is for an PFC (path "/webvisu/")
                            _sPath = "/webvisu/";
                            break;
                        case 4: // the webvisu is for an IPC (path ":8080/")
                            _sPath = ":8080/";
                            break;
                    }
                    if (!LocalFiles.checkFile(WEBVISU_JAR)) {
                        HTTP_HTTPs.downloadURL(_xHTTPs, _sIP, _sPath, WEBVISU_JAR);
                    }
                    if (!LocalFiles.checkFile(MINML_JAR)) {
                        HTTP_HTTPs.downloadURL(_xHTTPs, _sIP, _sPath, MINML_JAR);
                    }

                    //Create a new list of station to be filled by CSV file data 
                    ArrayList<AppletParameter> alAppletParameter;

                    alAppletParameter = ParseApplet.parseHtmFile();

                    CreateConf.writeConfFile(_xHTTPs, _sIP, _sPath, sVisu, alAppletParameter);

                    LocalFiles.cleanFiles("webvisu.htm");
                    
                    RunJavaWebVisu.runJavaWebVisu(_sIP);

                } else {
                    LOGGER.log(Level.WARNING, "HTTP/HTTPs not supported at ''{0}'' ({1})!", new Object[]{sAddress, _sIP});
                }
            } else {
                LOGGER.log(Level.WARNING, "Network Address ''{0}'' ({1}) unreachable!", new Object[]{sAddress, _sIP});
            }
        } else {
            LOGGER.log(Level.WARNING, "Network Address ''{0}'' incorrect!", sAddress);
        }
    }
}
