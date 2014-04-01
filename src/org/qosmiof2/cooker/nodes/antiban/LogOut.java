package org.qosmiof2.cooker.nodes.antiban;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.*;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.nodes.framework.Node;

public class LogOut extends Node {

	private int rawFood;
	private Fish food;

	public LogOut(ClientContext ctx, Fish food) {
		super(ctx);
		this.food = food;
	}

	@Override
	public boolean activate() {
		rawFood = food.getRawId();
		return ctx.bank.opened()
				&& ctx.backpack.select().id(rawFood).isEmpty()
				&& ctx.bank.opened()
				&& ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		System.out.println("Logging out!!");
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return !ctx.game.loggedIn();
			}
		}, 1000, 5);

	}

}
