package WebVisuClass;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AppletParameter {

    private static final Logger LOGGER = Logger.getLogger(AppletParameter.class.getName());

    private String _Name;
    private String _Value;

    /* Start constructor */
    public AppletParameter() {
    }

    public AppletParameter(String name) {
        if (name == null) {
            LOGGER.log(Level.WARNING, "name NULL");
            throw new IllegalArgumentException("nNull");
        }
        if (name.length() == 0) {
            LOGGER.log(Level.WARNING, "name empty");
            throw new IllegalArgumentException("nEmpty");
        }
        this._Name = name;
        this._Value = "";
    }

    public AppletParameter(String name, String value) {
        if (name == null) {
            LOGGER.log(Level.WARNING, "name NULL");
            throw new IllegalArgumentException("nNull");
        }
        if (name.length() == 0) {
            LOGGER.log(Level.WARNING, "name empty");
            throw new IllegalArgumentException("nEmpty");
        }
        this._Name = name;
        if (value == null) {
            LOGGER.log(Level.WARNING, "value NULL");
            throw new IllegalArgumentException("vNull");
        }
        if (value.length() == 0) {
            LOGGER.log(Level.WARNING, "value empty");
            throw new IllegalArgumentException("vEmpty");
        }
        this._Value = value;
    }

    /* End constructor */

 /* Start accessor */
    public String getName() {
        return _Name;
    }

    public String getValue() {
        return _Value;
    }
    /* End accessor */

 /* Start mutator */
    public void setName(String name) throws IllegalArgumentException {
        if (name == null) {
            LOGGER.log(Level.WARNING, "name NULL");
            throw new IllegalArgumentException("nNull");
        }
        if (name.length() == 0) {
            LOGGER.log(Level.WARNING, "name empty");
            throw new IllegalArgumentException("nEmpty");
        }
        this._Name = name;
    }

    public void setValue(String value) throws IllegalArgumentException {
        if (value == null) {
            LOGGER.log(Level.WARNING, "value NULL");
            throw new IllegalArgumentException("nNull");
        }
        if (value.length() == 0) {
            LOGGER.log(Level.WARNING, "value empty");
            throw new IllegalArgumentException("nEmpty");
        }
        this._Value = value;
    }
    /* End mutator */
    
    // Overriding compareTo()
    public Integer compareTo(Object object) throws IllegalArgumentException {
        if (object == null) {
            LOGGER.log(Level.WARNING, "object empty");
            throw new IllegalArgumentException("oNull");
        }
        if (!(object instanceof AppletParameter)) {
            LOGGER.log(Level.WARNING, "object is not an AppletParameter Oject");
            throw new IllegalArgumentException("oError");
        }
        AppletParameter autre = (AppletParameter) object;
        if (this._Name.compareTo(autre.getName()) == 0) {
            return this._Value.compareTo(autre.getValue());
        }
        return this._Name.compareTo(autre.getName());
    }
}
