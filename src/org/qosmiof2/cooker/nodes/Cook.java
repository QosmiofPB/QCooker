package org.qosmiof2.cooker.nodes;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Item;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.gui.Gui;

public class Cook extends Node {

	private Gui gui;
	private QCooker qc;
	private int rawFood;
	private int rangeId = 2772;

	public Cook(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		final GameObject range = ctx.objects.select().nearest().id(rangeId)
				.first().poll();
		rawFood = gui.food.getRawId();
		return !ctx.backpack.select().id(rawFood).isEmpty()
				&& ctx.players.local().getAnimation() == -1
				&& ctx.players.local().isIdle()
				&& !ctx.objects.select().id(range).isEmpty();
	}

	@Override
	public void execute() {
		qc.status = "Cooking food...";
		final GameObject range = ctx.objects.select().nearest().id(rangeId)
				.poll();
		if (range.isInViewport()) {
			final Item food = ctx.backpack.select()
					.id(gui.food.getRawId()).first().poll();

				if (food.interact("Use")) {
					if (range.interact("Use")) {
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
			} else { //walks to range
				qc.status = "Walking...";
				ctx.movement.stepTowards(range.getLocation());
				Condition.wait(new Callable<Boolean>() {
					public Boolean call() throws Exception {
						return range.isInViewport();
					}
				}, 1000, 3);
			}
		}
	}
