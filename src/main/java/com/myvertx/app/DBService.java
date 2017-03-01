package com.myvertx.app;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
/**Dans les méthodes l'utilisateur doit fournir une réponse au message*/
public interface DBService 
{
	/**Sauvegarde d'un document*/
	public void save(Message saveMessage);
	
	/**Insertion*/
	public void insert(Message insertMessage);
	
	/**Suppression d'un document ou d'un objet de la BDD*/
	public void delete(Message deleteMessage);
	
	/**Requete*/
	public void query(Message queryMessage);
}
