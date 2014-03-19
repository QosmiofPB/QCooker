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
import org.qosmiof2.cooker.nodes.framework.Node;

public class WalkToRange extends Node {

	private Fish food;
	private Location location;
	private int rangeId = 2772;

	public WalkToRange(MethodContext ctx, Fish food, Location location) {
		super(ctx);
		this.food = food;
		this.location = location;
	}

	@Override
	public boolean activate() {
		final GameObject range = ctx.objects.select().id(rangeId).nearest()
				.first().poll();
		return ctx.players.local().getAnimation() == -1
				&& !range.isInViewport()
				&& !ctx.backpack.select().id(food.getRawId()).isEmpty()
				&& ctx.players.local().isIdle()
				&& ctx.movement.getDistance(range, ctx.players.local().getLocation()) > 5;
	}

	@Override
	public void execute() {
		final GameObject range = ctx.objects.select().id(rangeId).first()
				.poll();
		QCooker.setStatus("Walking to range...");
		ctx.movement.stepTowards(new Tile((location.getX() +- Random.nextInt(1, 5)), (location.getY() +- Random.nextInt(1, 5)), location.getZ()));
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return range.isInViewport() && ctx.movement.getDistance(range, ctx.players.local().getLocation()) <= 10;
			}
		}, 1000, 2);
		
		if(!range.isInViewport()){
			ctx.camera.turnTo(range);
			ctx.camera.setPitch(Random.nextInt(80, 99));
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return range.isInViewport();
				}
			}, 500, 2);
		}
	}
}
