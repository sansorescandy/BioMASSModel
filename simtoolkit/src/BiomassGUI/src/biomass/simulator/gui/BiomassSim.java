package biomass.simulator.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import java.awt.BorderLayout;

import biomass.simulator.dataservice.DataService;
import biomass.simulator.dataservice.SqlLiteDAO;
import biomass.simulator.gui.controller.FunctionalGroupController;
import biomass.simulator.gui.controller.PopulationController;
import biomass.simulator.gui.controller.SimulationController;
import biomass.simulator.gui.model.Simulation;
import biomass.simulator.gui.view.FunctionalGroupView;
import biomass.simulator.gui.view.PopulationView;
import biomass.simulator.gui.view.SimulationView;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BiomassSim {

	private JFrame frame;
	private DataService dataservice;
	private SimulationController simulationController;
	private PopulationController populationController;
	private FunctionalGroupController functionalGroupController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BiomassSim window = new BiomassSim();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BiomassSim() {
		//Inicializa los controladores y servicios
		dataservice = new DataService(new SqlLiteDAO("lib/biomass.sqlite"));
		simulationController = new SimulationController(dataservice);
		populationController = new PopulationController(dataservice);
		functionalGroupController = new FunctionalGroupController(dataservice);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 790, 710);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		JLabel lblBiomassBiologicalMultiagent = new JLabel("BioMASS: Biological Multiagent Simulation System");
		lblBiomassBiologicalMultiagent.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblBiomassBiologicalMultiagent, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblBiomassBiologicalMultiagent, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel FunctionalGroupsPanel;
		try {
			FunctionalGroupsPanel = new FunctionalGroupView(functionalGroupController);
			tabbedPane.addTab("FunctionalGroups", null, FunctionalGroupsPanel, null);
			tabbedPane.setEnabledAt(0, true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JPanel PopulationsPanel;
		try {
			PopulationsPanel = new PopulationView(populationController);
			tabbedPane.addTab("Populations", null, PopulationsPanel, null);
			tabbedPane.setEnabledAt(1, true);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JPanel simulationPanel;
		try {
			simulationPanel = new SimulationView(simulationController);
			tabbedPane.addTab("Simulations", null, simulationPanel, null);
			tabbedPane.setEnabledAt(2, true);
			tabbedPane.setSelectedIndex(2);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			Logger.getLogger(BiomassSim.class.getName()).log(Level.SEVERE, null, e);		
		}
		
	}
}
