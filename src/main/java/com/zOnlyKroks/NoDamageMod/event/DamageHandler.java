package com.zOnlyKroks.NoDamageMod.event;

import com.zOnlyKroks.NoDamageMod.NoDamageMod;
import com.zOnlyKroks.NoDamageMod.client.gui.DeleteWorldScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class DamageHandler {


    @SubscribeEvent
    public void playerDamageEvent(LivingDamageEvent event){
        Entity entity = event.getEntity();
        if(event.getEntity() instanceof PlayerEntity) {
            NoDamageMod.LOGGER.debug("Worked?");
            Minecraft.getInstance().displayGuiScreen(new DeleteWorldScreen(event.getSource().getDeathMessage(event.getEntityLiving())));
            PlayerEntity player = (PlayerEntity)entity;
            player.setGameType(GameType.SPECTATOR);
        }
    }

}
