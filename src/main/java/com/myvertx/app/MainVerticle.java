package com.myvertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {
	@Override
	public void start() throws Exception 
	{
		//Utiliser cluster
		//Vertx.vertx().deployVerticle("com.myvertx.app.AppDB");
	}

	@Override
	public void stop() throws Exception 
	{
		
	}
}
