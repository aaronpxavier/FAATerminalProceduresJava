package com.fltprep.charts;

import java.util.*;

class MapIterator implements Iterator<Chart> {
    private Iterator<Map.Entry<String, Chart>> iterator;
    MapIterator(Iterator<Map.Entry<String, Chart>> iterator) {
        this.iterator = iterator;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public Chart next() {
        Map.Entry<String, Chart> chartMapSet = iterator.next();
        return chartMapSet.getValue();
    }
}

public class ChartMap implements Iterable<Chart> {
    private Map<String, Chart> chartMap = new HashMap<String, Chart>();

    public ChartMap() {}

    public ChartMap(Chart[] chartArray) {
        for(Chart chart : chartArray) {
            try {
                chartMap.put(chart.getPdfName(), chart);
            } catch(ChartException e) {
                e.printStackTrace();
            }
        }
    }

    public ChartMap(List<Chart> chartList) {
        for(Chart chart: chartList) {
            try {
                chartMap.put(chart.getPdfName(), chart);
            } catch(ChartException e) {
                e.printStackTrace();
            }
        }
    }

    public Iterator<Chart> iterator() {
        return new MapIterator(chartMap.entrySet().iterator());
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

}
