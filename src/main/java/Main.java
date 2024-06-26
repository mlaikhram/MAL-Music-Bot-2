import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrameBufferFactory;
import listener.MALMusicListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import util.ConfigHandler;
import util.Constants;

import java.io.IOException;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {

        ConfigHandler.init();

        MALMusicListener malMusicListener = new MALMusicListener();
        JDA jda =  JDABuilder.createLight(ConfigHandler.config().getDiscord().getToken(), Collections.emptyList())
            .enableIntents(GatewayIntent.GUILD_VOICE_STATES)
            .setMemberCachePolicy(MemberCachePolicy.VOICE)
            .enableCache(CacheFlag.VOICE_STATE)
            .addEventListeners(malMusicListener)
            .setActivity(Activity.listening("music"))
            .build();

        jda.updateCommands().addCommands(
            Commands.slash(Constants.commands.ADD_USER, "Add a MyAnimeList user's library to the song list")
                .addOption(OptionType.STRING, Constants.options.USERNAME, "the MyAnimeList username to add", true),
            Commands.slash(Constants.commands.REMOVE_USER, "Remove a MyAnimeList user's library from the song list")
                .addOption(OptionType.STRING, Constants.options.USERNAME, "the MyAnimeList username to remove", true),
            Commands.slash(Constants.commands.LIST_USERS, "List all MyAnimeList users who are currently part of the song list"),
            Commands.slash(Constants.commands.PLAY, "Play a random anime theme based on the current users and settings"),
            Commands.slash(Constants.commands.STOP, "Stop the currently playing anime theme"),
            Commands.slash(Constants.commands.SETTINGS, "Adjust the bot settings")
        ).queue();
    }
}
