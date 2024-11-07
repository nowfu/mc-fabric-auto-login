package fun.yuanbai.autologin.event;

import fun.yuanbai.autologin.config.ConfigE;
import fun.yuanbai.autologin.util.EncryptionUtil;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.UUID;

import static fun.yuanbai.autologin.config.Configs.*;


public class AutoLoginHandler implements ClientReceiveMessageEvents.Game, ClientSendMessageEvents.Command {

    @Override
    public void onReceiveGameMessage(Text message, boolean overlay) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.player.networkHandler.getServerInfo() != null) {

            //获取服务器id
            String serverIp = client.player.networkHandler.getServerInfo().address;


            // 获取玩家的GameProfile
            //GameProfile gameProfile = client.getGameProfile();

            //玩家UUID
            //UUID uuid = gameProfile.getId();

            UUID uuid = client.player.getGameProfile().getId();

            //服务器提醒玩家登录且开启了自动登录
            if (message.getString().contains("/login")&&isAutoLogin()) {

               String password = getPassword(uuid,serverIp);

               if (password != null) {

                   password = EncryptionUtil.decrypt(password);

                   if (MinecraftClient.getInstance().player != null) {
                       //发送登录命令
                        MinecraftClient.getInstance().player.networkHandler.sendCommand("login " +password);
                   }

               }

            }
        }
    }

    @Override
    public void onSendCommandMessage(String command) {

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.player.networkHandler.getServerInfo() != null && command.contains("login")&&isAutoLogin()) {

            // 获取玩家UUID
            UUID uuid = client.player.getGameProfile().getId();

            String serverIp = client.player.networkHandler.getServerInfo().address;


            String[] parts = command.split(" ");

            if (parts.length > 1) {
                String password = parts[1];

                if (password != null && password.length() >= 4) {

                    ConfigE.PlayerServerInfo playerServerInfo = new ConfigE.PlayerServerInfo();

                    playerServerInfo.setPlayerUUID(uuid);

                    playerServerInfo.setServerIP(serverIp);

                    playerServerInfo.setPassword(EncryptionUtil.encrypt(password));

                    updateOrCreatePlayerServerInfo(playerServerInfo);

                }

            }

        }
    }

}
