package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.nodes.framework.Node;

public class OpenBank extends Node {

	private int rawFood;
	private Fish food;

	public OpenBank(MethodContext ctx, Fish food) {
		super(ctx);
		this.food = food;
	}

	@Override
	public boolean activate() {
		rawFood = food.getRawId();
		return ctx.backpack.select().id(rawFood).first().isEmpty()
				&& ctx.players.local().isIdle()
				&& ctx.players.local().getAnimation() == -1
				&& !ctx.bank.isOpen();
	}

	@Override
	public void execute() {

		if (ctx.bank.isInViewport()) {
			QCooker.setStatus("Opening bank...");
			if (!ctx.bank.isOpen()) {
				ctx.bank.open();
				Condition.wait(new Callable<Boolean>() {
					public Boolean call() throws Exception {
						return ctx.bank.isOpen();
					}
				}, 500, 2);
			}

		} else {
			QCooker.setStatus("Walking to bank...");
			ctx.movement.stepTowards(ctx.bank.getNearest().getLocation());
			Condition.wait(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return ctx.bank.isInViewport();
				}
			}, 1000, 2);
		}

	}

}
