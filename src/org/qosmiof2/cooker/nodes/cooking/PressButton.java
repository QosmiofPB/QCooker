package org.qosmiof2.cooker.nodes.cooking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;
import org.qosmiof2.cooker.nodes.framework.Node;

public class PressButton extends Node{

	private Component buttonComponent = ctx.widgets.get(1370, 38);
	private Component cookingComponent = ctx.widgets.get(1251, 11);
	
	public PressButton(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return buttonComponent.isVisible()
				&& ctx.players.local().getAnimation() == -1;
	}

	@Override
	public void execute() {
		buttonComponent.click();
		Condition.wait(new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return cookingComponent.isVisible();
			}
		}, 1000, 5);
		
	}

}
