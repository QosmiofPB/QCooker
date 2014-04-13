package org.qosmiof2.cooker.nodes.cooking;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Location;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WalkToRange extends Node {

	private Fish food;
	private Location location;
	private int rangeId = 2772;

	public WalkToRange(ClientContext ctx, Fish food, Location location) {
		super(ctx);
		this.food = food;
		this.location = location;
	}

	@Override
	public boolean activate() {
		final GameObject range = ctx.objects.select().id(rangeId).nearest()
				.first().poll();
		return ctx.players.local().animation() == -1
				&& !range.inViewport()
				&& !ctx.backpack.select().id(food.getRawId()).isEmpty()
				&& ctx.players.local().idle()
				&& ctx.movement.distance(range, ctx.players.local().tile()) > 10;
	}

	@Override
	public void execute() {
		final GameObject range = ctx.objects.select().id(rangeId).nearest()
				.first().poll();
		ctx.movement.step(location.getRangeTile().derive(Random.nextInt(1, 3),
				Random.nextInt(1, 4)));

		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return range.inViewport()
						&& ctx.movement.distance(range, ctx.players.local()
								.tile()) <= 10;
			}
		}, 1000, 2);

		if (!range.inViewport()) {
			ctx.camera.turnTo(range);
			ctx.camera.pitch(Random.nextInt(80, 99));
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return range.inViewport();
				}
			}, 500, 2);
		}
	}
}
