package com.myvertx;


import com.myvertx.app.*;
import com.myvertx.db.*;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import user.example.AppUser;


public class MainLauncher  {
	public static void main(String[] args) {
        System.out.println("Start of the cluster");
        final DeploymentOptions deploymentOptions = new DeploymentOptions() //
                .setInstances(5);
        
        final ClusterManager mgr = new HazelcastClusterManager();
        final VertxOptions options = new VertxOptions().setClusterManager(mgr);
        												
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                
                final Vertx vertx = res.result();
                //on deploie tous les vertices ici!

                vertx.deployVerticle(MongoUser.class.getName());
                vertx.deployVerticle(MongoService.class.getName());
                
            } else {
                System.out.println("FAIL !!!");
            }
        });

        System.out.println("End of the Launcher");
    }
}
