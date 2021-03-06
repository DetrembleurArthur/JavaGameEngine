package com.elemengine.graphics.rendering;

import com.elemengine.entity.GameObject;
import com.elemengine.entity.GraphicElement;
import com.elemengine.graphics.camera.Camera;
import com.elemengine.graphics.shaders.Shader;

public class Renderer
{
	protected Shader shader;
	protected Camera camera;

	public Renderer(Shader shader, Camera camera)
	{
		this.shader = shader;
		this.camera = camera;
	}


	public void render(GraphicElement gelem)
	{
		shader.start();
		shader.uploadMat4f("uModel", gelem.getTransformMatrix());
		shader.uploadMat4f("uView", camera.updateViewMatrix());
		shader.uploadMat4f("uProjection", camera.updateProjectionMatrix());
		if(gelem instanceof GameObject && gelem.getTexture() != null)
		{
			shader.setUniform1i("isTextured", 1);
			shader.uploadTexture("TEX_SAMPLER", 0);
		}
		else
		{
			shader.setUniform1i("isTextured", 0);
		}
		shader.setUniformf4("uFillColor", gelem.getFillColor());
		gelem.draw();
		shader.stop();
	}



	public Shader getShader()
	{
		return shader;
	}

	public void setShader(Shader shader)
	{
		this.shader = shader;
	}

	public Camera getCamera()
	{
		return camera;
	}

	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}
}
