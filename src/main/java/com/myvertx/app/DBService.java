package com.myvertx.app;

public interface DBService {
	public void save(Object document);
	public void insert(Object object);
	public void delete(Object document);
	public Object query(Object query);
}
