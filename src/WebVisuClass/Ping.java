package WebVisuClass;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ping {

    private static final Logger LOGGER = Logger.getLogger(Ping.class.getName());

    public static boolean isReachable(String sIP, int iTimeout) {
        int _iTimeout;

        if (iTimeout == 0) {
            _iTimeout = 10000;
        } else {
            _iTimeout = iTimeout;
        }

        try {
            InetAddress inet = InetAddress.getByName(sIP);

            Boolean _xReachable;
            _xReachable = inet.isReachable(_iTimeout);
            return _xReachable;
        } catch (UnknownHostException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return false;
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
            return false;
        }

    }

    public static void main(String[] args) {
        String _sIP;
        _sIP = "192.168.0.1";
        LOGGER.log(Level.INFO, "Is {0} Available: {1}", new Object[]{_sIP, isReachable(_sIP, 1000)});
        _sIP = "192.168.0.2";
        LOGGER.log(Level.INFO, "Is {0} Available: {1}", new Object[]{_sIP, isReachable(_sIP, 1000)});
        _sIP = "192.168.0.3";
        LOGGER.log(Level.INFO, "Is {0} Available: {1}", new Object[]{_sIP, isReachable(_sIP, 1000)});
        _sIP = "192.168.0.4";
        LOGGER.log(Level.INFO, "Is {0} Available: {1}", new Object[]{_sIP, isReachable(_sIP, 1000)});
        _sIP = "192.168.1.1";
        LOGGER.log(Level.INFO, "Is {0} Available: {1}", new Object[]{_sIP, isReachable(_sIP, 1000)});
        _sIP = "192.168.1.2";
        LOGGER.log(Level.INFO, "Is {0} Available: {1}", new Object[]{_sIP, isReachable(_sIP, 1000)});
        _sIP = "192.168.1.3";
        LOGGER.log(Level.INFO, "Is {0} Available: {1}", new Object[]{_sIP, isReachable(_sIP, 1000)});
        _sIP = "192.168.1.4";
        LOGGER.log(Level.INFO, "Is {0} Available: {1}", new Object[]{_sIP, isReachable(_sIP, 1000)});
    }
}
