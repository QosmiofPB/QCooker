package org.qosmiof2.cooker.nodes.cooking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Tile;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Location;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WalkToRange extends Node {

	private Fish food;
	private Location location;
	private int rangeId = 2772;
	private int rawFood;

	public WalkToRange(MethodContext ctx, Fish food, Location location) {
		super(ctx);
		this.food = food;
		this.location = location;
	}

	@Override
	public boolean activate() {
		rawFood = food.getRawId();
		final GameObject range = ctx.objects.select().id(rangeId).nearest()
				.first().poll();
		return ctx.players.local().getAnimation() == -1
				&& !range.isInViewport()
				&& !ctx.backpack.select().id(rawFood).isEmpty();
	}

	@Override
	public void execute() {
		final GameObject range = ctx.objects.select().id(rangeId).first()
				.poll();
		QCooker.setStatus("Walking to range...");
		ctx.movement.stepTowards(new Tile((location.getX() +- Random.nextInt(1, 5)), (location.getY() +- Random.nextInt(1, 5)), location.getZ()));
		Condition.wait(new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return range.isInViewport() && ctx.movement.getDistance(range, ctx.players.local().getLocation()) <= 10;
			}
		}, 1000, 2);
		
		if(!range.isOnScreen()){
			ctx.camera.turnTo(range);
			Condition.wait(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return range.isOnScreen();
				}
			}, 500, 2);
		}
	}
}
