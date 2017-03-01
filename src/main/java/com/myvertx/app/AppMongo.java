package com.myvertx.app;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

public class AppMongo extends AbstractApp
{	
	/* le developpeur lorsqu'il crée son verticle <br>
	 * fait appel aux service de l'App
	 * */
	@Override
	protected void setup()
	{
		this.dbName = "mongo";
		/*
		 * Si on veut spécialiser, on peut rajouter ici d'autres consumers
		 */
	}

	@Override
	public void stop() throws Exception 
	{
		System.out.println("Stop of App Verticle");
	}
}
