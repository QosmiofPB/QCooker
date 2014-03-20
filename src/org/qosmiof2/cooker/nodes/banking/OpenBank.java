package org.qosmiof2.cooker.nodes.banking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.data.Fish;
import org.qosmiof2.cooker.data.Location;
import org.qosmiof2.cooker.data.Other;
import org.qosmiof2.cooker.gui.Gui;
import org.qosmiof2.cooker.nodes.framework.Node;

public class OpenBank extends Node {

	private int rawFood;
	private Other other;
	private Fish food;
	private Gui gui;
	private Location location;

	public OpenBank(MethodContext ctx, Fish food, Location location, Other other, Gui gui) {
		super(ctx);
		this.food = food;
		this.location = location;
		this.other = other;
		this.gui = gui;
	}

	@Override
	public boolean activate() {
		rawFood = food.getRawId();
		if(gui.makingPizza){
			
		}
		return ctx.backpack.select().id(rawFood).first().isEmpty()
				&& ctx.players.local().isIdle()
				&& ctx.players.local().getAnimation() == -1
				&& !ctx.bank.isOpen()
				&& ctx.bank.isInViewport()
				&& ctx.backpack.select().id(other.getTomatoId()).isEmpty()
				&& ctx.backpack.select().id(other.getCheeseId()).isEmpty()
				&& ctx.backpack.select().id(other.getPizzaBaseId()).isEmpty()
				&& ctx.movement.getDistance(ctx.bank.getNearest(), ctx.players
						.local().getLocation()) < 5;
	}

	@Override
	public void execute() {

		if (ctx.bank.isInViewport()) {
			QCooker.setStatus("Opening bank...");
			if (!ctx.bank.isOpen()) {
				ctx.bank.open();
				Condition.wait(new Callable<Boolean>() {
					@Override
					public Boolean call() throws Exception {
						return ctx.bank.isOpen();
					}
				}, 500, 2);
			}

		}

	}

}
