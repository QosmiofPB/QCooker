package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class OpenBank extends Node {

	private int rawFood;
	private Fish food;
	private Gui gui;

	public OpenBank(ClientContext ctx, Fish food, Gui gui) {
		super(ctx);
		this.food = food;
		this.gui = gui;
	}

	@Override
	public boolean activate() {
		rawFood = food.getRawId();
		if (gui.makingPizza) {

		}
		return ctx.backpack.select().id(rawFood).first().isEmpty()
				&& !ctx.players.local().inMotion()
				&& ctx.players.local().animation() == -1
				&& !ctx.bank.opened()
				&& ctx.movement.distance(ctx.bank.nearest(), ctx.players
						.local().tile()) < 10 && ctx.bank.inViewport();
	}

	@Override
	public void execute() {

		if (ctx.bank.inViewport()) {
			ctx.camera
					.turnTo(new Tile((ctx.bank.nearest().tile().x() + -Random
							.nextInt(1, 10)),
							(ctx.bank.nearest().tile().y() + -Random.nextInt(1,
									10)),
							(ctx.bank.nearest().tile().floor() + -Random
									.nextInt(1, 10))));
			if (!ctx.bank.opened()) {
				ctx.bank.open();
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ctx.bank.opened();
					}
				}, 500, 2);
			}

		}

	}

}
