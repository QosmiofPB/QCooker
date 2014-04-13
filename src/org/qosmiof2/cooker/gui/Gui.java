package org.qosmiof2.cooker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Location;
import org.qosmiof2.cooker.nodes.banking.CloseBank;
import org.qosmiof2.cooker.nodes.banking.DepositInventory;
import org.qosmiof2.cooker.nodes.banking.OpenBank;
import org.qosmiof2.cooker.nodes.banking.WalkToBank;
import org.qosmiof2.cooker.nodes.banking.WithdrawFood;
import org.qosmiof2.cooker.nodes.cooking.Cook;
import org.qosmiof2.cooker.nodes.cooking.PressButton;
import org.qosmiof2.cooker.nodes.cooking.WalkToRange;

public class Gui extends ClientAccessor {

	public static Fish food;
	public Location location;

	public Gui(ClientContext ctx) {
		super(ctx);
		init();
	}

	private final JFrame frame = new JFrame("QCooker");
	private final JTabbedPane tp = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panelCook = new JPanel();
	private final JButton buttonCook = new JButton("Cook");
	private final JComboBox<Fish> cbCook = new JComboBox<Fish>(Fish.values());
	private final JComboBox<Location> cbLoc = new JComboBox<Location>(
			Location.values());
	public boolean makingPizza = false;

	private void init() {
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(250, 150);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(tp);

		panelCook();

		tp.add("Cook", panelCook);

	}

	private void panelCook() {
		panelCook.setLayout(new GroupLayout(panelCook));
		panelCook.add(buttonCook);
		panelCook.add(cbCook);
		panelCook.add(cbLoc);

		cbCook.setBounds(5, 5, 100, 30);
		cbLoc.setBounds(120, 5, 100, 30);

		buttonCook.setBounds(120, 50, 100, 30);
		buttonCook.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				food = (Fish) cbCook.getSelectedItem();
				location = (Location) cbLoc.getSelectedItem();
				QCooker.nodes.add(new Cook(ctx, food));
				QCooker.nodes.add(new PressButton(ctx));
				QCooker.nodes.add(new WalkToRange(ctx, food, location));
				QCooker.nodes.add(new CloseBank(ctx, food, Gui.this));
				QCooker.nodes.add(new DepositInventory(ctx, food, Gui.this));
				QCooker.nodes.add(new OpenBank(ctx, food, Gui.this));
				QCooker.nodes.add(new WalkToBank(ctx, food, location));
				QCooker.nodes.add(new WithdrawFood(ctx, food, Gui.this));
//				QCooker.nodes.add(new Wait(ctx));
//				QCooker.nodes.add(new LogOut(ctx, food));
				frame.dispose();

				System.out.println(food.getRawId());

			}

		});

	}
}
