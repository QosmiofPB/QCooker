package org.qosmiof2.cooker.nodes.antiban;

import org.powerbot.script.Random;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.qosmiof2.cooker.nodes.framework.Node;

public class Wait extends Node {

	public Wait(ClientContext ctx) {
		super(ctx);
	}

	private Component cookingComponent = ctx.widgets.component(1251, 11);

	@Override
	public boolean activate() {
		return cookingComponent.visible()
				&& ctx.players.local().animation() == 896;
	}

	@Override
	public void execute() {
		do {
//			switch (Random.nextInt(0, 25)) {
//
//			case 1: // Moves mouse randomly
//				ctx.mouse.move(Random.nextInt(0, 800), Random.nextInt(0, 600));
//				break;
//
//			case 18:
//				ctx.camera.setPitch(Random.nextInt(70, 99));
//				break;
//
//			case 5:
//				ctx.mouse.move(0, Random.nextInt(0, 600));
//				sleep(1000, 10000);
//				break;
//
//			case 15: // Looks like you opened another tab/program/skype/etc...
//				ctx.mouse.move(Random.nextInt(0, 800), 0);
//				sleep(1000, 10000);
//				break;
//
//			case 7:// Looks like you opened another tab/program/skype/etc...
//				ctx.mouse.move(Random.nextInt(0, 800), 600);
//				sleep(1000, 2000);
//				break;
//				
//			case 14:
//				ctx.camera.setAngle(Random.nextInt(30, 90));
//				break;
//
//			default:
//				sleep(700, 2000);
//				break;
//
//			}
		} while (ctx.widgets.component(1251, 11).visible());

	}
}
