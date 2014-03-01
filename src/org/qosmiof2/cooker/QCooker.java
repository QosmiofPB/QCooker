package org.qosmiof2.cooker;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.Skills;
import org.powerbot.script.util.Random;
import org.powerbot.script.util.Timer;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.Node;

@Manifest(description = "Cooks anything (almost)", name = "AIO Cooker", authors = "Qosmiof2")
public class QCooker extends PollingScript implements MessageListener, PaintListener {

	public static ArrayList<Node> nodes = new ArrayList<>();

	private int startLvl, startExp;
	private int fishLeft;
	private int cooked;
	private int burned;
	public Fish food;
	
	private Timer runTime = new Timer(0);

	@Override
	public void start() {
//		startLvl = ctx.skills.getLevel(Skills.FISHING);
//		startExp = ctx.skills.getExperience(Skills.FISHING);
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
		if (msg.getMessage().startsWith("You manage to cook ")
				&& msg.getSender().equals("")) {
			cooked++;
		} else if(msg.getMessage().startsWith("You burned") && msg.getSender().equals("")){
			burned++;
		}
	}

	@Override
	public void repaint(Graphics g1) {
		Graphics2D g = (Graphics2D) g1;
		g.setColor(Color.green);
		g.drawRect(0, 0, 300, 200);
		g.setColor(Color.black);
		g.fillRect(0,  0, 300 , 200);
		
	}
}
