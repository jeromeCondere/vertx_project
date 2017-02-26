package com.myvertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class AppDB extends AbstractVerticle {
	MongoClient mongoClient;
	private static int MONGO_PORT = 12345;
	private static int HTTP_PORT = 28017;
	private static String uri = "mongodb://localhost:27017";
	private static String DB_NAME = "mongodb://localhost:27017";
	@Override
	public void start() throws Exception 
	{
	    JsonObject mongoconfig = new JsonObject()
	            .put("connection_string", uri)
	            .put("db_name", "db");
		mongoClient = MongoClient.createShared(vertx, mongoconfig);
		JsonObject document = new JsonObject().put("title", "The Hobbit");

		mongoClient.save("books", document, res -> {

		  if (res.succeeded()) {

		    String id = res.result();
		    System.out.println("Saved book with id " + id);

		  } else {
		    res.cause().printStackTrace();
		  }

		});
	}
	
	@Override
	public void stop() throws Exception 
	{
		
	}
	
}
