package com.myvertx.db;

import com.myvertx.app.AbstractApp;

public class AppJDBC extends AbstractApp {

	@Override
	protected void setup() 
	{
		// TODO Auto-generated method stub
		this.dbName = "jdbc";
		 vertx.eventBus().consumer("launch.service."+dbName+".execute", message -> {
			 vertx.eventBus().send("db."+dbName+".execute", message, deliveryOptions ,reponse -> {
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
		System.out.println("Stop of the JDBC App Verticle");
	}

}
