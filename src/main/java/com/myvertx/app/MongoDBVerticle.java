package com.myvertx.app;


import io.netty.handler.codec.json.JsonObjectDecoder;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class MongoDBVerticle extends AbstractDBVerticle {
	MongoClient client;
	private static int MONGO_PORT = 27017;
	private static int HTTP_PORT = 28017;
	private static String uri = "mongodb://localhost:27017";
	private static String DB_NAME = "db";
	private static String collection = "test";
	@Override
	protected void setup()
	{
	    JsonObject mongoconfig = new JsonObject()
	            .put("connection_string", uri)
	            .put("db_name", DB_NAME);	
	    client = MongoClient.createShared(vertx, mongoconfig);

	}
	@Override
	public void save(Message document, Handler<AsyncResult<Object>> result) {
		// TODO utiliser client pour sauvegarder
		
	}
	@Override
	public void insert(Message object, Handler<AsyncResult<Object>> result) {
		
		if(object.headers().get("action").equals("insert"))
		{
			JsonObject body = (JsonObject) object.body();
			JsonObject document = body.getJsonObject("document");
			
			client.insert(collection, document , res -> {
				
			});
		}
	
	}
	@Override
	public void delete(Message object, Handler<AsyncResult<Object>> result) {
		
		
		if(object.headers().get("action").equals("delete"))
		{
			JsonObject body = (JsonObject) object.body();
			JsonObject query = body.getJsonObject("query");
		client.removeDocuments(collection, query, res -> {
			  if (res.succeeded()) 
			  {
			    System.out.println("document supprimé");
			  } 
			  else 
			  {
			    res.cause().printStackTrace();
			  }
			});
		}
	}
	@Override
	public void query(Message query, Handler<AsyncResult<Object>> result) {
		// TODO Auto-generated method stub
		
	}

}
