package com.myvertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

abstract public class AbstractDBVerticle extends AbstractVerticle implements DBService 
{
	protected String dbName="mongo";
	
	@Override
	final public void start(Future<Void> startFuture) throws Exception 
	{
		 //traitement d'un insert
	   vertx.eventBus().consumer("db."+dbName+".insert", message -> {
	          insert(message, result -> {
	        	  if(result.succeeded())
	        	  { 
	        		  if(result.result())
	        			  message.reply(infoMessage("insert","done"));
	        		  else
	        			  message.reply(infoMessage("insert","failed"));
	        	  }
	        	  else
	        	  {
	        		  System.out.println("erreur: "+result.cause());
	        		  message.reply(infoMessage("insert","failed"));
	        	  }
	          });
	     });
	   
	   //traitement d'une query
	   vertx.eventBus().consumer("db."+dbName+".query", message -> {
		   query(message, result -> {
			   if(result.succeeded())
			   {
				   message.reply(result.result());
			   }
			   else
			   {
				   System.out.println("erreur: "+result.cause());
				   message.reply(infoMessage("query","failed"));
			   }
		   });
	   });
	   
	   //traitement d'une sauvegarde d'un fichier
	   vertx.eventBus().consumer("db."+dbName+".save", message -> {
		   save(message, result -> {
			   if(result.succeeded())
			   {
				   if(result.result())
	        			  message.reply(infoMessage("save","done"));
	        		  else
	        			  message.reply(infoMessage("save","failed"));
			   }
			   else
			   {
				   System.out.println("erreur: "+result.cause());
				   message.reply(infoMessage("save","failed"));
			   }
		   });
	   });
	   
	   vertx.eventBus().consumer("db."+dbName+".delete", message -> {
		   delete(message, result -> {
			   if(result.succeeded())
			   {
				   if(result.result())
	        			  message.reply(infoMessage("delete","done"));
	        		  else
	        			  message.reply(infoMessage("delete","failed"));
			   }
			   else
			   {
				   System.out.println("erreur: "+result.cause());
				   message.reply(infoMessage("delete","failed"));
			   }
		   });
	   });
	   
	   startFuture.complete();
	   
	}
	private JsonObject infoMessage(String action, String state)
	{
		return new JsonObject().put("action", action)
							   .put("state", state);
	}
	
}
