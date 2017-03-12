package com.myvertx.db;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.*;
import io.vertx.servicediscovery.types.MessageSource;

public class MongoService extends AbstractVerticle {
	ServiceDiscovery discovery;
	@Override
	final public void start() throws Exception 
	{
		discovery = ServiceDiscovery.create(vertx,
			    new ServiceDiscoveryOptions()
			        .setAnnounceAddress("db.services")
			        .setName("mongo.db"));
		
		Record record = MessageSource.createRecord(
			    "sayHello", // The service name
			    "hello" // The event bus address
			);
		
		discovery.publish(record, ar -> {
			System.out.println("hello record published");
		});
		
		discovery.getRecord(new JsonObject().put("name", "sayHello"), ar -> {
			  if (ar.succeeded() && ar.result() != null) {
				    // Retrieve the service reference
				    ServiceReference reference = discovery.getReference(ar.result());
				    // Retrieve the service object)
				    MessageConsumer<JsonObject> consumer = reference.get();

				    // Attach a message handler on it
				    consumer.handler(message -> {
				      // message handler
				      JsonObject payload = message.body();
				      System.out.println("hello "  + message.body());
				    });
				    
				    reference.release();
				  }
			
		});
	}
	@Override
	final public void stop() throws Exception 
	{
		
	}
}
