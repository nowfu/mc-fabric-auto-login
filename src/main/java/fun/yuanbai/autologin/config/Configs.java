package fun.yuanbai.autologin.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import fun.yuanbai.autologin.Reference;
import fun.yuanbai.autologin.util.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

// 定义Configs类，用于处理模组的配置
public class Configs {

    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    // 定义密码保存文件的名称，使用了模组ID作为文件名
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";

    //获取游戏配置文件目录
    private static final String CONFIG_FILE_PATH = FileUtils.getConfigDirectory() +"\\"+ CONFIG_FILE_NAME; // 替换为实际的配置文件路径

    // 统一的ConfigE实例
    private static ConfigE config;

    // 静态块，在类加载时初始化config实例
    static {
        loadConfig();
    }

    // 检查配置文件是否存在
    private static boolean configFileExists() {

        return Files.exists(Paths.get(Configs.CONFIG_FILE_PATH));

    }

    // 读取配置文件并初始化config实例
    private static void loadConfig() {
        if (configFileExists()) {
            Gson gson = new Gson();
            try (Reader settingsReader = new FileReader(CONFIG_FILE_PATH)) {
                config = gson.fromJson(settingsReader, ConfigE.class);
            }catch (JsonSyntaxException e) {
                // 处理 JSON 语法错误
                config = new ConfigE();
                saveConfig(); // 重新保存配置
            } catch (IOException e) {
                // 读取失败，重新创建配置文件
                config = new ConfigE();
                saveConfig();
            }
        } else {
            // 配置文件不存在，创建新的配置文件实例
            config = new ConfigE();
            saveConfig();
        }
    }

    // 保存配置文件的方法
    public static void saveConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer settingsWriter = new FileWriter(CONFIG_FILE_PATH)) {

            System.out.println(gson.toJson(CONFIG_FILE_PATH));

            gson.toJson(config, settingsWriter);
        } catch (IOException e) {
            LOGGER.error("保存配置文件失败：", e);
        }
    }

    // 添加或更新玩家服务器信息的方法
    public static void updateOrCreatePlayerServerInfo(ConfigE.PlayerServerInfo nowPlayerServerInfo) {
        for (ConfigE.PlayerServerInfo info : config.getPlayerServerInfoList()) {
            //如果已经存在则更新并退出方法
            if (info.getPlayerUUID().equals(nowPlayerServerInfo.getPlayerUUID()) && info.getServerIP().equals(nowPlayerServerInfo.getServerIP())) {
                info.setPassword(nowPlayerServerInfo.getPassword());
                saveConfig();
                return;
            }
        }
        config.getPlayerServerInfoList().add(nowPlayerServerInfo); // 将新的玩家服务器信息添加到列表中
        saveConfig();
    }

    // 根据玩家UUID和服务器IP获取密码的方法
    public static String getPassword(UUID playerUUID, String serverIP) {
        for (ConfigE.PlayerServerInfo info : config.getPlayerServerInfoList()) {
            if (info.getPlayerUUID().equals(playerUUID) && info.getServerIP().equals(serverIP)) {
                return info.getPassword();
            }
        }
        return null;
    }

    // 设置自动登录开关
    public static void setAutoLogin(boolean autoLogin) {
        config.setAutoLogin(autoLogin);
        saveConfig();
    }

    // 获取自动登录开关状态
    public static boolean isAutoLogin() {
        return config.isAutoLogin();
    }

}