package org.qosmiof2.cooker.nodes.antiban;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.nodes.framework.Node;

public class LogOut extends Node {

	private int rawFood;
	private Fish food;
	
	public LogOut(MethodContext ctx, Fish food) {
		super(ctx);
		this.food = food;
	}

	@Override
	public boolean activate() {
		rawFood = food.getRawId();
		return ctx.bank.select().id(rawFood).isEmpty()
				&& ctx.backpack.select().id(rawFood).isEmpty()
				&& ctx.bank.isOpen()
				&& ctx.players.local().getAnimation() == -1;
	}

	@Override
	public void execute() {
		System.out.println("Logging out!!");
		ctx.game.logout(true);
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.game.isLoggedIn();
			}
		}, 1000, 5);
		
	}

}
