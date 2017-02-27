package com.myvertx.app;


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
	@Override
	public void start() throws Exception 
	{
		
	    JsonObject mongoconfig = new JsonObject()
	            .put("connection_string", uri)
	            .put("db_name", DB_NAME);
	    		
	    client = MongoClient.createShared(vertx, mongoconfig);
	    final EventBus bus = vertx.eventBus();
		 vertx.eventBus().consumer("db.mongo.insert", message -> {
	          
	     });
		 
		 vertx.eventBus().consumer("db.mongo.query", message -> {
			 	JsonObject jsonMessage = (JsonObject) message.body();
	           client.runCommand(jsonMessage.getString("command"),
	        		     jsonMessage.getJsonObject("parameters") ,res -> {
	        		    	 if (res.succeeded()) {
	        		    		    JsonArray resArr = res.result().getJsonArray("result");
	        		    		    // sauvegarder dans un fichier temporaire
	        		    		  } else {
	        		    		    res.cause().printStackTrace();
	        		    		  }
	        		     });
	     });
		 
		 vertx.eventBus().consumer("db.mongo.save", message -> {
			 
		 });
		 
		 vertx.eventBus().consumer("db.mongo.delete", message -> {
			 
		 });
		 

	}
	
	@Override
	public void save(Object document, Handler<AsyncResult<Void>> result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void insert(Object object, Handler<AsyncResult<Boolean>> result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Object document, Handler<AsyncResult<Boolean>> result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void query(Object query, Handler<AsyncResult<Object>> result) {
		// TODO Auto-generated method stub
		
	}
}
