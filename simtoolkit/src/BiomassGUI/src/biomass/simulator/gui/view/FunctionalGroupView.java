package biomass.simulator.gui.view;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;

import biomass.simulator.gui.controller.FunctionalGroupController;
import biomass.simulator.gui.controller.PopulationController;
import biomass.simulator.gui.model.EcologicalTrait;
import biomass.simulator.gui.model.FunctionalGroup;
import biomass.simulator.gui.model.Population;
import biomass.simulator.gui.model.Simulation;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;

public class FunctionalGroupView extends JPanel {
	private JTextField perceptionrangeTextField;
	private JTextField maxspeedTextField;
	private JTextField escapespeedTextField;
	private JTextField huntingspeedTextField;
	private JTextField wanderingspeedTextField;
	private JTextField foragingspeedTextField;
	private JTextField scoutingspeedTextField;
	private JTextField maxdistancerefugeTextField;
	private JTextField alertlevelTextField;
	private JTextField nameTextField;
	private FunctionalGroup model;
	private JList functionalGroupList;
	private JList preysList = new JList();
	private DefaultListModel functionalGroupsModel;
	private DefaultListModel preysModel = new DefaultListModel();
	private FunctionalGroupController controller;
	private final JList nopreyList = new JList();
	private final DefaultListModel inmodel = new DefaultListModel();
	private final DefaultListModel outpreys = new DefaultListModel();
	private JPanel panel;
	private JScrollPane scrollPane_2;
	private final JLabel lblFunctionalGroups = new JLabel("<html>Functional<br>groups:");
	
	/**
	 * Create the panel.
	 */
	public FunctionalGroupView(final FunctionalGroupController controller) throws Throwable{
		setLayout(null);
		this.controller = controller;
		functionalGroupsModel = new DefaultListModel();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 6, 129, 258);
		scrollPane.setEnabled(false);
		add(scrollPane);
		
		functionalGroupList = new JList();
		functionalGroupList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()){
					//enableGUI();
					Object o=functionalGroupList.getSelectedValue();
					model = (FunctionalGroup)o;
					nameTextField.setText(model.getName().toString());
					perceptionrangeTextField.setText(model.getPerceprange()+"");
					maxspeedTextField.setText(model.getMaxspeed()+"");
					wanderingspeedTextField.setText(model.getWanderingspeed()+"");
					scoutingspeedTextField.setText(model.getScoutingspeed()+"");
					maxdistancerefugeTextField.setText(model.getMaxdistancerefuge()+"");
					foragingspeedTextField.setText(model.getForagingspeed()+"");
					escapespeedTextField.setText(model.getEscapingspeed()+"");
					huntingspeedTextField.setText(model.getHuntingspeed()+"");
					alertlevelTextField.setText(model.getAlertlevel()+"");
				}
			}
		});
		scrollPane.setViewportView(functionalGroupList);
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(157, 6, 598, 500);
		add(panel);
		
		JLabel lblBehaviorsParameters = new JLabel("Behaviors parameters:");
		lblBehaviorsParameters.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		lblBehaviorsParameters.setBounds(18, 316, 180, 16);
		panel.add(lblBehaviorsParameters);
		
		JLabel label_21 = new JLabel("Perception range factor:");
		label_21.setBounds(18, 343, 154, 21);
		panel.add(label_21);
		
		perceptionrangeTextField = new JTextField();
		perceptionrangeTextField.setColumns(10);
		perceptionrangeTextField.setBounds(165, 343, 70, 21);
		panel.add(perceptionrangeTextField);
		

		JLabel label_15 = new JLabel("Max speed ratio:");
		label_15.setBounds(18, 371, 110, 21);
		panel.add(label_15);
		
		maxspeedTextField = new JTextField();
		maxspeedTextField.setColumns(10);
		maxspeedTextField.setBounds(165, 371, 70, 21);
		panel.add(maxspeedTextField);
		
		JLabel label_16 = new JLabel("Escaping speed ratio:");
		label_16.setBounds(318, 371, 134, 21);
		panel.add(label_16);
		
		escapespeedTextField = new JTextField();
		escapespeedTextField.setColumns(10);
		escapespeedTextField.setBounds(445, 371, 70, 21);
		panel.add(escapespeedTextField);
		
		JLabel label_17 = new JLabel("Hunting speed ratio:");
		label_17.setBounds(318, 399, 134, 21);
		panel.add(label_17);
		
		huntingspeedTextField = new JTextField();
		huntingspeedTextField.setColumns(10);
		huntingspeedTextField.setBounds(445, 399, 70, 21);
		panel.add(huntingspeedTextField);
		
		JLabel label_18 = new JLabel("Wandering speed ratio:");
		label_18.setBounds(18, 399, 144, 21);
		panel.add(label_18);
		
		wanderingspeedTextField = new JTextField();
		wanderingspeedTextField.setColumns(10);
		wanderingspeedTextField.setBounds(165, 399, 70, 21);
		panel.add(wanderingspeedTextField);
		
		JLabel label_19 = new JLabel("Foraging speed ratio:");
		label_19.setBounds(318, 343, 134, 21);
		panel.add(label_19);
		
		foragingspeedTextField = new JTextField();
		foragingspeedTextField.setColumns(10);
		foragingspeedTextField.setBounds(445, 343, 70, 21);
		panel.add(foragingspeedTextField);
		
		JLabel label_20 = new JLabel("Scouting speed ratio:");
		label_20.setBounds(18, 427, 134, 16);
		panel.add(label_20);
		
		scoutingspeedTextField = new JTextField();
		scoutingspeedTextField.setColumns(10);
		scoutingspeedTextField.setBounds(165, 427, 70, 21);
		panel.add(scoutingspeedTextField);
		
		JLabel lblScoutingMaximumDistance = new JLabel("<html>Scouting maximum<br>distance from refuge:");
		lblScoutingMaximumDistance.setBounds(18, 455, 144, 34);
		panel.add(lblScoutingMaximumDistance);
		
		JLabel lblescapingAlertlevel = new JLabel("Escaping alert level:");
		lblescapingAlertlevel.setBounds(318, 427, 134, 21);
		panel.add(lblescapingAlertlevel);
		
		maxdistancerefugeTextField = new JTextField();
		lblScoutingMaximumDistance.setLabelFor(maxdistancerefugeTextField);
		maxdistancerefugeTextField.setColumns(10);
		maxdistancerefugeTextField.setBounds(165, 455, 70, 21);
		panel.add(maxdistancerefugeTextField);
		
		alertlevelTextField = new JTextField();
		alertlevelTextField.setColumns(10);
		alertlevelTextField.setBounds(445, 427, 70, 21);
		panel.add(alertlevelTextField);
		
		JButton saveBT = new JButton("Save");
		saveBT.setBounds(352, 7, 117, 29);
		saveBT.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					if (!validateFunctionalGroupTab(nameTextField.getText(), perceptionrangeTextField.getText(), maxspeedTextField.getText(), wanderingspeedTextField.getText(),
							scoutingspeedTextField.getText(), maxdistancerefugeTextField.getText(), foragingspeedTextField.getText(), escapespeedTextField.getText(),
							huntingspeedTextField.getText(), alertlevelTextField.getText())){
						model.setName(nameTextField.getText());
						model.setPerceprange(Double.parseDouble(perceptionrangeTextField.getText()));
						model.setMaxspeed(Double.parseDouble(maxspeedTextField.getText()));
						model.setWanderingspeed(Double.parseDouble(wanderingspeedTextField.getText()));
						model.setScoutingspeed(Double.parseDouble(scoutingspeedTextField.getText()));
						model.setMaxdistancerefuge(Double.parseDouble(maxdistancerefugeTextField.getText()));
						model.setForagingspeed(Double.parseDouble(foragingspeedTextField.getText()));
						model.setEscapingspeed(Double.parseDouble(escapespeedTextField.getText()));
						model.setHuntingspeed(Double.parseDouble(huntingspeedTextField.getText()));
						model.setAlertlevel(Double.parseDouble(alertlevelTextField.getText()));
				       	controller.save(model);
				       	updateFunctionalGroupsView();
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
		
		JButton button_1 = new JButton("Advanced");
		button_1.setBounds(468, 7, 117, 29);
		//panel.add(button_1);
		
		JLabel label_26 = new JLabel("Preys:");
		label_26.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		label_26.setBounds(308, 73, 61, 16);
		panel.add(label_26);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(308, 110, 110, 121);
		panel.add(scrollPane_1);
		scrollPane_1.setViewportView(nopreyList);
		
		JButton button_2 = new JButton("<");
		button_2.setBounds(417, 110, 43, 29);
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						removeNoPreyListItem(e);
					} catch (Throwable e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		panel.add(button_2);
		
		JButton button_3 = new JButton(">");
		button_3.addActionListener(new ActionListener() {
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
		button_3.setBounds(417, 137, 43, 29);
		panel.add(button_3);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(460, 110, 110, 121);
		panel.add(scrollPane_2);
		
		
		//scrollPane_2.setViewportView(nopreyList);
		
		JLabel label_27 = new JLabel("Name:");
		label_27.setBounds(18, 11, 43, 21);
		panel.add(label_27);
		
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		nameTextField.setBounds(63, 11, 134, 21);
		panel.add(nameTextField);
		
		final JCheckBox chckbxDrift = new JCheckBox("Drift");
		chckbxDrift.setBounds(18, 278, 128, 23);
		panel.add(chckbxDrift);
		
		final JCheckBox chckbxRest = new JCheckBox("Rest");
		chckbxRest.setBounds(18, 234, 128, 23);
		panel.add(chckbxRest);
		
		final JCheckBox chckbxExplore = new JCheckBox("Explore");
		chckbxExplore.setBounds(18, 212, 128, 23);
		panel.add(chckbxExplore);
		
		final JCheckBox chckbxScouting = new JCheckBox("Scout");
		chckbxScouting.setBounds(18, 190, 128, 23);
		panel.add(chckbxScouting);
		
		final JCheckBox chckbxForraging = new JCheckBox("Forage");
		chckbxForraging.setBounds(18, 168, 128, 23);
		panel.add(chckbxForraging);
		
		final JCheckBox chckbxWandering = new JCheckBox("Wander");
		chckbxWandering.setBounds(18, 256, 128, 23);
		panel.add(chckbxWandering);
		
		final JCheckBox chckbxHunting = new JCheckBox("Hunt");
		chckbxHunting.setBounds(18, 146, 128, 23);
		panel.add(chckbxHunting);
		
		final JCheckBox chckbxEscaping = new JCheckBox("Escape");
		chckbxEscaping.setBounds(18, 124, 128, 23);
		panel.add(chckbxEscaping);
		
		final JCheckBox chckbxHidding = new JCheckBox("Hide");
		chckbxHidding.setBounds(18, 102, 128, 23);
		panel.add(chckbxHidding);
		
		final JComboBox classidComboBox = new JComboBox();
				
		classidComboBox.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chckbxExplore.setSelected(false);
				chckbxScouting.setSelected(false);
				chckbxForraging.setSelected(false);
				chckbxWandering.setSelected(false);
				chckbxHunting.setSelected(false);
				chckbxEscaping.setSelected(false);
				chckbxHidding.setSelected(false);
				chckbxDrift.setSelected(false);
				chckbxRest.setSelected(false);
				
				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Autotrophs")){
					chckbxDrift.setSelected(true);
				}
				
				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Herbivores invertebrates")){
					chckbxEscaping.setSelected(true);
					chckbxExplore.setSelected(true);
					chckbxForraging.setSelected(true);
					chckbxHidding.setSelected(true);
					chckbxRest.setSelected(true);
					chckbxScouting.setSelected(true);
				}
				
				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Scavenger invertebrates")){
					chckbxExplore.setSelected(true);
					chckbxScouting.setSelected(true);
					chckbxForraging.setSelected(true);
					chckbxEscaping.setSelected(true);
					chckbxHidding.setSelected(true);
					chckbxRest.setSelected(true);
				}
				
				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Omnivores invertebrates")){
					chckbxExplore.setSelected(true);
					chckbxScouting.setSelected(true);
					chckbxForraging.setSelected(true);
					chckbxHunting.setSelected(true);
					chckbxEscaping.setSelected(true);
					chckbxHidding.setSelected(true);
					chckbxRest.setSelected(true);
				}				

				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Predatory invertebrates")){
					chckbxEscaping.setSelected(true);
					chckbxExplore.setSelected(true);
					chckbxHidding.setSelected(true);
					chckbxHunting.setSelected(true);
					chckbxRest.setSelected(true);
					chckbxScouting.setSelected(true);
				}

				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Herbivores fish")){
					chckbxEscaping.setSelected(true);
					chckbxExplore.setSelected(true);
					chckbxForraging.setSelected(true);
					chckbxHidding.setSelected(true);
					chckbxRest.setSelected(true);
					chckbxWandering.setSelected(true);
				}

				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Planktivores fish")){
					chckbxEscaping.setSelected(true);
					chckbxForraging.setSelected(true);
					chckbxWandering.setSelected(true);
				}

				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Territorial small-invertebrate-eating fish")){
					chckbxEscaping.setSelected(true);
					chckbxExplore.setSelected(true);
					chckbxHidding.setSelected(true);
					chckbxHunting.setSelected(true);
					chckbxRest.setSelected(true);
					chckbxScouting.setSelected(true);
				}

				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Large-invertebrate-eating fish")){
					chckbxEscaping.setSelected(true);
					chckbxHunting.setSelected(true);
					chckbxWandering.setSelected(true);
				}

				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Piscivores")){
					chckbxEscaping.setSelected(true);
					chckbxHunting.setSelected(true);
					chckbxWandering.setSelected(true);
				}

				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Omnivores fish")){
					
				}
				
				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Zooplankton")){
					chckbxDrift.setSelected(true);
				}
				
				if(compareEcologicalTraitsString(classidComboBox.getSelectedItem().toString(), "Top predator")){
					chckbxHunting.setSelected(true);
					chckbxWandering.setSelected(true);
				}

				chckbxExplore.setEnabled(false);
				chckbxScouting.setEnabled(false);
				chckbxForraging.setEnabled(false);
				chckbxWandering.setEnabled(false);
				chckbxHunting.setEnabled(false);
				chckbxEscaping.setEnabled(false);
				chckbxHidding.setEnabled(false);
				chckbxDrift.setEnabled(false);
				chckbxRest.setEnabled(false);
			}
		});
		//classidComboBox.setModel(new DefaultComboBoxModel(new String[] {"Autotrophs", "Herbivores invertebrates", "Omnivores invertebrates", "Scavenger invertebrates", "Predatory invertebrates", "Herbivores fish", "Planktivores fish", "Territorial small-invertebrate-eating fish", "Large-invertebrate-eating fish", "Piscivores", "Omnivores fish"}));
		for(int i = 0; i < controller.ecologicalTraitslist().size();i++){
			classidComboBox.addItem(controller.ecologicalTraitslist().get(i));
		}
		classidComboBox.setBounds(18, 72, 175, 21);
		panel.add(classidComboBox);
		
		JLabel lblEcologicalTraits = new JLabel("Ecological Traits:");
		lblEcologicalTraits.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblEcologicalTraits.setBounds(18, 45, 122, 16);
		panel.add(lblEcologicalTraits);
		lblFunctionalGroups.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 13));
		lblFunctionalGroups.setBounds(460, 57, 110, 42);
		
		panel.add(lblFunctionalGroups);
		
		JButton newBT = new JButton("+");
		newBT.setBounds(12, 264, 43, 29);
		newBT.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					nameTextField.setText("New FunctionalGroup");
					perceptionrangeTextField.setText("0.0");
					maxspeedTextField.setText("0.0");
					wanderingspeedTextField.setText("0.0");
					scoutingspeedTextField.setText("0.0");
					maxdistancerefugeTextField.setText("0.0");
					foragingspeedTextField.setText("0.0");
					escapespeedTextField.setText("0.0");
					huntingspeedTextField.setText("0.0");
					alertlevelTextField.setText("0.0");
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
					nameTextField.setText("");
					perceptionrangeTextField.setText("");
					maxspeedTextField.setText("");
					wanderingspeedTextField.setText("");
					scoutingspeedTextField.setText("");
					maxdistancerefugeTextField.setText("");
					foragingspeedTextField.setText("");
					escapespeedTextField.setText("");
					huntingspeedTextField.setText("");
					alertlevelTextField.setText("");
					removeListItem(e);
					controller.delete(model);
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.print("Presionado -");
			}});
		add(deleteBT);
		
		updateFunctionalGroupsView();
		updatePreysView();
		
	}
	
	public void updateFunctionalGroupsView() throws Throwable{
		functionalGroupsModel.clear();
		for(FunctionalGroup s:controller.list()){
			functionalGroupsModel.addElement(s);
		}
		functionalGroupList.setModel(functionalGroupsModel);
		//preysList.setModel(functionalGroupsModel);
     }	
	
	public void updatePreysView() throws Throwable{
		preysModel.clear();
		for(FunctionalGroup s:controller.list()){
			preysModel.addElement(s);
		}
		scrollPane_2.setViewportView(preysList);
		preysList.setModel(preysModel);
     }	
	
	public void removeListItem(ActionEvent e) throws Throwable{
		DefaultListModel model = (DefaultListModel) functionalGroupList.getModel();
		int selectedIndex = functionalGroupList.getSelectedIndex();
	      if (selectedIndex != -1) {
	    	  model.remove(selectedIndex);
	      }
	}
	
	public void removeNoPreyListItem(ActionEvent e) throws Throwable{
		DefaultListModel outmodel = (DefaultListModel) preysList.getModel();
		int selectedIndex = preysList.getSelectedIndex();
	      if (selectedIndex != -1) {
	    	  inmodel.addElement(preysList.getSelectedValue());
	    	  outmodel.remove(selectedIndex);
	    	  nopreyList.setModel(inmodel);
	      }
	}
	
	public void removeInPopulationListItem(ActionEvent e) throws Throwable{
		DefaultListModel inmodel = (DefaultListModel) nopreyList.getModel();
		DefaultListModel auxinpreys = (DefaultListModel) preysList.getModel();
		int selectedIndex = nopreyList.getSelectedIndex();
	      if (selectedIndex != -1) {
	    	  auxinpreys.addElement(nopreyList.getSelectedValue());
	    	  inmodel.remove(selectedIndex);
	    	  preysList.setModel(auxinpreys);
	      }
	}
	
	public boolean compareEcologicalTraitsString(String selecteditem, String item){
		if(selecteditem.equals(item))
			return true;
		return false;
	}
	
	private boolean validateFunctionalGroupTab(String name, String perception, String max, String wandering, String scouting, String refuge,
												String foraging, String escaping, String hunting, String alert) throws Throwable{
		
		if (name.equals("")){
			showErrorMessage("Name can't be empty");
			nameTextField.requestFocusInWindow();
			return true;
		}
		else if(name.length() > 255){
			showErrorMessage("Name must be 255 characters long or less");
			nameTextField.requestFocusInWindow();
			return true;
		}
		else {
			if (model.isNewrecord()){
				for(FunctionalGroup s:controller.list()){
					if (s.getName().equals(name)){
						showErrorMessage("There is already a functional group with this name");
						nameTextField.requestFocusInWindow();
						return true;
					}
				}
			}
		}
		
		if (perception.equals("") || Double.parseDouble(perception) <= 0){
			showErrorMessage("Perception range must be greater than 0");
			perceptionrangeTextField.requestFocusInWindow();
			return true;
		}
		if (max.equals("") || Double.parseDouble(max) <= 0){
			showErrorMessage("Max speed must be greater than 0");
			maxspeedTextField.requestFocusInWindow();
			return true;
		}
		if (wandering.equals("") || Double.parseDouble(wandering) <= 0){
			showErrorMessage("Wandering speed must be greater than 0");
			wanderingspeedTextField.requestFocusInWindow();
			return true;
		}
		if (scouting.equals("") || Double.parseDouble(scouting) <= 0){
			showErrorMessage("Scouting speed must be greater than 0");
			scoutingspeedTextField.requestFocusInWindow();
			return true;
		}
		if (refuge.equals("") || Double.parseDouble(refuge) <= 0){
			showErrorMessage("Distance from refuge must be greater than 0");
			maxdistancerefugeTextField.requestFocusInWindow();
			return true;
		}
		if (foraging.equals("") || Double.parseDouble(foraging) <= 0){
			showErrorMessage("Foraging speed must be greater than 0");
			foragingspeedTextField.requestFocusInWindow();
			return true;
		}
		if (escaping.equals("") || Double.parseDouble(escaping) <= 0){
			showErrorMessage("Escaping speed must be greater than 0");
			escapespeedTextField.requestFocusInWindow();
			return true;
		}
		if (hunting.equals("") || Double.parseDouble(hunting) <= 0){
			showErrorMessage("Hunting speed must be greater than 0");
			huntingspeedTextField.requestFocusInWindow();
			return true;
		}
		if (alert.equals("") || Double.parseDouble(alert) <= 0){
			showErrorMessage("Escaping alert must be greater than 0");
			alertlevelTextField.requestFocusInWindow();
			return true;
		}

		return false;
	}
	
	private void showErrorMessage(String error){
		JOptionPane.showMessageDialog(null, error, "Message", JOptionPane.WARNING_MESSAGE);
	}
}
