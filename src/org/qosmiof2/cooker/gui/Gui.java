package org.qosmiof2.cooker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import org.qosmiof2.cooker.data.Location;
import org.qosmiof2.cooker.nodes.antiban.LogOut;
import org.qosmiof2.cooker.nodes.antiban.Wait;
import org.qosmiof2.cooker.nodes.banking.DepositInventory;
import org.qosmiof2.cooker.nodes.banking.OpenBank;
import org.qosmiof2.cooker.nodes.banking.WithdrawFood;
import org.qosmiof2.cooker.nodes.cooking.Cook;
import org.qosmiof2.cooker.nodes.cooking.PressButton;
import org.qosmiof2.cooker.nodes.cooking.WalkToRange;
import org.qosmiof2.cooker.nodes.framework.Node;

public class Gui extends MethodProvider {

	private JFrame frame = new JFrame("QCooker");
	private JPanel panel = new JPanel();
	private JButton startButton = new JButton();
	private JLabel selectFishLabel = new JLabel();
	private JComboBox<Fish> cb = new JComboBox<>(Fish.values());
	private JComboBox<Location> locationCb = new JComboBox<>(Location.values());
	private GroupLayout layout = new GroupLayout(panel);
	
	public static Fish food;
	public static Location location;

	public Gui(MethodContext ctx) {
		super(ctx);
		init();
	}

	public void init() {

		frame.setBounds(300, 150, 255, 270);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		frame.add(panel);
		frame.setResizable(false);

		panel.setLayout(layout);
		panel.add(startButton);
		panel.add(selectFishLabel);
		panel.add(cb);
		panel.add(locationCb);

		selectFishLabel.setBounds(10, 10, 230, 50);
		selectFishLabel.setText("Please select: ");

		cb.setBounds(10, 50, 230, 50);

		locationCb.setBounds(10, 110, 230, 50);
		
		startButton.setText("Start");
		startButton.setBounds(10, 170, 230, 50);

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				food = (Fish) cb.getSelectedItem();
				location = (Location) locationCb.getSelectedItem();
				QCooker.nodes.add(new DepositInventory(ctx));
				QCooker.nodes.add(new OpenBank(ctx));
				QCooker.nodes.add(new WithdrawFood(ctx));
				QCooker.nodes.add(new Cook(ctx));
				QCooker.nodes.add(new Wait(ctx));
				QCooker.nodes.add(new WalkToRange(ctx));
				QCooker.nodes.add(new PressButton(ctx));
				QCooker.nodes.add(new LogOut(ctx));
				QCooker.location = location.getNAME();
				frame.dispose();
			}

		});

	}
}
