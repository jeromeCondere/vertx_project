package com.myvertx.app;

import java.io.IOException;
import java.net.ServerSocket;

import javax.xml.ws.Response;

import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.core.*;
import io.vertx.core.http.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import junit.framework.TestCase;
import com.myvertx.app.*;

/**
 * Unit test for simple App.
 */
@RunWith(VertxUnitRunner.class)
public class AppTest
{
	Vertx vertx;
	int port;
	@Before
	public void setUp(TestContext context)
	{
	  vertx = Vertx.vertx();
	  vertx.deployVerticle("com.myvertx.app.AppCSVServer", context.asyncAssertSuccess());
	  vertx.deployVerticle("com.myvertx.app.AppServer", context.asyncAssertSuccess());
	}
	

	@Test
	 public void testServerRessourceAvailable(TestContext context) 
	 {
		  Async async = context.async();
		  HttpClientOptions options = new HttpClientOptions().setDefaultHost("localhost").setDefaultPort(8081);
		  HttpClient client = vertx.createHttpClient(); 
		  client.post("ressource/availaible", response -> {
			  response.bodyHandler(body -> {
					 context.assertEquals("error", body.toJsonObject().getString("message"));
					 async.complete();
				 
			  });
		  }).putHeader("content-type", "application/json")
		  	.end(new JsonObject().put("path", "cujk.kh")
		  						 .encodePrettily());
	 }
	@Test
	 public void testServerGetAlimentbyId(TestContext context) 
	 {
		context.fail();
	 }
	@Test
	 public void testServerGetAllAliments(TestContext context) 
	 {
	    context.fail();
	 }
	@Test
	 public void testServerGetAlimentbyName(TestContext context) 
	 {
		context.fail();
	 }
	@Test
	 public void testServerGetAlimentGlucide(TestContext context) 
	 {
		context.fail();
	 }	
	@Test
	 public void testServerGetAlimentLipide(TestContext context) 
	 {
		context.fail();
	 }	
	@Test
	 public void testServerGetAlimentProtide(TestContext context) 
	 {
		context.fail();
	 }	
	
	
	
	
	
	/*@After
	public void tearDown(TestContext context) 
	{
	  vertx.close(context.asyncAssertSuccess());
	}*/
}
