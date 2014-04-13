package org.qosmiof2.cooker;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Timer;

import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script.Manifest;
import org.powerbot.script.rt6.Skills;
import org.qosmiof2.cooker.data.Update;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

@Manifest(description = "Cooks anything (almost)", name = "AIO Cooker", properties = "Qosmiof2")
public class QCooker extends
		PollingScript<org.powerbot.script.rt6.ClientContext> implements
		MessageListener, PaintListener {

	public static ArrayList<Node> nodes = new ArrayList<>();

	private int burnt, cooked, x, y;
	private long time;
	private Update update;

	@Override
	public void start() {
		Timer timer = new Timer();
		timer.schedule(update = new Update(ctx, QCooker.this), 0, 100);
		update.setStartExp(ctx.skills.experience(Skills.COOKING));
		update.setStartLvl(ctx.skills.realLevel(Skills.COOKING));
		// update.setCookedPrice(GeItem.price(fish.getCookedId()));
		// update.setRawPrice(GeItem.price(fish.getRawId()));
		nodes.clear();

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Gui(ctx);
			}

		});
	}

	@Override
	public void stop(){
		ctx.controller().stop();
	}

	@Override
	public void poll() {
		for (Node node : nodes) {
			if (node.activate()) {
				node.execute();
			}
		}
	}

	@Override
	public void messaged(MessageEvent msg) {
		if (msg.getMessage().startsWith("You successfully ")
				|| msg.getMessage().startsWith("You manage ")
				&& msg.getSender().equals("")) {
			cooked++;
			update.setCooked(cooked);
		} else if (msg.getMessage().startsWith("You accidentally")
				&& msg.getSender().equals("")) {
			burnt++;
			update.setBurnt(burnt);

		}
	}

	public long getTime() {
		time = getTotalRuntime();
		return time;
	}

	@Override
	public void repaint(Graphics g1) {
		x = ctx.mouse.getLocation().x;
		y = ctx.mouse.getLocation().y;

		Graphics2D g = (Graphics2D) g1;

		g.setColor(Color.white);
		g.drawLine(x - 5, y, x + 5, y);
		g.drawLine(x, y - 5, x, y + 5);
	}

}
