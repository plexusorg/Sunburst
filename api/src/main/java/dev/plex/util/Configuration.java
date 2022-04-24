package dev.plex.util;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Configuration extends YamlConfiguration
{
    /**
     * The plugin instance
     */
    private JavaPlugin plugin;

    /**
     * The File instance
     */
    private File file;

    /**
     * The file name
     */
    private String name;

    /**
     * Whether new entries were added to the file automatically
     */
    private boolean added = false;

    /**
     * Creates a config object
     *
     * @param plugin The plugin instance
     * @param name   The file name
     */
    public Configuration(JavaPlugin plugin, String name)
    {
        this(plugin, name, true);
    }

    public Configuration(JavaPlugin plugin, String name, boolean fromJar)
    {
        this.plugin = plugin;
        if (!plugin.getDataFolder().exists())
        {
            plugin.getDataFolder().mkdir();
        }
        this.file = new File(plugin.getDataFolder(), name);
        this.name = name;

        if (!file.exists())
        {
            if (fromJar)
            {
                saveDefault();
            } else
            {
                try
                {
                    file.createNewFile();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load()
    {
        this.load(true);
    }

    /**
     * Loads the configuration file
     */
    public void load(boolean loadFromFile)
    {
        try
        {
            if (loadFromFile)
            {
                YamlConfiguration externalYamlConfig = YamlConfiguration.loadConfiguration(file);
                InputStreamReader internalConfigFileStream = new InputStreamReader(plugin.getResource(name), StandardCharsets.UTF_8);
                YamlConfiguration internalYamlConfig = YamlConfiguration.loadConfiguration(internalConfigFileStream);

                // Gets all the keys inside the internal file and iterates through all of it's key pairs
                for (String string : internalYamlConfig.getKeys(true))
                {
                    // Checks if the external file contains the key already.
                    if (!externalYamlConfig.contains(string))
                    {
                        // If it doesn't contain the key, we set the key based off what was found inside the plugin jar
                        externalYamlConfig.setComments(string, internalYamlConfig.getComments(string));
                        externalYamlConfig.setInlineComments(string, internalYamlConfig.getInlineComments(string));
                        externalYamlConfig.set(string, internalYamlConfig.get(string));
                        Logger.log("Setting key: " + string + " in " + this.name + " to the default value(s) since it does not exist!");
                        added = true;
                    }
                }
                if (added)
                {
                    externalYamlConfig.save(file);
                    Logger.log("Saving new file...");
                    added = false;
                }
            }
            super.load(file);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Saves the configuration file
     */
    public void save()
    {
        try
        {
            super.save(file);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Moves the configuration file from the plugin's resources folder to the data folder (plugins/Plex/)
     */
    private void saveDefault()
    {
        plugin.saveResource(name, false);
    }
}
