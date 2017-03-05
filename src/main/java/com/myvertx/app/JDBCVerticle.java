package com.myvertx.app;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class JDBCVerticle extends AbstractDBVerticle {

	private JDBCClient client;
	private static String DEFAULT_DATASOURCE = "datasource";
	
	
	@Override
	protected void setup() {
		this.dbName = "jdbc";
		JsonObject config = new JsonObject()
				  .put("url", "jdbc:hsqldb:mem:test?shutdown=true")
				  .put("driver_class", "org.hsqldb.jdbcDriver")
				  .put("max_pool_size", 30);
		client = JDBCClient.createShared(vertx, config, DEFAULT_DATASOURCE);
		   vertx.eventBus().consumer("db."+dbName+".execute", message -> {
		          execute(message);
		     });
	}
	
	@Override
	public void save(Message saveMessage) 
	{
		saveMessage.reply(infoMessage("save","error (use execute consumer instead)"));
	}

	@Override
	public void insert(Message insertMessage) 
	{
		insertMessage.reply(infoMessage("insert","error (use execute consumer instead)"));
	}

	@Override
	public void delete(Message deleteMessage) 
	{
		deleteMessage.reply(infoMessage("delete","error (use execute consumer instead)"));
	}

	@Override
	public void query(Message queryMessage) 
	{
		if(queryMessage.headers().get("action").equals("query"))
		{
			client.getConnection(res -> {
				if(res.succeeded())
				{
					String queryString = (String) queryMessage.body();
					SQLConnection connection = res.result();
					connection.query(queryString, result -> {
						
						
					});
				}
				else
				{
					res.cause().printStackTrace();
				}
				
			});
		}

	}

	@Override
	public void find(Message findMessage) {
		findMessage.reply(infoMessage("find","error (use query consumer)"));
	}
	/**Use insert, update and delete statement here*/
	public void execute(Message executeMessage) 
	{
		if(executeMessage.headers().get("action").equals("query"))
		{
			client.getConnection(res -> {
				if(res.succeeded())
				{
					String executeString = (String) executeMessage.body();
					SQLConnection connection = res.result();
					connection.execute(executeString, result -> {
						if(result.succeeded())
							executeMessage.reply(infoMessage("execute","done"));
						else
						{
							result.cause().printStackTrace();
							executeMessage.reply(infoMessage("execute","failed"));
						}
					});
				}
				else
				{
					executeMessage.reply(infoMessage("execute","failed"));
					res.cause().printStackTrace();
				}
			});
		}
	}
	
	@Override
	public void stop() throws Exception 
	{
		client.close();
	}



}
