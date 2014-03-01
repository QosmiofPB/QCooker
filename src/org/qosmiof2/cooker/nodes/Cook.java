package org.qosmiof2.cooker.nodes;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Item;
import org.qosmiof2.cooker.QCooker;

public class Cook extends Node {

	private QCooker qc;
	private int raw_food;

	public Cook(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		final GameObject fire = ctx.objects.select().nearest().id(1).first()
				.poll();
		raw_food = qc.food.getRawId();
		return !ctx.backpack.select().id(raw_food).isEmpty()
				&& ctx.players.local().getAnimation() == -1
				&& ctx.players.local().isIdle()
				&& !ctx.objects.select().id(fire).isEmpty();
	}

	@Override
	public void execute() {
		final GameObject fire = ctx.objects.select().nearest().id(1).poll();
		if (fire.isInViewport()) {
			for (final Item food : ctx.backpack.select().id(qc.food.getRawId())) {
				food.interact("Use");

				Condition.wait(new Callable<Boolean>() {
					public Boolean call() throws Exception {
						return food.interact("Use");
					}
				}, 500, 5);

				fire.interact("Use");
				Condition.wait(new Callable<Boolean>() {
					public Boolean call() throws Exception {
						return fire.interact("Use")
								&& ctx.widgets.get(1370, 38).isVisible();
					}
				}, 500, 5);
			}

			if (ctx.widgets.get(1370, 38).isVisible()) {
				ctx.widgets.get(1370, 38).click();
				Condition.wait(new Callable<Boolean>() {
					public Boolean call() throws Exception {
						return ctx.widgets.get(1370, 38).click();
					}
				}, 1000, 5);
			}

			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ctx.widgets.get(1251, 11).isVisible();
				}
			}, 1000, 70);

		}

	}
}
