package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import model.config.YmlConfig;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {
    private static final String CONFIG_PATH = "bot.yml";

    private static ConfigHandler instance;

    private YmlConfig config;

    public ConfigHandler() throws IOException {
        File file = new File(CONFIG_PATH);
        config = new ObjectMapper(new YAMLFactory()).readValue(file, YmlConfig.class);
    }

    public static void init() throws IOException {
        instance = new ConfigHandler();
    }

    public static YmlConfig config() {
        return instance.config;
    }
}
