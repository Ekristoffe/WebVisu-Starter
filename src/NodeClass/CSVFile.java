package NodeClass;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVFile {

    private static final Logger LOGGER = Logger.getLogger(CSVFile.class.getName());

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\r\n";

    //CSV file header
    private static final String FILE_HEADER = "name,ip,web";

    //Index Meaning
    private static final int NAME_IDX = 0;
    private static final int ADDRESS_IDX = 1;
    private static final int WEB_IDX = 2;

    public static ArrayList<Node> readCSVFile(String fileName) {

        BufferedReader _fileReader = null;

        //Create a new list of station to be filled by CSV file data 
        ArrayList<Node> alNode = new ArrayList<>();

        try {
            String _line;

            //Create the file reader
            _fileReader = new BufferedReader(new FileReader(fileName));

            //Read the CSV file header to skip it
            _fileReader.readLine();

            //Read the file line by line starting from the second line
            _line = _fileReader.readLine();
            while (_line != null) {
                //Get all tokens available in line
                String[] _items = _line.split(COMMA_DELIMITER);
                Node _station;
                switch (_items.length){
                case 1:
                    //Create a new student object and fill his  data
                    _station = new Node(_items[NAME_IDX]);
                    alNode.add(_station);
                    break;
                case 2:
                    //Create a new student object and fill his  data
                    _station = new Node(_items[NAME_IDX], _items[ADDRESS_IDX]);
                    alNode.add(_station);
                    break;
                case 3:
                    //Create a new student object and fill his  data
                    _station = new Node(_items[NAME_IDX], _items[ADDRESS_IDX], _items[WEB_IDX]);
                    alNode.add(_station);
                    break;
                }
                _line = _fileReader.readLine();
            }
            LOGGER.log(Level.FINE, "CSV file was read successfully !!!");
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } finally {
            if (_fileReader != null) {
                try {
                    _fileReader.close();
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                }
            }
        }

        return alNode;

    }

    public static void writeCSVFile(String fileName, ArrayList<Node> alNode) {

        FileWriter _fileWriter = null;

        try {
            _fileWriter = new FileWriter(fileName, false);  // true to append
            // false to overwrite.
            //Write the CSV file header
            _fileWriter.append(FILE_HEADER);

            //Add a new line separator after the header
            _fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new student object list to the CSV file
            for (Node _station : alNode) {
                _fileWriter.append(_station.getName());
                _fileWriter.append(COMMA_DELIMITER);
                _fileWriter.append(_station.getAddress());
                _fileWriter.append(NEW_LINE_SEPARATOR);
            }
            LOGGER.log(Level.FINE, "CSV file was created successfully !!!");

        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.toString(), ex);
        } finally {
            if (_fileWriter != null) {
                try {
                    _fileWriter.flush();
                    try {
                        _fileWriter.close();
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, ex.toString(), ex);
                    }
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, ex.toString(), ex);
                }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ArrayList<Node> alNode = new ArrayList<>();
        String FILENAME = "stations.csv";
        alNode = readCSVFile(FILENAME);
        writeCSVFile(FILENAME, alNode);
    }

}
