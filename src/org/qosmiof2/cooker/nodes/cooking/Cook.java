package org.qosmiof2.cooker.nodes.cooking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Item;
import org.powerbot.script.wrappers.Widget;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class Cook extends Node {

	private int rawFood;
	private int rangeId = 2772;
	private Component buttonComponent = ctx.widgets.get(1370, 38);

	public Cook(MethodContext ctx) {
		super(ctx);
	}
	
	@Override
	public boolean activate() {
		final GameObject range = ctx.objects.select().id(rangeId).nearest().first()
				.poll();
		rawFood = Gui.food.getRawId();
		return !ctx.backpack.select().id(rawFood).isEmpty()
				&& ctx.players.local().getAnimation() == -1
				&& ctx.players.local().isIdle()
				&& !ctx.objects.select().id(range).isEmpty()
				&& !ctx.widgets.get(1370, 38).isVisible()
				&& !ctx.widgets.get(1251, 11).isVisible()
				&& range.isInViewport();
	}

	@Override
	public void execute() {
		final GameObject range = ctx.objects.select().id(rangeId).nearest().first()
				.poll();
		if (range.isOnScreen()) {
			QCooker.setStatus("Cooking...");
			final Item food = ctx.backpack.select().id(rawFood).first().poll();

			if (food.interact("Use") && !buttonComponent.isVisible()) {
				if (range.interact("Use") && !buttonComponent.isVisible()) {
					Condition.wait(new Callable<Boolean>() {
						public Boolean call() throws Exception {
							return buttonComponent.isVisible();
						}
					}, 1000, 2);

				}
			}
		} else {
			ctx.camera.turnTo(range);
			Condition.wait(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return range.isInViewport();
				}
			}, 500, 2);
			}
		}
}
