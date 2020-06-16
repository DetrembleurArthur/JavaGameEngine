package game.jgengine.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transformable
{
	private Matrix4f trMatrix;
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale;

	public Transformable(Vector3f position, Vector3f rotation, Vector3f scale)
	{
		trMatrix = new Matrix4f();
		trMatrix.identity();
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public Matrix4f getMatrix()
	{
		return trMatrix;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public void setPosition(Vector2f position)
	{
		this.position = new Vector3f(position.x, position.y, 0);
	}

	public Vector2f getPosition2D()
	{
		return new Vector2f(position.x, position.y);
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

	public float getRotation2D()
	{
		return rotation.z;
	}

	public void setRotation(Vector3f rotation)
	{
		this.rotation = rotation;
	}

	public void setRotation(float angleDegre)
	{
		this.rotation = new Vector3f(0, 0, angleDegre);
	}

	public Vector3f getScale()
	{
		return scale;
	}

	public void setScale(Vector3f scale)
	{
		this.scale = scale;
	}

	public Matrix4f getTransformMatrix()
	{
		return trMatrix.identity()
				.translate(position)
				.rotateX((float)Math.toRadians(rotation.x))
				.rotateY((float)Math.toRadians(rotation.y))
				.rotateZ((float)Math.toRadians(rotation.z))
				.scale(scale);
	}
}
