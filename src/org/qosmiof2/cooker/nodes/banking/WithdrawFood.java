package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WithdrawFood extends Node {

	private Fish food;
	public WithdrawFood(MethodContext ctx, Fish food) {
		super(ctx);
		this.food = food;
	}

	private int rawFood;

	@Override
	public boolean activate() {
		rawFood = food.getRawId();
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

	}

}
