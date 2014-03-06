package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WithdrawFood extends Node {

	public WithdrawFood(MethodContext ctx) {
		super(ctx);
	}

	private int rawFood;

	@Override
	public boolean activate() {
		rawFood = Gui.food.getRawId();
		return ctx.players.local().getAnimation() == -1 && ctx.bank.isOpen()
				&& ctx.backpack.select().isEmpty();
	}

	@Override
	public void execute() {
		if (ctx.backpack.select().id(rawFood).isEmpty()) {
			ctx.bank.withdraw(rawFood, org.powerbot.script.methods.Bank.Amount.ALL);
			Condition.wait(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return !ctx.backpack.select().id(rawFood).isEmpty();
				}
			}, 500, 2);
		}

		if (!ctx.backpack.select().id(rawFood).isEmpty()) {
			QCooker.setFishLeft(ctx.bank.select().id(rawFood).count(true));
			ctx.bank.close();
			Condition.wait(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return !ctx.bank.isOpen();
				}
			}, 500, 2);
		}

	}

}
