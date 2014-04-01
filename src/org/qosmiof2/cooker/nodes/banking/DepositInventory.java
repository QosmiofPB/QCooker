package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class DepositInventory extends Node {

	private Fish food;
	private int id;

	public DepositInventory(ClientContext ctx, Fish food, Gui gui) {
		super(ctx);
		this.food = food;
	}

	@Override
	public boolean activate() {
		id = food.getRawId();
		return ctx.players.local().animation() == -1 && ctx.bank.opened()
				&& !ctx.backpack.select().isEmpty()
				&& ctx.backpack.select().id(id).isEmpty();
	}

	@Override
	public void execute() {
		ctx.bank.depositInventory();
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return ctx.backpack.select().isEmpty();
			}
		}, 500, 2);

	}

}
