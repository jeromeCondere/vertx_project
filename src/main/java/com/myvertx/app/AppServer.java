package com.myvertx.app;

import io.vertx.core.AbstractVerticle;

public class AppServer extends AbstractVerticle {
	@Override
    public void start() throws Exception {
    
        vertx.createHttpServer() 
        .requestHandler(req -> req.response() 
            .putHeader("content-type", "text/html") 
            .end("<html><body><h1>AppB2</h1></body></html>"))
        .listen(8080);
    }
}
