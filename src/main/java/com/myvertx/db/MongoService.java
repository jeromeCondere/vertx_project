package com.myvertx.db;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.servicediscovery.*;
import io.vertx.servicediscovery.types.MessageSource;

public class MongoService extends AbstractVerticle {
	ServiceDiscovery discovery;
	@Override
	final public void start() throws Exception 
	{
		discovery = ServiceDiscovery.create(vertx,
			    new ServiceDiscoveryOptions()
			        .setAnnounceAddress("db.service.")
			        .setName("mongo.db"));
		
		Record record = MessageSource.createRecord(
			    "message", // The service name
			    "hello" // The event bus address
			);
	}
	@Override
	final public void stop() throws Exception 
	{
		
	}
}
