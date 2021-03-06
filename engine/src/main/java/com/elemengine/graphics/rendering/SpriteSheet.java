package com.elemengine.graphics.rendering;

import org.joml.Vector2f;

import java.util.ArrayList;

public class SpriteSheet
{
	private ArrayList<Sprite> sprites;
	private Texture texture;

	public SpriteSheet(Texture texture, int lines, int columns, int limit)
	{
		this.sprites = new ArrayList<>();
		this.texture = texture;
		float swidth = texture.getDimension().x / columns / texture.getDimension().x;
		float sheight = texture.getDimension().y / lines / texture.getDimension().y;

		int k = 0;
		for(int i = 0; i < lines; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				if(k++ + 1 > limit) return;
				sprites.add(new Sprite(texture, new Vector2f[]
						{
								new Vector2f(j * swidth, i * sheight),
								new Vector2f(j * swidth, i * sheight + sheight),
								new Vector2f(j * swidth + swidth, i * sheight + sheight),
								new Vector2f(j * swidth + swidth, i * sheight)
						}));
			}
		}
	}


	public Sprite getSprite(int n)
	{
		return sprites.get(n);
	}

	public int getNbSprite()
	{
		return sprites.size();
	}

	public Texture getTexture()
	{
		return texture;
	}
}
