package dev.plex.plugin;


import dev.plex.permission.IPermissionHandler;
import dev.plex.storage.IStorage;
import io.papermc.paper.chat.ChatRenderer;

public interface PluginBase
{

    IPermissionHandler getPermissionHandler();
    void setPermissionHandler(IPermissionHandler permissionHandler);

    IStorage getStorageSystem();
    void setStorageSystem(IStorage storageSystem);

    ChatRenderer getChatRenderer();
    void setChatRenderer(ChatRenderer renderer);
}
