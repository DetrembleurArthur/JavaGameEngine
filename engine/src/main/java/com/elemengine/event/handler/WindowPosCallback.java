package com.elemengine.event.handler;

import org.lwjgl.glfw.GLFWWindowPosCallback;

public class WindowPosCallback extends GLFWWindowPosCallback
{
	private WindowPosEventHandler handler;

	public WindowPosCallback(WindowPosEventHandler handler)
	{
		this.handler = handler;
	}

	@Override
	public void invoke(long window, int xpos, int ypos)
	{
		handler.windowPosEventHandler(xpos, ypos);
	}
}
