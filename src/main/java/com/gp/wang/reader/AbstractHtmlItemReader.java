package com.gp.wang.reader;

public abstract class AbstractHtmlItemReader extends AbstractItemReader {
 
    protected abstract void init();
	
	protected abstract void processData();

	public abstract void readData();
}
