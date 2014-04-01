package org.qosmiof2.cooker.data;

import java.util.Timer;
import java.util.TimerTask;

import org.powerbot.script.rt6.*;
import org.qosmiof2.cooker.gui.InfoGui;

public class Update extends TimerTask {

	private ClientContext ctx;

	public Update(ClientContext ctx) {
		this.ctx = ctx;
	}

	private int exp = 0;
	private int lvl = 0;
	public int cooked = 0;
	public int burnt = 0;
	public int startExp = 0;
	public int startLvl = 0;
	private InfoGui gui = new InfoGui(ctx);
	private Timer runTime = new Timer();

	public void setStartExp(int startExp) {
		this.startExp = startExp;
	}

	public void setStartLvl(int startLvl) {
		this.startLvl = startLvl;
	}

	public void setCooked(int cooked) {
		this.cooked = cooked;
	}

	public void setBurnt(int burnt) {
		this.burnt = burnt;
	}

	@Override
	public void run() {
		exp = (ctx.skills.experience(Skills.COOKING) - startExp);
		lvl = (ctx.skills.realLevel(Skills.COOKING) - startLvl);
		gui.setExp(exp);
		gui.setLvl(ctx.skills.realLevel(Skills.COOKING), lvl);
		gui.setCooked(cooked);
		gui.setTimeRunning(runTime.toString());
		gui.setBurnt(burnt);
		gui.updateStats();
	}
}
