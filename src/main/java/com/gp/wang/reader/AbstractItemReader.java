package com.gp.wang.reader;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractItemReader implements ItemReader {

	// static variable
	protected  AtomicBoolean initialized = new AtomicBoolean();

	protected abstract void init();
	protected abstract void processData();


}
