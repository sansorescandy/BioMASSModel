package biomass.simulator.core;


import java.awt.BorderLayout;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.PlotOrientation;


public class PopulationWasteChartGraph {
    /**
	 * 
	 */
	private JFrame popbiomassframe;
    
    //Biomas Series
    private XYSeries wasteleanmass=new XYSeries("WasteLeanMass");
    private XYSeries wastefatmass=new XYSeries("WasteFatMass");
    private JFreeChart chart;

    public PopulationWasteChartGraph(String title) {
    	popbiomassframe=new JFrame(title+" Population Waste Biomass Graph");
    	ChartPanel cpb=createPopBiomassChart(title);
    	popbiomassframe.add(cpb, BorderLayout.CENTER);
    	popbiomassframe.pack();
    	popbiomassframe.setVisible(true);
    }

    
    private ChartPanel createPopBiomassChart(String title) {
    	XYSeriesCollection dataset = createPopBiomassDataset();
        chart = ChartFactory.createXYLineChart(title, "Time", "Biomass", dataset, PlotOrientation.VERTICAL, true, false, false);
        return new ChartPanel(chart);
    }
    
    public void setNotify(boolean notify) {
    	chart.setNotify(notify);
    }
    
    private XYSeriesCollection createPopBiomassDataset() {
    	XYSeriesCollection tsc = new XYSeriesCollection();
        tsc.addSeries(wasteleanmass);
        tsc.addSeries(wastefatmass);
        return tsc;
    }  
    
    
    public void updateWasteFatmass(long step, double num) {
    	wastefatmass.add(step, num);	           
    }
    
    public void updateWasteLeanmass(long step, double num) {
    	wasteleanmass.add(step, num);	           
    }
}