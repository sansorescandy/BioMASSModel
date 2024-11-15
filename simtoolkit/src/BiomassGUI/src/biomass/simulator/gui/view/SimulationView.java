package biomass.simulator.gui.view;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import biomass.simulator.gui.controller.SimulationController;
import biomass.simulator.gui.model.Population;
import biomass.simulator.gui.model.Simulation;
import biomass.simulator.gui.model.SimulationQuery;

import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.Canvas;

public class SimulationView extends JPanel {
	private JTextField nameTF;
	private JTextField widthTF;
	private JTextField lengthTF;
	private JTextField depthTF;
	private JTextField refugespaceTF;
	private JTextField recordTF;
	private JScrollPane insimulationSP;
	private JScrollPane outsimulationSP;
	private JList simulationsList;
	private JList populationsList;
	private Simulation model;
	private SimulationController controller;
	private DefaultListModel simulationsModel;
	private DefaultListModel populationsModel;
	private final JLabel lblSecs = new JLabel("secs");
	private final JList inpopulationsList;
	private final DefaultListModel inmodel = new DefaultListModel();
	private final DefaultListModel outmodel = new DefaultListModel();
	private final JTextField textField_3 = new JTextField();
	private final JLabel lblCm = new JLabel("cm");
	private JTextField textField_4;
	private JTextField textField_5;
	private DefaultTableModel scheduler;
	private JTable table;
	private final JButton button = new JButton("+");
	private final JButton button_1 = new JButton("-");
	
	/**
	 * Create the panel.
	 */
	public SimulationView(final SimulationController controller) throws Throwable {
		this.controller = controller;
		//Inicio de los componentes de la interfaz gr‡fica
		setLayout(null);
		
		populationsModel = new DefaultListModel();
		
		simulationsModel = new DefaultListModel();
		populationsModel = new DefaultListModel();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(12, 6, 129, 258);
		add(scrollPane);
		
		simulationsList = new JList();
		simulationsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()){
					enableGUI();
					Object o=simulationsList.getSelectedValue();
					model=(Simulation)o;
					nameTF.setText(model.getName());
			        widthTF.setText(model.getWidth()+"");
			        lengthTF.setText(model.getLength()+"");
			        depthTF.setText(model.getDepth()+"");
			        refugespaceTF.setText(model.getRefuge()+"");
				}
			}
		});
		scrollPane.setViewportView(simulationsList);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(157, 6, 598, 560);
		add(panel);
		
		JLabel nameLB = new JLabel("Name:");
		nameLB.setBounds(18, 11, 61, 21);
		panel.add(nameLB);
		
		nameTF = new JTextField();
		nameTF.setColumns(10);
		nameTF.setBounds(106, 11, 134, 21);
		panel.add(nameTF);
		
		JLabel widthLB = new JLabel("Space width:");
		widthLB.setBounds(18, 44, 88, 21);
		panel.add(widthLB);
		
		widthTF = new JTextField();
		widthTF.setColumns(10);
		widthTF.setBounds(106, 44, 70, 21);
		panel.add(widthTF);

		JLabel lengthLB = new JLabel("Space length:");
		lengthLB.setBounds(18, 72, 88, 21);
		panel.add(lengthLB);
		
		lengthTF = new JTextField();
		lengthTF.setColumns(10);
		lengthTF.setBounds(106, 72, 70, 21);
		panel.add(lengthTF);
		
		JLabel refugespaceLB = new JLabel("Percentage of space in shelters:");
		refugespaceLB.setBounds(18, 174, 199, 21);
		panel.add(refugespaceLB);
		
		refugespaceTF = new JTextField();
		refugespaceTF.setText("0.01");
		refugespaceTF.setColumns(10);
		refugespaceTF.setBounds(225, 173, 70, 21);
		panel.add(refugespaceTF);
		
		JLabel inLB = new JLabel("In simulation:");
		inLB.setBounds(307, 97, 88, 16);
		panel.add(inLB);
		
		JLabel outLB = new JLabel("Out simulation:");
		outLB.setBounds(470, 97, 109, 16);
		panel.add(outLB);
		
		insimulationSP = new JScrollPane();
		insimulationSP.setBounds(470, 124, 118, 144);
		panel.add(insimulationSP);
		populationsList = new JList();
		insimulationSP.setViewportView(populationsList);
		
		updatePopulationsView();
		
		outsimulationSP = new JScrollPane();
		outsimulationSP.setBounds(307, 124, 118, 144);
		panel.add(outsimulationSP);
		inpopulationsList = new JList();
		outsimulationSP.setViewportView(inpopulationsList);
		
		JList outpopulationsList = new JList();
		
		JLabel populationsLB = new JLabel("Populations:");
		populationsLB.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		populationsLB.setBounds(307, 68, 134, 16);
		panel.add(populationsLB);
		
		JButton outBT = new JButton(">");
		outBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						removeInPopulationListItem(e);
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		outBT.setBounds(426, 150, 43, 29);
		panel.add(outBT);
		
		JButton inBT = new JButton("<");
		inBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						removeOutPopulationListItem(e);
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		inBT.setBounds(426, 123, 43, 29);
		panel.add(inBT);
		
		JButton saveBT = new JButton("Save");
		saveBT.setBounds(352, 7, 117, 29);
		saveBT.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					if (!validateSimulationTab(nameTF.getText(), widthTF.getText(), lengthTF.getText(), depthTF.getText(), refugespaceTF.getText())){
						model.setName(nameTF.getText());
						model.setWidth(Double.parseDouble(widthTF.getText()));
						model.setLength(Double.parseDouble(lengthTF.getText()));
						model.setDepth(Double.parseDouble(depthTF.getText()));
						model.setRefuge(Double.parseDouble(refugespaceTF.getText()));
						model.setZooplankton(false);	// Temporalmente
						controller.save(model);
						updateSimulationsView();
						System.out.println("Save");
					}
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					System.out.print("Error Save");
					e1.printStackTrace();
				}
				System.out.print("Presionado Save");
			}
		});
		panel.add(saveBT);
		
		JButton initializeBT = new JButton("Initialize");
		initializeBT.setBounds(468, 7, 117, 29);
		panel.add(initializeBT);
		
		/*JButton advancedBT = new JButton("Advanced");
		advancedBT.setBounds(442, 12, 117, 29);
		advancedBT.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.print("Presionado Advanced");
			}
		});
		panel.add(advancedBT);*/
		
		JLabel chartsLB = new JLabel("Scheduler:");
		chartsLB.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		chartsLB.setBounds(18, 310, 134, 16);
		panel.add(chartsLB);
		
		JLabel recordLB = new JLabel("Time units:");
		recordLB.setBounds(100, 347, 88, 21);
		panel.add(recordLB);
		
		recordTF = new JTextField();
		recordTF.setText("0");
		recordTF.setColumns(10);
		recordTF.setBounds(188, 347, 70, 21);
		panel.add(recordTF);
		lblSecs.setBounds(262, 347, 46, 21);
		
		panel.add(lblSecs);
		
		JLabel lblRefuges = new JLabel("Shelters:");
		lblRefuges.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 13));
		lblRefuges.setBounds(18, 147, 134, 16);
		panel.add(lblRefuges);
		
		JLabel lblMinimumRatio = new JLabel("Minimun radius:");
		lblMinimumRatio.setBounds(18, 202, 109, 21);
		panel.add(lblMinimumRatio);
		textField_3.setText("50");
		textField_3.setColumns(10);
		textField_3.setBounds(128, 201, 70, 21);
		
		panel.add(textField_3);
		lblCm.setBounds(208, 202, 46, 21);
		
		panel.add(lblCm);
		
		JLabel lblMaximumRatio = new JLabel("Maximun radius:");
		lblMaximumRatio.setBounds(18, 230, 109, 21);
		panel.add(lblMaximumRatio);
		
		textField_4 = new JTextField();
		textField_4.setText("250");
		textField_4.setColumns(10);
		textField_4.setBounds(128, 229, 70, 21);
		panel.add(textField_4);
		
		JLabel label_4 = new JLabel("cm");
		label_4.setBounds(208, 230, 46, 21);
		panel.add(label_4);
		
		JLabel lblDispersion = new JLabel("Dispersion:");
		lblDispersion.setBounds(18, 258, 109, 21);
		panel.add(lblDispersion);
		
		textField_5 = new JTextField();
		textField_5.setText("15000");
		textField_5.setColumns(10);
		textField_5.setBounds(106, 258, 70, 21);
		panel.add(textField_5);
		
		Object[][] data = {
				{"1", "0", "0"},
				{"2", "0", "0"},};
		
		Object[] columnNames = {"Loops","Steps","Time units",};
		
		scheduler = new DefaultTableModel(data, columnNames);
		
		table = new JTable(scheduler);
		//table.setBounds(18, 392, 250, 100);
		table.setFillsViewportHeight(true);
		
		JScrollPane scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setBounds(100, 411, 250, 100);
		
		panel.add(scrollPaneTable);
		button.setBounds(352, 411, 43, 29);
		
		panel.add(button);
		button_1.setBounds(352, 439, 43, 29);
		
		panel.add(button_1);
		
		JLabel lblScheduler = new JLabel("Scheduler");
		lblScheduler.setBounds(31, 454, 61, 14);
		panel.add(lblScheduler);
		
		JLabel lblCm_1 = new JLabel("cm");
		lblCm_1.setBounds(179, 49, 61, 16);
		panel.add(lblCm_1);
		
		JLabel lblCm_2 = new JLabel("cm");
		lblCm_2.setBounds(179, 74, 61, 16);
		panel.add(lblCm_2);
		
		JButton newBT = new JButton("+");
		newBT.setBounds(12, 264, 43, 29);
		newBT.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					enableGUI();
					nameTF.setText("New Simulation");
			        widthTF.setText("0.0");
			        lengthTF.setText("0.0");
			        depthTF.setText("0.0");
			        refugespaceTF.setText("0.0");
			        model = controller.create();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					System.out.print("Error +");
					e1.printStackTrace();
				}
				System.out.print("Presionado +");
			}
		});
		add(newBT);
		
		JButton deleteBT = new JButton("-");
		deleteBT.setBounds(52, 264, 43, 29);
		deleteBT.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					nameTF.setText("");
			        widthTF.setText("");
			        lengthTF.setText("");
			        depthTF.setText("");
			        refugespaceTF.setText("");
			        removeListItem(e);
					controller.delete(model);
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.print("Presionado -");
			}});
		add(deleteBT);
		
		//Termina la inicializaci—n de componentes de la interfaz gr‡fica
		updateSimulationsView();
		//updatePopulationsView();
		//disableGUI();		
		
 /*       this.model = model;
        nameTF.setText(model.getName());
        widthTF.setText(model.getWidth()+"");
        lengthTF.setText(model.getLength()+"");
        depthTF.setText(model.getDepth()+"");
        refugespaceFT.setText(model.getRefuge()+"");
        zooplanktonCB.setSelected(model.isZooplankton());
        
        
        DefaultListModel populationsModel = new DefaultListModel();
        
        for (final int id : model.getPopids()) {
            Population pop = CollectionUtils.firstOrDefault(model.getAllpopulations(), new IFunction<Population, Boolean>() {
                public Boolean execute(Population p) throws Throwable {
                    return p.getId() == id;
                }
            });

            if (pop != null)
            	populationsModel.addElement(pop);
        }
        inpopulationsList.setModel(populationsModel);
        */
	}
	
	public void updateSimulationsView() throws Throwable{
		simulationsModel.clear();
		for(Simulation s:controller.list()){
			simulationsModel.addElement(s);
		}
		simulationsList.setModel(simulationsModel);
	}
	
	public void updatePopulationsView() throws Throwable{
        populationsModel.clear();
        for(Population p:controller.populationlist()){
                populationsModel.addElement(p);
        }
        populationsList.setModel(populationsModel);
	}
	
	public void removeListItem(ActionEvent e) throws Throwable{
		DefaultListModel model = (DefaultListModel) simulationsList.getModel();
		int selectedIndex = simulationsList.getSelectedIndex();
	      if (selectedIndex != -1) {
	    	  model.remove(selectedIndex);
	      }
	}
	
	public void removeOutPopulationListItem(ActionEvent e) throws Throwable{
		DefaultListModel outmodel = (DefaultListModel) populationsList.getModel();
		int selectedIndex = populationsList.getSelectedIndex();
	      if (selectedIndex != -1) {
	    	  inmodel.addElement(populationsList.getSelectedValue());
	    	  outmodel.remove(selectedIndex);
	    	  inpopulationsList.setModel(inmodel);
	      }
	}
	
	public void removeInPopulationListItem(ActionEvent e) throws Throwable{
		DefaultListModel inmodel = (DefaultListModel) inpopulationsList.getModel();
		DefaultListModel auxinpopulation = (DefaultListModel) populationsList.getModel();
		int selectedIndex = inpopulationsList.getSelectedIndex();
	      if (selectedIndex != -1) {
	    	  auxinpopulation.addElement(inpopulationsList.getSelectedValue());
	    	  inmodel.remove(selectedIndex);
	    	  populationsList.setModel(auxinpopulation);
	      }
	}
	
	public void enableGUI(){
		nameTF.setEnabled(true);
		nameTF.setBackground(Color.WHITE);
		widthTF.setEnabled(true);
		widthTF.setBackground(Color.WHITE);
		lengthTF.setEnabled(true);
		lengthTF.setBackground(Color.WHITE);
		depthTF.setEnabled(true);
		depthTF.setBackground(Color.WHITE);
		refugespaceTF.setEnabled(true);
		refugespaceTF.setBackground(Color.WHITE);
		//zooplanktonCB.setEnabled(true);
		insimulationSP.setEnabled(true);
		insimulationSP.setBackground(Color.WHITE);
		outsimulationSP.setEnabled(true);
		outsimulationSP.setBackground(Color.WHITE);
	}
	public void disableGUI(){
		nameTF.setEnabled(false);
		nameTF.setBackground(Color.lightGray);
		widthTF.setEnabled(false);
		widthTF.setBackground(Color.lightGray);
		lengthTF.setEnabled(false);
		lengthTF.setBackground(Color.lightGray);
		depthTF.setEnabled(false);
		depthTF.setBackground(Color.lightGray);
		refugespaceTF.setEnabled(false);
		refugespaceTF.setBackground(Color.lightGray);
		insimulationSP.setEnabled(false);
		insimulationSP.setBackground(Color.LIGHT_GRAY);
		outsimulationSP.setEnabled(false);
		outsimulationSP.setBackground(Color.lightGray);
	}
	
	private boolean validateSimulationTab(String name, String width, String length, String depth, String refuges) throws Throwable{
		if (name.equals("")){
			showErrorMessage("Name can't be empty");
			nameTF.requestFocusInWindow();
			return true;
		}
		else if(name.length() > 255){
			showErrorMessage("Name must be 255 characters long or less");
			nameTF.requestFocusInWindow();
			return true;
		}
		else {
			if (model.isNewrecord()){
				for(Simulation s:controller.list()){
					if (s.getName().equals(name)){
						showErrorMessage("There is already a simulation with this name");
						nameTF.requestFocusInWindow();
						return true;
					}
				}
			}
		}
		
		if (width.equals("") || Double.parseDouble(width) <= 0){
			showErrorMessage("Space width must be greater than 0");
			widthTF.requestFocusInWindow();
			return true;
		}		
		if (length.equals("") || Double.parseDouble(length) <= 0){
			showErrorMessage("Space length must be greater than 0");
			lengthTF.requestFocusInWindow();
			return true;
		}
		if (depth.equals("") || Double.parseDouble(depth) <= 0){
			showErrorMessage("Space depth must be greater than 0");
			depthTF.requestFocusInWindow();
			return true;
		}
		if (refuges.equals("") || Double.parseDouble(refuges) <= 0){
			showErrorMessage("Percentage of space in refuges must be greater than 0");
			refugespaceTF.requestFocusInWindow();
			return true;
		}

		return false;
	}
	
	private void showErrorMessage(String error){
		JOptionPane.showMessageDialog(null, error, "Message", JOptionPane.WARNING_MESSAGE);
	}
}
