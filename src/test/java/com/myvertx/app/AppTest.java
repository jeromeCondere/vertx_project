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
	}
	
	@After
	public void tearDown(TestContext context) 
	{
	  vertx.close(context.asyncAssertSuccess());
	}
}
