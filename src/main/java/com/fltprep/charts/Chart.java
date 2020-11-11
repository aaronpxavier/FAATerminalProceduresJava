package com.fltprep.charts;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Arun Mavumkal
 */
@Entity
@Table(name = "chart")
public class Chart implements Comparable<Chart>, Serializable {
    @Id
    @Column(name = "pdf_name", unique = true)
    private String pdfName = null;
    @Column(name = "airport_id_icao")
    private String airportIdIcao = null;
    @Column(name = "airport_id")
    private String airportId = null;
    @Column(name = "state")
    private String state = null;
    @Column(name = "chart_type")
    private String chartType = null;
    @Column(name = "chart_name")
    private String chartName = null;
    @Column(name = "volume")
    private String volume = null;
    @Column(name = "cycle")
    private String cycle = null;

    private static final long serialVersionUID = 1L;

    public Chart() {}

    /**
     * @param chart - copy constructor.
     */
    public Chart(Chart chart) {
        airportIdIcao = chart.airportIdIcao;
        airportId = chart.airportId;
        state = chart.state;
        pdfName = chart.pdfName;
        chartType = chart.chartType;
        chartName = chart.chartName;
        volume = chart.volume;
    }

    /**
     * @param airportIdIcao - icao identifier for airport
     */
    public void setAirportIdIcao(String airportIdIcao) { this.airportIdIcao = airportIdIcao; }

    /**
     * @param airportId nonICao airportIdentifier
     */
    public void setAirportId(String airportId) { this.airportId = airportId; }

    /**
     * @param state - state abreviation
     */
    public void setStateName(String state) { this.state = state; }

    /**
     * @param pdfName - unique name for faa chart
     */
    public void setPdfName(String pdfName) { this.pdfName = pdfName; }

    /**
     * @param chartType - type of chart: "IAP;
     */
    public void setChartType(String chartType) { this.chartType = chartType; }

    /**
     * @param chartName - non unique name for chart
     */
    public void setChartName(String chartName) { this.chartName = chartName; }

    /**
     * @param volume - faa publication volume
     */
    public void setVolume(String volume) { this.volume = volume; }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    /**
     * @return - returns ICAO identifier for airport
     */
    public String getAirportIdIcao() { return airportIdIcao; }

    /**
     * @return -
     */
    public String getAirportId() { return  airportId; }

    public String getState() { return  state; }

    /**
     * @return 
     */
    public String getChartType() { return chartType; }

    public String getChartName() { return chartName; }

    public String getVolume() { return  volume; }

    public String getCycle() { return cycle; }

    public String getPdfName() throws ChartException {
        if(pdfName == null)
            throw new ChartException("pdfName Field is not initialized");
        return pdfName;
    }

    public boolean hasIcao() {
        return !(airportIdIcao == null || airportIdIcao.isEmpty());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof Chart))
            return false;
        Chart tempChart = (Chart)obj;
        return this.pdfName.equals(tempChart.pdfName);
    }

    @Override
    public int hashCode() {
        return pdfName.hashCode();
    }

    @Override
    public int compareTo(Chart chart) {
        return (this.pdfName).compareTo(chart.pdfName);
    }

    @Override
    public String toString() {
        return airportIdIcao + " " + airportId + " " + cycle + " " + state + " " +
                pdfName + " " + chartType + " " + chartName + " " + volume;
    }

}
