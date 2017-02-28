package com.myvertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;


/**La classe AbstractDBVerticle permet de mettre en place plusieurs systèmes de BDD <br>
 * et donc de gerer plusieurs types de formats de données
 *  */
abstract public class AbstractDBVerticle extends AbstractVerticle implements DBService 
{
	protected String dbName="mongo";
	
	abstract protected void setup();
	
	@Override
	final public void start(Future<Void> startFuture) throws Exception 
	{
		setup();
		 //traitement d'un insert
	   vertx.eventBus().consumer("db."+dbName+".insert", message -> {
	          insert(message, result -> {
	        	  if(result.succeeded())
	        	  { 
	        		message.reply(infoMessage("insert","done"));
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
				   //on répond le resultat de la query
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
				   message.reply(infoMessage("save","done"));
			   }
			   else
			   {
				   System.out.println("erreur: "+result.cause());
				   message.reply(infoMessage("save","failed"));
			   }
		   });
	   });
	   
	 //traitement de la suppression
	   vertx.eventBus().consumer("db."+dbName+".delete", message -> {
		   delete(message, result -> {
			   if(result.succeeded())
			   {
				   message.reply(infoMessage("delete","done"));
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
