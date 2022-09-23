package com.themysterys.mcciutils.mixin.menu;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.themysterys.mcciutils.util.chat.CustomFont.joinIcon;

@Mixin(TitleScreen.class)
public class QuickJoin extends Screen {
    protected QuickJoin(Text title) {
        super(title);
    }

    @Inject(at = @At("TAIL"), method = "init")
    public void addQuickJoinButton(CallbackInfo ci) {
        int l = this.height / 4 + 48;

        ServerAddress address = new ServerAddress("play.mccisland.net", 25565);
        ServerInfo info = new ServerInfo("MCC Island", "play.mccisland.net", false);
        info.setResourcePackPolicy(ServerInfo.ResourcePackPolicy.ENABLED);
        this.addDrawableChild(new ButtonWidget(
                this.width / 2 + 104,
                l,
                20,
                20,
                joinIcon(),
                (button) -> ConnectScreen.connect(
                        MinecraftClient.getInstance().currentScreen,
                        MinecraftClient.getInstance(),
                        address,
                        info
                )
        ));
    }


}
