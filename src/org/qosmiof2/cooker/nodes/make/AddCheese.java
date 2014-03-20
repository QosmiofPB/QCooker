package org.qosmiof2.cooker.nodes.make;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.Item;
import org.qosmiof2.cooker.data.Other;
import org.qosmiof2.cooker.nodes.framework.Node;

public class AddCheese extends Node {

	private Other other;
	private int pizzaBaseId, tomatoId, cheeseId, incompletePizzaId;
	private Component buttonComponent = ctx.widgets.get(1370, 38);

	

	public AddCheese(MethodContext ctx, Other other) {
		super(ctx);
		this.other = other;
	}

	@Override
	public boolean activate() {
		pizzaBaseId = other.getPizzaBaseId();
		tomatoId = other.getTomatoId();
		cheeseId = other.getCheeseId();
		incompletePizzaId = 2285;
		return !ctx.bank.isOpen()
				&& ctx.backpack.select().id(pizzaBaseId).isEmpty()
				&& ctx.backpack.select().id(tomatoId).isEmpty()
				&& !ctx.backpack.select().id(cheeseId).isEmpty()
				&& !ctx.backpack.select().id(incompletePizzaId).isEmpty();
	}

	@Override
	public void execute() {

		final Item incompletePizza = ctx.backpack.select().id(incompletePizzaId).first().poll();
		final Item cheese = ctx.backpack.select().id(cheeseId).first().poll();
		
		if (incompletePizza.interact("Use")) {
			if (cheese.interact("Use")) {
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return buttonComponent.isVisible();
					}
				}, 500, 3);
				buttonComponent.click();
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ctx.backpack.select().id(incompletePizza)
								.isEmpty()
								&& ctx.backpack.select().id(cheeseId).isEmpty();
					}
				}, 1000, 9);
			}
		}

	}

}
