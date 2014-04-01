package org.qosmiof2.cooker.nodes.framework;

import org.powerbot.script.rt6.*;

public abstract class Node extends ClientAccessor {

	public Node(ClientContext ctx) {
		super(ctx);
	}

	public abstract boolean activate();

	public abstract void execute();

}