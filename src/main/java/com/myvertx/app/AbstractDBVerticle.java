package com.myvertx.app;

import io.vertx.core.AbstractVerticle;

abstract public class AbstractDBVerticle extends AbstractVerticle implements DBService{


	public abstract void save(Object document);


	public abstract void insert(Object object);


	public abstract void delete(Object document);


	public abstract Object query(Object query);

}
