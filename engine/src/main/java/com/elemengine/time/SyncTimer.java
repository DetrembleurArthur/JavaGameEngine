package com.elemengine.time;


public class SyncTimer extends DynamicTimer
{
	public SyncTimer(float maxDelay, int maxPeriod, Runnable action)
	{
		super(maxDelay, maxPeriod, action);
	}

	@Override
	public void run()
	{
		if(isRunning())
			test();
	}
}
