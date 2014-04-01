package org.qosmiof2.cooker.nodes.cooking;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Item;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.nodes.framework.Node;

public class Cook extends Node {

	private int rawFood;
	private int rangeId = 2772;
	private Component buttonComponent = ctx.widgets.component(1370, 38);
	
	private Fish food;

	public Cook(ClientContext ctx, Fish food) {
		super(ctx);
		this.food = food;
	}
	
	@Override
	public boolean activate() {
		final GameObject range = ctx.objects.select().id(rangeId).nearest().first()
				.poll();
		rawFood = food.getRawId();
		return !ctx.backpack.select().id(rawFood).isEmpty()
				&& ctx.players.local().animation() == -1
				&& ctx.players.local().idle()
				&& !ctx.objects.select().id(range).isEmpty()
				&& !ctx.widgets.component(1370, 38).visible()
				&& !ctx.widgets.component(1251, 11).visible()
				&& range.inViewport();
	}

	@Override
	public void execute() {
		final GameObject range = ctx.objects.select().id(rangeId).nearest().first()
				.poll();
		if (range.inViewport()) {
			final Item food = ctx.backpack.select().id(rawFood).shuffle().poll();

			if (food.interact("Use") && !buttonComponent.visible()) {
				ctx.camera.pitch(Random.nextInt(70, 72));
				if (range.interact("Use") && !buttonComponent.visible()) {
					Condition.wait(new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return buttonComponent.visible();
						}
					}, 1000, 3);

				} else {
					ctx.camera.turnTo(range);
					ctx.camera.pitch(99);
					Condition.wait(new Callable<Boolean>() {
						@Override
						public Boolean call() throws Exception {
							return range.inViewport();
						}
					}, 1000, 2);
				}
			}
		} else {
			ctx.camera.turnTo(range);
			ctx.camera.pitch(99);
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return range.inViewport();
				}
			}, 500, 2);
			}
		}
}
