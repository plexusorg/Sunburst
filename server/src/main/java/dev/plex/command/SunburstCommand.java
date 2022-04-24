package dev.plex.command;

import dev.plex.Sunburst;
import dev.plex.command.util.CommandInfo;
import dev.plex.command.util.CommandPerms;
import dev.plex.command.util.RequiredSource;
import dev.plex.exception.CommandNotFoundException;
import dev.plex.util.CommandUtil;
import dev.plex.util.ComponentUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public abstract class SunburstCommand extends Command implements PluginIdentifiableCommand
{
    protected final Sunburst plugin = Sunburst.inst();
    private CommandInfo commandInfo;
    private CommandPerms commandPerms;

    public SunburstCommand()
    {
        super("");
        this.commandInfo = this.getClass().getDeclaredAnnotation(CommandInfo.class);
        this.commandPerms = this.getClass().getDeclaredAnnotation(CommandPerms.class);

        this.setLabel(commandInfo.name());
        this.setName(commandInfo.name());

        this.setDescription(commandInfo.description());
        this.setUsage(commandInfo.usage().replace("<command>", commandInfo.name()));
        this.setAliases(Arrays.asList(commandInfo.aliases()));

        this.setPermission(commandPerms.permission().isEmpty() ? null : commandPerms.permission());
        try
        {
            CommandUtil.unregisterCommand(this.getName());
            this.getAliases().forEach(CommandUtil::unregisterCommand);
        } catch (CommandNotFoundException ignored)
        {

        }
        plugin.getServer().getCommandMap().register(plugin.getDescription().getName().toLowerCase(Locale.ROOT), this);
    }

    public abstract Component execute(@NotNull CommandSender sender, @Nullable Player player, String[] args);

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args)
    {
        if (this.getPermission() != null && !plugin.getObjectHolder().getPermissionHandler().hasPermission(sender, this.getPermission()))
        {
            sender.sendMessage(confMsg("noPermission", this.getPermission()));
            return true;
        }
        if (this.commandPerms.source() == RequiredSource.CONSOLE && sender instanceof Player)
        {
            sender.sendMessage(confMsg("consoleOnly"));
            return true;
        }
        if (this.commandPerms.source() == RequiredSource.PLAYER && sender instanceof ConsoleCommandSender)
        {
            sender.sendMessage(confMsg("playerOnly"));
            return true;
        }
        if (sender instanceof Player player)
        {
            Component component = this.execute(sender, player, args);
            if (component != null)
            {
                player.sendMessage(component);
            }
            return true;
        } else
        {
            Component component = this.execute(sender, (Player) null, args);
            if (component != null)
            {
                sender.sendMessage(component);
            }
            return true;
        }
    }

    @Override
    public @NotNull Plugin getPlugin()
    {
        return plugin;
    }

    protected Component mini(String text)
    {
        return MiniMessage.miniMessage().deserialize(text);
    }

    protected String miniString(Component component)
    {
        return MiniMessage.miniMessage().serialize(component);
    }

    protected Component confMsg(String entry, Object... objects)
    {
        return ComponentUtil.configComponent(entry, objects);
    }

    protected Component confMsg(String entry, TagResolver[] tagResolvers, Object... objects)
    {
        return ComponentUtil.configComponent(entry, tagResolvers, objects);
    }

    protected Component usage()
    {
        return confMsg("incorrectUsage", getUsage());
    }

    @NotNull
    protected Player getNonNullPlayer(String username)
    {
        return Objects.requireNonNull(Bukkit.getPlayer(username));
    }
}
