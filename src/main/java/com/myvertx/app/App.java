package com.myvertx.app;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

public class App extends AbstractVerticle
{
	private static int DEFAULT_SEND_TIMOUT = 70000;
	
	/** le developpeur lorsqu'il crée son verticle <br>
	 * fait appel aux service de l'App
	 * */
	@Override
	public void start(Future<Void> startFuture) throws Exception 
	{
		/*
		 * avec l'event bus l'application est plus scalable
		 * de sorte que on peut l'etendre sur plusieurs serveurs
		 * Les macros ne communiquent pas directement avec la base de
		 * données ils passent par les services de l'App.
		 */
		//l'utilisateur fait appel au différents services proposés par l'App
		//MAIS n'a JAMAIS accès aux services de la BDD
		
		
		DeliveryOptions deliveryOptions = new DeliveryOptions().setSendTimeout(DEFAULT_SEND_TIMOUT);
		
		//TODO rajouter un abstract classe DBAccessor par dessus
		
		 vertx.eventBus().consumer("launch.service.delete", message -> {
			 vertx.eventBus().send("db.mongo.delete", message, deliveryOptions ,reponse -> {
				 if(reponse.succeeded())
					 message.reply(reponse.result());
				 else
					 System.out.println(reponse.cause() );
			 });      
	     });
		 
		 vertx.eventBus().consumer("launch.service.query", message -> {
			 vertx.eventBus().send("db.mongo.query", message, deliveryOptions ,reponse -> {
				 if(reponse.succeeded())
					 message.reply(reponse.result());
				 else
					 System.out.println(reponse.cause() );
			 });  
	     });
		 
		 vertx.eventBus().consumer("launch.service.save", message -> {
			 vertx.eventBus().send("db.mongo.save", message, deliveryOptions ,reponse -> {
				 if(reponse.succeeded())
					 message.reply(reponse.result());
				 else
					 System.out.println(reponse.cause() );
			 });  
	     });
		 
		 vertx.eventBus().consumer("launch.service.insert", message -> {
			 vertx.eventBus().send("db.mongo.insert", message, deliveryOptions ,reponse -> {
				 if(reponse.succeeded())
					 message.reply(reponse.result());
				 else
					 System.out.println(reponse.cause() );
			 });  
	     });
	}

	@Override
	public void stop() throws Exception 
	{
		System.out.println("Stop of Cat Verticle");
	}
}
