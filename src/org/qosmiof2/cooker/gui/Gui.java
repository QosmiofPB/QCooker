package org.qosmiof2.cooker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.nodes.Cook;
import org.qosmiof2.cooker.nodes.Wait;
import org.qosmiof2.cooker.nodes.banking.*;
public class Gui extends MethodProvider {

	private JFrame frame = new JFrame("QCooker");
	private JPanel panel = new JPanel();
	private JButton button = new JButton();
	private JLabel label = new JLabel();
	private JComboBox<Fish> cb = new JComboBox<>(Fish.values());

	public static Fish food;

	public Gui(MethodContext ctx) {
		super(ctx);
		init();
	}

	public void init() {

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

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				food = (Fish) cb.getSelectedItem();
				QCooker.nodes.add(new CloseBank(ctx));
				QCooker.nodes.add(new DepositInventory(ctx));
				QCooker.nodes.add(new CloseBank(ctx));
				QCooker.nodes.add(new WithdrawFood(ctx));
				QCooker.nodes.add(new Cook(ctx));
				QCooker.nodes.add(new Wait(ctx));
				frame.dispose();
			}

		});

	}
}
