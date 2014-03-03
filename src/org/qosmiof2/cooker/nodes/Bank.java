package org.qosmiof2.cooker.nodes;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.gui.Gui;

public class Bank extends Node {

	private QCooker qc;
	private int rawFood;

	public Bank(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		rawFood = Gui.food.getRawId();

		return ctx.backpack.select().id(rawFood).first().isEmpty()
				&& ctx.players.local().isIdle()
				&& ctx.players.local().getAnimation() == -1;
	}

	@Override
	public void execute() {
		qc.status = "Banking";
		if (ctx.bank.isInViewport()) {
			if (ctx.bank.isOpen()) {
				if (ctx.bank.depositInventory()) {

					Condition.wait(new Callable<Boolean>() {
						public Boolean call() throws Exception {
							return ctx.backpack.select().isEmpty();
						}
					}, 700, 2);

					if (ctx.backpack.isEmpty()) {
						if (ctx.bank.withdraw(rawFood, 28)) {
							Condition.wait(new Callable<Boolean>() {
								public Boolean call() throws Exception {
									return !ctx.backpack.select().id(rawFood)
											.isEmpty();
								}
							}, 1000, 2);
							if (!ctx.backpack.select().id(rawFood).isEmpty()) {
								qc.fishLeft = ctx.bank.id(rawFood).count();
								ctx.bank.close();

								Condition.wait(new Callable<Boolean>() {
									public Boolean call() throws Exception {
										return !ctx.bank.isOpen();
									}
								}, 800, 1);

							}
						}
					} else { // Deposits inventory if its not empty
						ctx.bank.depositInventory();
						Condition.wait(new Callable<Boolean>() {
							public Boolean call() throws Exception {
								return ctx.backpack.select().isEmpty();
							}
						}, 800, 2);
					}
				}
			} else { // Opens bank if its not open.
				ctx.bank.open();
				Condition.wait(new Callable<Boolean>() {
					public Boolean call() throws Exception {
						return ctx.bank.isOpen();
					}
				}, 500, 3);
			}
		} else { // Walks to bank if its not visible.
			qc.status = "Walking to bank...";
			ctx.movement.stepTowards(ctx.bank.getNearest().getLocation());
			Condition.wait(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return ctx.bank.isInViewport();
				}
			}, 1000, 10);
		}

	}

}
