package org.qosmiof2.cooker.nodes;

import org.powerbot.script.methods.MethodContext;
import org.qosmiof2.cooker.QCooker;

public class Bank extends Node {

	private QCooker qc;
	private int raw_food;
	
	public Bank(MethodContext ctx) {
		super(ctx);
		this.qc = qc;
	}

	@Override
	public boolean activate() {
		raw_food = qc.food.getRawId();
		return ctx.backpack.select().id(raw_food).isEmpty()
				&& ctx.players.local().isIdle()
				&& ctx.players.local().getAnimation() == -1;
	}

	@Override
	public void execute() {
		
	}

}
