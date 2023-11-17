package util;

import model.iwa.IwaUser;
import model.jikan.JikanAnimeStats;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.List;

public class Embeds {

    public static MessageEmbed List(String title, List<String> items) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(String.join("\n", items))
                .setColor(3035554)
                .build();
    }

    public static MessageEmbed UserAddPending(IwaUser user, String message) {
        return UserAddPending(user, message, null);
    }

    public static MessageEmbed UserAddPending(IwaUser user, String message, JikanAnimeStats expectedStats) {
        return UserBase(user, 15779389, expectedStats).setFooter(message).build();
    }

    public static MessageEmbed UserAddComplete(IwaUser user) {
        return UserBase(user, 3035554).build();
    }

    private static EmbedBuilder UserBase(IwaUser user, int color) {
        return UserBase(user, color, null);
    }

    private static EmbedBuilder UserBase(IwaUser user, int color, JikanAnimeStats expectedStats) {
        return new EmbedBuilder()
                .setTitle(user.getUsername())
                .setDescription(String.format("[MyAnimeList Page](%s)", user.getProfileUrl()))
                .setColor(color)
                .setThumbnail(user.getProfileImageUrl())
                .addField("Completed", user.getMalIds(Constants.myanimelist.status.completed.toString()).size() + (expectedStats == null ? "" : ("/" + expectedStats.getCompleted())), true)
                .addField("Watching", user.getMalIds(Constants.myanimelist.status.watching.toString()).size() + (expectedStats == null ? "" : ("/" + expectedStats.getWatching())), true);
    }

    public static MessageEmbed PendingEmbed(String title, String message) {
        return BasicEmbed(title, message, 15779389);
    }

    public static MessageEmbed CompleteEmbed(String title, String message) {
        return BasicEmbed(title, message, 3035554);
    }

    public static MessageEmbed ErrorEmbed(String title, String message) {
        return BasicEmbed(title, message, 10628913);
    }

    private static MessageEmbed BasicEmbed(String title, String message, int color) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(message)
                .setColor(color)
                .build();
    }
}
