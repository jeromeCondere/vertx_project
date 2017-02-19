package com.myvertx.app;
import io.vertx.core.AbstractVerticle;
/**
 * Le verticle principal <br>
 * il instancie 2 sous verticles : <br>
 * Un client chargé d'aller chercher les données sur le serveur distant <br>
 * Un serveur REST sur lequel on effectue nos requêtes pour obtenir les bonnes données
 */
public class App extends AbstractVerticle
{
	 @Override
	 public void start() throws Exception {
	    	
	        vertx.deployVerticle("com.myvertx.app.AppServer");
	        vertx.deployVerticle("com.myvertx.app.AppCSVServer");
	    }
}
