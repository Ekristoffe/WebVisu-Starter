package NodeClass;

import WebVisuClass.NetworkAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Node {

    private static final Logger LOGGER = Logger.getLogger(Node.class.getName());

    private String _sName;
    private String _sAddress;
    private Boolean _xWebPage; // Default page is the webvisu


    /* Start constructor */
    public Node() {
    }

    public Node(String sAddress) {
        String _sIP;
        _sIP = NetworkAddress.addressResolver(sAddress);
        if (NetworkAddress.addressChecker(_sIP) == false) {
            LOGGER.log(Level.WARNING, "sAddress Error");
            throw new IllegalArgumentException("ipError");
        }
        this._sName = sAddress;
        
        this._sAddress = sAddress;
        
        this._xWebPage = false;
    }

    public Node(String sName, String sAddress) {
        if (sName == null) {
            LOGGER.log(Level.WARNING, "sName NULL");
            throw new IllegalArgumentException("nNull");
        }
        if (sName.length() == 0) {
            LOGGER.log(Level.WARNING, "sName Empty");
            throw new IllegalArgumentException("nEmpty");
        }
        this._sName = sName;
        
        String _sIP;
        _sIP = NetworkAddress.addressResolver(sAddress);
        if (NetworkAddress.addressChecker(_sIP) == false) {
            LOGGER.log(Level.WARNING, "sIP Error");
            throw new IllegalArgumentException("ipError");
        }
        this._sAddress = sAddress;
        
        this._xWebPage = false;
    }

    public Node(String sName, String sAddress, Boolean xWebPage) {
        if (sName == null) {
            LOGGER.log(Level.WARNING, "sName NULL");
            throw new IllegalArgumentException("nNull");
        }
        if (sName.length() == 0) {
            LOGGER.log(Level.WARNING, "sName Empty");
            throw new IllegalArgumentException("nEmpty");
        }
        this._sName = sName;
        
        String _sIP;
        _sIP = NetworkAddress.addressResolver(sAddress);
        if (NetworkAddress.addressChecker(_sIP) == false) {
            LOGGER.log(Level.WARNING, "sIP Error");
            throw new IllegalArgumentException("ipError");
        }
        this._sAddress = sAddress;
        
        if (xWebPage == null) {
            LOGGER.log(Level.WARNING, "xWebPage NULL");
            throw new IllegalArgumentException("wNull");
        }
        this._xWebPage = xWebPage;
    }
    public Node(String sName, String sAddress, Integer iWebPage) {
        if (sName == null) {
            LOGGER.log(Level.WARNING, "sName NULL");
            throw new IllegalArgumentException("nNull");
        }
        if (sName.length() == 0) {
            LOGGER.log(Level.WARNING, "sName Empty");
            throw new IllegalArgumentException("nEmpty");
        }
        this._sName = sName;
        
        String _sIP;
        _sIP = NetworkAddress.addressResolver(sAddress);
        if (NetworkAddress.addressChecker(_sIP) == false) {
            LOGGER.log(Level.WARNING, "sIP Error");
            throw new IllegalArgumentException("ipError");
        }
        this._sAddress = sAddress;
        
        if (iWebPage == null) {
            LOGGER.log(Level.WARNING, "iWebPage NULL");
            throw new IllegalArgumentException("wNull");
        }
        this._xWebPage = (iWebPage != 0);
    }

    public Node(String sName, String sAddress, String sWebPage) {
        if (sName == null) {
            LOGGER.log(Level.WARNING, "sName NULL");
            throw new IllegalArgumentException("nNull");
        }
        if (sName.length() == 0) {
            LOGGER.log(Level.WARNING, "sName Empty");
            throw new IllegalArgumentException("nEmpty");
        }
        this._sName = sName;
        
        String _sIP;
        _sIP = NetworkAddress.addressResolver(sAddress);
        if (NetworkAddress.addressChecker(_sIP) == false) {
            LOGGER.log(Level.WARNING, "sIP Error");
            throw new IllegalArgumentException("ipError");
        }
        this._sAddress = sAddress;
        
        if (sWebPage == null) {
            LOGGER.log(Level.WARNING, "sWebPage NULL");
            throw new IllegalArgumentException("wNull");
        }
        this._xWebPage = (Integer.parseInt(sWebPage) != 0);
    }

    /* End constructor */

 /* Start accessor */
    public String getName() {
        return _sName;
    }

    public String getAddress() {
        return _sAddress;
    }

    public Boolean getWebPage() {
        return _xWebPage;
    }

    /* End accessor */

 /* Start mutator */
    public void setName(String sName) throws IllegalArgumentException {
        if (sName == null) {
            LOGGER.log(Level.WARNING, "sName NULL");
            throw new IllegalArgumentException("nNull");
        }
        if (sName.length() == 0) {
            LOGGER.log(Level.WARNING, "sName Empty");
            throw new IllegalArgumentException("nEmpty");
        }
        this._sName = sName;
    }

    public void setIP(String sAddress) throws IllegalArgumentException {
        String _sIP;
        _sIP = NetworkAddress.addressResolver(sAddress);
        if (NetworkAddress.addressChecker(_sIP) == false) {
            LOGGER.log(Level.WARNING, "sIP error");
            throw new IllegalArgumentException("ipError");
        }
        this._sAddress = sAddress;
    }

    public void setWebPage(Boolean xWebPage) throws IllegalArgumentException {
        if (xWebPage == null) {
            LOGGER.log(Level.WARNING, "xWebPage NULL");
            throw new IllegalArgumentException("wNull");
        }
    }

    /* End mutator */
    // Overriding equals()
    @Override
    public boolean equals(Object oAutre) {
        if (oAutre == null) {
            return false;
        }
        // If the object is compared with itself then return true  
        if (oAutre == this) {
            return true;
        }
        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(oAutre instanceof Node)) {
            return false;
        } // Compare the data members and return accordingly 
        else {
            return ((Node) oAutre).getAddress().equals(this._sAddress);
        }
    }

    // Overriding hashCode()
    @Override
    public int hashCode() {
        return _sAddress.hashCode();
    }

    // Overriding compareTo()
    public int compareTo(Object oAutre) throws IllegalArgumentException {
        if (oAutre == null) {
            LOGGER.log(Level.WARNING, "object empty");
            throw new IllegalArgumentException("oNull");
        }
        if (!(oAutre instanceof Node)) {
            LOGGER.log(Level.WARNING, "object is not an Station Oject");
            throw new IllegalArgumentException("oError");
        }
        Node stationAutre = (Node) oAutre;
        if (this._sName.compareTo(stationAutre.getName()) == 0) {
            return this._sAddress.compareTo(stationAutre.getAddress());
        }
        return this._sName.compareTo(stationAutre.getName());
    }
}
