package org.example;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;

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
import java.util.Collections;

public class CsvXMLQuestion2 {

    public static void CSVReaderXMLWriter(String file){
         ArrayList<Integer> avail_seat_km_per_week = new ArrayList<Integer>();
         ArrayList<Integer> incidents_85_99 = new ArrayList<Integer>();
         ArrayList<Integer> fatal_accidents_85_99 = new ArrayList<Integer>();
         ArrayList<Integer> fatalities_85_99 = new ArrayList<Integer>();
         ArrayList<Integer> incidents_00_14 = new ArrayList<Integer>();
         ArrayList<Integer> fatal_accidents_00_14 = new ArrayList<Integer>();
         ArrayList<Integer> fatalities_00_14 = new ArrayList<Integer>();
         ArrayList<Integer> incidents_85_14 = new ArrayList<Integer>();

        try{
            FileReader filereader = new FileReader(file);
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            String[] headerArray=csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
//                avail_seat_km_per_week.add(Integer.parseInt(nextRecord[1]));
                incidents_85_99.add(Integer.parseInt(nextRecord[2]));
                fatal_accidents_85_99.add(Integer.parseInt(nextRecord[3]));
                fatalities_85_99.add(Integer.parseInt(nextRecord[4]));
                incidents_00_14.add(Integer.parseInt(nextRecord[5]));
                fatal_accidents_00_14.add(Integer.parseInt(nextRecord[6]));
                fatalities_00_14.add(Integer.parseInt(nextRecord[7]));
                incidents_85_14.add(Integer.parseInt(nextRecord[8]));
            }


            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element summary = doc.createElement("Summary");
            doc.appendChild(summary);

//            summary.appendChild(getSummary(doc, "avail_seat_km_per_week", Collections.min(avail_seat_km_per_week), Collections.max(avail_seat_km_per_week), getAvg(avail_seat_km_per_week)));
            summary.appendChild(getSummary(doc, "incidents_85_99", Collections.min(incidents_85_99), Collections.max(incidents_85_99), getAvg(incidents_85_99)));
            summary.appendChild(getSummary(doc, "fatal_accidents_85_99", Collections.min(fatal_accidents_85_99), Collections.max(fatal_accidents_85_99), getAvg(fatal_accidents_85_99)));
            summary.appendChild(getSummary(doc, "fatalities_85_99", Collections.min(fatalities_85_99), Collections.max(fatalities_85_99), getAvg(fatalities_85_99)));
            summary.appendChild(getSummary(doc, "incidents_00_14", Collections.min(incidents_00_14), Collections.max(incidents_00_14), getAvg(incidents_00_14)));
            summary.appendChild(getSummary(doc, "fatal_accidents_00_14", Collections.min(fatal_accidents_00_14), Collections.max(fatal_accidents_00_14), getAvg(fatal_accidents_00_14)));
            summary.appendChild(getSummary(doc, "fatalities_00_14", Collections.min(fatalities_00_14), Collections.max(fatalities_00_14), getAvg(fatalities_00_14)));
            summary.appendChild(getSummary(doc, "incidents_85_14", Collections.min(incidents_85_14), Collections.max(incidents_85_14), getAvg(incidents_85_14)));

            summary.appendChild(getSummary(doc, "avg_85_99", Collections.min(incidents_85_99), Collections.max(incidents_85_99), getAvg(incidents_85_99)));
            summary.appendChild(getSummary(doc, "avg_00_14", Collections.min(incidents_00_14), Collections.max(incidents_00_14), getAvg(incidents_00_14)));


            try (FileOutputStream output =
                         new FileOutputStream("airline_summary_statistic.xml")) {
                writeXml(doc, output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
    private static double getAvg(ArrayList<Integer> al){
        double sum=0;
        for(int a:al){
            sum+=a;
        }
        return sum/al.size();
    }
    private static Node getSummary(Document doc, String name, int minV, int maxV, Double avgV){

        String min=String.valueOf(minV);
        String max = String.valueOf(maxV);
        String avg = String.valueOf(avgV);

        Element row = doc.createElement("Stat");

        Element nameEle = doc.createElement("Name");
        nameEle.appendChild(doc.createTextNode(name));
        row.appendChild(nameEle);

        Element minEle = doc.createElement("Min");
        minEle.appendChild(doc.createTextNode(min));
        row.appendChild(minEle);

        Element maxEle = doc.createElement("Max");
        maxEle.appendChild(doc.createTextNode(max));
        row.appendChild(maxEle);

        Element avgEle = doc.createElement("Avg");
        avgEle.appendChild(doc.createTextNode(avg));
        row.appendChild(avgEle);

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
