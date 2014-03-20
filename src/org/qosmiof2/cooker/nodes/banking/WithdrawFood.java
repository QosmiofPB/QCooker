package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Other;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class WithdrawFood extends Node {

	private Fish food;
	private Gui gui;
	private Other other;

	public WithdrawFood(MethodContext ctx, Fish food, Gui gui, Other other) {
		super(ctx);
		this.food = food;
		this.gui = gui;
		this.other = other;
	}

	private int rawFood, pizzaBaseId, tomatoId, cheeseId, id;

	@Override
	public boolean activate() {
		rawFood = food.getRawId();
		pizzaBaseId = other.getPizzaBaseId();
		tomatoId = other.getTomatoId();
		cheeseId = other.getCheeseId();
		return ctx.players.local().getAnimation() == -1 && ctx.bank.isOpen()
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
			QCooker.setStatus("Closing bank...");
			ctx.bank.close();
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ctx.bank.isOpen();
				}
			}, 500, 2);
		}

	}

	private void withdrawRawFood() {
		ctx.bank.withdraw(rawFood, org.powerbot.script.methods.Bank.Amount.ALL);
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.backpack.select().id(rawFood).isEmpty();
			}
		}, 500, 2);
		if (!ctx.backpack.select().id(rawFood).isEmpty()) {
			QCooker.setStatus("Closing bank...");
			ctx.bank.close();
			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return !ctx.bank.isOpen();
				}
			}, 500, 2);
		}
	}

}
