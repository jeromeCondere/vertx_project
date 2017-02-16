package com.myvertx.app;

import java.io.IOException;
import java.net.ServerSocket;


import org.junit.*;
import org.junit.Test;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;

import junit.framework.TestCase;


/**
 * Unit test for simple App.
 */
public class AppTest   extends TestCase
{
	Vertx vertx;
	int port;
	@Before
	public void setUp(TestContext context) throws IOException 
	{
	  vertx = Vertx.vertx();
	  ServerSocket socket = new ServerSocket(0);
	  port = socket.getLocalPort();
	  socket.close();
	  DeploymentOptions options = new DeploymentOptions()
	      .setConfig(new JsonObject().put("http.port", port)
	      );
	  vertx.deployVerticle("com.myvertx.app.AppClient", context.asyncAssertSuccess());
	  vertx.deployVerticle("com.myvertx.app.AppServer", options, context.asyncAssertSuccess());
	}
	
	@Test
	 public void testClientConnection() 
	 {
	    fail();
	 }
	@Test
	 public void testClientDownload() 
	 {
	    fail();
	 }
	@Test
	 public void testServerRessourceAvailable() 
	 {
	    fail();
	 }
	@Test
	 public void testServerGetAlimentbyId() 
	 {
	    fail();
	 }
	@Test
	 public void testServerGetAllAliments() 
	 {
	    fail();
	 }
	@Test
	 public void testServerGetAlimentbyName() 
	 {
	    fail();
	 }
	@Test
	 public void testServerGetAlimentGlucide() 
	 {
	    fail();
	 }	
	@Test
	 public void testServerGetAlimentLipide() 
	 {
	    fail();
	 }	
	@Test
	 public void testServerGetAlimentProtide() 
	 {
	    fail();
	 }	
	
	
	
	
	
	@After
	public void tearDown(TestContext context) 
	{
	  vertx.close(context.asyncAssertSuccess());
	}
}
