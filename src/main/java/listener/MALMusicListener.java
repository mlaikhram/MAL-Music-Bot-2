package listener;

import audio.SessionManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.managers.AudioManager;
import util.Constants;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Collection;


public class MALMusicListener extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        event.deferReply(event.getName().equals(Constants.commands.STOP)).queue();
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

            case Constants.commands.STOP:
                stop(event);
                break;

            case Constants.commands.SETTINGS:
                settings(event);
                break;
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        event.deferEdit().queue();
        switch (event.getComponentId()) {
            case Constants.componentids.ANIME_TYPES_DROPDOWN:
                setFilters(event.getValues(), event);
                break;

            case Constants.componentids.ANIME_BALANCER_DROPDOWN:
                setBalancer(event.getValues().get(0), event);
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

    private void stop(SlashCommandInteractionEvent event) {
        Member commander = event.getMember();
        Guild guild = event.getGuild();
        AudioManager audioManager = guild.getAudioManager();
        SessionManager.getInstance().getSession(guild).stopTheme(audioManager, commander, event.getHook());
    }

    private void settings(SlashCommandInteractionEvent event) {
        SessionManager.getInstance().getSession(event.getGuild()).displayCurrentSettings(event.getHook());
    }

    private void setFilters(Collection<String> allowedTypes, StringSelectInteractionEvent event) {
        SessionManager.getInstance().getSession(event.getGuild()).setFilters(allowedTypes, event.getHook());
    }

    private void setBalancer(String balancer, StringSelectInteractionEvent event) {
        SessionManager.getInstance().getSession(event.getGuild()).setBalancer(balancer, event.getHook());
    }
}
