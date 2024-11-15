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


public class PopulationChartGraph {

    /**
	 * 
	 */
	private JFrame organismsageframe;

	//Population Series
    //private XYSeries population=new XYSeries("Population");
    private XYSeries starved=new XYSeries("Starved");
    private XYSeries eaten=new XYSeries("Eaten");
    private JFreeChart chart;
    
    //Organisms per Age Series
   /* private XYSeries agegroup0=new XYSeries("Age Group 0");
    private XYSeries agegroup1=new XYSeries("Age Group 1");
    private XYSeries agegroup2=new XYSeries("Age Group 2");
    private XYSeries agegroup3=new XYSeries("Age Group 3");
    private XYSeries agegroup4=new XYSeries("Age Group 4");*/
    private ArrayList<XYSeries> series=new ArrayList<XYSeries>();
 

    public PopulationChartGraph(String title, int classgroups) {
    	for(int i=0;i<classgroups;i++)
    		series.add(new XYSeries("Age Group "+i));
    	organismsageframe=new JFrame(title+" per Age Graph");
    	ChartPanel cpoa=createOrganismsPerAgeChart(title);
    	cpoa.setHorizontalAxisTrace(true);
    	cpoa.setVerticalAxisTrace(true);
    	organismsageframe.add(cpoa, BorderLayout.CENTER);
    	organismsageframe.pack();
    	organismsageframe.setVisible(true);
    }
    
    public void setNotify(boolean notify) {
    	chart.setNotify(notify);
    }

    
    private ChartPanel createOrganismsPerAgeChart(String title) {
    	XYSeriesCollection dataset = createOrganismsPerAgeDataset();
        chart = ChartFactory.createXYLineChart(title, "Time", "Organisms", dataset, PlotOrientation.VERTICAL, true, false, false);
        return new ChartPanel(chart);
    }
   
/*    private XYSeriesCollection createOrganismsPerAgeDataset() {
    	XYSeriesCollection tsc = new XYSeriesCollection();
        tsc.addSeries(agegroup0);
        tsc.addSeries(agegroup1);
        tsc.addSeries(agegroup2);
        tsc.addSeries(agegroup3);
        tsc.addSeries(agegroup4);
        tsc.addSeries(population);
        tsc.addSeries(starved);
        tsc.addSeries(eaten);
        return tsc;
    }*/
    
    private XYSeriesCollection createOrganismsPerAgeDataset() {
    	XYSeriesCollection tsc = new XYSeriesCollection();
    	for(XYSeries s:series) 
    		tsc.addSeries(s);
        //tsc.addSeries(population);
        //tsc.addSeries(starved);
        //tsc.addSeries(eaten);
        return tsc;
    }
    
/*    public void updatePopulation(long step, int num) {
    	population.add(step, num);	           
    }*/
    
    public void updateStarved(long step, int num) {
    	starved.add(step, num);	 
    }
    
    public void updateEaten(long step, int num) {
    	eaten.add(step, num);	           
    }
    
    public void updateAgeGroups(long step, int[] num) {
    	for(int i=0; i< series.size(); i++)
    		series.get(i).add(step, num[i]);
    }
    
/*    public void updateAgeGroup0(long step, int num) {
    	agegroup0.add(step, num);	           
    }
 
    public void updateAgeGroup1(long step, int num) {
    	agegroup1.add(step, num);	           
    }
    
    public void updateAgeGroup2(long step, int num) {
    	agegroup2.add(step, num);	           
    }
    
    public void updateAgeGroup3(long step, int num) {
    	agegroup3.add(step, num);	           
    }
    
    public void updateAgeGroup4(long step, int num) {
    	agegroup4.add(step, num);	           
    }*/
}