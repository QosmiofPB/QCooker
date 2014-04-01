package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.Bank.Amount;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WithdrawFood extends Node {

	private Fish food;
	private Gui gui;

	public WithdrawFood(ClientContext ctx, Fish food, Gui gui) {
		super(ctx);
		this.food = food;
		this.gui = gui;
	}

	private int rawFood, pizzaBaseId, tomatoId, cheeseId;

	@Override
	public boolean activate() {
		rawFood = food.getRawId();
		return ctx.players.local().animation() == -1 && ctx.bank.opened()
				&& ctx.backpack.select().isEmpty();
	}

	@Override
	public void execute() {
		if (gui.makingPizza) {
			withdrawStuffMake();
		} else {
			withdrawRawFood();
		}

	}

	private void withdrawStuffMake() {
		ctx.bank.withdraw(pizzaBaseId, 9);
		ctx.bank.withdraw(cheeseId, 9);
		ctx.bank.withdraw(tomatoId, 9);
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.backpack.select().id(pizzaBaseId).isEmpty()
						&& !ctx.backpack.select().id(tomatoId).isEmpty()
						&& !ctx.backpack.select().id(cheeseId).isEmpty();
			}
		}, 500, 2);
		if (!ctx.backpack.select().id(pizzaBaseId).isEmpty()
				&& !ctx.backpack.select().id(tomatoId).isEmpty()
				&& !ctx.backpack.select().id(cheeseId).isEmpty()) {
			ctx.bank.close();
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ctx.bank.opened();
				}
			}, 500, 2);
		}

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
