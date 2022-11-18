package WebVisuClass;

import java.util.logging.Logger;

public class Version {

    private static final Logger LOGGER = Logger.getLogger(Version.class.getName());

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static String version() {
        String _sVersion;
        _sVersion = "v0.0.12";
        System.out.println(_sVersion);
        return _sVersion;
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void changeLog() {
        String _sChangeLog;
        _sChangeLog = "Changelog:"
                + "\r\n" + "\t" + "v0.0.1"
                + "\r\n" + "\t" + "\t" + "First test version."
                + "\r\n" + "\t" + "v0.0.2"
                + "\r\n" + "\t" + "\t" + "Add autodetection of HTTP/HTTPs (accept all certificate)."
                + "\r\n" + "\t" + "v0.0.3"
                + "\r\n" + "\t" + "\t" + "Add autodetection of the I/O 70-8xx, 758-87x, 750-82xx."
                + "\r\n" + "\t" + "v0.0.4"
                + "\r\n" + "\t" + "\t" + "Add HTTPs certificate check."
                + "\r\n" + "\t" + "v0.0.5"
                + "\r\n" + "\t" + "\t" + "Add command line."
                + "\r\n" + "\t" + "v0.0.6"
                + "\r\n" + "\t" + "\t" + "The graphical system use a task to update the current status in the frame."
                + "\r\n" + "\t" + "v0.0.7"
                + "\r\n" + "\t" + "\t" + "Add detection of HTML5 webvisu and start it in the default web browser."
                + "\r\n" + "\t" + "v0.0.8"
                + "\r\n" + "\t" + "\t" + "Change run system from Java Web Start to java call in command."
                + "\r\n" + "\t" + "v0.0.9"
                + "\r\n" + "\t" + "\t" + "Change from Java FX to Swing."
                + "\r\n" + "\t" + "v0.0.10"
                + "\r\n" + "\t" + "\t" + "Add the use of Hostname."
                + "\r\n" + "\t" + "v0.0.11"
                + "\r\n" + "\t" + "\t" + "Add an option to disable the PING command."
                + "\r\n" + "\t" + "v0.0.12"
                + "\r\n" + "\t" + "\t" + "Each Webvisu instance start in their own task.";
        System.out.println(_sChangeLog);
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public static void help() {
        String _sHelp;
        _sHelp = "Use:"
                + "\r\n" + "\t" + "-c" + "\t" + "\t" + "Print the Changelog"
                + "\r\n" + "\t" + "-v" + "\t" + "\t" + "Print the Version Number."
                + "\r\n" + "\t" + "-h" + "\t" + "\t" + "Print this help"
                + "\r\n" + "\t" + "-ip IP" + "\t" + "\t" + "Connect to the defined IP address."
                + "\r\n" + "\t" + "-np" + "\t" + "\t" + "Disable the PING command."
                + "\r\n" + "\t" + "-visu STARTVISU" + "\t" + "Force the Start page of the WebVisu (Default: PLC_VISU)";
        System.out.println(_sHelp);
    }

    public static void main(String[] args) {
        version();
        changeLog();
        help();
    }

}
