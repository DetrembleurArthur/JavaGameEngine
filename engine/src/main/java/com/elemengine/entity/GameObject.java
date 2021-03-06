package com.elemengine.entity;

import com.elemengine.components.event.EventManagerComponent;
import com.elemengine.graphics.rendering.Texture;
import com.elemengine.graphics.vertex.Mesh;
import com.elemengine.utils.LaterList;
import com.elemengine.utils.MathUtil;
import com.elemengine.components.Component;
import com.elemengine.components.animations.AnimationsComponent;
import com.elemengine.components.moves.MoveManagerComponent;
import com.elemengine.components.properties.CommonPropertiesComponent;
import com.elemengine.components.properties.TextPropertyComponent;
import com.elemengine.components.properties.ValuePropertyComponent;
import com.elemengine.components.scripts.ScriptsComponent;
import com.elemengine.components.sprites.SpritesComponent;
import com.elemengine.components.timers.TimersComponent;
import com.elemengine.sys.Application;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GameObject extends GraphicElement
{
	private ArrayList<Component> components;

	public GameObject(Mesh mesh, Texture texture)
	{
		super(mesh, texture);
	}

	public <T extends Component> void addComponent(T component)
	{
		if(components == null) components = new ArrayList<>();
		for(Component c : components)
		{
			if(c.getClass().equals(component.getClass()))
				return;
		}
		components.add(component);
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponent(Class<T> cclass)
	{
		if(components == null)
		{
			components = new LaterList<>();
			return null;
		}
		for(Component c : components)
		{
			if(c.getClass().equals(cclass))
				return (T)c;
		}
		return null;
	}

	public <T extends Component> void removeComponent(Class<T> cclass)
	{
		components.removeIf(component -> component.getClass().equals(cclass));
	}

	public <T extends Component> T accessComponent(Class<T> cclass)
	{
		T component = getComponent(cclass);
		if(component == null)
		{
			try
			{
				addComponent(component = cclass.getConstructor(GameObject.class).newInstance(this));
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
			{
				e.printStackTrace();
			}
		}
		return component;
	}

	public EventManagerComponent events_c()
	{
		return accessComponent(EventManagerComponent.class);
	}

	public CommonPropertiesComponent properties_c()
	{
		return accessComponent(CommonPropertiesComponent.class);
	}

	public TextPropertyComponent textProperty_c()
	{
		return accessComponent(TextPropertyComponent.class);
	}

	public ValuePropertyComponent valueProperty_c()
	{
		return accessComponent(ValuePropertyComponent.class);
	}

	public AnimationsComponent animations_c()
	{
		return accessComponent(AnimationsComponent.class);
	}

	public ScriptsComponent scripts_c()
	{
		return accessComponent(ScriptsComponent.class);
	}

	public MoveManagerComponent moves_c()
	{
		return accessComponent(MoveManagerComponent.class);
	}

	public SpritesComponent sprites_c()
	{
		return accessComponent(SpritesComponent.class);
	}

	public TimersComponent timers_c()
	{
		return accessComponent(TimersComponent.class);
	}

	@Override
	public void run()
	{
		if(components != null)
		{
			for(Component component : components)
			{
				if(component instanceof Runnable)
					((Runnable) component).run();
			}
		}
	}

	public void move(float vx, float vy, float vz)
	{
		position.x += vx;
		position.y += vy;
		position.z += vz;
	}

	public void move(float vx, float vy)
	{
		move(vx, vy, 0);
	}

	public void move(Vector2f speed)
	{
		move(speed.x, speed.y);
	}

	public void move(Vector3f speed)
	{
		move(speed.x, speed.y, speed.z);
	}

	public void movedt(float vx, float vy, float vz)
	{
		position.x += vx * Application.DT;
		position.y += vy * Application.DT;
		position.z += vz * Application.DT;
	}

	public void movedt(float vx, float vy)
	{
		movedt(vx, vy, 0);
	}

	public void movedt(Vector2f speed)
	{
		movedt(speed.x, speed.y);
	}

	public void movedt(Vector3f speed)
	{
		movedt(speed.x, speed.y, speed.z);
	}

	public Vector2f getVectorComponent(Vector2f pos)
	{
		if(pos.x == position.x && pos.y == position.y) return new Vector2f();
		return pos.sub(getPosition2D()).normalize();
	}

	public Vector2f getVectorComponent(float x, float y)
	{
		return getVectorComponent(new Vector2f(x, y));
	}

	public Vector2f getVectorComponent(Vector2f pos, Vector2f speed)
	{
		return getVectorComponent(pos).mul(speed);
	}

	public Vector2f getVectorComponent(float x, float y, float sx, float sy)
	{
		return getVectorComponent(new Vector2f(x, y), new Vector2f(sx, sy));
	}

	public Vector2f getVectorComponent(float x, float y, float s)
	{
		return getVectorComponent(x, y, s, s);
	}

	public Vector2f getVectorComponent(Vector2f pos, float s)
	{
		return getVectorComponent(pos, new Vector2f(s, s));
	}

	public void moveToward(Vector2f pos, Vector2f speed)
	{
		move(getVectorComponent(pos, speed));
	}

	public void moveToward(float x, float y,  float sx, float sy)
	{
		move(getVectorComponent(x, y, sx, sy));
	}

	public void moveToward(Vector2f pos, float speed)
	{
		move(getVectorComponent(pos, speed));
	}

	public void moveToward(float x, float y, float speed)
	{
		move(getVectorComponent(x, y, speed));
	}

	public void moveTowarddt(Vector2f pos, Vector2f speed)
	{
		movedt(getVectorComponent(pos, speed));
	}

	public void moveTowarddt(float x, float y,  float sx, float sy)
	{
		movedt(getVectorComponent(x, y, sx, sy));
	}

	public void moveTowarddt(Vector2f pos, float speed)
	{
		movedt(getVectorComponent(pos, speed));
	}

	public void moveTowarddt(float x, float y, float speed)
	{
		movedt(getVectorComponent(x, y, speed));
	}

	public void rotate(float angle)
	{
		rotation.z += angle;
	}

	public void rotate(Vector3f rotation)
	{
		this.rotation.add(rotation);
	}

	public void rotate(float rx, float ry, float rz)
	{
		rotate(new Vector3f(rx, ry, rz));
	}

	public void rotatedt(float angle)
	{
		rotation.z += angle * Application.DT;
	}

	public void rotatedt(Vector3f rotation)
	{
		this.rotation.add(rotation.mul((float) Application.DT));
	}

	public void rotatedt(float rx, float ry, float rz)
	{
		rotatedt(new Vector3f(rx, ry, rz));
	}

	public float getRotation(Vector2f target)
	{
		return (float) Math.toDegrees(Math.atan2(-(position.y - target.y), -(position.x - target.x)));
	}

	public void rotateAround(Vector2f target, float angle)
	{
		setPosition(MathUtil.rotateAround(getPosition2D(), target, angle));
	}

	public void rotateAround(float x, float y, float angle)
	{
		rotateAround(new Vector2f(x, y), angle);
	}

	public void rotateAround(Vector2f target, Vector2f angle)
	{
		setPosition(MathUtil.rotateAround(getPosition2D(), target, angle));
	}

	public void rotateAround(float x, float y, float ax, float ay)
	{
		rotateAround(new Vector2f(x, y), new Vector2f(ax, ay));
	}

	public void rotateArounddt(Vector2f target, float angle)
	{
		setPosition(MathUtil.rotateAround(getPosition2D(), target, (float) (angle * Application.DT)));
	}

	public void rotateArounddt(float x, float y, float angle)
	{
		rotateAround(new Vector2f(x, y), angle);
	}

	public void rotateArounddt(Vector2f target, Vector2f angle)
	{
		setPosition(MathUtil.rotateAround(getPosition2D(), target, angle.mul((float) Application.DT)));
	}

	public void rotateArounddt(float x, float y, float ax, float ay)
	{
		rotateArounddt(new Vector2f(x, y), new Vector2f(ax, ay));
	}

	public float getTowardRotationComponent(Vector2f target)
	{
		float r1 = getRotation(target);
		float r2 = getRotation2D();
		float rt1 = r1 - r2;
		float rt2 = 360 - Math.abs(rt1);
		return Float.compare(Math.max(rt2, Math.abs(rt1)), rt2) == 0 ? rt1 : -rt2;
	}

	public float getTowardRotationComponent(Vector2f target, float speed)
	{
		return getTowardRotationComponent(target) * speed;
	}

	public void placeAround(Vector2f position, float distance, Vector2f radComponent)
	{
		setPosition(new Vector2f(position).add(new Vector2f(distance).mul(radComponent)));
	}

	public void placeAround(Vector2f position, float distance, float angleDegree)
	{
		setPosition(new Vector2f(position).add(new Vector2f(distance).mul(MathUtil.degreeAgnleToRadianVector(angleDegree))));
	}

	public void rotateToward(Vector2f target)
	{
		rotate(getTowardRotationComponent(target));
	}

	public void rotateToward(float x, float y)
	{
		rotateToward(new Vector2f(x, y));
	}

	public void rotateTowarddt(Vector2f target)
	{
		rotate((float) (getTowardRotationComponent(target) * Application.DT));
	}

	public void rotateTowarddt(float x, float y)
	{
		rotateTowarddt(new Vector2f(x, y));
	}

	public void rotateToward(Vector2f target, float speed)
	{
		rotate(getTowardRotationComponent(target, speed));
	}

	public void rotateToward(float x, float y, float speed)
	{
		rotateToward(new Vector2f(x, y), speed);
	}

	public void rotateTowarddt(Vector2f target, float speed)
	{
		rotate((float) (getTowardRotationComponent(target, speed) * Application.DT));
	}

	public void rotateTowarddt(float x, float y, float speed)
	{
		rotateTowarddt(new Vector2f(x, y), speed);
	}

	public Vector2f getAngleVectorComponent(float angle)
	{
		angle = (float) Math.toRadians(angle);
		return getPosition2D().mul((float) Math.cos(angle), (float) Math.sin(angle)).normalize();
	}

	public Vector2f getAngleVectorComponent(float angle, Vector2f speed)
	{
		return getAngleVectorComponent(angle).mul(speed);
	}

	public Vector2f getAngleVectorComponent(float angle, float speed)
	{
		return getAngleVectorComponent(angle).mul(speed);
	}
}
