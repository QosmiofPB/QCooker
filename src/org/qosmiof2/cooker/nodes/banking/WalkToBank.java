package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Location;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WalkToBank extends Node {

	private Location location;
	private int rawFood;
	

	public WalkToBank(ClientContext ctx, Fish food, Location location) {
		super(ctx);
		this.location = location;
		rawFood = food.getRawId();
	}

	@Override
	public boolean activate() {
		return ctx.players.local().idle()
				&& ctx.backpack.select().id(rawFood).isEmpty()
				&& ctx.players.local().animation() == -1 && !ctx.bank.opened()
				&& !ctx.bank.inViewport();
	}

	@Override
	public void execute() {
		ctx.movement.step(location.getBankTile().derive(Random.nextInt(1, 4),
				Random.nextInt(1, 5)));
		System.out.println(location.getBankTile().derive(Random.nextInt(1, 4),
				Random.nextInt(1, 5)));
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.bank.inViewport();
			}
		}, 1000, 4);
	}

}
