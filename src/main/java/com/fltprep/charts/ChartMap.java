package com.fltprep.charts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedList;

public class ChartMap implements Iterator<Chart> {
    private Map<String, Chart> chartMap = new HashMap<String, Chart>();
    private Iterator iterator = chartMap.entrySet().iterator();
    ChartMap() {}

    ChartMap(Chart[] chartArray) {
        for(Chart chart : chartArray) {
            try {
                chartMap.put(chart.getPdfName(), chart);
            } catch(ChartException e) {
                e.printStackTrace();
            }
        }
    }

    ChartMap(LinkedList<Chart> chartList) {
        for(Chart chart: chartList) {
            try {
                chartMap.put(chart.getPdfName(), chart);
            } catch(ChartException e) {
                e.printStackTrace();
            }
        }
    }

    public void put(Chart chart) {
        try {
            chartMap.put(chart.getPdfName(), chart);
        } catch (ChartException e) {
            e.printStackTrace();
        }
    }

    public Chart get(String pdfName) {
        return chartMap.get(pdfName);
    }

    public boolean hasChart(String pdfName) {
        return chartMap.containsKey(pdfName);
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Chart next() {
        Map.Entry<String, Chart> chartMapSet = (Map.Entry<String, Chart>) iterator.next();
        return chartMapSet.getValue();
    }

}
