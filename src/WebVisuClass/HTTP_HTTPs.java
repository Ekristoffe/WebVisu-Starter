package WebVisuClass;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Christophe ICARD
 */
public class HTTP_HTTPs {

    private static final String KEY_STORE_PASS = "changeit";
    private static final String KEY_STORE_FILE = "webVisuKeyStore.jks";
    private static KeyStore ksKeyStore;
    private static SavingTrustManager stmSavingTrustManager;
    private static X509TrustManager tmTrustManager;

    private static final Logger LOGGER = Logger.getLogger(HTTP_HTTPs.class.getName());

    // Author: AndreasSterbenz: InstallCertJFrame.java
    // Created on 22 September 2008, 13:21
    // Edit: Christophe ICARD
    private static final char[] HEXDIGITS = "0123456789ABCDEF".toCharArray();

    // Author: AndreasSterbenz: InstallCertJFrame.java
    // Created on 22 September 2008, 13:21
    // Edit: Christophe ICARD
    private static String toHexString(byte[] bInput) {
        StringBuilder _sbHexByte = new StringBuilder(bInput.length * 3);
        int _iIndex;
        int _iByte;
        for (_iIndex = 0; _iIndex < bInput.length; _iIndex++) {
            _iByte = (int) bInput[_iIndex] & 0xFF;
            _sbHexByte.append(HEXDIGITS[_iByte >> 4]);
            _sbHexByte.append(HEXDIGITS[_iByte & 15]);
            _sbHexByte.append(' ');
        }
        return _sbHexByte.toString();
    }

    // Author: AndreasSterbenz: InstallCertJFrame.java
    // Created on 22 September 2008, 13:21
    // Edit: Christophe ICARD
    public static void importCertificates(String host, X509Certificate cert) {
        String alias = host;
        OutputStream out = null;
        try {
            ksKeyStore.setCertificateEntry(alias, cert);
            out = new BufferedOutputStream(new FileOutputStream(KEY_STORE_FILE));
            ksKeyStore.store(out, KEY_STORE_PASS.toCharArray());
            out.close();
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    try {
                        out.close();
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                    }
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                }
            }
        }
        LOGGER.log(Level.INFO, "Added certificate to keystore ''{0}'' using alias ''{1}''", new Object[]{KEY_STORE_FILE, alias});
    }

    // Author: AndreasSterbenz: InstallCertJFrame.java
    // Created on 22 September 2008, 13:21
    // Edit: Christophe ICARD
    public static void loadKeyStore() {
        try {
            LOGGER.log(Level.FINE, "Loading KeyStore ...");
            // Check if we have a local KeyStore
            File _fKeyStore = new File(KEY_STORE_FILE);
            if (!_fKeyStore.isFile()) {
                LOGGER.log(Level.FINE, "Keystore {0} not found", _fKeyStore);
                File dir = new File(new File(System.getProperty("java.home"), "lib"), "security");
                _fKeyStore = new File(dir, "jssecacerts");
                if (!_fKeyStore.isFile()) {
                    LOGGER.log(Level.FINE, "Keystore {0} not found", _fKeyStore);
                    _fKeyStore = new File(dir, "cacerts");
                    if (!_fKeyStore.isFile()) {
                        LOGGER.log(Level.FINE, "Keystore {0} not found", _fKeyStore);
                    }
                }
            }
            InputStream inKeyStore;
            inKeyStore = new FileInputStream(_fKeyStore);
            ksKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            ksKeyStore.load(inKeyStore, KEY_STORE_PASS.toCharArray());
            inKeyStore.close();

            TrustManagerFactory tmfTrustManagerFactory;
            tmfTrustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmfTrustManagerFactory.init(ksKeyStore);
            tmTrustManager = (X509TrustManager) tmfTrustManagerFactory.getTrustManagers()[0];
            stmSavingTrustManager = new SavingTrustManager(tmTrustManager);

            LOGGER.log(Level.FINE, "KeyStore {0} Loaded.", _fKeyStore);

        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        }
    }

    private static String autoWrap(String sInput) {
        int _iWrap = sInput.length() / 50;
        char[] caInput = sInput.toCharArray();
        StringBuilder _sbHexByte = new StringBuilder(caInput.length + ((_iWrap + 1) * 4));
        int _iIndex;
        int _iChar = 0;
        for (_iIndex = 0; _iIndex < caInput.length; _iIndex++) {
            if (_iChar > 50) {
                if (caInput[_iIndex] == ' ') {
                    _iChar = 0;
                    _sbHexByte.append("<br>");
                } else {
                    _sbHexByte.append(caInput[_iIndex]);
                }
            } else {
                _sbHexByte.append(caInput[_iIndex]);
            }
            _iChar++;
        }
        return _sbHexByte.toString();
    }

    private static String sshSignaturetoString(byte[] bInput) {
        StringBuilder _sbHexByte = new StringBuilder(bInput.length * 3);
        int _iIndex;
        int _iByte;
        int _iChar = 0;
        int _iLine = 0;
        _sbHexByte.append("0000: ");
        for (_iIndex = 0; _iIndex < bInput.length; _iIndex++) {
            if (_iChar == 8) {
                _sbHexByte.append("  ");
            } else if (_iChar == 16) {
                _sbHexByte.append("<br>00");
                _sbHexByte.append(_iLine);
                _sbHexByte.append("0: ");
                _iChar = 0;
                _iLine++;
            }
            _iByte = (int) bInput[_iIndex] & 0xFF;
            _sbHexByte.append(HEXDIGITS[_iByte >> 4]);
            _sbHexByte.append(HEXDIGITS[_iByte & 15]);
            _sbHexByte.append(' ');
            _iChar++;
        }
        return _sbHexByte.toString();
    }

    public static Boolean checkHTTPs(String sIP) {

        Boolean _xHTTPs = null;
        String _sURL;
        String _sMethod = "HEAD";
        Integer _iResponceCode;

        _sURL = "http://" + sIP;

        System.setProperty("http.keepAlive", "false");

        Boolean _xExitLoop = false;
        while (_xExitLoop == false) {
            URL _URL;
            URLConnection _URLcon;
            try {
                _URL = new URL(_sURL);
                // open a connection to the given url
                _URLcon = _URL.openConnection();
                // set the connection timeout to 5 seconds (default 15s)
                _URLcon.setConnectTimeout(5000);
                // set the read timeout to 10 seconds (default 60s)
                _URLcon.setReadTimeout(10000);

                // JMD - this is a better way to do it that doesn't override the default SSL factory.
                if (_URLcon instanceof HttpsURLConnection) {

                    loadKeyStore();

                    try {
                        HttpsURLConnection _HTTPscon = (HttpsURLConnection) _URLcon;
                        /* Disable the automatir redirection system we just need to check if the port is Open or not */
                        _HTTPscon.setInstanceFollowRedirects(false);
                        HttpsURLConnection.setFollowRedirects(false);
                        // Get a new SSL context
                        SSLContext sslcContext = SSLContext.getInstance("TLSv1.2");
                        sslcContext.init(null, new TrustManager[]{stmSavingTrustManager}, new java.security.SecureRandom());
                        // Set our connection to use this SSL context, with the "Trust all" manager in place.
                        _HTTPscon.setSSLSocketFactory(sslcContext.getSocketFactory());
                        // Set up a Hostname Verifier to trust all hosts
                        HostnameVerifier allHostsValid = (String hostname, SSLSession session) -> true;
                        // Set the hostname verifier.
                        _HTTPscon.setHostnameVerifier(allHostsValid);

                        /*  Start of the HTTP Request   */
                        try {
                            _HTTPscon.setRequestMethod(_sMethod);
                            try {
                                _iResponceCode = _HTTPscon.getResponseCode();
                                if (_iResponceCode == HttpsURLConnection.HTTP_NOT_FOUND) {
                                    _xExitLoop = true;
                                    _xHTTPs = null;
                                } else {
                                    switch (_iResponceCode) {
                                        case HttpsURLConnection.HTTP_OK:
                                            _xExitLoop = true;
                                            _xHTTPs = true;
                                            break;
                                        case HttpsURLConnection.HTTP_MOVED_PERM: // 301 Moved Permanently
                                        case HttpsURLConnection.HTTP_MOVED_TEMP: // 302 Found
                                        case 307: // 307 Temporary Redirect (since HTTP/1.1)
                                        case 308: // 308 Permanent Redirect (RFC 7538)
                                            _xExitLoop = true;
                                            _xHTTPs = true;
                                            break;
                                            /*
                                            _sURL = _HTTPscon.getHeaderField("Location");
                                            if (!(_sURL.toLowerCase().contains(sIP.toLowerCase()))) {
                                                // The redirection is a HTTPs URL.
                                                _sURL = sIP + _sURL;
                                            }
                                            if (!(_sURL.toLowerCase().contains("https"))) {
                                                // The redirection is a HTTPs URL.
                                                _sURL = "https://" + _sURL;
                                            }
                                            break;
                                            */
                                        case HttpsURLConnection.HTTP_BAD_METHOD:
                                            _sMethod = "GET";
                                            break;
                                        default:
                                            LOGGER.log(Level.WARNING, "HTTPs return code: {0}", _iResponceCode.toString());
                                            _xExitLoop = true;
                                            _xHTTPs = null;
                                            break;
                                    }
                                }
                            } catch (SSLException ex) {
                                // Get the certificate
                                X509Certificate[] _Cert = stmSavingTrustManager.getAcceptedIssuers();

                                // Create the parser
                                MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
                                MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
                                MessageDigest md5 = MessageDigest.getInstance("MD5");
                                // Parse the first cert
                                sha256.update(_Cert[0].getEncoded());
                                sha1.update(_Cert[0].getEncoded());
                                md5.update(_Cert[0].getEncoded());
                                // Turn off metal's use of bold fonts
                                UIManager.put("swing.boldMetal", Boolean.FALSE);
                                // New JFrame for the Dialog
                                JFrame frame = new JFrame();

                                String sHTMLMessage = ""
                                        + "<html>"
                                        + "	<head>"
                                        + "		<style>"
                                        + "			BODY {FONT-FAMILY: Segoe UI, Helvetica, Arial, sans-serif; FONT-SIZE: 13pt;}"
                                        + "			.Left{FONT-SIZE: 11pt; BACKGROUND-COLOR: #FFFFFF; COLOR: #000000; BORDER-LEFT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-RIGHT: #000000 0px solid; BORDER-BOTTOM: #000000 0px solid;}"
                                        + "			.Right{FONT-SIZE: 11pt; BACKGROUND-COLOR: #FFFFFF; COLOR: #000000; BORDER-LEFT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-RIGHT: #000000 1px solid; BORDER-BOTTOM: #000000 0px solid;}"
                                        + "			.LastLeft{FONT-SIZE: 11pt; BACKGROUND-COLOR: #FFFFFF; COLOR: #000000; BORDER-LEFT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-RIGHT: #000000 0px solid; BORDER-BOTTOM: #000000 1px solid;}"
                                        + "			.LastRight{FONT-SIZE: 11pt; BACKGROUND-COLOR: #FFFFFF; COLOR: #000000; BORDER-LEFT: #000000 1px solid; BORDER-TOP: #000000 1px solid; BORDER-RIGHT: #000000 1px solid; BORDER-BOTTOM: #000000 1px solid;}"
                                        + "			.Valid{BACKGROUND-COLOR: #FFFFFF; COLOR: #000000; BORDER-BOTTOM: #FFFFFF 1px solid; BORDER-LEFT: #FFFFFF 1px solid; BORDER-RIGHT: #FFFFFF 1px solid; BORDER-TOP: #FFFFFF 1px solid;}"
                                        + "		</style>"
                                        + "	</head>"
                                        + "	<body>"
                                        + "		<font size=\"6\"><b>Do you want to Continue?</b></font><br>"
                                        + "		<font color=\"red\">The connection to the following website is untrusted.</font><br>"
                                        + "		URL:&nbsp;&nbsp;" + _sURL + "<br>"
                                        + "		Certificate Detail:<br>"
                                        + "		<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\">"
                                        + "			<tr>"
                                        + "				<td valign=\"top\" class=\"Left\"> " + "Version:" + " </td>"
                                        + "				<td class=\"Right\"> " + _Cert[0].getVersion() + " </td>"
                                        + "			</tr>"
                                        + "			<tr>"
                                        + "				<td valign=\"top\" class=\"Left\"> " + "Serial Number:" + " </td>"
                                        + "				<td class=\"Right\"> " + autoWrap(_Cert[0].getSerialNumber().toString()) + " </td>"
                                        + "			</tr>"
                                        + "			<tr>"
                                        + "				<td valign=\"top\" class=\"Left\"> " + "Signature Algorithm:" + " </td>"
                                        + "				<td class=\"Right\"> " + autoWrap(_Cert[0].getSigAlgName()) + " </td>"
                                        + "			</tr>"
                                        + "			<tr>"
                                        + "				<td valign=\"top\" class=\"Left\"> " + "Issuer:" + " </td>"
                                        + "				<td class=\"Right\"> " + autoWrap(_Cert[0].getIssuerX500Principal().toString()) + " </td>"
                                        + "			</tr>"
                                        + "			<tr>"
                                        + "				<td valign=\"top\" class=\"Left\">" + "Validity:" + "</td>"
                                        + "				<td class=\"Right\">"
                                        + "					<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
                                        + "						<tr>"
                                        + "							<td class=\"Valid\"> " + "From:" + " </td>"
                                        + "							<td class=\"Valid\"> " + _Cert[0].getNotBefore().toString() + " </td>"
                                        + "						</tr>"
                                        + "						<tr>"
                                        + "							<td class=\"Valid\"> " + "To:" + "</td>"
                                        + "							<td class=\"Valid\"> " + _Cert[0].getNotAfter().toString() + " </td>"
                                        + "						</tr>"
                                        + "					</table>"
                                        + "				</td>"
                                        + "			</tr>"
                                        + "			<tr>"
                                        + "				<td valign=\"top\" class=\"Left\"> " + "Subject:" + " </td>"
                                        + "				<td class=\"Right\"> " + autoWrap(_Cert[0].getSubjectX500Principal().toString()) + " </td>"
                                        + "			</tr>"
                                        + "			<tr>"
                                        + "				<td valign=\"top\" class=\"Left\"> " + "Signature:" + " </td>"
                                        + "				<td class=\"Right\"> " + sshSignaturetoString(_Cert[0].getSignature()) + " </td>"
                                        + "			</tr>"
                                        + "			<tr>"
                                        + "				<td valign=\"top\" class=\"Left\"> " + "MD5 Fingerprint:" + " </td>"
                                        + "				<td class=\"Right\"> " + autoWrap(toHexString(md5.digest())) + " </td>"
                                        + "			</tr>"
                                        + "			<tr>"
                                        + "				<td valign=\"top\" class=\"Left\"> " + "SHA 1 Fingerprint:" + " </td>"
                                        + "				<td class=\"Right\"> " + autoWrap(toHexString(sha1.digest())) + " </td>"
                                        + "			</tr>"
                                        + "			<tr>"
                                        + "				<td valign=\"top\" class=\"LastLeft\"> " + "SHA 256 Fingerprint:" + " </td>"
                                        + "				<td class=\"LastRight\"> " + autoWrap(toHexString(sha256.digest())) + " </td>"
                                        + "			</tr>"
                                        + "		</table>"
                                        + "	</body>"
                                        + "</html>";
                                int n;
                                n = JOptionPane.showConfirmDialog(frame, sHTMLMessage,
                                        "Security Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
                                if (n == JOptionPane.YES_OPTION) {
                                    importCertificates(sIP, stmSavingTrustManager.getAcceptedIssuers()[0]);
                                } else {
                                    LOGGER.log(Level.FINE, "Certificate invalid: {0}", ex.toString());
                                    _xExitLoop = true;
                                    _xHTTPs = null;
                                }
                                frame.dispose();
                            }
                        } catch (IOException ex) {
                            LOGGER.log(Level.FINE, "HTTPs Failed: {0}", ex.toString());
                            _xExitLoop = true;
                            _xHTTPs = null;
                        } finally {
                            _HTTPscon.disconnect();
                        }
                        /*  End of the HTTP Request   */

                    } catch (NoSuchAlgorithmException | CertificateException | KeyManagementException ex) {
                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                        _xExitLoop = true;
                        _xHTTPs = null;
                    }
                } else if (_URLcon instanceof HttpURLConnection) {
                    HttpURLConnection _HTTPcon = (HttpURLConnection) _URLcon;
                    /* Disable the automatir redirection system we just need to check if the port is Open or not */
                    _HTTPcon.setInstanceFollowRedirects(false);
                    HttpURLConnection.setFollowRedirects(false);
                    /*  Start of the HTTP Request   */
                    try {
                        _HTTPcon.setRequestMethod(_sMethod);
                        _iResponceCode = _HTTPcon.getResponseCode();
                        if (_iResponceCode == HttpURLConnection.HTTP_NOT_FOUND) {
                            _xExitLoop = true;
                            _xHTTPs = null;
                        } else {
                            switch (_iResponceCode) {
                                case HttpURLConnection.HTTP_OK:
                                    _xExitLoop = true;
                                    _xHTTPs = false;
                                    break;
                                case HttpURLConnection.HTTP_MOVED_PERM: // 301 Moved Permanently
                                case HttpURLConnection.HTTP_MOVED_TEMP: // 302 Found
                                case 307: // 307 Temporary Redirect (since HTTP/1.1)
                                case 308: // 308 Permanent Redirect (RFC 7538)
                                    if (_HTTPcon.getHeaderField("Location").toLowerCase().contains("https")) {
                                        // The redirection is a HTTPs URL.
                                        _sURL = _HTTPcon.getHeaderField("Location");
                                        LOGGER.log(Level.FINE, "HTTP Redirected to HTTPs: {0}", _sURL);
                                    } else {
                                        _xExitLoop = true;
                                        _xHTTPs = false;
                                    }
                                    break;
                                case HttpURLConnection.HTTP_BAD_METHOD:
                                    _sMethod = "GET";
                                    break;
                                default:
                                    LOGGER.log(Level.WARNING, "HTTP return code: {0}", _iResponceCode.toString());
                                    _xExitLoop = true;
                                    _xHTTPs = null;
                                    break;
                            }
                        }
                    } catch (IOException ex) {
                        // error occurred connecting to the server.
                        _sURL = "https://" + sIP;
                        LOGGER.log(Level.FINE, "HTTP Failed try HTTPs: {0}", ex.toString());
                    } finally {
                        _HTTPcon.disconnect();
                    }
                    /*  End of the HTTP Request   */
                }
            } catch (MalformedURLException ex) {
                // unknown protocol is specified.
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                _xExitLoop = true;
                _xHTTPs = null;
            } catch (IOException ex) {
                // I/O exception occurs
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                _xExitLoop = true;
                _xHTTPs = null;
            }
        }

        return _xHTTPs;
    }

    public static Integer downloadWebVisu(Boolean xHttps, String sIP) {

        Integer _iWebvisu = 0;
        String _sURL;
        Integer _iResponceCode;

        if (xHttps == true) {
            _sURL = "https://" + sIP;
        } else {
            _sURL = "http://" + sIP;
        }

        URL _URL;
        URLConnection _URLcon;

        System.setProperty("http.keepAlive", "false");

        String _sTestURL;

        Boolean _xExitLoop = false;
        while (_xExitLoop == false) {

            _iWebvisu += 1;
            switch (_iWebvisu) {
                case 1: // the webvisu is direct (port 80, path "")
                    _sTestURL = _sURL + "/webvisu.htm";
                    break;
                case 2: // the webvisu is for a 750-88x (port 80, path "/plc/")
                    _sTestURL = _sURL + "/plc/webvisu.htm";
                    break;
                case 3: // the webvisu is for an PFC (port 80, path "/webvisu/")
                    _sTestURL = _sURL + "/webvisu/webvisu.htm";
                    break;
                case 4: // the webvisu is for an pfc (port 8080, path "")
                    _sTestURL = _sURL + ":8080";
                    break;
                case 5: // the webvisu is for an IPC (port 8080, path "")
                    _sTestURL = _sURL + ":8080/webvisu.htm";
                    break;
                default:
                    return (0);
            }

            try {
                _URL = new URL(_sTestURL);
                // open a connection to the given url
                _URLcon = _URL.openConnection();
                // set the connection timeout to 5 seconds (default 15s)
                _URLcon.setConnectTimeout(5000);
                // set the read timeout to 10 seconds (default 60s)
                _URLcon.setReadTimeout(10000);
                // JMD - this is a better way to do it that doesn't override the default SSL factory.
                if (_URLcon instanceof HttpsURLConnection) {

                    if (ksKeyStore == null) {
                        loadKeyStore();
                    }

                    try {

                        HttpsURLConnection _HTTPscon = (HttpsURLConnection) _URLcon;
                        // Get a new SSL context
                        SSLContext sslcContext = SSLContext.getInstance("TLSv1.2");
                        sslcContext.init(null, new TrustManager[]{stmSavingTrustManager}, new java.security.SecureRandom());
                        // Set our connection to use this SSL context, with the "Trust all" manager in place.
                        _HTTPscon.setSSLSocketFactory(sslcContext.getSocketFactory());
                        // Set up a Hostname Verifier to trust all hosts
                        HostnameVerifier allHostsValid = (String hostname, SSLSession session) -> true;
                        // Set the hostname verifier.
                        _HTTPscon.setHostnameVerifier(allHostsValid);
                        /*  Start of the HTTP Request   */
                        try {
                            try {
                                _iResponceCode = _HTTPscon.getResponseCode();
                                switch (_iResponceCode) {
                                    case HttpURLConnection.HTTP_OK:
                                        InputStream in = _HTTPscon.getInputStream();
                                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                        StringBuilder _sbWebVisuHtm = new StringBuilder(500000);
                                        String line;
                                        line = reader.readLine();
                                        while (line != null) {
                                            _sbWebVisuHtm.append(line);
                                            line = reader.readLine();
                                        }
                                        String _sWebVisuHtm = _sbWebVisuHtm.toString();

                                        if (_sWebVisuHtm.contains("<APPLET")) {
                                            // CoDeSys 2.3 WebVisu

                                            String _sFileName = "webvisu.htm";
                                            FileWriter _fileWriter = null;

                                            try {
                                                // true to append / false to overwrite.
                                                _fileWriter = new FileWriter(_sFileName, false);
                                                _fileWriter.append(_sWebVisuHtm);

                                                LOGGER.log(Level.FINE, "webvisu.htm file was created successfully !!!");

                                            } catch (FileNotFoundException ex) {
                                                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                            } catch (IOException ex) {
                                                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                            } finally {
                                                if (_fileWriter != null) {
                                                    try {
                                                        _fileWriter.flush();
                                                    } catch (IOException ex) {
                                                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                                    }
                                                    try {
                                                        _fileWriter.close();
                                                    } catch (IOException ex) {
                                                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                                    }
                                                }
                                            }
                                        } else if (_sWebVisuHtm.contains("There is currently no WebVisu stored in this controller.")){
                                            // No CoDeSys WebVisu
                                            _iWebvisu = 0;
                                        } else if (_sWebVisuHtm.contains("Webvisu not supported")){
                                            // CoDeSys 3.x WebVisu
                                            RunHtmlWebVisu.runWebVisuURI(_sTestURL);
                                            _iWebvisu = -1;
                                        } else {
                                            // Unknown
                                            _iWebvisu = 0;
                                        }
                                        try {
                                            if (in != null) {
                                                in.close();
                                            }
                                        } catch (IOException ex) {
                                            LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                        }
                                        _xExitLoop = true;
                                        _HTTPscon.disconnect();
                                        break;
                                    case HttpURLConnection.HTTP_MOVED_TEMP:
                                        _sURL = _HTTPscon.getHeaderField("Location");
                                        _iWebvisu -= 1;
                                        break;
                                }
                            } catch (SSLException ex) {
                                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                            }
                        } catch (IOException ex) {
                            LOGGER.log(Level.FINE, "URL {0} don''t exist: {1}", new Object[]{_sURL, ex.toString()});
                        }
                        /*  End of the HTTP Request   */
                    } catch (NoSuchAlgorithmException | KeyManagementException ex) {
                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                    }
                } else if (_URLcon instanceof HttpURLConnection) {
                    HttpURLConnection _HTTPcon = (HttpURLConnection) _URLcon;
                    /*  Start of the HTTP Request   */
                    try {
                        _iResponceCode = _HTTPcon.getResponseCode();
                        switch (_iResponceCode) {
                            case HttpURLConnection.HTTP_OK:
                                InputStream in = _HTTPcon.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                StringBuilder _sbWebVisuHtm = new StringBuilder(500000);
                                String line;
                                line = reader.readLine();
                                while (line != null) {
                                    _sbWebVisuHtm.append(line);
                                    line = reader.readLine();
                                }
                                String _sWebVisuHtm = _sbWebVisuHtm.toString();

                                if (_sWebVisuHtm.contains("<APPLET")) {

                                    String _sFileName = "webvisu.htm";
                                    FileWriter _fileWriter = null;

                                    try {
                                        // true to append / false to overwrite.
                                        _fileWriter = new FileWriter(_sFileName, false);
                                        _fileWriter.append(_sWebVisuHtm);

                                        LOGGER.log(Level.FINE, "webvisu.htm file was created successfully !!!");

                                    } catch (FileNotFoundException ex) {
                                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                    } catch (IOException ex) {
                                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                    } finally {
                                        if (_fileWriter != null) {
                                            try {
                                                _fileWriter.flush();
                                            } catch (IOException ex) {
                                                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                            }
                                            try {
                                                _fileWriter.close();
                                            } catch (IOException ex) {
                                                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                            }
                                        }
                                    }
                                } else if (_sWebVisuHtm.contains("There is currently no WebVisu stored in this controller.")){
                                    // No CoDeSys WebVisu
                                    _iWebvisu = 0;
                                } else if (_sWebVisuHtm.contains("Webvisu not supported")){
                                    // CoDeSys 3.x WebVisu
                                    RunHtmlWebVisu.runWebVisuURI(_sTestURL);
                                    _iWebvisu = -1;
                                } else {
                                    // Unknown
                                    _iWebvisu = 0;
                                }
                                try {
                                    if (in != null) {
                                        in.close();
                                    }
                                } catch (IOException ex) {
                                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                }
                                _xExitLoop = true;
                                _HTTPcon.disconnect();
                                break;
                            case HttpURLConnection.HTTP_MOVED_TEMP:
                                _sURL = _HTTPcon.getHeaderField("Location");
                                _iWebvisu -= 1;
                                break;
                        }
                    } catch (IOException ex) {
                        LOGGER.log(Level.FINE, "URL {0} don''t exist: {1}", new Object[]{_sURL, ex.toString()});
                    }
                    /*  End of the HTTP Request   */
                }
            } catch (MalformedURLException ex) {
                // unknown protocol is specified.
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            } catch (IOException ex) {
                // I/O exception occurs
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
            }
        }
        return _iWebvisu;
    }

    public static boolean downloadURL(Boolean xHttps, String sIP, String sPath, String sFilename) {

        Boolean _xDownload = false;
        String _sURL;
        Integer _iResponceCode;

        if (xHttps == true) {
            _sURL = "https://" + sIP + sPath + sFilename;
        } else {
            _sURL = "http://" + sIP + sPath + sFilename;
        }

        URL _URL;
        URLConnection _URLcon;

        System.setProperty("http.keepAlive", "false");

        Boolean _xExitLoop = false;
        while (_xExitLoop == false) {

            try {
                _URL = new URL(_sURL);
                // open a connection to the given url
                _URLcon = _URL.openConnection();
                // set the connection timeout to 5 seconds (default 15s)
                _URLcon.setConnectTimeout(5000);
                // set the read timeout to 10 seconds (default 60s)
                _URLcon.setReadTimeout(10000);
                // JMD - this is a better way to do it that doesn't override the default SSL factory.
                if (_URLcon instanceof HttpsURLConnection) {

                    if (ksKeyStore == null) {
                        loadKeyStore();
                    }
                    try {

                        HttpsURLConnection _HTTPscon = (HttpsURLConnection) _URLcon;
                        // Get a new SSL context
                        SSLContext sslcContext = SSLContext.getInstance("TLSv1.2");
                        sslcContext.init(null, new TrustManager[]{stmSavingTrustManager}, new java.security.SecureRandom());
                        // Set our connection to use this SSL context, with the "Trust all" manager in place.
                        _HTTPscon.setSSLSocketFactory(sslcContext.getSocketFactory());
                        // Set up a Hostname Verifier to trust all hosts
                        HostnameVerifier allHostsValid = (String hostname, SSLSession session) -> true;
                        // Set the hostname verifier.
                        _HTTPscon.setHostnameVerifier(allHostsValid);

                        /*  Start of the HTTP Request   */
                        try {
                            try {
                                _iResponceCode = _HTTPscon.getResponseCode();
                                if (_iResponceCode == HttpURLConnection.HTTP_NOT_FOUND) {
                                    _xExitLoop = true;
                                    _xDownload = false;
                                } else {
                                    switch (_iResponceCode) {
                                        case HttpURLConnection.HTTP_OK:
                                            Integer _FileSize = _HTTPscon.getContentLength();
                                            //get connection inputstream
                                            InputStream in = _HTTPscon.getInputStream();
                                            FileOutputStream _fos = null;
                                            try {
                                                //open outputstream to local file
                                                _fos = new FileOutputStream(sFilename);
                                                //declare 4KB buffer
                                                byte[] _buffer = new byte[4096];
                                                int _iBytesRead;
                                                _iBytesRead = in.read(_buffer);
                                                //while we have availble data, continue downloading and storing to local file
                                                while (_iBytesRead > 0) {
                                                    _fos.write(_buffer, 0, _iBytesRead);
                                                    _iBytesRead = in.read(_buffer);
                                                }
                                            } catch (FileNotFoundException ex) {
                                                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                            } finally {
                                                try {
                                                    if (in != null) {
                                                        in.close();
                                                    }
                                                } catch (IOException ex) {
                                                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                                }
                                                if (_fos != null) {
                                                    _fos.close();
                                                }
                                            }
                                            _xExitLoop = true;
                                            _xDownload = true;
                                            break;
                                        case HttpURLConnection.HTTP_MOVED_PERM: // 301 Moved Permanently
                                        case HttpURLConnection.HTTP_MOVED_TEMP: // 302 Found
                                        case 307: // 307 Temporary Redirect (since HTTP/1.1)
                                        case 308: // 308 Permanent Redirect (RFC 7538)
                                            _sURL = _HTTPscon.getHeaderField("Location");
                                            break;
                                        default:
                                            LOGGER.log(Level.WARNING, "HTTP return code: {0}", _iResponceCode.toString());
                                            _xExitLoop = true;
                                            break;
                                    }
                                }
                            } catch (SSLException ex) {
                                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                _xExitLoop = true;
                                _xDownload = false;
                            }
                        } catch (IOException ex) {
                            LOGGER.log(Level.FINE, "URL {0} don''t exist: {1}", new Object[]{_sURL, ex.toString()});
                            _xExitLoop = true;
                            _xDownload = false;
                        } finally {
                            _HTTPscon.disconnect();
                        }
                        /*  End of the HTTP Request   */
                    } catch (NoSuchAlgorithmException | KeyManagementException ex) {
                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                        _xExitLoop = true;
                        _xDownload = false;
                    }
                } else if (_URLcon instanceof HttpURLConnection) {
                    HttpURLConnection _HTTPcon = (HttpURLConnection) _URLcon;
                    /*  Start of the HTTP Request   */
                    try {
                        _iResponceCode = _HTTPcon.getResponseCode();
                        if (_iResponceCode == HttpURLConnection.HTTP_NOT_FOUND) {
                            _xExitLoop = true;
                            _xDownload = false;
                        } else {
                            switch (_iResponceCode) {
                                case HttpURLConnection.HTTP_OK:
                                    Integer _FileSize = _HTTPcon.getContentLength();
                                    //get connection inputstream
                                    InputStream in = _HTTPcon.getInputStream();
                                    FileOutputStream _fos = null;
                                    try {
                                        //open outputstream to local file
                                        _fos = new FileOutputStream(sFilename);
                                        //declare 4KB buffer
                                        byte[] _buffer = new byte[4096];
                                        int _iBytesRead;
                                        _iBytesRead = in.read(_buffer);
                                        //while we have availble data, continue downloading and storing to local file
                                        while (_iBytesRead > 0) {
                                            _fos.write(_buffer, 0, _iBytesRead);
                                            _iBytesRead = in.read(_buffer);
                                        }
                                    } catch (FileNotFoundException ex) {
                                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                    } finally {
                                        try {
                                            if (in != null) {
                                                in.close();
                                            }
                                        } catch (IOException ex) {
                                            LOGGER.log(Level.SEVERE, ex.toString(), ex);
                                        }
                                        if (_fos != null) {
                                            _fos.close();
                                        }
                                    }
                                    _xExitLoop = true;
                                    _xDownload = true;
                                    break;
                                case HttpURLConnection.HTTP_MOVED_PERM: // 301 Moved Permanently
                                case HttpURLConnection.HTTP_MOVED_TEMP: // 302 Found
                                case 307: // 307 Temporary Redirect (since HTTP/1.1)
                                case 308: // 308 Permanent Redirect (RFC 7538)
                                    _sURL = _HTTPcon.getHeaderField("Location");
                                    break;
                                default:
                                    LOGGER.log(Level.WARNING, "HTTP return code: {0}", _iResponceCode.toString());
                                    _xExitLoop = true;
                                    break;
                            }
                        }
                    } catch (IOException ex) {
                        LOGGER.log(Level.FINE, "URL {0} don''t exist: {1}", new Object[]{_sURL, ex.toString()});
                        _xExitLoop = true;
                        _xDownload = false;
                    } finally {
                        _HTTPcon.disconnect();
                    }
                    /*  End of the HTTP Request   */
                }
            } catch (MalformedURLException ex) {
                // unknown protocol is specified.
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                _xExitLoop = true;
                _xDownload = false;
            } catch (IOException ex) {
                // I/O exception occurs
                LOGGER.log(Level.SEVERE, ex.toString(), ex);
                _xExitLoop = true;
                _xDownload = false;
            }
        }
        return _xDownload;
    }

    public static void main(String[] args) {
        Boolean _xHTTPs;
        Integer _iWebvisuhtm;
        Boolean _xWebvisujar;
        Boolean _xMinmljar;
        String _sIP;
        String _sPath;
        String _sWebvisuhtm = "webvisu.htm"; //needs to be replaced with local file path
        String _sWebvisujar = "webvisu.jar"; //needs to be replaced with local file path
        String _sMinmljar = "minml.jar"; //needs to be replaced with local file path

        _sIP = "10.210.12.105";
        _xHTTPs = checkHTTPs(_sIP);
        LOGGER.log(Level.INFO, "Is {0} HTTPs: {1}", new Object[]{_sIP, _xHTTPs});
        if (_xHTTPs != null) {
            _iWebvisuhtm = downloadWebVisu(_xHTTPs, _sIP);
            LOGGER.log(Level.INFO, "{0} Webvisu type: {1}", new Object[]{_sIP, _iWebvisuhtm});
            if (_iWebvisuhtm != 0) {
                switch (_iWebvisuhtm) {
                    case 1: // the webvisu is direct (path "/")
                        _sPath = "/";
                        break;
                    case 2: // the webvisu is for a 750-88x (path "/plc/")
                        _sPath = "/PLC/";
                        break;
                    case 3: // the webvisu is for an PFC (path "/webvisu/")
                        _sPath = "/webvisu/";
                        break;
                    case 4: // the webvisu is for an pfc (port 8080, path "")
                        _sPath = ":8080";
                        break;
                    case 5: // the webvisu is for an IPC (port 8080, path "")
                        _sPath = ":8080/webvisu.htm";
                        break;
                    default:
                        _sPath = "/";
                        break;
                }
                LOGGER.log(Level.INFO, "{0} got a WebVisu", new Object[]{_sIP});
                LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisuhtm, _sIP});
                _xWebvisujar = downloadURL(_xHTTPs, _sIP, _sPath, _sWebvisujar);
                if (_xWebvisujar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisujar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sWebvisujar});
                }
                _xMinmljar = downloadURL(_xHTTPs, _sIP, _sPath, _sMinmljar);
                if (_xMinmljar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sMinmljar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sMinmljar});
                }
            } else {
                LOGGER.log(Level.INFO, "{0} don't have a WebVisu", new Object[]{_sIP});
            }
        }

        _sIP = "192.168.1.2";
        _xHTTPs = checkHTTPs(_sIP);
        LOGGER.log(Level.INFO, "Is {0} HTTPs: {1}", new Object[]{_sIP, _xHTTPs});
        if (_xHTTPs != null) {
            _iWebvisuhtm = downloadWebVisu(_xHTTPs, _sIP);
            LOGGER.log(Level.INFO, "{0} Webvisu type: {1}", new Object[]{_sIP, _iWebvisuhtm});
            if (_iWebvisuhtm != 0) {
                switch (_iWebvisuhtm) {
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
                    default:
                        _sPath = "/";
                        break;
                }
                LOGGER.log(Level.INFO, "{0} got a WebVisu", new Object[]{_sIP});
                LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisuhtm, _sIP});
                _xWebvisujar = downloadURL(_xHTTPs, _sIP, _sPath, _sWebvisujar);
                if (_xWebvisujar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisujar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sWebvisujar});
                }
                _xMinmljar = downloadURL(_xHTTPs, _sIP, _sPath, _sMinmljar);
                if (_xMinmljar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sMinmljar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sMinmljar});
                }
            } else {
                LOGGER.log(Level.INFO, "{0} don't have a WebVisu", new Object[]{_sIP});
            }
        }

        _sIP = "192.168.1.1";
        _xHTTPs = checkHTTPs(_sIP);
        LOGGER.log(Level.INFO, "Is {0} HTTPs: {1}", new Object[]{_sIP, _xHTTPs});
        if (_xHTTPs != null) {
            _iWebvisuhtm = downloadWebVisu(_xHTTPs, _sIP);
            LOGGER.log(Level.INFO, "{0} Webvisu type: {1}", new Object[]{_sIP, _iWebvisuhtm});
            if (_iWebvisuhtm != 0) {
                switch (_iWebvisuhtm) {
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
                    default:
                        _sPath = "/";
                        break;
                }
                LOGGER.log(Level.INFO, "{0} got a WebVisu", new Object[]{_sIP});
                LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisuhtm, _sIP});
                _xWebvisujar = downloadURL(_xHTTPs, _sIP, _sPath, _sWebvisujar);
                if (_xWebvisujar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisujar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sWebvisujar});
                }
                _xMinmljar = downloadURL(_xHTTPs, _sIP, _sPath, _sMinmljar);
                if (_xMinmljar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sMinmljar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sMinmljar});
                }
            } else {
                LOGGER.log(Level.INFO, "{0} don't have a WebVisu", new Object[]{_sIP});
            }
        }

        _sIP = "192.168.1.17";
        _xHTTPs = checkHTTPs(_sIP);
        LOGGER.log(Level.INFO, "Is {0} HTTPs: {1}", new Object[]{_sIP, _xHTTPs});
        if (_xHTTPs != null) {
            _iWebvisuhtm = downloadWebVisu(_xHTTPs, _sIP);
            LOGGER.log(Level.INFO, "{0} Webvisu type: {1}", new Object[]{_sIP, _iWebvisuhtm});
            if (_iWebvisuhtm != 0) {
                switch (_iWebvisuhtm) {
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
                    default:
                        _sPath = "/";
                        break;
                }
                LOGGER.log(Level.INFO, "{0} got a WebVisu", new Object[]{_sIP});
                LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisuhtm, _sIP});
                _xWebvisujar = downloadURL(_xHTTPs, _sIP, _sPath, _sWebvisujar);
                if (_xWebvisujar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisujar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sWebvisujar});
                }
                _xMinmljar = downloadURL(_xHTTPs, _sIP, _sPath, _sMinmljar);
                if (_xMinmljar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sMinmljar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sMinmljar});
                }
            } else {
                LOGGER.log(Level.INFO, "{0} don't have a WebVisu", new Object[]{_sIP});
            }
        }

        _sIP = "192.193.10.70";
        _xHTTPs = checkHTTPs(_sIP);
        LOGGER.log(Level.INFO, "Is {0} HTTPs: {1}", new Object[]{_sIP, _xHTTPs});
        if (_xHTTPs != null) {
            _iWebvisuhtm = downloadWebVisu(_xHTTPs, _sIP);
            LOGGER.log(Level.INFO, "{0} Webvisu type: {1}", new Object[]{_sIP, _iWebvisuhtm});
            if (_iWebvisuhtm != 0) {
                switch (_iWebvisuhtm) {
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
                    default:
                        _sPath = "/";
                        break;
                }
                LOGGER.log(Level.INFO, "{0} got a WebVisu", new Object[]{_sIP});
                LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisuhtm, _sIP});
                _xWebvisujar = downloadURL(_xHTTPs, _sIP, _sPath, _sWebvisujar);
                if (_xWebvisujar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisujar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sWebvisujar});
                }
                _xMinmljar = downloadURL(_xHTTPs, _sIP, _sPath, _sMinmljar);
                if (_xMinmljar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sMinmljar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sMinmljar});
                }
            } else {
                LOGGER.log(Level.INFO, "{0} don't have a WebVisu", new Object[]{_sIP});
            }
        }

        _sIP = "10.210.44.23";
        _xHTTPs = checkHTTPs(_sIP);
        LOGGER.log(Level.INFO, "Is {0} HTTPs: {1}", new Object[]{_sIP, _xHTTPs});
        if (_xHTTPs != null) {
            _iWebvisuhtm = downloadWebVisu(_xHTTPs, _sIP);
            LOGGER.log(Level.INFO, "{0} Webvisu type: {1}", new Object[]{_sIP, _iWebvisuhtm});
            if (_iWebvisuhtm != 0) {
                switch (_iWebvisuhtm) {
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
                    default:
                        _sPath = "/";
                        break;
                }
                LOGGER.log(Level.INFO, "{0} got a WebVisu", new Object[]{_sIP});
                LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisuhtm, _sIP});
                _xWebvisujar = downloadURL(_xHTTPs, _sIP, _sPath, _sWebvisujar);
                if (_xWebvisujar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisujar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sWebvisujar});
                }
                _xMinmljar = downloadURL(_xHTTPs, _sIP, _sPath, _sMinmljar);
                if (_xMinmljar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sMinmljar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sMinmljar});
                }
            } else {
                LOGGER.log(Level.INFO, "{0} don't have a WebVisu", new Object[]{_sIP});
            }
        }

        _sIP = "10.210.44.25";
        _xHTTPs = checkHTTPs(_sIP);
        LOGGER.log(Level.INFO, "Is {0} HTTPs: {1}", new Object[]{_sIP, _xHTTPs});
        if (_xHTTPs != null) {
            _iWebvisuhtm = downloadWebVisu(_xHTTPs, _sIP);
            LOGGER.log(Level.INFO, "{0} Webvisu type: {1}", new Object[]{_sIP, _iWebvisuhtm});
            if (_iWebvisuhtm != 0) {
                switch (_iWebvisuhtm) {
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
                    default:
                        _sPath = "/";
                        break;
                }
                LOGGER.log(Level.INFO, "{0} got a WebVisu", new Object[]{_sIP});
                LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisuhtm, _sIP});
                _xWebvisujar = downloadURL(_xHTTPs, _sIP, _sPath, _sWebvisujar);
                if (_xWebvisujar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sWebvisujar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sWebvisujar});
                }
                _xMinmljar = downloadURL(_xHTTPs, _sIP, _sPath, _sMinmljar);
                if (_xMinmljar) {
                    LOGGER.log(Level.INFO, "{0} downloaded from: {1}", new Object[]{_sMinmljar, _sIP});
                } else {
                    LOGGER.log(Level.INFO, "{0} don't have the file {1}", new Object[]{_sIP, _sMinmljar});
                }
            } else {
                LOGGER.log(Level.INFO, "{0} don't have a WebVisu", new Object[]{_sIP});
            }
        }
    }

    // Author: AndreasSterbenz: InstallCertJFrame.java
    // Created on 22 September 2008, 13:21
    // Edit: Christophe ICARD
    private static class SavingTrustManager implements X509TrustManager {

        private final X509TrustManager _tmTrustManager;
        private X509Certificate[] _certAcceptedIssuers;

        SavingTrustManager(X509TrustManager tmTrustManager) {
            this._tmTrustManager = tmTrustManager;
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
//            throw new UnsupportedOperationException();
//            return new X509Certificate[0];
            return _certAcceptedIssuers.clone();
        }

        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType)
                throws CertificateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType)
                throws CertificateException {
            this._certAcceptedIssuers = Arrays.copyOf(chain, chain.length);
            _tmTrustManager.checkServerTrusted(chain, authType);
        }
    }
}
