package biomass.simulator.core;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import java.awt.FlowLayout;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.plot.PlotOrientation;


/** @see http://stackoverflow.com/questions/5522575 */
public class ChartGraph {

    /**
	 * 
	 */
	private JFrame popframe, bioframe, bioageframe, orgageframe;
	private static final long serialVersionUID = 1L;

	//Population Series
    private XYSeries population=new XYSeries("Population");
    private XYSeries starved=new XYSeries("Starved");
    private XYSeries eaten=new XYSeries("Eaten");
    
    //Biomas Series
    private XYSeries leanmass=new XYSeries("LeanMass");
    private XYSeries fatmass=new XYSeries("FatMass");
    private XYSeries starvedmass=new XYSeries("StarvedMass");
    private XYSeries eatenmass=new XYSeries("EatenMass");
    private XYSeries wasteleanmass=new XYSeries("WasteLeanMass");
    private XYSeries wastefatmass=new XYSeries("WasteFatMass");
    
    //Biomass per Age Series
    private XYSeries bioagegroup1=new XYSeries("Age Group 1");
    private XYSeries bioagegroup2=new XYSeries("Age Group 2");
    private XYSeries bioagegroup3=new XYSeries("Age Group 3");
    private XYSeries bioagegroup4=new XYSeries("Age Group 4");
    private XYSeries bioagegroup5=new XYSeries("Age Group 5");
    
    //Organisms per Age Series
    private XYSeries agegroup1=new XYSeries("Age Group 1");
    private XYSeries agegroup2=new XYSeries("Age Group 2");
    private XYSeries agegroup3=new XYSeries("Age Group 3");
    private XYSeries agegroup4=new XYSeries("Age Group 4");
    private XYSeries agegroup5=new XYSeries("Age Group 5");
    
    private String title;

    public ChartGraph(String name) {
    	title=name;

    	bioframe=new JFrame(title+" Biomass Graph");
    	ChartPanel cpb=createBiomassChart(title);
    	bioframe.add(cpb, BorderLayout.CENTER);
    	bioframe.pack();
    	bioframe.setVisible(true);

    	orgageframe=new JFrame(title+" per Age Graph");
    	ChartPanel cpoa=createOrganismsAgeChart(title);
    	orgageframe.add(cpoa, BorderLayout.CENTER);
    	orgageframe.pack();
    	orgageframe.setVisible(true);

    	bioageframe=new JFrame(title+" Biomass per Age Graph");
    	ChartPanel cpba=createBiomassAgeChart(title);
    	bioageframe.add(cpba, BorderLayout.CENTER);
    	bioageframe.pack();
    	bioageframe.setVisible(true);

    }

    
    private ChartPanel createBiomassChart(String title) {
    	XYSeriesCollection dataset = createBiomassDataset();
        JFreeChart chart = ChartFactory.createXYLineChart(title, "Time", "Biomass", dataset, PlotOrientation.VERTICAL, true, false, false);
        return new ChartPanel(chart);
    }
    
    private ChartPanel createBiomassAgeChart(String title) {
    	XYSeriesCollection dataset = createBiomassAgeDataset();
        JFreeChart chart = ChartFactory.createXYLineChart(title, "Time", "Biomass", dataset, PlotOrientation.VERTICAL, true, false, false);
        return new ChartPanel(chart);
    }
    
    private ChartPanel createOrganismsAgeChart(String title) {
    	XYSeriesCollection dataset = createOrganismsAgeDataset();
        JFreeChart chart = ChartFactory.createXYLineChart(title, "Time", "Organisms", dataset, PlotOrientation.VERTICAL, true, false, false);
        return new ChartPanel(chart);
    }
    
    private XYSeriesCollection createBiomassDataset() {
    	XYSeriesCollection tsc = new XYSeriesCollection();
        tsc.addSeries(leanmass);
        tsc.addSeries(fatmass);
        tsc.addSeries(starvedmass);
        tsc.addSeries(eatenmass);
        tsc.addSeries(wasteleanmass);
        tsc.addSeries(wastefatmass);
        return tsc;
    }
    
    private XYSeriesCollection createBiomassAgeDataset() {
    	XYSeriesCollection tsc = new XYSeriesCollection();
        tsc.addSeries(bioagegroup1);
        tsc.addSeries(bioagegroup2);
        tsc.addSeries(bioagegroup3);
        tsc.addSeries(bioagegroup4);
        tsc.addSeries(bioagegroup5);
        return tsc;
    }
   
    private XYSeriesCollection createOrganismsAgeDataset() {
    	XYSeriesCollection tsc = new XYSeriesCollection();
        tsc.addSeries(agegroup1);
        tsc.addSeries(agegroup2);
        tsc.addSeries(agegroup3);
        tsc.addSeries(agegroup4);
        tsc.addSeries(agegroup5);
        tsc.addSeries(population);
        tsc.addSeries(starved);
        tsc.addSeries(eaten);
        return tsc;
    }
    
    public void updatePopulation(long step, int num) {
    	population.add(step, num);	           
    }
    
    public void updateStarved(long step, int num) {
    	starved.add(step, num);	           
    }
    
    public void updateEaten(long step, int num) {
    	eaten.add(step, num);	           
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
    
    public void updateEatenmass(long step, double num) {
    	eatenmass.add(step, num);	           
    }
    
    public void updateWasteFatmass(long step, double num) {
    	wastefatmass.add(step, num);	           
    }
    
    public void updateWasteLeanmass(long step, double num) {
    	wasteleanmass.add(step, num);	           
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
    }
    
    public void updateBioAgeGroup5(long step, double num) {
    	bioagegroup5.add(step, num);	           
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
    }
    
    public void updateAgeGroup5(long step, int num) {
    	agegroup5.add(step, num);	           
    }
}