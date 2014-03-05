package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.Node;

public class CloseBank extends Node {

	private int rawFood;

	public CloseBank(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		rawFood = Gui.food.getRawId();
		return ctx.bank.isOpen() && ctx.players.local().getAnimation() == -1
				&& !ctx.backpack.select().id(rawFood).isEmpty();
	}

	@Override
	public void execute() {
		if (!ctx.backpack.select().id(rawFood).isEmpty()) {
			ctx.bank.close();
			Condition.wait(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return !ctx.bank.isOpen();
				}
			}, 500, 2);
		}

	}

}
