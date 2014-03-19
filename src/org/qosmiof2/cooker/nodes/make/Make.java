package org.qosmiof2.cooker.nodes.make;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.Item;
import org.qosmiof2.cooker.data.Other;
import org.qosmiof2.cooker.nodes.framework.Node;

public class Make extends Node {

	private Other other;

	// Didn't know how to call this.... so Other haha

	private int pizzaBaseId, tomatoId, cheeseId, incompletePizzaId;
	private Component buttonComponent = ctx.widgets.get(1370, 38);

	public Make(MethodContext ctx, Other other) {
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
				&& !ctx.backpack.select().id(pizzaBaseId).isEmpty()
				&& !ctx.backpack.select().id(tomatoId).isEmpty()
				&& !ctx.backpack.select().id(cheeseId).isEmpty();
	}

	@Override
	public void execute() {
		final Item pizzaBase = ctx.backpack.select().id(pizzaBaseId).first()
				.poll();
		final Item tomato = ctx.backpack.select().id(tomatoId).first().poll();
		final Item cheese = ctx.backpack.select().id(cheeseId).first().poll();
		final Item incompletePizza = ctx.backpack.select()
				.id(incompletePizzaId).first().poll();

		if (pizzaBase.interact("Use")) {
			if (tomato.interact("Use")) {
				buttonComponent.click();
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ctx.backpack.select().id(pizzaBaseId).isEmpty()
								&& ctx.backpack.select().id(tomatoId).isEmpty();
					}
				}, 800, 9);
				if (incompletePizza.interact("Use")) {
					if (cheese.interact("Use")) {
						buttonComponent.click();
						Condition.wait(new Callable<Boolean>() {
							@Override
							public Boolean call() throws Exception {
								return ctx.backpack.select()
										.id(incompletePizza).isEmpty()
										&& ctx.backpack.select().id(cheeseId).isEmpty();
							}
						}, 800, 9);
					}
				}
			}
		}

	}

}
