package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class CloseBank extends Node {

	private Fish food;
	private Gui gui;

	private int id;

	public CloseBank(ClientContext ctx, Fish food, Gui gui) {
		super(ctx);
		this.food = food;
		this.gui = gui;
	}

	@Override
	public boolean activate() {
		id = food.getRawId();
		return ctx.bank.opened() && ctx.players.local().animation() == -1
				&& !ctx.players.local().inMotion()
				&& !ctx.backpack.select().id(id).isEmpty();
	}

	@Override
	public void execute() {
		ctx.bank.close();
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.bank.opened();
			}
		}, 500, 2);

	}

}
