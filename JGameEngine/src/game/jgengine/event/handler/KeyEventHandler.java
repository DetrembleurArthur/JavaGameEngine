package game.jgengine.event.handler;

public interface KeyEventHandler
{
	void keyPressedEventHandler(int key);

	void keyReleasedEventHandler(int key);

	void keyRepeatedEventHandler(int key);
}
