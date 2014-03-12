package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.nodes.framework.Node;

public class DepositInventory extends Node{


	private int rawFood;
	private Fish food;
	
	public DepositInventory(MethodContext ctx, Fish food) {
		super(ctx);
		this.food = food;
	}

	@Override
	public boolean activate() {
		rawFood = food.getRawId();
		return ctx.players.local().getAnimation() == -1
				&& ctx.bank.isOpen()
				&& !ctx.backpack.select().isEmpty()
				&& ctx.backpack.select().id(rawFood).isEmpty();
	}

	@Override
	public void execute() {
		if(ctx.backpack.select().id(rawFood).isEmpty()){
			ctx.bank.depositInventory();
			Condition.wait(new Callable<Boolean>() {
				public Boolean call() throws Exception {
					return ctx.backpack.select().isEmpty();
				}
			}, 500, 2);
		}
		
	}

}
