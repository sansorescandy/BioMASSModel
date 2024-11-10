package multiagent.simulator.gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
	private static final long serialVersionUID = 3920269817883839095L;
	private JTextField txtWidth = new JTextField("800");
	private JTextField txtHeigh = new JTextField("600");
	private JTextField txtNumPrey = new JTextField("3000");
	private JTextField txtNumPred = new JTextField("4");
	private JTextField txtPreyNeighborDistance = new JTextField("20");
	private JTextField txtMaxPreyNeighbors = new JTextField("7");
	private JTextField txtTimeScale = new JTextField("0.095");
	private JTextField txtPreySpacing=new JTextField("8.0");
	private JTextField txtPreyAttractForce = new JTextField("60.0");
	private JTextField txtPreyRepelForce = new JTextField("-80.0");
	private JTextField txtPreyFearRadius = new JTextField("80.0");
	private JTextField txtPreyFearForce = new JTextField("-500000.0");
	private JTextField txtPreyAcceleration = new JTextField("1.0");
	private JTextField txtPreyMaxSpeed = new JTextField("70.0");
	private JTextField txtPredPerceptionDistance = new JTextField("100.0");
	private JTextField txtKillRadius = new JTextField("60.0");
	private JTextField txtPredAcceleration = new JTextField("3.0");
	private JTextField txtPredMaxSpeed = new JTextField("300.0");
	JButton btnStart;
	JButton btnStop;

	private SimulationModel simulationModel;
	private Parameter parameters;

	public Main() {
		setTitle("BioMASS Space Model Testing");
		setSize(new Dimension(500, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.WHITE);
		getRootPane().setBorder(BorderFactory.createMatteBorder(5, 8, 5, 8, Color.WHITE));
		setLayout(new GridLayout(19, 2));
		parameters = new Parameter();
		add(new JLabel("Space Width:"));
		add(txtWidth);
		add(new JLabel("Space Heigh:"));
		add(txtHeigh);
		add(new JLabel("Preys number:"));
		add(txtNumPrey);
		add(new JLabel("Predators number:"));
		add(txtNumPred);
		add(new JLabel("Prey neighbor distance:"));
		add(txtPreyNeighborDistance);
		add(new JLabel("Max Prey Neighbors:"));
		add(txtMaxPreyNeighbors);
		add(new JLabel("Prey Spacing:"));
		add(txtPreySpacing);
		add(new JLabel("Prey Attraction Force:"));
		add(txtPreyAttractForce);
		add(new JLabel("Prey Repel Force:"));
		add(txtPreyRepelForce);
		add(new JLabel("Prey Fear Radius:"));
		add(txtPreyFearRadius);
		add(new JLabel("Prey Fear Force:"));
		add(txtPreyFearForce);
		add(new JLabel("Prey Acceleration:"));
		add(txtPreyAcceleration);
		add(new JLabel("Prey Max Speed:"));
		add(txtPreyMaxSpeed);
		add(new JLabel("Predator Perception Distance:"));
		add(txtPredPerceptionDistance);
		add(new JLabel("Predator Kill Radius:"));
		add(txtKillRadius);
		add(new JLabel("Predator Acceleration:"));
		add(txtPredAcceleration);
		add(new JLabel("Predator Max Speed:"));
		add(txtPredMaxSpeed);
		add(new JLabel("Simulation Time Scale:"));
		add(txtTimeScale);

		btnStart = new JButton("Start Simulation");
		btnStart.setBackground(Color.GREEN);
		btnStart.setOpaque(true);
		btnStart.setBorderPainted(false);
		btnStop = new JButton("Stop Simulation");
		btnStop.setBackground(Color.GRAY);
		btnStop.setOpaque(true);
		btnStop.setBorderPainted(false);
        btnStop.setEnabled(false);

		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startSimulation();
			}
		});

		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopSimulation();
			}
		});

		add(btnStart);
		add(btnStop);
	}

	private void startSimulation() {
		SwingUtilities.invokeLater(() -> {
			parameters.setWidth(Float.parseFloat(txtWidth.getText()));
			parameters.setHeigh(Float.parseFloat(txtHeigh.getText()));
			parameters.setNumPrey(Integer.parseInt(txtNumPrey.getText()));
			parameters.setNumPred(Integer.parseInt(txtNumPred.getText()));
			parameters.setPreyNeighborDistance(Integer.parseInt(txtPreyNeighborDistance.getText())); 
			parameters.setMaxPreyNeighbors(Integer.parseInt(txtMaxPreyNeighbors.getText()));
			parameters.setPreySpacing(Float.parseFloat(txtPreySpacing.getText()));
			parameters.setPreyAttractForce(Float.parseFloat(txtPreyAttractForce.getText()));
			parameters.setPreyRepelForce(Float.parseFloat(txtPreyRepelForce.getText())); 
			parameters.setPreyFearRadius(Float.parseFloat(txtPreyFearRadius.getText())); 
			parameters.setPreyFearForce(Float.parseFloat(txtPreyFearForce.getText())); 
			parameters.setPreyAcceleration(Float.parseFloat(txtPreyAcceleration.getText()));
			parameters.setPreyMaxSpeed(Float.parseFloat(txtPreyMaxSpeed.getText()));
			parameters.setPredPerceptionDistance(Float.parseFloat(txtPredPerceptionDistance.getText()));
			parameters.setKillRadius(Float.parseFloat(txtKillRadius.getText()));
			parameters.setPredAcceleration(Float.parseFloat(txtPredAcceleration.getText()));
			parameters.setPredMaxSpeed(Float.parseFloat(txtPredMaxSpeed.getText()));
			parameters.setTimeScale(Float.parseFloat(txtTimeScale.getText()));

			simulationModel = new SimulationModel(this);
			simulationModel.startSimulation();
			btnStart.setEnabled(false);
	        btnStart.setBackground(Color.GRAY);
	        btnStop.setEnabled(true);
	        btnStop.setBackground(Color.RED);
			
		});
	}

	private void stopSimulation() {
		if (simulationModel != null) {
			simulationModel.stopSimulation();
		}
		btnStart.setEnabled(true);
        btnStart.setBackground(Color.GREEN);
        btnStop.setEnabled(false);
        btnStop.setBackground(Color.GRAY);
	}
	
	public void onSimulationClosed() {
	    btnStop.setEnabled(false);
	    btnStop.setBackground(Color.GRAY);
	    btnStart.setEnabled(true);
	    btnStart.setBackground(Color.GREEN);
	}
	
	public Parameter getParameters(){
		return parameters;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Main().setVisible(true);
		});
	}
}

