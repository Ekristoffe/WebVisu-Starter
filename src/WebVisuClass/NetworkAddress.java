package WebVisuClass;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkAddress {

    private static final Logger LOGGER = Logger.getLogger(NetworkAddress.class.getName());

    
    public static String addressResolver(String sAddress) {
        try {
            InetAddress ipAddress = InetAddress.getByName(sAddress);
            sAddress = ipAddress.getHostAddress();
            return sAddress;
        } catch (UnknownHostException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return null;
        }
    }
    public static boolean addressChecker(String sAddress) {
        
        try {
            if (sAddress == null || sAddress.isEmpty()) {
                return false;
            }

            if (sAddress.endsWith(".")) {
                return false;
            }

            if (sAddress.startsWith(".")) {
                return false;
            }

            String[] _Parts = sAddress.split("\\.");
            if (_Parts.length != 4) {
                return false;
            }

            int _i0 = Integer.parseInt(_Parts[0]);
            if ((_i0 < 1) || (_i0 > 254)) {
                return false;
            }

            int _i1 = Integer.parseInt(_Parts[1]);
            if ((_i1 < 0) || (_i1 > 254)) {
                return false;
            }

            int _i2 = Integer.parseInt(_Parts[2]);
            if ((_i2 < 0) || (_i2 > 254)) {
                return false;
            }

            int _i3 = Integer.parseInt(_Parts[3]);
            return ((_i3 >= 1) && (_i3 <= 254));

        } catch (NumberFormatException nfe) {
            LOGGER.log(Level.SEVERE, nfe.toString(), nfe);
            return false;
        }
    }

    public static void main(String[] args) {

        String _sAddress, _sIP;
        _sAddress = "google.com";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = "www.google.com";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = "www.free.fr";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = ".192.168.1.1";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = "192.168.1.1.";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = "192.0.0.1";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = "192.0.0.0";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = "0.0.0.1";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = "255.0.0.1";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = "192.255.0.1";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = "192.0.255.1";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = "192.0.0.255";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
        _sAddress = "192.0.0";
        _sIP = addressResolver(_sAddress);
        LOGGER.log(Level.INFO, "Is {0} a valid IP: {1}", new Object[]{_sAddress, addressChecker(_sIP)});
    }

}
