package biomass.simulator.core;


import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.PlotOrientation;


public class BiomassAgeGroupChartGraph {

    /**
	 * 
	 */
	private JFrame biomassageframe;

	private ArrayList<XYSeries> series=new ArrayList<XYSeries>();
	private JFreeChart chart;
   /* private XYSeries bioagegroup0=new XYSeries("Age Group 0");
    private XYSeries bioagegroup1=new XYSeries("Age Group 1");
    private XYSeries bioagegroup2=new XYSeries("Age Group 2");
    private XYSeries bioagegroup3=new XYSeries("Age Group 3");
    private XYSeries bioagegroup4=new XYSeries("Age Group 4");*/
    

    public BiomassAgeGroupChartGraph(String title, int classgroups) {
    	for(int i=0;i<classgroups;i++)
    		series.add(new XYSeries("Age Group "+i));
    	biomassageframe=new JFrame(title+" Biomass per Age Graph");
    	ChartPanel cpba=createBiomassAgeChart(title);
    	biomassageframe.add(cpba, BorderLayout.CENTER);
    	biomassageframe.pack();
    	biomassageframe.setVisible(true);
    }

    
    private ChartPanel createBiomassAgeChart(String title) {
    	XYSeriesCollection dataset = createBiomassAgeDataset();
        chart = ChartFactory.createXYLineChart(title, "Time", "Biomass", dataset, PlotOrientation.VERTICAL, true, false, false);
        return new ChartPanel(chart);
    }
      
    
    public void setNotify(boolean notify) {
    	chart.setNotify(notify);
    }
    
/*    private XYSeriesCollection createBiomassAgeDataset() {
    	XYSeriesCollection tsc = new XYSeriesCollection();
        tsc.addSeries(bioagegroup0);
        tsc.addSeries(bioagegroup1);
        tsc.addSeries(bioagegroup2);
        tsc.addSeries(bioagegroup3);
        tsc.addSeries(bioagegroup4);
        return tsc;
    }*/
    
    private XYSeriesCollection createBiomassAgeDataset() {
    	XYSeriesCollection tsc = new XYSeriesCollection();
    	for(XYSeries s:series) 
    		tsc.addSeries(s);
        return tsc;
    }
    
    public void updateAgeGroups(long step, double[] num) {
    	for(int i=0; i< series.size(); i++)
    		series.get(i).add(step, num[i]);
    }
    
/*    public void updateBioAgeGroup0(long step, double num) {
    	bioagegroup0.add(step, num);	           
    }
 
    public void updateBioAgeGroup1(long step, double num) {
    	bioagegroup1.add(step, num);	           
    }
    
    public void updateBioAgeGroup2(long step, double num) {
    	bioagegroup2.add(step, num);	           
    }
    
    public void updateBioAgeGroup3(long step, double num) {
    	bioagegroup3.add(step, num);	           
    }
    
    public void updateBioAgeGroup4(long step, double num) {
    	bioagegroup4.add(step, num);	           
    }*/
}