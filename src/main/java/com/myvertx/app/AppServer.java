package com.myvertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class AppServer extends AbstractVerticle {
	private HttpServer server;
	private HttpClient client;
	@Override
    public void start(final Future future) throws Exception 
	{
    
		Router router = Router.router(vertx);

	    router.route().handler(BodyHandler.create());
	    router.route("/").handler(req -> {
	    	req.response().end("accueil");
	    });
	    router.get("/aliments").handler(this::handleGetAliments);
	    router.post("/aliment/:alimentID").handler(this::handleGetAlimentById);
	    router.get("/aliment/:alimentID/glucide").handler(this::handleGetAlimentGlucide);
	    router.get("/aliment/:alimentID/lipide").handler(this::handleGetAlimentLipide);
	    router.get("/aliment/:alimentID/protide").handler(this::handleGetAlimentProtide);
	    router.get("/aliment/nom/:nom").handler(this::handleGetAlimentByName);
	    
	    server =  vertx.createHttpServer().requestHandler(router::accept).listen(8080, done -> {
	          if (done.failed()) {
	              future.fail(done.cause());
	            } else {
	              future.complete();
	            }
	          });
	    
	    HttpClientOptions options = new HttpClientOptions().setDefaultHost("localhost").setDefaultPort(8081);
		client = vertx.createHttpClient(options);
    }
	  /**Permet d'obtenir les informations sur l'aliment à partir de son id*/
	  private void handleGetAlimentById(RoutingContext routingContext)
	  {
		    String alimentID = routingContext.request().getParam("alimentID");
		    JsonObject bodyRequestJson = routingContext.getBodyAsJson();
			HttpServerResponse response = routingContext.response();
			
			client.post("/line/by/columnValue",responseClient -> {
					responseClient.bodyHandler(bodyResponseClient -> {
						if(responseClient.statusCode() == 200)
						{
							//s'il n'y a pas eu d'erreur
							JsonArray result = bodyResponseClient.toJsonArray();
							System.out.println(result.getString(0));
							if(result.isEmpty())
								response.end(result.encode());
							else
								response.end(result.getString(0));
						}
						else
						{
							System.out.println("vuuyv");
							//sinon on affiche le message
							response.setStatusCode(400).end(bodyResponseClient.toJsonObject().encodePrettily());
						}
					});
				
			})
			.putHeader("content-type", "application/json")
			.setTimeout(4000)
			.end(new JsonObject()
			  		.put("file", bodyRequestJson.getString("file"))
			  		.put("field", "ORIGFDCD")
			  		.put("value", alimentID)
			  		.encodePrettily());
			
			
	  }
	  //fonction a supprimer à terme
	  private void handleGetAliments(RoutingContext routingContext) 
	  {
		    routingContext.response().end("all aliments");
	  }
	  
	  /**Permet d'obtenir les protides de l'aliment à partir de son id*/
	  private void handleGetAlimentProtide(RoutingContext routingContext) 
	  {
		    routingContext.response().end("protide");
	  }
	  
	  /**Permet d'obtenir les lipides de l'aliment à partir de son id*/
	  private void handleGetAlimentLipide(RoutingContext routingContext) 
	  {
		    routingContext.response().end("lipide");
	  }
	  
	  /**Permet d'obtenir les glucides de l'aliment à partir de son id*/
	  private void handleGetAlimentGlucide(RoutingContext routingContext) 
	  {
		    routingContext.response().end("lipide");
	  }
	  private void handleGetAlimentByName(RoutingContext routingContext) 
	  {
		    routingContext.response().end("nom");
	  }
	
	@Override
	public void stop(Future<Void> stopFuture) 
	{
		server.close();
		stopFuture.complete();
	}
}
