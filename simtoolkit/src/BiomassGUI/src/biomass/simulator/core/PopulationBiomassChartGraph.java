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
        chart = ChartFactory.createXYLineChart(title, "Time", "Biomass", dataset, PlotOrientation.VERTICAL, true, false, false);
        return new ChartPanel(chart);
    }
    
    
    private XYSeriesCollection createPopBiomassDataset() {
    	XYSeriesCollection tsc = new XYSeriesCollection();
        tsc.addSeries(leanmass);
        tsc.addSeries(fatmass);
        tsc.addSeries(starvedmass);
        //tsc.addSeries(eatenmass);
        //tsc.addSeries(wasteleanmass);
        //tsc.addSeries(wastefatmass);
        return tsc;
    }  
    
    public void setNotify(boolean notify) {
    	chart.setNotify(notify);
    }
    
    public void updateLeanmass(long step, double num) {
    	leanmass.add(step, num);	           
    }
    
    public void updateFatmass(long step, double num) {
    	fatmass.add(step, num);	           
    }
    
    public void updateStarvedmass(long step, double num) {
    	starvedmass.add(step, num);	           
    }
    
/*    public void updateEatenmass(long step, double num) {
    	eatenmass.add(step, num);	           
    }*/
    
/*    public void updateWasteFatmass(long step, double num) {
    	wastefatmass.add(step, num);	           
    }*/
    
/*    public void updateWasteLeanmass(long step, double num) {
    	wasteleanmass.add(step, num);	           
    }*/
}