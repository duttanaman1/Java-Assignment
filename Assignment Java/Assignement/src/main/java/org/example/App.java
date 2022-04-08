package org.example;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ParserConfigurationException, IOException, TransformerException {
        String csvFilePath = "airline_safety.csv";
        CsvXML.CSVReaderWriterXML(csvFilePath);
        CsvXMLQuestion2.CSVReaderXMLWriter("output.csv");

    }
}
