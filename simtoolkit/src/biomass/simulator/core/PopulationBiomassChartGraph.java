package biomass.simulator.core;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.PlotOrientation;


public class PopulationBiomassChartGraph {
    /**
	 * 
	 */
	private JFrame popbiomassframe;
    
    //Biomas Series
    private XYSeries leanmass=new XYSeries("LeanMass");
    private XYSeries fatmass=new XYSeries("FatMass");
    private XYSeries killedmass=new XYSeries("KilledMass");
    private XYSeries starvedmass=new XYSeries("StarvedMass");
    private JFreeChart chart;
    
    //private XYSeries eatenmass=new XYSeries("EatenMass");
    //private XYSeries wasteleanmass=new XYSeries("WasteLeanMass");
    //private XYSeries wastefatmass=new XYSeries("WasteFatMass");

    public PopulationBiomassChartGraph(String title) {
    	popbiomassframe=new JFrame(title+" Population Biomass Graph");
    	ChartPanel cpb=createPopBiomassChart(title);
    	popbiomassframe.add(cpb, BorderLayout.CENTER);
    	popbiomassframe.pack();
    	popbiomassframe.setVisible(true);
    }

    
    private ChartPanel createPopBiomassChart(String title) {
    	XYSeriesCollection dataset = createPopBiomassDataset();
        chart = ChartFactory.createXYLineChart(title, "Simulation Time (days)", "Biomass", dataset, PlotOrientation.VERTICAL, true, false, false);
        return new ChartPanel(chart);
    }
    
    
    private XYSeriesCollection createPopBiomassDataset() {
    	XYSeriesCollection tsc = new XYSeriesCollection();
        tsc.addSeries(leanmass);
        tsc.addSeries(fatmass);
        tsc.addSeries(killedmass);
        tsc.addSeries(starvedmass);
        return tsc;
    }  
    
    public void setNotify(boolean notify) {
    	chart.setNotify(notify);
    }
    
    public void updateLeanmass(double time, double num) {
    	leanmass.add(time, num);	
    }
    
    public void updateFatmass(double time, double num) {
    	fatmass.add(time, num);	           
    }
    
    public void updateStarvedmass(double time, double num) {
    	starvedmass.add(time, num);	           
    }
    
    public void updateKilledmass(double time, double num) {
    	killedmass.add(time, num);	           
    }
    
    public void clear(){
    	leanmass.clear();
    	fatmass.clear();
    	killedmass.clear();
    	starvedmass.clear();
    }
    
}