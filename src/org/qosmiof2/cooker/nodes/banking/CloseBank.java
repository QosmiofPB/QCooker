package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.nodes.framework.Node;

public class CloseBank extends Node {

	private Fish food;

	public CloseBank(MethodContext ctx, Fish food) {
		super(ctx);
		this.food = food;
	}

	@Override
	public boolean activate() {
		// TODO Auto-generated method stub
		return ctx.bank.isOpen()
				&& ctx.players.local().getAnimation() == -1
				&& ctx.players.local().isIdle()
				&& !ctx.backpack.select().id(food.getRawId()).isEmpty();
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
