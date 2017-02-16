package com.myvertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class AppServer extends AbstractVerticle {
	private HttpServer server;
	@Override
    public void start() throws Exception 
	{
    
		Router router = Router.router(vertx);

	    router.route().handler(req -> {
	    	req.response().end("accueil");
	    });
	    router.get("/aliments").handler(this::handleGetAliments);
	    router.get("/aliment/:alimentID").handler(this::handleGetAlimentById);
	    router.get("/aliment/:alimentID/glucide").handler(this::handleGetAlimentGlucide);
	    router.get("/aliment/:alimentID/lipide").handler(this::handleGetAlimentLipide);
	    router.get("/aliment/:alimentID/protide").handler(this::handleGetAlimentProtide);
	    server =  vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
	  private void handleGetAlimentById(RoutingContext routingContext)
	  {
		    String alimentID = routingContext.request().getParam("alimentID");
		    HttpServerResponse response = routingContext.response();
		    response.end("get aliment by id");
	  }
	  //fonction a supprimer à terme
	  private void handleGetAliments(RoutingContext routingContext) 
	  {
		    routingContext.response().end("all aliments");
	  }
	  private void handleGetAlimentProtide(RoutingContext routingContext) 
	  {
		    routingContext.response().end("protide");
	  }
	  private void handleGetAlimentLipide(RoutingContext routingContext) 
	  {
		    routingContext.response().end("lipide");
	  }
	  private void handleGetAlimentGlucide(RoutingContext routingContext) 
	  {
		    routingContext.response().end("lipide");
	  }
	
	@Override
	public void stop(Future<Void> stopFuture) 
	{
		server.close();
		stopFuture.complete();
	}
}
