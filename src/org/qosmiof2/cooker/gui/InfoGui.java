package org.qosmiof2.cooker.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.powerbot.script.rt6.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GeItem;
import org.qosmiof2.cooker.data.Fish;

public class InfoGui extends ClientAccessor {
	public InfoGui(ClientContext ctx) {
		super(ctx);
		showGui();
	}

	private final JFrame frame = new JFrame();
	private final JPanel panel = new JPanel();
	private final JPanel panel1 = new JPanel();

	private final Border loweredetched = BorderFactory
			.createEtchedBorder(EtchedBorder.LOWERED);
	private final TitledBorder lowered = BorderFactory.createTitledBorder(
			loweredetched, "Stats");

	private final JLabel labelExp = new JLabel();
	private final JLabel labelLvl = new JLabel();
	private final JLabel labelCooked = new JLabel();
	private final JLabel labelTime = new JLabel();
	private final JLabel profitLabel = new JLabel();
	private final JLabel labelBurnt = new JLabel();
	private final JTabbedPane tp = new JTabbedPane();
	private final JPanel profitPanel = new JPanel();
	private Fish fish;
	private int cookedFood, rawFood;
	private final JLabel gpLabel = new JLabel("Profit | loss: 0");
	private JComboBox<Fish> fishCb = new JComboBox<Fish>(Fish.values());

	private int exp = 0;
	private int lvl = 0;
	private int lvlGained = 0;
	private int cooked = 0;
	private int burnt = 0;
	private String runTime;
	private int profit;

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void setProfit(int profit){
		this.profit = profit;
	}
	public void setTimeRunning(String string) {
		this.runTime = string;
	}

	public void setLvl(int lvl, int lvlGained) {
		this.lvl = lvl;
		this.lvlGained = lvlGained;
	}

	public void setCooked(int cooked) {
		this.cooked = cooked;
	}

	public void setBurnt(int burnt) {
		this.burnt = burnt;
	}

	private void showGui() {
		statPanel();
		profitPanel();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(panel);
		frame.requestFocus();

		frame.setResizable(false);
		frame.setSize(125, 220);

		frame.add(tp);

		tp.add(panel, "Stats");
		tp.add(profitPanel, "Profit");
		tp.setFocusable(false);

	}

	public void updateStats() {
		fish = (Fish) fishCb.getSelectedItem();
		cookedFood = GeItem.price(fish.getCookedId());
		rawFood = GeItem.price(fish.getRawId());
		profit = (cookedFood - rawFood) * cooked;
		labelExp.setText("Exp: " + exp);
		labelLvl.setText("Level: " + lvl + "(" + lvlGained + ")");
		labelCooked.setText("Cooked: " + cooked);
		labelTime.setText("" + runTime);
		labelBurnt.setText("Burnt: " + burnt);
		profitLabel.setText("Profit / loss: " + profit);
	}

	private void statPanel() {
		panel.setLayout(new GroupLayout(panel));
		panel.add(panel1);

		panel1.setLocation(5, 5);
		panel1.setSize(110, 150);
		panel1.setLayout(new GroupLayout(panel1));
		panel1.setBorder(lowered);

		panel1.add(labelExp);
		panel1.add(labelLvl);
		panel1.add(labelCooked);
		panel1.add(labelTime);
		panel1.add(labelBurnt);
		panel1.add(profitLabel);
		
		labelExp.setLocation(20, 20);
		labelExp.setSize(120, 20);

		labelLvl.setLocation(20, 40);
		labelLvl.setSize(120, 20);

		labelCooked.setLocation(20, 60);
		labelCooked.setSize(120, 20);

		labelBurnt.setLocation(20, 80);
		labelBurnt.setSize(120, 20);

		profitLabel.setLocation(20, 100);
		profitLabel.setSize(120, 20);
		
		labelTime.setLocation(20, 120);
		labelTime.setSize(120, 20);

	}

	private void profitPanel() {
		fish = (Fish) fishCb.getSelectedItem();
		cookedFood = GeItem.price(fish.getCookedId());
		rawFood = GeItem.price(fish.getRawId());
		
		profitPanel.setLayout(new GroupLayout(profitPanel));
		profitPanel.add(fishCb);
		profitPanel.add(gpLabel);

		fishCb.setLocation(5, 10);
		fishCb.setSize(110, 30);
		fishCb.setFocusable(false);
		fishCb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fish = (Fish) fishCb.getSelectedItem();
				cookedFood = GeItem.price(fish.getCookedId());
				rawFood = GeItem.price(fish.getRawId());
				gpLabel.setText("Profit | loss: " + (cookedFood - rawFood)
						+ "gp.");

			}

		});

		gpLabel.setLocation(5, 50);
		gpLabel.setText("Profit | loss: " + (cookedFood - rawFood) + "gp.");
		gpLabel.setSize(110, 20);

	}
}
