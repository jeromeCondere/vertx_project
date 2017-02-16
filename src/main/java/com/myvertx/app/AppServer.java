package com.myvertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class AppServer extends AbstractVerticle {
	@Override
    public void start() throws Exception 
	{
    
        vertx.createHttpServer() 
        .requestHandler(req -> req.response() 
            .putHeader("content-type", "text/html") 
            .end("<html><body><h1>Acceuil</h1></body></html>"))
        .listen(8080);
    }
	
	@Override
	public void stop(Future<Void> stopFuture) 
	{
		stopFuture.complete();
	}
}
