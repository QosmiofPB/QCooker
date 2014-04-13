package org.qosmiof2.cooker.data;

import java.util.TimerTask;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Skills;
import org.qosmiof2.cooker.QCooker;
import org.qosmiof2.cooker.gui.InfoGui;

public class Update extends TimerTask {

	private ClientContext ctx;
	private QCooker qc;

	public Update(ClientContext ctx, QCooker qc) {
		this.ctx = ctx;
		this.qc = qc;
	}

	private int exp, lvl, cooked, burnt, startExp, startLvl, cookedPrice,
			rawPrice = 0;
	private InfoGui gui = new InfoGui(ctx);
	private String time;
	private long runTime;

	public void setStartExp(int startExp) {
		this.startExp = startExp;
	}

	public void setCookedPrice(int cookedPrice) {
		this.cookedPrice = cookedPrice;
	}

	public void setRawPrice(int rawPrice) {
		this.rawPrice = rawPrice;
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

	public void setRunTime(String time) {
		this.time = time;
	}

	@Override
	public void run() {
		exp = (ctx.skills.experience(Skills.COOKING) - startExp);
		lvl = (ctx.skills.realLevel(Skills.COOKING) - startLvl);
		gui.setExp(exp);
		gui.setLvl(ctx.skills.realLevel(Skills.COOKING), lvl);
		gui.setCooked(cooked);
		runTime = qc.getTime();
		time = formatTime(runTime);
		gui.setTimeRunning(time);
		gui.setProfit((cooked * cookedPrice) - (cooked * rawPrice));
		gui.setBurnt(burnt);
		gui.updateStats();
	}

	private String formatTime(final long time) {
		final int sec = (int) (time / 1000), hour = sec / 3600, minute = sec / 60 % 60, s = sec % 60;
		return (hour < 10 ? "0" + hour : hour) + ":"
				+ (minute < 10 ? "0" + minute : minute) + ":"
				+ (s < 10 ? "0" + s : s);
	}
}
