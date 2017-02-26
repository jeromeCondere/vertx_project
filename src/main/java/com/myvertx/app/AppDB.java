package com.myvertx.app;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class AppDB extends AbstractVerticle {
	MongoClient client;
	private static int MONGO_PORT = 27017;
	private static int HTTP_PORT = 28017;
	private static String uri = "mongodb://localhost:27017";
	private static String DB_NAME = "db";
	@Override
	public void start() throws Exception 
	{
		
	    JsonObject mongoconfig = new JsonObject()
	            .put("connection_string", uri)
	            .put("db_name", DB_NAME);
	    client = MongoClient.createShared(vertx, mongoconfig);
	    final EventBus bus = vertx.eventBus();
		 vertx.eventBus().consumer("db.import", message -> {
	           System.out.println("import du fichier: "+message.body());
	     });
		 vertx.eventBus().consumer("launch.service.query", message -> {
	           System.out.println("lancement du service: "+message.body());
	     });

	}
	public void doQuery(Message query)
	{
		
	}
	@Override
	public void stop() throws Exception 
	{
		
	}
	
}
