package com.fltprep.dttp;

import com.fltprep.charts.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;


public class ParseMetaFile {

    private static ChartMap getCharts(NodeList statesNodeList, String cycle) {
        ChartMap chartMap = new ChartMap();
        for(int i = 0; i < statesNodeList.getLength(); ++ i) {
            Element stateElement = (Element)statesNodeList.item(i);
            NodeList citiesNodeList = stateElement.getElementsByTagName("city_name");
            for(int j = 0; j < citiesNodeList.getLength(); ++j) {
                Element cityElement = (Element)citiesNodeList.item(j);
                NodeList airportsNodeList = cityElement.getElementsByTagName("airport_name");
                for(int k = 0; k < airportsNodeList.getLength(); ++k) {
                    Element airportElement = (Element)airportsNodeList.item(k);
                    NodeList recordsList = airportElement.getElementsByTagName("record");
                    for(int z = 0; z < recordsList.getLength(); ++z) {
                        Element recordElement = (Element)recordsList.item(z);
                        String pdfName = recordElement.getElementsByTagName("pdf_name").item(0).getTextContent();
                        if (!chartMap.hasChart(pdfName)) {
                            Chart tempChart = new Chart();
                            tempChart.setCycle(cycle);
                            tempChart.setStateName(stateElement.getAttribute("ID"));
                            tempChart.setAirportId(airportElement.getAttribute("apt_ident"));
                            tempChart.setAirportIdIcao(airportElement.getAttribute("icao_ident"));
                            tempChart.setChartType(recordElement.getElementsByTagName("chart_code").item(0).getTextContent());
                            tempChart.setChartName(recordElement.getElementsByTagName("chart_name").item(0).getTextContent());
                            tempChart.setPdfName(pdfName);
                            tempChart.setVolume(cityElement.getAttribute("volume"));
                            chartMap.put(tempChart);
                            //System.out.println(i + " " + tempChart.toString());
                        }
                    }
                }
            }
        }
        return chartMap;
    }

    public static ChartMap parseMetafile(File file) {
        ChartMap chartMap = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            String cycle = doc.getDocumentElement().getAttribute("cycle");
            chartMap = getCharts(doc.getDocumentElement().getElementsByTagName("state_code"), cycle);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chartMap;
    }
}
