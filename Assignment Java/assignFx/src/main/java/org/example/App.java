package org.example;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


/**
 * Hello world!
 *
 */
public class App extends Application
{

    public static void main( String[] args ){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Bar Chart Sample");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Airlines Summary");
        xAxis.setLabel("Airlines");
        yAxis.setLabel("Value");


        ArrayList<Integer> incidents_85_99 = new ArrayList<Integer>();
        ArrayList<Integer> incidents_00_14 = new ArrayList<Integer>();
        FileReader filereader = new FileReader("C:\\Users\\User\\Desktop\\assignment\\airline_safety.csv");
        CSVReader csvReader = new CSVReader(filereader);
        String[] headerArray=csvReader.readNext();
        String[] nextRecord;
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("incidents between 1985 – 1999");
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("incidents between 2000 – 2014");
        while ((nextRecord = csvReader.readNext()) != null) {
            incidents_85_99.add(Integer.parseInt(nextRecord[3]));
            incidents_00_14.add(Integer.parseInt(nextRecord[6]));

            series1.getData().add(new XYChart.Data(String.valueOf(nextRecord[0]), Integer.parseInt(nextRecord[3])));
            series2.getData().add(new XYChart.Data(String.valueOf(nextRecord[0]), Integer.parseInt(nextRecord[6])));
        }
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1, series2);
        stage.setScene(scene);
        stage.show();
    }
}
