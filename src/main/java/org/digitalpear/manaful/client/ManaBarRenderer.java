package org.digitalpear.manaful.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.digitalpear.manaful.Manaful;
import org.digitalpear.manaful.init.ManaAttachments;
import org.digitalpear.manaful.init.ManaAttributes;

import java.awt.*;

public class ManaBarRenderer {

    private static final Identifier MANA_FULL = Manaful.id("textures/gui/sprites/hud/mana_full.png");
    private static final Identifier MANA_HALF = Manaful.id("textures/gui/sprites/hud/mana_half.png");
    private static final Identifier MANA_EMPTY = Manaful.id("textures/gui/sprites/hud/mana_empty.png");

    public static final ManaBarRenderer INSTANCE = new ManaBarRenderer();

    public void extract(GuiGraphicsExtractor graphics, int startX, int startY, Player player) {
        if (player != null) {
            if (player.isSpectator() || player.isCreative()) {
                return;
            }
            int size = 9;
            int textureSize = 9;
            int maxRowWidth = 10;

            int actualX = startX - maxRowWidth * (size - 1);
            actualX -= 11;

            double maxMana = player.getAttribute(ManaAttributes.MAX_MANA).getValue();
            double mana = player.getAttached(ManaAttachments.MANA).getMana();
            double manaRatio = mana / maxMana;

            double amount = manaRatio * maxRowWidth;
            for (int i = 0; i < maxRowWidth; i++) {
                int x = actualX + ((size-1) * i);

                graphics.blit(RenderPipelines.GUI_TEXTURED, MANA_EMPTY, x, startY, 0, 0, size, size, textureSize, textureSize, textureSize, textureSize, Color.WHITE.getRGB());

                if (amount > 0){
                    Identifier currentTexture = MANA_FULL;
                    if (amount >= 1){
                        amount--;
                    }
                    else if (amount >= 0.5){
                        amount -= 0.5;
                        currentTexture = MANA_HALF;
                    }
                    else{
                        continue;
                    }
                    graphics.blit(RenderPipelines.GUI_TEXTURED, currentTexture, x, startY, 0, 0, size, size, textureSize, textureSize, textureSize, textureSize, Color.WHITE.getRGB());
                }
            }
            graphics.text(Minecraft.getInstance().font, ((int) mana) + "", actualX, startY - 10, Color.WHITE.getRGB(), true);
        }
    }
}
