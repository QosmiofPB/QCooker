package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.Bank.Amount;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WithdrawFood extends Node {

	private int rawFood;

	public WithdrawFood(ClientContext ctx, Fish food) {
		super(ctx);
		rawFood = food.getRawId();
	}

	@Override
	public boolean activate() {
		return ctx.players.local().animation() == -1 && ctx.bank.opened()
				&& ctx.backpack.select().isEmpty();
	}

	@Override
	public void execute() {
		withdrawRawFood();
	}

	private void withdrawRawFood() {
		switch (Random.nextInt(0, 10)) {
		default:
			ctx.bank.withdraw(rawFood, Amount.ALL);
			break;

		case 5:
			ctx.bank.withdraw(rawFood, 28);
			break;
		}
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.backpack.select().id(rawFood).isEmpty();
			}
		}, 500, 2);
		if (!ctx.backpack.select().id(rawFood).isEmpty()) {
			ctx.bank.close();
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ctx.bank.opened();
				}
			}, 500, 2);
		}
	}

}
