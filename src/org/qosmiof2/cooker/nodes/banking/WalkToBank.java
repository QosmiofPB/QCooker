package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Location;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WalkToBank extends Node {

	private Fish food;
	private Location location;

	// So it doesn't go to the same bank everytime....

	public WalkToBank(ClientContext ctx, Fish food, Location location) {
		super(ctx);
		this.location = location;
		this.food = food;
	}

	@Override
	public boolean activate() {
		return ctx.players.local().idle()
				&& ctx.backpack.select().id(food.getRawId()).isEmpty()
				&& ctx.players.local().animation() == -1
				&& !ctx.bank.opened() && !ctx.bank.inViewport();
	}

	@Override
	public void execute() {;
		ctx.movement.step(new Tile(location.getBankX(), location
				.getBankY(), location.getBankZ()));
//		ctx.camera.turnTo(ctx.bank.getNearest());
		System.out.println(location.getBankX() + "," + location.getBankY()
				+ "," + location.getBankZ());
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.bank.inViewport();
			}
		}, 1000, 4);
	}

}
