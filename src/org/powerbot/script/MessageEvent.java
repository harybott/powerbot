package org.powerbot.script;

import java.util.EventListener;

import org.powerbot.bot.rs3.event.AbstractEvent;

/**
 * A message event that is dispatched when a new message is dispatched in the game.
 *
 */
public class MessageEvent extends AbstractEvent {
	private static final long serialVersionUID = 4178447203851407187L;
	public static final int ID = 0x20;
	private final int id;
	private final String sender, message;

	public MessageEvent(final int id, final String sender, final String message) {
		setId(ID);
		this.id = id;
		this.sender = sender;
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dispatch(final EventListener eventListener) {
		((MessageListener) eventListener).messaged(this);
	}

	/**
	 * @return The Id of this message (id).
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The name of the sender of this message.
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @return The contents of this message.
	 */
	public String getMessage() {
		return message;
	}
}