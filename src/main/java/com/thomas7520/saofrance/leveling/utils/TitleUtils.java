package com.thomas7520.saofrance.leveling.utils;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class TitleUtils extends ReflectionUtils {

    public void send(final Player player, final String title, final String subtitle, final int fadeInTime, final int showTime, final int fadeOutTime) {
        try {
            final Object chatTitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + title + "\"}");
            final Constructor<?> titleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
            final Object packet = titleConstructor.newInstance(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle, fadeInTime, showTime, fadeOutTime);
            final Object chatsTitle = this.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class).invoke(null, "{\"text\": \"" + subtitle + "\"}");
            final Constructor<?> timingTitleConstructor = this.getNMSClass("PacketPlayOutTitle").getConstructor(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], this.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE);
            final Object timingPacket = timingTitleConstructor.newInstance(this.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle, fadeInTime, showTime, fadeOutTime);
            this.sendPacket(player, packet);
            this.sendPacket(player, timingPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
