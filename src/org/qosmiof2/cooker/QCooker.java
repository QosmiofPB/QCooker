package org.qosmiof2.cooker;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
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
		PaintListener, MouseListener {

	public static ArrayList<Node> nodes = new ArrayList<>();

	private int startLvl, startExp, expGained, cooked, expPH, lvlGained, x, y, lvl, exp;
	public static int fishLeft;
	public static int cookedFishInBank;
	private int burnt;
	public static String status = "Waiting for input...";
	public static String location = "Waiting for input...";
	private Point p;
	private boolean showPaint = true;

	public static void setCookedFishInBank(int cookedFishInBank) {
		QCooker.cookedFishInBank = cookedFishInBank;
	}

	public static void setStatus(String status) {
		QCooker.status = status;
	}

	public static void setFishLeft(int fishLeft) {
		QCooker.fishLeft = fishLeft;
	}

	private Timer runTime = new Timer(0);

	Image background, offButton;

	@Override
	public void start() {
		try {
			URL url = new URL("http://s11.postimg.org/eaos8imer/QCooker.png");
			background = java.awt.Toolkit.getDefaultToolkit()
					.getDefaultToolkit().createImage(url);

			URL url2 = new URL("http://s1.postimg.org/5z4vq7wdn/Off_Button.png");
			offButton = java.awt.Toolkit.getDefaultToolkit()
					.getDefaultToolkit().createImage(url2);

		} catch (Exception e) {

		}
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

	private Rectangle show = new Rectangle(1, 1, 38, 20);
	private Rectangle close = new Rectangle(625, 438, 38, 15);
	private Font font = new Font("Arial", 1, 13);

	@Override
	public void repaint(Graphics g1) {

		lvl = ctx.skills.getRealLevel(Skills.COOKING);
		lvlGained = (lvl - startLvl);
		
		exp = ctx.skills.getExperience(Skills.COOKING);
		expGained = (exp - startExp);
		expPH = (int) (expGained * (3600000D/runTime.getElapsed()));
		
		x = ctx.mouse.getLocation().x;
		y = ctx.mouse.getLocation().y;

		Graphics2D g = (Graphics2D) g1;

		g.setColor(Color.MAGENTA);
		g.drawLine(x - 10, y + 10, x + 10, y - 10);
		g.drawLine(x - 10, y - 10, x + 10, y + 10);

		if (showPaint) {
			g.setFont(font);
			g.setColor(Color.white);
			g.drawImage(background, 102, 420, null);
			g.drawString("" + expGained, 265, 530);
			g.drawString("" + expPH, 500, 530);
			g.drawString("" + lvl + "(" + lvlGained + ")", 490, 575);
			g.drawString("" + cooked + "("+ burnt + ")" , 280, 575);
		} else {
			g.drawImage(offButton, 1, 1, null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		p = e.getPoint();
		if (show.contains(p)) {
			showPaint = true;
		}

		if (close.contains(p)) {
			showPaint = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
