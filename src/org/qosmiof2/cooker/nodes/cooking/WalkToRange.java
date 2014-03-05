package org.qosmiof2.cooker.nodes.cooking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Tile;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WalkToRange extends Node {

	private Tile rangeTile = new Tile(3274, 3183, 0);
	private int rangeId = 2772;

	public WalkToRange(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		final GameObject range = ctx.objects.select().nearest().id(rangeId)
				.poll();
		return ctx.players.local().getAnimation() == -1
				&& !range.isInViewport();
	}

	@Override
	public void execute() {
		final GameObject range = ctx.objects.select().nearest().id(rangeId)
				.poll();
		QCooker.setStatus("Walking to range...");
		ctx.movement.stepTowards(rangeTile);
		Condition.wait(new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return range.isInViewport();
			}
		}, 1000, 3);
	}
}
