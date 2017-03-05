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
	private static String DEFAULT_URI = "mongodb://localhost:27017";
	private static String DEFAULT_DB_NAME = "db";
	private static String collection = "test";
	
	@Override
	protected void setup()
	{
	    JsonObject mongoconfig = new JsonObject()
	            .put("connection_string", DEFAULT_URI)
	            .put("db_name", DEFAULT_DB_NAME);	
	    client = MongoClient.createShared(vertx, mongoconfig);

	}
	@Override
	public void save(Message saveMessage) 
	{
		if(saveMessage.headers().get("action").equals("save"))
		{
			JsonObject body = (JsonObject) saveMessage.body();
			JsonObject document = body.getJsonObject("document");
			
			client.save(collection, document , result -> {
				if (result.succeeded()) 
				  {
				    System.out.println("document saved");
				    saveMessage.reply(infoMessage("save", "done")
				    								.put("id", result.result()));
				  } 
				  else 
				  {
					  result.cause().printStackTrace();
					  saveMessage.reply(infoMessage("save", "error"));
				  }
			});
		}
	}
	@Override
	public void  insert(Message insertMessage) 
	{
		
		if(insertMessage.headers().get("action").equals("insert"))
		{
			JsonObject body = (JsonObject) insertMessage.body();
			JsonObject document = body.getJsonObject("document");
			
			client.insert(collection, document , result -> {
				if (result.succeeded()) 
				  {
				    System.out.println("document deleted");
				    insertMessage.reply(infoMessage("insert", "done")
				    								.put("id", result.result()));
				  } 
				  else 
				  {
					  result.cause().printStackTrace();
				    insertMessage.reply(infoMessage("insert", "error"));
				  }
			});
		}
	}
	@Override
	public void delete(Message deleteMessage) 
	{
		
		if(deleteMessage.headers().get("action").equals("delete"))
		{
			JsonObject body = (JsonObject) deleteMessage.body();
			JsonObject query = body.getJsonObject("query");
			client.removeDocuments(collection, query, res -> {
			  if (res.succeeded()) 
			  {
			    System.out.println("document inserted");
			    deleteMessage.reply(infoMessage("delete", "done"));
			  } 
			  else 
			  {
			    res.cause().printStackTrace();
			    deleteMessage.reply(infoMessage("delete", "error"));
			  }
			});
		}
	}
	
	//TODO traiter le cas où le resultat de la requete est énorme
	@Override
	public void query(Message queryMessage) 
	{
		if(queryMessage.headers().get("action").equals("delete"))
		{
			JsonObject body = (JsonObject) queryMessage.body();
			JsonObject query = body.getJsonObject("query");
			
			//requete naive
			client.runCommand(collection, query, buffer -> {
				//if()
			});
		}
	}

}
