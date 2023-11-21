package listener;

import audio.SessionManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import util.Constants;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class MALMusicListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        switch (event.getName()) {
            case Constants.commands.ADD_USER:
                addUser(event);
                break;

            case Constants.commands.REMOVE_USER:
                removeUser(event);
                break;

            case Constants.commands.LIST_USERS:
                listUsers(event);
                break;

            case Constants.commands.PLAY:
                play(event);
                break;
        }
    }

    private void addUser(SlashCommandInteractionEvent event) {
        SessionManager.getInstance().getSession(event.getGuild()).addUser(event.getOption(Constants.options.USERNAME, OptionMapping::getAsString), event.getHook());
    }

    private void removeUser(SlashCommandInteractionEvent event) {
        SessionManager.getInstance().getSession(event.getGuild()).removeUser(event.getOption(Constants.options.USERNAME, OptionMapping::getAsString), event.getHook());
    }

    private void listUsers(SlashCommandInteractionEvent event) {
        SessionManager.getInstance().getSession(event.getGuild()).listUsers(event.getHook());
    }

    private void play(SlashCommandInteractionEvent event) {
        Member commander = event.getMember();
        Guild guild = event.getGuild();
        AudioManager audioManager = guild.getAudioManager();
        SessionManager.getInstance().getSession(guild).playTheme(audioManager, commander, event.getHook());
    }
}
