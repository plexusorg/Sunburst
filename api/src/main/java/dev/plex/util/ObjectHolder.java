package dev.plex.util;

import dev.plex.permission.IPermissionHandler;
import dev.plex.plugin.PluginBase;
import dev.plex.storage.IStorage;
import io.papermc.paper.chat.ChatRenderer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ObjectHolder implements PluginBase
{
    private IPermissionHandler permissionHandler;
    private IStorage storageSystem;
    private ChatRenderer chatRenderer;

    @Override
    public IPermissionHandler getPermissionHandler()
    {
        return this.permissionHandler;
    }

    @Override
    public void setPermissionHandler(IPermissionHandler permissionHandler)
    {
        this.permissionHandler = permissionHandler;
    }

    @Override
    public IStorage getStorageSystem()
    {
        return this.storageSystem;
    }

    @Override
    public void setStorageSystem(IStorage storageSystem)
    {
        this.storageSystem = storageSystem;
    }

    @Override
    public ChatRenderer getChatRenderer()
    {
        return chatRenderer;
    }

    @Override
    public void setChatRenderer(ChatRenderer renderer)
    {
        this.chatRenderer = renderer;
    }

}
