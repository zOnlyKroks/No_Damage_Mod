package com.zOnlyKroks.Hardcoreex.challenge;

import com.zOnlyKroks.Hardcoreex.Hardcore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.IServerWorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * The no-damage challenge!
 * When you take damage, the challenge is failed.
 *
 * @author Qboi123
 */
public class NoDayChallenge extends Challenge {
    public NoDayChallenge() {
        super();
    }

    /**
     * An example tick event.
     * Used to do things like in the no-day challenge.
     *
     * Not needed to use it for this challenge, so it's empty.
     */
    public void tick() {
        IntegratedServer integratedServer = Minecraft.getInstance().getIntegratedServer();
        GameRules gameRules = null;
        if (integratedServer != null) {
            gameRules = integratedServer.getGameRules();

            GameRules.BooleanValue doDayLightCycle = gameRules.get(GameRules.DO_DAYLIGHT_CYCLE);
            doDayLightCycle.set(false, integratedServer);
            Hardcore.LOGGER.debug(doDayLightCycle + "(doDaylightCycle)");

            for (ServerWorld world : integratedServer.getWorlds()) {
                IServerWorldInfo worldInfo = (IServerWorldInfo) (world.getWorldInfo());
                worldInfo.setDayTime(18000);
            }
        }
    }

    /**
     * An example event.
     * This event checks for damage by the player.
     * 
     * @param event the event used for the no-damage challenge.
     */
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event) {
        // Cancel event, we don't want the player to die.
        event.setCanceled(true);

        // Check if there's a client player.
        if (Minecraft.getInstance().player != null) {
            // Check if the entity is the client player.
            if (event.getEntityLiving().getEntityId() == Minecraft.getInstance().player.getEntityId()) {
                // Fail challenge.
                this.failChallenge();
            }
        }
    }
}
