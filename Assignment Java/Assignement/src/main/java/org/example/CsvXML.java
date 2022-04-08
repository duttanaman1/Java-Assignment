package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class CsvXML {
    public static void CSVReaderWriterXML(String file)
    {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("AirlineSafety");
            doc.appendChild(rootElement);

            // Create an object of filereader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object passing
            // file reader as a parameter
            CSVReader csvReader = new CSVReader(filereader);
            CSVWriter csvwriter = new CSVWriter(new

                    FileWriter("output.csv"), ',');

            String[] nextRecord;
            String[] headerArray=csvReader.readNext();
            String[] newHeaderArray = Arrays.copyOf(headerArray, headerArray.length + 1);
            newHeaderArray[newHeaderArray.length-1]="incidents_85_14";
            csvwriter.writeNext(newHeaderArray);
            ArrayList<String> totalIncidents = new ArrayList<>();

            // we are going to read data line by line
            while ((nextRecord = csvReader.readNext()) != null) {
                String[] newRecord = Arrays.copyOf(nextRecord, nextRecord.length + 1);
                for (String cell : nextRecord) {
                    System.out.print(cell + "\t");
                }
                System.out.println();

                totalIncidents.add(String.valueOf(Integer.parseInt(nextRecord[2])+Integer.parseInt(nextRecord[7])));
                newRecord[newRecord.length-1] = String.valueOf(Integer.parseInt(nextRecord[2])+Integer.parseInt(nextRecord[7]));
                csvwriter.writeNext(newRecord);

                rootElement.appendChild(getDetails(doc, newRecord[0],  newRecord[1],newRecord[2],newRecord[3],
                        newRecord[4],newRecord[5],newRecord[6],newRecord[7],newRecord[8] ));

            }
            System.out.println(totalIncidents);

            filereader.close();
            csvwriter.close();

            try (FileOutputStream output =
                         new FileOutputStream("converted_airline_safety.xml")) {
                writeXml(doc, output);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Node getDetails(Document doc, String airline, String avail_seat_km_per_week, String incidents_85_99, String fatal_accidents_85_99,
                                   String fatalities_85_99, String incidents_00_14, String fatal_accidents_00_14, String fatalities_00_14,
                                   String incidents_85_14){
        Element row = doc.createElement("row");

        Element airlineEle = doc.createElement("airline");
        airlineEle.appendChild(doc.createTextNode(airline));
        row.appendChild(airlineEle);

        Element avail_seat_km_per_weekEle = doc.createElement("avail_seat_km_per_week");
        avail_seat_km_per_weekEle.appendChild(doc.createTextNode(avail_seat_km_per_week));
        row.appendChild(avail_seat_km_per_weekEle);

        Element incidents_85_99Ele = doc.createElement("incidents_85_99");
        incidents_85_99Ele.appendChild(doc.createTextNode(incidents_85_99));
        row.appendChild(incidents_85_99Ele);

        Element fatal_accidents_85_99Ele = doc.createElement("fatal_accidents_85_99");
        fatal_accidents_85_99Ele.appendChild(doc.createTextNode(fatal_accidents_85_99));
        row.appendChild(fatal_accidents_85_99Ele);

        Element fatalities_85_99Ele = doc.createElement("fatalities_85_99");
        fatalities_85_99Ele.appendChild(doc.createTextNode(fatalities_85_99));
        row.appendChild(fatalities_85_99Ele);

        Element incidents_00_14Ele = doc.createElement("incidents_00_14");
        incidents_00_14Ele.appendChild(doc.createTextNode(incidents_00_14));
        row.appendChild(incidents_00_14Ele);

        Element fatal_accidents_00_14Ele = doc.createElement("fatal_accidents_00_14");
        fatal_accidents_00_14Ele.appendChild(doc.createTextNode(fatal_accidents_00_14));
        row.appendChild(fatal_accidents_00_14Ele);

        Element fatalities_00_14Ele = doc.createElement("fatalities_00_14");
        fatalities_00_14Ele.appendChild(doc.createTextNode(fatalities_00_14));
        row.appendChild(fatalities_00_14Ele);

        Element incidents_85_14Ele = doc.createElement("incidents_85_14");
        incidents_85_14Ele.appendChild(doc.createTextNode(incidents_85_14));
        row.appendChild(incidents_85_14Ele);

        return row;
    }
    private static void writeXml(Document doc,
                                 OutputStream output)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        System.out.println("XML created successfully");
        transformer.transform(source, result);

    }
}



