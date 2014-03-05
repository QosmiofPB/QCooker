package org.qosmiof2.cooker.nodes.cooking;

import java.util.concurrent.Callable;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.qosmiof2.cooker.nodes.framework.Node;

public class PressButton extends Node{

	public PressButton(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.widgets.get(1370, 38).isVisible()
				&& ctx.players.local().getAnimation() == -1;
	}

	@Override
	public void execute() {
		ctx.widgets.get(1370, 38).click();
		Condition.wait(new Callable<Boolean>() {
			public Boolean call() throws Exception {
				return ctx.widgets.get(1251, 11).isVisible();
			}
		}, 1000, 5);
		
	}

}
