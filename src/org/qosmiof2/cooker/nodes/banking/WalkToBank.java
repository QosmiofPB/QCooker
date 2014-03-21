package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Tile;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Location;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WalkToBank extends Node {

	private Fish food;
	private Location location;

	// So it doesn't go to the same bank everytime....

	public WalkToBank(MethodContext ctx, Fish food, Location location) {
		super(ctx);
		this.location = location;
		this.food = food;
	}

	@Override
	public boolean activate() {
		return ctx.players.local().isIdle()
				&& ctx.backpack.select().id(food.getRawId()).isEmpty()
				&& ctx.players.local().getAnimation() == -1
				&& !ctx.bank.isOpen() && !ctx.bank.isInViewport();
	}

	@Override
	public void execute() {
		QCooker.status = "Walking to bank...";
		ctx.movement.stepTowards(new Tile(location.getBank_X(), location
				.getBank_Y(), location.getBank_Z()));
//		ctx.camera.turnTo(ctx.bank.getNearest());
		System.out.println(location.getBank_X() + "," + location.getBank_Y()
				+ "," + location.getBank_Z());
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.bank.isInViewport();
			}
		}, 1000, 4);
	}

}
