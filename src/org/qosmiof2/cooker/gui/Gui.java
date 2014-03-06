package org.qosmiof2.cooker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.nodes.antiban.*;
import org.qosmiof2.cooker.nodes.banking.*;
import org.qosmiof2.cooker.nodes.cooking.*;

public class Gui extends MethodProvider {

	private JFrame frame = new JFrame("QCooker");
	private JPanel panel = new JPanel();
	private JButton startButton = new JButton();
	private JLabel selectFishLabel = new JLabel();
	private JComboBox<Fish> cb = new JComboBox<>(Fish.values());
	private GroupLayout layout = new GroupLayout(panel);
	
	public static Fish food;

	public Gui(MethodContext ctx) {
		super(ctx);
		init();
	}

	public void init() {

		frame.setBounds(300, 150, 255, 240);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.add(panel);
		frame.setResizable(false);

		panel.setLayout(layout);
		panel.add(startButton);
		panel.add(selectFishLabel);
		panel.add(cb);

		selectFishLabel.setBounds(10, 10, 230, 50);
		selectFishLabel.setText("Please select the fish you want to cook:");

		cb.setBounds(10, 50, 230, 50);

		startButton.setText("Start");
		startButton.setBounds(10, 130, 230, 50);

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				food = (Fish) cb.getSelectedItem();
				QCooker.nodes.add(new DepositInventory(ctx));
				QCooker.nodes.add(new OpenBank(ctx));
				QCooker.nodes.add(new WithdrawFood(ctx));
				QCooker.nodes.add(new Cook(ctx));
				QCooker.nodes.add(new Wait(ctx));
				QCooker.nodes.add(new WalkToRange(ctx));
				QCooker.nodes.add(new PressButton(ctx));
				QCooker.nodes.add(new LogOut(ctx));
				frame.dispose();
			}

		});

	}
}
