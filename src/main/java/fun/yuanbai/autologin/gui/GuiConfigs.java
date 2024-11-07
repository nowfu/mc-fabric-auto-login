package fun.yuanbai.autologin.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextWidget;

import net.minecraft.text.Text;

import static fun.yuanbai.autologin.config.Configs.isAutoLogin;
import static fun.yuanbai.autologin.config.Configs.setAutoLogin;

@Environment(EnvType.CLIENT)
public class GuiConfigs extends Screen {

    private final Screen parentScreen;

    public GuiConfigs(Screen parentScreen) {
        super(Text.literal("配置界面"));
        this.parentScreen = parentScreen;
    }

    private  boolean isAutoLoginEnabled = isAutoLogin();

    // 根据当前状态获取按钮文本
    private Text getTextBasedOnState() {

        return Text.translatable(isAutoLoginEnabled ? "autologin.gui.button.ok" : "autologin.gui.button.off");

    }

    @Override
    protected void init() {

        ButtonWidget autoLoginButton = ButtonWidget.builder(getTextBasedOnState(), button -> {
                    // 切换状态
                    setAutoLogin(!isAutoLoginEnabled);

                    isAutoLoginEnabled = isAutoLogin();

                    // 更新按钮文本
                    button.setMessage(getTextBasedOnState());
                })
                .dimensions(width / 2 - 120, 60, 150, 20)
                .tooltip(Tooltip.of(Text.translatable("autologin.gui.button.cue.isautologin")))
                .build();


        ButtonWidget modMenuButton = ButtonWidget.builder(Text.translatable("autologin.gui.button.finish"), button -> {
                    this.close();
                })
                .dimensions(width / 2 -70 , 200, 150, 20)
                .tooltip(Tooltip.of(Text.translatable("autologin.gui.button.cue.expound")))
                .build();

        // 标题
        TextWidget titleTextWidget = new TextWidget(width / 2 - 200, 10, 100, 20, Text.translatable("autologin.gui.title"), textRenderer);
        // 自动登录标签
        TextWidget autoLoginLabel = new TextWidget(width / 2 - 200, 60, 100, 20, Text.translatable("autologin.gui.label.autologin"), textRenderer);
        autoLoginLabel.setTooltip(Tooltip.of(Text.translatable("autologin.gui.cue.expound")));

        addDrawableChild(autoLoginButton);
        addDrawableChild(modMenuButton);
        addDrawableChild(titleTextWidget);
        addDrawableChild(autoLoginLabel);

    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parentScreen);
        } else {
            MinecraftClient.getInstance().setScreen(null);
        }
    }
}