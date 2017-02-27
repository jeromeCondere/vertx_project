package com.myvertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

abstract public class AbstractDBVerticle extends AbstractVerticle implements DBService{
	
	protected String dbName="mongo";
	
	@Override
	final public void start(Future<Void> startFuture) throws Exception 
	{
	   
	   vertx.eventBus().consumer("db."+dbName+".insert", message -> {
	          insert(message);
	     });
	   
	   vertx.eventBus().consumer("db."+dbName+".query", message -> {
		   query(message);
	   });
	   
	   vertx.eventBus().consumer("db."+dbName+".save", message -> {
		   save(message);
	   });
	   
	   vertx.eventBus().consumer("db."+dbName+".delete", message -> {
		   save(message);
	   });
	   
	   startFuture.complete();
	   
	}
	
	public abstract void save(Object document);


	public abstract void insert(Object object);


	public abstract void delete(Object document);


	public abstract Object query(Object query);

}
