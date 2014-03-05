package org.qosmiof2.cooker.nodes.cooking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Item;
import org.powerbot.script.wrappers.Tile;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class Cook extends Node {

	private Gui gui;
	private int rawFood;
	private int rangeId = 2772;
	private Tile rangeTile = new Tile(3274, 3183, 0);

	public Cook(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		final GameObject range = ctx.objects.select().id(rangeId).nearest().poll();
		rawFood = Gui.food.getRawId();
		return !ctx.backpack.select().id(rawFood).isEmpty()
				&& ctx.players.local().getAnimation() == -1
				&& ctx.players.local().isIdle()
				&& !ctx.objects.select().id(range).isEmpty()
				&& !ctx.widgets.get(1370, 38).isVisible()
				&& !ctx.widgets.get(1251, 11).isVisible();
	}

	@Override
	public void execute() {
		QCooker.setStatus("Cooking...");
		final GameObject range = ctx.objects.select().nearest().id(rangeId)
				.poll();
		if (range.isInViewport() && !ctx.widgets.get(1370, 38).isVisible()) {
			final Item food = ctx.backpack.select().id(rawFood).first().poll();

			if (food.interact("Use") && !ctx.widgets.get(1370, 38).isVisible()) {
				if (range.interact("Use")
						&& !ctx.widgets.get(1370, 38).isVisible()) {
					Condition.wait(new Callable<Boolean>() {
						public Boolean call() throws Exception {
							return ctx.widgets.get(1370, 38).isVisible();
						}
					}, 1000, 2);

					ctx.widgets.get(1370, 38).click();
					Condition.wait(new Callable<Boolean>() {
						public Boolean call() throws Exception {
							return ctx.widgets.get(1251, 11).isVisible();
						}
					}, 1000, 5);
				}
			}
		} else { // walks to range
			QCooker.setStatus("Walking...");
			ctx.movement.stepTowards(rangeTile);
			Condition.wait(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return range.isInViewport();
				}
			}, 1000, 3);
		}
	}
}
