package dev.plex.messaging;

import java.util.UUID;

public class MessageData
{
    private final UUID sender;
    private final UUID receiver;
    private final String message;

    public MessageData(UUID sender, UUID receiver, String message)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public UUID getSender()
    {
        return sender;
    }

    public UUID getReceiver()
    {
        return receiver;
    }

    public String getMessage()
    {
        return message;
    }
}
