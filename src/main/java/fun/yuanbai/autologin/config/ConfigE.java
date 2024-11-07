package fun.yuanbai.autologin.config;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConfigE {

    // 自动登录的开关
    private boolean autoLogin = true;

    // 玩家服务器信息的列表
    private List<PlayerServerInfo> playerServerInfoList = new ArrayList<>();

    // 获取自动登录的开关状态
    public boolean isAutoLogin() {
        return autoLogin;
    }

    // 设置自动登录的开关状态
    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    // 获取玩家服务器信息的列表
    // 如果列表为null，则初始化一个新的ArrayList
    public List<PlayerServerInfo> getPlayerServerInfoList() {
        if (playerServerInfoList == null) {
            playerServerInfoList = new ArrayList<>();
        }
        return playerServerInfoList;
    }

    // 玩家服务器信息的静态内部类
    public static class PlayerServerInfo {
        // 玩家的唯一标识符
        private UUID playerUUID;
        // 服务器IP地址
        private String serverIP;
        // 登录密码
        private String password;

        // 获取玩家的唯一标识符
        public UUID getPlayerUUID() {
            return playerUUID;
        }

        // 设置玩家的唯一标识符
        public void setPlayerUUID(UUID playerUUID) {
            this.playerUUID = playerUUID;
        }

        // 获取服务器IP地址
        public String getServerIP() {
            return serverIP;
        }

        // 设置服务器IP地址
        public void setServerIP(String serverIP) {
            this.serverIP = serverIP;
        }

        // 获取登录密码
        public String getPassword() {
            return password;
        }

        // 设置登录密码
        public void setPassword(String password) {
            this.password = password;
        }
    }
}