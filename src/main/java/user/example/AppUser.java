package user.example;

import com.myvertx.app.AbstractApp;

import io.vertx.core.AbstractVerticle;

public class AppUser extends AbstractVerticle {

	@Override
	public void start() {
		String sql = "create database  db";
		vertx.eventBus().send("launch.service.jdbc.execute", "create database", response -> {
			if(response.succeeded())
			{
				System.out.println(response.result());
			}
			else
				response.cause();
				
		});
			
	}

}
