package com.myvertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.*;

public class MongoUser extends AbstractVerticle {
	final public void start() throws Exception 
	{
		vertx.eventBus().send("hello", "iedjf");
	}
}
