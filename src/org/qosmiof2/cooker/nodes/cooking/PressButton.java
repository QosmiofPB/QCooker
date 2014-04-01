package org.qosmiof2.cooker.nodes.cooking;

import java.util.concurrent.Callable;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.qosmiof2.cooker.nodes.framework.Node;

public class PressButton extends Node{

	private Component buttonComponent = ctx.widgets.component(1370, 38);
	private Component cookingComponent = ctx.widgets.component(1251, 11);
	
	public PressButton(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return buttonComponent.visible()
				&& ctx.players.local().animation() == -1;
	}

	@Override
	public void execute() {
		buttonComponent.click();
		Condition.wait(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return cookingComponent.visible();
			}
		}, 1000, 5);
		
	}

}
