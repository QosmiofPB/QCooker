package org.qosmiof2.cooker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.nodes.Bank;
import org.qosmiof2.cooker.nodes.Cook;


public class Gui extends MethodProvider {

	private JFrame frame = new JFrame("QCooker");
	private JPanel panel = new JPanel();
	private JButton button = new JButton();
	private JTextField tf = new JTextField();
	private JLabel label = new JLabel();
	private JComboBox<Fish> cb = new JComboBox<>(Fish.values());
	
	private QCooker qc;
	
	public Gui(MethodContext ctx) {
		super(ctx);
		showGui();
	}

	public void showGui() {

		frame.setBounds(300, 150, 250, 260);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.add(panel);
		frame.setResizable(false);

		panel.setLayout(null);
		panel.add(button);
		panel.add(label);
		panel.add(cb);

		label.setBounds(10, 10, 230, 50);
		label.setText("Please select the fish you want to cook:");
		
		cb.setBounds(10, 50, 230, 50);

		button.setText("Start");
		button.setBounds(10, 130, 230, 50);
		
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				qc.food = (Fish) cb.getSelectedItem();
				qc.nodes.add(new Bank(ctx));
				qc.nodes.add(new Cook(ctx));
			}
			
		});

	}
}
