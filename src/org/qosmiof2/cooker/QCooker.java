package org.qosmiof2.cooker;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.Skills;
import org.powerbot.script.util.Random;
import org.powerbot.script.util.Timer;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

@Manifest(description = "Cooks anything (almost)", name = "AIO Cooker", authors = "Qosmiof2")
public class QCooker extends PollingScript implements MessageListener,
		PaintListener {

	public static ArrayList<Node> nodes = new ArrayList<>();

	private int startLvl, startExp;
	public static int fishLeft;
	private int cooked;
	private int burnt;
	public static String status = "Waiting for input...";

	public static void setStatus(String status) {
		QCooker.status = status;
	}

	public static void setFishLeft(int fishLeft) {
		QCooker.fishLeft = fishLeft;
	}

	private Timer runTime = new Timer(0);

	@Override
	public void start() {
		nodes.clear();
		startLvl = ctx.skills.getLevel(Skills.COOKING);
		startExp = ctx.skills.getExperience(Skills.COOKING);
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Gui(ctx);
			}

		});
	}

	@Override
	public int poll() {
		for (Node node : nodes) {
			if (node.activate()) {
				node.execute();
				return Random.nextInt(200, 550);
			}
		}
		
		return 100;
	}

	@Override
	public void messaged(MessageEvent msg) {
		if (msg.getMessage().startsWith("You successfully ")
				&& msg.getSender().equals("")) {
			cooked++;
		} else if (msg.getMessage().startsWith("You accidentally")
				&& msg.getSender().equals("")) {
			burnt++;
		}
	}

	private int x;
	private int y;

	private int lvl;
	private int exp;

	private final Font font = new Font("Arial", 1, 15);

	@Override
	public void repaint(Graphics g1) {
		lvl = ctx.skills.getRealLevel(Skills.COOKING);
		exp = ctx.skills.getExperience(Skills.COOKING);

		x = ctx.mouse.getLocation().x;
		y = ctx.mouse.getLocation().y;

		Graphics2D g = (Graphics2D) g1;

		g.setColor(Color.MAGENTA);
		g.drawLine(x - 10, y + 10, x + 10, y - 10);
		g.drawLine(x - 10, y - 10, x + 10, y + 10);

		g.setFont(font);
		g.setColor(Color.MAGENTA);
		g.drawRect(1, 1, 300, 170);
		g.setColor(Color.black);
		g.fillRect(2, 2, 298, 168);
		g.setColor(Color.white);
		g.drawString("Level: " + ctx.skills.getLevel(Skills.COOKING) + "("
				+ (ctx.skills.getLevel(Skills.COOKING) - startLvl) + ")", 10,
				30);
		g.drawString("Experience: "
				+ (ctx.skills.getExperience(Skills.COOKING) - startExp) + "("
				+ ((ctx.skills.getExperienceAt(lvl + 1) - exp)) + ")", 10, 50);
		g.drawString("Running: " + runTime.toElapsedString(), 10, 70);
		g.drawString("Status: " + status, 10, 90);
		g.drawString("Cooked: " + cooked, 10, 110);
		g.drawString("Burnt: " + burnt, 10, 130);
		g.drawString("Fish left in bank: " + fishLeft , 10, 150);

	}

}
