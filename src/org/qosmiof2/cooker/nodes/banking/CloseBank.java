package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Other;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class CloseBank extends Node {

	private Fish food;
	private Gui gui;
	private Other other;

	private int id;
	
	public CloseBank(MethodContext ctx, Fish food, Gui gui, Other other) {
		super(ctx);
		this.food = food;
		this.gui = gui;
		this.other = other;
	}

	@Override
	public boolean activate() {
		if (gui.makingPizza) {
			id = other.getTomatoId();
		} else {
			id = food.getRawId();
		}
			return ctx.bank.isOpen() && ctx.players.local().getAnimation() == -1
	
			&& ctx.players.local().isIdle()
				&& !ctx.backpack.select().id(id).isEmpty();
	}

	@Override
	public void execute() {
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
