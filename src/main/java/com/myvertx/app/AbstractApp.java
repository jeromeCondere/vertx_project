package com.myvertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;

public abstract class AbstractApp extends AbstractVerticle {

	private static int DEFAULT_SEND_TIMOUT = 70000;
	protected String dbName;
	protected DeliveryOptions deliveryOptions;
	
	/**Cette methode est utilisée pour faire les initialisations <br>
	 * le nom de la BDD (dbName) <br>
	 * les options d'envoi (deliveryOptions)
	 * */
	abstract protected void setup();
	
	@Override
	final public void start(Future<Void> startFuture) throws Exception 
	{
		/*
		 * avec l'event bus l'application est plus scalable
		 * de sorte que on peut l'etendre sur plusieurs serveurs
		 * Les macros ne communiquent pas directement avec la base de
		 * données ils passent par les services de l'App.
		 */
		//l'utilisateur fait appel au différents services proposés par l'App
		//MAIS n'a JAMAIS accès aux services de la BDD
		
		setup();
		
		deliveryOptions = new DeliveryOptions().setSendTimeout(DEFAULT_SEND_TIMOUT);
		
		vertx.eventBus().consumer("launch.service."+dbName+".delete", message -> {
			 vertx.eventBus().send("db."+dbName+".delete", message, deliveryOptions ,reponse -> {
				 if(reponse.succeeded())
					 message.reply(reponse.result());
				 else
					 System.out.println(reponse.cause() );
			 });      
	     });
		 
		 vertx.eventBus().consumer("launch.service."+dbName+".query", message -> {
			 vertx.eventBus().send("db."+dbName+".query", message, deliveryOptions ,reponse -> {
				 if(reponse.succeeded())
					 message.reply(reponse.result());
				 else
					 System.out.println(reponse.cause() );
			 });  
	     });
		 
		 vertx.eventBus().consumer("launch.service."+dbName+".save", message -> {
			 vertx.eventBus().send("db."+dbName+".save", message, deliveryOptions ,reponse -> {
				 if(reponse.succeeded())
					 message.reply(reponse.result());
				 else
					 System.out.println(reponse.cause() );
			 });  
	     });
		 
		 vertx.eventBus().consumer("launch.service."+dbName+".insert", message -> {
			 vertx.eventBus().send("db."+dbName+".insert", message, deliveryOptions ,reponse -> {
				 if(reponse.succeeded())
					 message.reply(reponse.result());
				 else
					 System.out.println(reponse.cause() );
			 });  
	     });
	}
	
}
