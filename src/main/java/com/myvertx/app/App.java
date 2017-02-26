package com.myvertx.app;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class App extends AbstractVerticle
{
	@Override
	public void start() throws Exception 
	{
		/*
		 * avec l'event bus l'application est plus scalable
		 * de sorte que on peut l'etendre sur plusieurs serveurs
		 * Les macros ne communiquent pas directement avec la base de
		 * données ils passent par les services de l'App.
		 */
		System.out.println("Start of Container");
		final EventBus bus = vertx.eventBus();
		 vertx.eventBus().consumer("db.import", message -> {
	           System.out.println("import du fichier: "+message.body());
	     });
		 vertx.eventBus().consumer("launch.service.query", message -> {
	           System.out.println("lancement du service: "+message.body());
	     });
	}

	@Override
	public void stop() throws Exception 
	{
		System.out.println("Stop of Cat Verticle");
	}
}
