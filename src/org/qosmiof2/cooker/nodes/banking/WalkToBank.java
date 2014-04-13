package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Location;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WalkToBank extends Node {

	private Fish food;
	private Location location;

	public WalkToBank(ClientContext ctx, Fish food, Location location) {
		super(ctx);
		this.location = location;
		this.food = food;
	}

	@Override
	public boolean activate() {
		return ctx.players.local().idle()
				&& ctx.backpack.select().id(food.getRawId()).isEmpty()
				&& ctx.players.local().animation() == -1 && !ctx.bank.opened()
				&& !ctx.bank.inViewport();
	}

	@Override
	public void execute() {
		ctx.movement.step(location.getBankTile().derive(Random.nextInt(1, 3),
				Random.nextInt(1, 4)));
		System.out.println(location.getBankTile().derive(Random.nextInt(1, 3),
				Random.nextInt(1, 4)));
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.bank.inViewport();
			}
		}, 1000, 4);
	}

}
