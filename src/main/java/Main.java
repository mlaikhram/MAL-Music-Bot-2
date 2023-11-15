import listener.MALMusicListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import resource.AnimeThemes;
import resource.MAL;
import util.ConfigHandler;
import util.Constants;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        ConfigHandler.init();

        MAL.getList("PeaQueueAre", "watching");
        AnimeThemes.getList(List.of("16498", "5114", "32182", "9253", "37999"));
//        MALMusicListener malMusicListener = new MALMusicListener();
//        JDA jda =  JDABuilder.createLight(ConfigHandler.config().getDiscord().getToken(), Collections.emptyList())
//            .addEventListeners(malMusicListener)
//            .setActivity(Activity.listening("music"))
//            .build();
//
//        jda.updateCommands().addCommands(
//            Commands.slash(Constants.commands.ADD_USER, "Add a MyAnimeList user's library to the song list")
//                .addOption(OptionType.STRING, Constants.options.USERNAME, "the MyAnimeList username to add", true),
//            Commands.slash(Constants.commands.REMOVE_USER, "Remove a MyAnimeList user's library from the song list")
//                .addOption(OptionType.STRING, Constants.options.USERNAME, "the MyAnimeList username to remove", true),
//            Commands.slash(Constants.commands.LIST_USERS, "List all MyAnimeList users who are currently part of the song list")
//        ).queue();
    }
}
