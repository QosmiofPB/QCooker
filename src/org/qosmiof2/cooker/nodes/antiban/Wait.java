package org.qosmiof2.cooker.nodes.antiban;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Random;
import org.qosmiof2.cooker.nodes.framework.Node;

public class Wait extends Node {

	public Wait(MethodContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.widgets.get(1251, 11).isVisible()
				&& ctx.players.local().getAnimation() == -1
				&& ctx.players.local().isIdle();
	}

	@Override
	public void execute() {
		do {
			switch (Random.nextInt(0, 25)) {

			default:
				sleep(700, 2000);
				break;

			case 1: // Moves mouse randomly
				ctx.mouse.move(Random.nextInt(0, 800), Random.nextInt(0, 600));
				break;

			case 18:
				ctx.camera.setAngle(Random.nextInt(1, 99));
				break;

			case 5:
				ctx.mouse.move(0, Random.nextInt(0, 600));
				sleep(1000, 10000);
				break;

			case 15: // Looks like you opened another tab/program/skype/etc...
				ctx.mouse.move(Random.nextInt(0, 800), 0);
				sleep(1000, 10000);
				break;

			case 7:// Looks like you opened another tab/program/skype/etc...
				ctx.mouse.move(Random.nextInt(0, 800), 600);
				sleep(1000, 2000);
				break;

			}
		} while (ctx.widgets.get(1251, 11).isVisible());

	}

}
