package com.myvertx.app;
import io.vertx.core.AbstractVerticle;

public class App extends AbstractVerticle
{
	@Override
	public void start() throws Exception 
	{
	    
	}

	@Override
	public void stop() throws Exception 
	{
		System.out.println("Stop of Cat Verticle");
	}
}
