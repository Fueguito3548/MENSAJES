package com.eventomc.mensajesvida;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingUseTotemEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Mensajes en el chat para todos los jugadores:
 *  - Al usar un totem: "<jugador> ha escapado de la muerte" (amarillo, negrita)
 *  - Al morir:         "La conciencia de <jugador> se ha desvanecido..." (rojo, negrita)
 */
@Mod(MensajesVida.MODID)
public class MensajesVida {

    public static final String MODID = "mensajesvida";

    public MensajesVida() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    // Prioridad LOWEST: se ejecuta al final, cuando otros mods ya decidieron si cancelan el evento.
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void alUsarTotem(LivingUseTotemEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        Component mensaje = Component
                .literal(player.getGameProfile().getName() + " ha escapado de la muerte")
                .withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
        player.server.getPlayerList().broadcastSystemMessage(mensaje, false);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void alMorir(LivingDeathEvent event) {
        if (event.isCanceled()) {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        Component mensaje = Component
                .literal("La conciencia de " + player.getGameProfile().getName() + " se ha desvanecido...")
                .withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
        player.server.getPlayerList().broadcastSystemMessage(mensaje, false);
    }
}
