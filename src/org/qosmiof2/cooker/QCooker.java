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

	private int startLvl, startExp, expGained, cooked, expPH, lvlGained, x, y, lvl, exp, expTNL;
	private int burnt;
	public static String status = "Waiting for input...";
	private Point p;
	private boolean showPaint = true;

	public static void setStatus(String status) {
		QCooker.status = status;
	}

	private Timer runTime = new Timer(0);

	Image background, offButton;

	@Override
	public void start() {
		try {
			URL url = new URL("http://s17.postimg.org/6whk1ybqn/QCooker.png");
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
	private Rectangle hide = new Rectangle(625, 438, 38, 15);
	private Font font = new Font("Arial", 1, 13);
	private Color orange = new Color(255,140,0);

	@Override
	public void repaint(Graphics g1) {
		
		//Information
		lvl = ctx.skills.getRealLevel(Skills.COOKING);
		lvlGained = (lvl - startLvl);
		
		exp = ctx.skills.getExperience(Skills.COOKING);
		expGained = (exp - startExp);
		expPH = (int) (expGained * (3600000D/runTime.getElapsed()));
		expTNL = (ctx.skills.getExperienceAt(lvl + 1) - exp); 
		
		x = ctx.mouse.getLocation().x;
		y = ctx.mouse.getLocation().y;

		Graphics2D g = (Graphics2D) g1;

		//Mouse
		g.setColor(Color.black);
		g.drawLine(x - 1000, y, x + 1000, y);
		g.drawLine(x, y -1000, x, y +1000);
		
		g.setColor(Color.red);
		g.drawOval(x-4, y-4, 8, 8);
		g.setColor(orange);
		g.fillOval(x-3, y-3, 7, 7);
		g.setColor(Color.black);

		if (showPaint) {
			g.setFont(font);
			g.setColor(Color.white);
			g.drawImage(background, 1, 415, null);
			g.drawString("" + expGained, 265, 525);
			g.drawString("" + expPH, 500, 525);
			g.drawString("" + lvl + "(" + lvlGained + ")", 490, 570);
			g.drawString("" + cooked + "("+ burnt + ")" , 280, 570);
			g.drawString("" + runTime.toElapsedString(), 650, 570);
			g.drawString("" + status, 355, 500);
			g.drawString("" + expTNL, 100, 570);
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

		if (hide.contains(p)) {
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
