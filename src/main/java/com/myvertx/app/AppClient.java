package com.myvertx.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;

/**
* Le client qui permet d'obtenir les ressources adéquates
*/
public class AppClient extends AbstractVerticle 
{
	HttpClient client;
	@Override
	 public void start() throws Exception 
	 {
			HttpClientOptions options = new HttpClientOptions();
			client = vertx.createHttpClient(options).getNow(80, "www.google.com", "/", resp -> {
			      System.out.println("Got response " + resp.statusCode());
			      });
	 }
	
	@Override
	public void stop(Future<Void> stopFuture) 
	{
		stopFuture.complete();
		client.close();
	}
			
}
