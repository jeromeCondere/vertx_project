package com.myvertx.app;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Before;
import org.junit.After;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


/**
 * Unit test for simple App.
 */
public class AppTest   extends TestCase
{
	Vertx vertx;
	int port;
	@Before
	public void setUp(TestContext context) throws IOException {
	  vertx = Vertx.vertx();
	  ServerSocket socket = new ServerSocket(0);
	  port = socket.getLocalPort();
	  socket.close();
	  DeploymentOptions options = new DeploymentOptions()
	      .setConfig(new JsonObject().put("http.port", port)
	      );
	  vertx.deployVerticle("com.myvertx.app.AppClient", options, context.asyncAssertSuccess());
	  vertx.deployVerticle("com.myvertx.app.AppClient", options, context.asyncAssertSuccess());
	}
	
	
	@After
	public void tearDown(TestContext context) {
	  vertx.close(context.asyncAssertSuccess());
	}
}
