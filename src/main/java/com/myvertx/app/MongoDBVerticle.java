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
	protected void setup()
	{
	    JsonObject mongoconfig = new JsonObject()
	            .put("connection_string", uri)
	            .put("db_name", DB_NAME);	
	    client = MongoClient.createShared(vertx, mongoconfig);

	}
	@Override
	public void save(Message document, Handler<AsyncResult<Boolean>> result) {
		// TODO utiliser client pour sauvegarder
		
	}
	@Override
	public void insert(Message object, Handler<AsyncResult<Boolean>> result) {
		// TODO utiliser client pour inserer
		
	}
	@Override
	public void delete(Message document, Handler<AsyncResult<Boolean>> result) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void query(Message query, Handler<AsyncResult<Object>> result) {
		// TODO Auto-generated method stub
		
	}

}
