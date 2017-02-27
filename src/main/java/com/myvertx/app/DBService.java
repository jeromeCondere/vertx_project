package com.myvertx.app;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;

public interface DBService {
	public void save(Message document, Handler<AsyncResult<Boolean>> result);
	public void insert(Message object, Handler<AsyncResult<Boolean>> result);
	public void delete(Message document, Handler<AsyncResult<Boolean>> result);
	public void query(Message query, Handler<AsyncResult<Object>> result);
}
