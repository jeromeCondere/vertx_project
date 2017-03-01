package com.myvertx.app;


import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;


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
                vertx.deployVerticle(MongoDBVerticle.class.getName(),deploymentOptions);
                vertx.deployVerticle(AppMongo.class.getName(),deploymentOptions);
                
            } else {
                System.out.println("FAIL !!!");
            }
        });

        System.out.println("End of the Launcher");
    }
}
