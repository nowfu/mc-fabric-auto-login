package fun.yuanbai.autologin.util;

import net.minecraft.client.MinecraftClient;
import java.io.File;

public class FileUtils {

     //获取Minecraft的配置文件目录。
    public static String getConfigDirectory() {
        File configFile = new File(MinecraftClient.getInstance().runDirectory, "config");
        return configFile.getPath();
    }
}