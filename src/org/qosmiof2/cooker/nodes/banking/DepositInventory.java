package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Other;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class DepositInventory extends Node{

	private Fish food;
	private Other other;
	private Gui gui;
	private int id;
	
	public DepositInventory(MethodContext ctx, Fish food, Other other, Gui gui) {
		super(ctx);
		this.other = other;
		this.food = food;
		this.gui = gui;
	}

	@Override
	public boolean activate() {
		if(gui.makingPizza){
			id = other.getId();
		} else {
			id = food.getRawId();
		}
		return ctx.players.local().getAnimation() == -1
				&& ctx.bank.isOpen()
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
