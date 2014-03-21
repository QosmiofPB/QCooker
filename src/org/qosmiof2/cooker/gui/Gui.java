package org.qosmiof2.cooker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Location;
import org.qosmiof2.cooker.data.Other;
import org.qosmiof2.cooker.nodes.antiban.LogOut;
import org.qosmiof2.cooker.nodes.antiban.Wait;
import org.qosmiof2.cooker.nodes.banking.CloseBank;
import org.qosmiof2.cooker.nodes.banking.DepositInventory;
import org.qosmiof2.cooker.nodes.banking.OpenBank;
import org.qosmiof2.cooker.nodes.banking.WalkToBank;
import org.qosmiof2.cooker.nodes.banking.WithdrawFood;
import org.qosmiof2.cooker.nodes.cooking.Cook;
import org.qosmiof2.cooker.nodes.cooking.PressButton;
import org.qosmiof2.cooker.nodes.cooking.WalkToRange;
import org.qosmiof2.cooker.nodes.make.AddCheese;
import org.qosmiof2.cooker.nodes.make.Make;

public class Gui extends MethodProvider {

	public static Fish food;
	public Location location;
	public Other other;

	public Gui(MethodContext ctx) {
		super(ctx);
		init();
	}

	private final JFrame frame = new JFrame("QCooker");
	private final JTabbedPane tp = new JTabbedPane(JTabbedPane.TOP);
	private final JPanel panelCook = new JPanel();
	private final JPanel panelMake = new JPanel();
	private final JButton buttonCook = new JButton("Cook");
	private final JButton buttonMake = new JButton("Make");
	private final JComboBox<Fish> cbCook = new JComboBox<Fish>(Fish.values());
	private final JComboBox<Location> cbLoc = new JComboBox<Location>(Location.values());
	private final JComboBox<Other> cbMake = new JComboBox<Other>(Other.values());
	public boolean makingPizza = false;

	private void init() {
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(250, 150);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.add(tp);
		
		panelCook();
		panelMake();

		tp.add("Cook", panelCook);
		tp.add("Make", panelMake);
	

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
				other = (Other) cbMake.getSelectedItem();
				QCooker.nodes.add(new Cook(ctx, food));
				QCooker.nodes.add(new PressButton(ctx));
				QCooker.nodes.add(new WalkToRange(ctx, food, location));
				QCooker.nodes.add(new CloseBank(ctx, food, Gui.this, other));
				QCooker.nodes.add(new DepositInventory(ctx, food, other, Gui.this));
				QCooker.nodes.add(new OpenBank(ctx, food, location, other, Gui.this));
				QCooker.nodes.add(new WalkToBank(ctx, food, location));
				QCooker.nodes.add(new WithdrawFood(ctx, food, Gui.this, other));
				QCooker.nodes.add(new Wait(ctx));
				QCooker.nodes.add(new LogOut(ctx, food));
				frame.dispose();
				
				System.out.println(food.getRawId());

			}

		});
		


	}

	private void panelMake() {
		panelMake.setLayout(new GroupLayout(panelMake));
		panelMake.add(buttonMake);
		panelMake.add(cbMake);

		buttonMake.setBounds(120, 50, 100, 30);
		buttonMake.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				other = (Other) cbMake.getSelectedItem();
				food = (Fish) cbCook.getSelectedItem();
				location = (Location) cbLoc.getSelectedItem();
				makingPizza = true;
				QCooker.nodes.add(new CloseBank(ctx, food, Gui.this, other));
				QCooker.nodes.add(new DepositInventory(ctx, food, other, Gui.this));
				QCooker.nodes.add(new OpenBank(ctx, food, location, other, Gui.this));
				QCooker.nodes.add(new WalkToBank(ctx, food, location));
				QCooker.nodes.add(new WithdrawFood(ctx, food, Gui.this, other));
				QCooker.nodes.add(new Make(ctx, other));
				QCooker.nodes.add(new AddCheese(ctx, other));
				QCooker.nodes.add(new Wait(ctx));
				QCooker.nodes.add(new LogOut(ctx, food));

				frame.dispose();
				System.out.println(other.getId());
			}

		});
		
		cbMake.setBounds(5, 5, 150, 30);
	}
}
