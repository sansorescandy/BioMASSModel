package biomass.simulator.gui.view;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JTextField;
import javax.swing.JButton;

import biomass.simulator.gui.controller.FunctionalGroupController;
import biomass.simulator.gui.controller.PopulationController;
import biomass.simulator.gui.controller.SimulationController;
import biomass.simulator.gui.model.Population;
import biomass.simulator.gui.model.Simulation;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;

public class PopulationView extends JPanel {
	private JTextField nameTF;
	private JTextField cohorteTF;
	private JTextField mortalityTF;
	private JTextField linfTF;
	private JTextField kTF;
	private JTextField t0TF;
	private JTextField aTF;
	private JTextField bTF;
	private JTextField lifespanTF;
	private JTextField stomachTF;
	private JTextField mouthTF;
	private JTextField lengthTF;
	private JTextField recruitmentTF;
	private JTextField recruitmentlengthTF;
	private Population model;
	private JList populationsList;
	private DefaultListModel populationsModel;
	private JComboBox functionalGroupCB;
	private PopulationController controller;

	/**
	 * Create the panel.
	 */
	public PopulationView(final PopulationController controller) throws Throwable {
		this.controller = controller;
		//Inicio de los componentes de la interfaz gr‡fica
		setLayout(null);
		populationsModel = new DefaultListModel();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(12, 6, 129, 258);
		add(scrollPane);
		
		populationsList = new JList();
		populationsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()){
					//enableGUI();
					Object o=populationsList.getSelectedValue();
					model = (Population)o;
					nameTF.setText(model.getName().toString());
					cohorteTF.setText(model.getCohorte()+"");
					mortalityTF.setText(model.getMortalityrate()+"");
					linfTF.setText(model.getLinf()+"");
					kTF.setText(model.getK()+"");
					t0TF.setText(model.getT0()+"");
					aTF.setText(model.getA()+"");
					bTF.setText(model.getB()+"");
					lifespanTF.setText(model.getLifeSpan()+"");
					stomachTF.setText(model.getStomachratio()+"");
					mouthTF.setText(model.getMouthratio()+"");
					lengthTF.setText(model.getMaturitylength()+"");
					recruitmentTF.setText(model.getRecruitment()+"");
					recruitmentlengthTF.setText(model.getRecruitmentlength()+"");
					//functionalGroupCB.setSelectedItem(model.getFg());
				}
			}
		});
		scrollPane.setViewportView(populationsList);	
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(157, 6, 598, 450);		
		add(panel);
		
		JLabel label = new JLabel("Initial Distribution:\n");
		label.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		label.setBounds(18, 45, 183, 16);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Name:");
		label_1.setBounds(18, 11, 61, 21);
		panel.add(label_1);
		
		nameTF = new JTextField();
		nameTF.setColumns(10);
		nameTF.setBounds(67, 11, 134, 21);
		panel.add(nameTF);
		
		JLabel label_2 = new JLabel("N0:");
		label_2.setBounds(18, 70, 61, 21);
		panel.add(label_2);
		
		cohorteTF = new JTextField();
		cohorteTF.setColumns(10);
		cohorteTF.setBounds(67, 70, 70, 21);
		panel.add(cohorteTF);
		
		JLabel label_6 = new JLabel("Cohorte");
		label_6.setBounds(140, 70, 61, 21);
		panel.add(label_6);
		
		JLabel label_3 = new JLabel("Z:");
		label_3.setBounds(18, 100, 61, 21);
		panel.add(label_3);
		
		mortalityTF = new JTextField();
		mortalityTF.setColumns(10);
		mortalityTF.setBounds(67, 100, 70, 21);
		panel.add(mortalityTF);
		
		JLabel label_5 = new JLabel("Mortality rate");
		label_5.setBounds(140, 100, 89, 21);
		panel.add(label_5);
		
		JLabel label_4 = new JLabel("Functional Group:");
		label_4.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		label_4.setBounds(18, 135, 125, 16);
		panel.add(label_4);
		
		functionalGroupCB = new JComboBox();
		for(int i = 0; i < controller.functionalGrouplist().size();i++){
			functionalGroupCB.addItem(controller.functionalGrouplist().get(i));
		}
		//functionalGroupCB.setModel(new DefaultComboBoxModel(new String[] {"Functionalgrp1", "Functionalgrp2", "Functionalgrp3", "Functionalgrp4"}));
		functionalGroupCB.setBounds(18, 162, 157, 27);
		panel.add(functionalGroupCB);
		
		JButton saveBT = new JButton("Save");
		saveBT.setBounds(352, 7, 117, 29);
		saveBT.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					if(!validatePopulationTab(nameTF.getText(), cohorteTF.getText(), mortalityTF.getText(), linfTF.getText(),
					kTF.getText(), t0TF.getText(), aTF.getText(), bTF.getText(), lifespanTF.getText(), stomachTF.getText(),
					mouthTF.getText(), lengthTF.getText(), recruitmentTF.getText(), recruitmentlengthTF.getText())){
						model.setName(nameTF.getText());
						model.setMortalityrate(Double.parseDouble(mortalityTF.getText()));
						model.setCohorte(Integer.parseInt(cohorteTF.getText()));
						model.setLinf(Double.parseDouble(linfTF.getText()));
						model.setK(Double.parseDouble(kTF.getText()));
						model.setT0(Double.parseDouble(t0TF.getText()));
						model.setA(Double.parseDouble(aTF.getText()));
						model.setB(Double.parseDouble(bTF.getText()));
						model.setLifeSpan(Double.parseDouble(lifespanTF.getText()));
						model.setStomachratio(Double.parseDouble(stomachTF.getText()));
						model.setMouthratio(Double.parseDouble(mouthTF.getText()));
						model.setMaturitylength(Double.parseDouble(lengthTF.getText()));
						model.setRecruitment(Integer.parseInt(recruitmentTF.getText()));
						model.setRecruitmentlength(Double.parseDouble(recruitmentlengthTF.getText()));
						model.setFg(10);
				       	controller.save(model);
				        updatePopulationsView();
				        System.out.println("Save");
					}
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					System.out.println("Error Save");
					e1.printStackTrace();
				}
				System.out.println("Presionado Save");
			}
		});
		panel.add(saveBT);		
		
		JLabel lblIndividualProperties = new JLabel("Individual Properties:");
		lblIndividualProperties.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblIndividualProperties.setBounds(18, 205, 154, 16);
		panel.add(lblIndividualProperties);
		
		JLabel label_8 = new JLabel("Growth:");
		label_8.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		label_8.setBounds(18, 235, 61, 16);
		panel.add(label_8);
		
		JLabel label_9 = new JLabel("Linf:");
		label_9.setBounds(18, 262, 61, 21);
		panel.add(label_9);
		
		linfTF = new JTextField();
		linfTF.setColumns(10);
		linfTF.setBounds(56, 262, 70, 21);
		panel.add(linfTF);
		
		JLabel label_10 = new JLabel("k:");
		label_10.setBounds(18, 290, 27, 21);
		panel.add(label_10);
		
		kTF = new JTextField();
		kTF.setColumns(10);
		kTF.setBounds(56, 290, 70, 21);
		panel.add(kTF);
		
		JLabel label_11 = new JLabel("t0:");
		label_11.setBounds(18, 318, 27, 21);
		panel.add(label_11);
		
		t0TF = new JTextField();
		t0TF.setColumns(10);
		t0TF.setBounds(56, 318, 70, 21);
		panel.add(t0TF);
		
		JLabel label_12 = new JLabel("a:");
		label_12.setBounds(18, 346, 61, 21);
		panel.add(label_12);
		
		aTF = new JTextField();
		aTF.setColumns(10);
		aTF.setBounds(56, 346, 70, 21);
		panel.add(aTF);
		
		JLabel label_13 = new JLabel("b:");
		label_13.setBounds(18, 374, 61, 21);
		panel.add(label_13);
		
		bTF = new JTextField();
		bTF.setColumns(10);
		bTF.setBounds(56, 374, 70, 21);
		panel.add(bTF);
		
		JLabel lblLifeSpan = new JLabel("Life span:");
		lblLifeSpan.setBounds(18, 402, 79, 21);
		panel.add(lblLifeSpan);
		
		lifespanTF = new JTextField();
		lifespanTF.setColumns(10);
		lifespanTF.setBounds(80, 402, 70, 21);
		panel.add(lifespanTF);
		
		JLabel lblYrs = new JLabel("yrs");
		lblYrs.setBounds(155, 402, 27, 21);
		panel.add(lblYrs);
		
		JLabel label_15 = new JLabel("Metabolism:");
		label_15.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		label_15.setBounds(273, 235, 101, 16);
		panel.add(label_15);
		
		JLabel lblLengthstomachRatio = new JLabel("Stomach/Length ratio:");
		lblLengthstomachRatio.setBounds(273, 262, 144, 21);
		panel.add(lblLengthstomachRatio);
		
		stomachTF = new JTextField();
		stomachTF.setColumns(10);
		stomachTF.setBounds(410, 262, 70, 21);
		panel.add(stomachTF);
		
		JLabel lblLengthmouthWidthRatio = new JLabel("Mouth/Width ratio:");
		lblLengthmouthWidthRatio.setBounds(273, 290, 119, 21);
		panel.add(lblLengthmouthWidthRatio);
		
		mouthTF = new JTextField();
		mouthTF.setColumns(10);
		mouthTF.setBounds(410, 290, 70, 21);
		panel.add(mouthTF);
		
		JLabel label_18 = new JLabel("Reproduction:");
		label_18.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		label_18.setBounds(273, 319, 101, 16);
		panel.add(label_18);
		
		JLabel label_19 = new JLabel("Length at first maturity:");
		label_19.setBounds(273, 346, 154, 21);
		panel.add(label_19);
		
		lengthTF = new JTextField();
		lengthTF.setColumns(10);
		lengthTF.setBounds(410, 346, 70, 21);
		panel.add(lengthTF);
		
		JLabel lblCm = new JLabel("cm");
		lblCm.setBounds(484, 346, 61, 21);
		panel.add(lblCm);
		
		JLabel lblRecruitment = new JLabel("Recruitment:");
		lblRecruitment.setBounds(273, 374, 144, 21);
		panel.add(lblRecruitment);
		
		recruitmentTF = new JTextField();
		recruitmentTF.setColumns(10);
		recruitmentTF.setBounds(410, 374, 70, 21);
		panel.add(recruitmentTF);
		
		JLabel lblRecruimentLength = new JLabel("Recruitment length:");
		lblRecruimentLength.setBounds(273, 402, 129, 21);
		panel.add(lblRecruimentLength);
		
		recruitmentlengthTF = new JTextField();
		recruitmentlengthTF.setColumns(10);
		recruitmentlengthTF.setBounds(410, 402, 70, 21);
		panel.add(recruitmentlengthTF);
		
		JLabel lblCm_1 = new JLabel("cm");
		lblCm_1.setBounds(484, 402, 61, 21);
		panel.add(lblCm_1);
		
		JButton newBT = new JButton("+");
		newBT.setBounds(12, 264, 43, 29);
		newBT.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					//enableGUI();
					nameTF.setText("New Population");
					cohorteTF.setText("0");
					mortalityTF.setText("0.0");
					linfTF.setText("0.0");
					kTF.setText("0.0");
					t0TF.setText("0.0");
					aTF.setText("0.0");
					bTF.setText("0.0");
					lifespanTF.setText("0.0");
					stomachTF.setText("0.0");
					mouthTF.setText("0.0");
					lengthTF.setText("0.0");
					recruitmentTF.setText("0");
					recruitmentlengthTF.setText("0.0");
			        model = controller.create();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					System.out.println("Error +");
					e1.printStackTrace();
				}
				System.out.println("Presionado +");
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
					cohorteTF.setText("");
					mortalityTF.setText("");
					linfTF.setText("");
					kTF.setText("");
					t0TF.setText("");
					aTF.setText("");
					bTF.setText("");
					lifespanTF.setText("");
					stomachTF.setText("");
					mouthTF.setText("");
					lengthTF.setText("");
					recruitmentTF.setText("");
					recruitmentlengthTF.setText("");
			        removeListItem(e);
					controller.delete(model);
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.print("Presionado -");
			}});
		add(deleteBT);
		updatePopulationsView();
	}
	
	public void updatePopulationsView() throws Throwable{
		populationsModel.clear();
		for(Population s:controller.list()){
			populationsModel.addElement(s);
		}
		populationsList.setModel(populationsModel);
     }	
	
	public void removeListItem(ActionEvent e) throws Throwable{
		DefaultListModel model = (DefaultListModel) populationsList.getModel();
		int selectedIndex = populationsList.getSelectedIndex();
	      if (selectedIndex != -1) {
	    	  model.remove(selectedIndex);
	      }
	}
	
	private boolean validatePopulationTab(String name, String cohorte, String mortality, String linf,
										String k, String t0, String a, String b, String lifespan,
										String stomach, String mouth, String length, String recruitment,
										String recruitmentlength) throws Throwable{

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
				for(Population s:controller.list()){
					if (s.getName().equals(name)){
						showErrorMessage("There is already a population with this name");
						nameTF.requestFocusInWindow();
						return true;
					}
				}
			}
		}
		
		if (cohorte.equals("") || Integer.parseInt(cohorte) <= 0){
			showErrorMessage("Cohorte must be greater than 0");
			cohorteTF.requestFocusInWindow();
			return true;
		}
		if (mortality.equals("") || Double.parseDouble(mortality) <= 0){
			showErrorMessage("Mortality rate must be greater than 0");
			mortalityTF.requestFocusInWindow();
			return true;
		} else if (Double.parseDouble(mortality) > 1){
			showErrorMessage("Mortality rate must be less than or equal to 1");
			mortalityTF.requestFocusInWindow();
			return true;
		}
		if (linf.equals("") || Double.parseDouble(linf) <= 0){
			showErrorMessage("LInf must be greater than 0");
			linfTF.requestFocusInWindow();
			return true;
		}
		if (k.equals("") || Double.parseDouble(k) <= 0){
			showErrorMessage("k must be greater than 0");
			kTF.requestFocusInWindow();
			return true;
		}
		if (t0.equals("") || Double.parseDouble(t0) <= 0){
			showErrorMessage("t0 must be greater than 0");
			t0TF.requestFocusInWindow();
			return true;
		}
		if (a.equals("") || Double.parseDouble(a) <= 0){
			showErrorMessage("a must be greater than 0");
			aTF.requestFocusInWindow();
			return true;
		}
		if (b.equals("") || Double.parseDouble(b) <= 0){
			showErrorMessage("b must be greater than 0");
			bTF.requestFocusInWindow();
			return true;
		}
		if (lifespan.equals("") || Double.parseDouble(lifespan) <= 0){
			showErrorMessage("Life span must be greater than 0");
			lifespanTF.requestFocusInWindow();
			return true;
		}
		if (stomach.equals("") || Double.parseDouble(stomach) <= 0){
			showErrorMessage("Stomach/Length ratio must be greater than 0");
			stomachTF.requestFocusInWindow();
			return true;
		}
		if (mouth.equals("") || Double.parseDouble(mouth) <= 0){
			showErrorMessage("Mouth/Width ratio must be greater than 0");
			mouthTF.requestFocusInWindow();
			return true;
		}
		if (length.equals("") || Double.parseDouble(length) <= 0){
			showErrorMessage("Length at first maturity must be greater than 0");
			lengthTF.requestFocusInWindow();
			return true;
		}
		if (recruitment.equals("") || Integer.parseInt(recruitment) <= 0){
			showErrorMessage("Recruitment must be greater than 0");
			recruitmentTF.requestFocusInWindow();
			return true;
		}
		if (recruitmentlength.equals("") || Double.parseDouble(recruitmentlength) <= 0){
			showErrorMessage("Recruitment length must be greater than 0");
			recruitmentlengthTF.requestFocusInWindow();
			return true;
		}
		
		return false;
	}
	
	private void showErrorMessage(String error){
		JOptionPane.showMessageDialog(null, error, "Message", JOptionPane.WARNING_MESSAGE);
	}
}
