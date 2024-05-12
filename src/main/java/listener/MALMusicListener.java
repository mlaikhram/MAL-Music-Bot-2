package listener;

import audio.GuildSession;
import audio.SessionManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
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
            case Constants.componentids.ANIME_STATUSES_DROPDOWN:
                setStatusFilter(event.getValues(), event);
                break;

            case Constants.componentids.ANIME_TYPES_DROPDOWN:
                setTypeFilter(event.getValues(), event);
                break;

            case Constants.componentids.ANIME_BALANCER_DROPDOWN:
                setBalancer(event.getValues().get(0), event);
                break;

            case Constants.componentids.AUTOPLAY_SELECTOR:
                autoplayManager(event.getValues().get(0), event);
        }
    }

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        long myId = event.getJDA().getSelfUser().getIdLong();
        AudioChannelUnion leftChannel = event.getChannelLeft();
        AudioChannelUnion joinedChannel = event.getChannelJoined();
        GuildSession currentSession = SessionManager.getInstance().getSession(event.getGuild());
        if (leftChannel != null && amAlone(leftChannel, myId)) {
//            event.getEntity().getUser().openPrivateChannel().queue((channel) -> {
//                channel.sendMessage("You left me alone in the voice channel!").queue();
//            });
            currentSession.setChannelEmpty(true);
            if (currentSession.scheduler.isPlayingTrack()) {
                currentSession.stopTheme(event.getGuild().getAudioManager(), null, null);
            }
            leftChannel.getGuild().getAudioManager().closeAudioConnection();
        }
        else if (joinedChannel != null && !amAlone(joinedChannel, myId)) {
            currentSession.setChannelEmpty(false);
        }
    }

    private boolean amAlone(AudioChannelUnion leftChannel, long myId) {
        return leftChannel.getMembers().stream().anyMatch((member) -> member.getIdLong() == myId) && leftChannel.getMembers().size() == 1;
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

    private void setStatusFilter(Collection<String> allowedStatuses, StringSelectInteractionEvent event) {
        SessionManager.getInstance().getSession(event.getGuild()).setStatusFilter(allowedStatuses, event.getHook());
    }

    private void setTypeFilter(Collection<String> allowedTypes, StringSelectInteractionEvent event) {
        SessionManager.getInstance().getSession(event.getGuild()).setTypeFilter(allowedTypes, event.getHook());
    }

    private void setBalancer(String balancer, StringSelectInteractionEvent event) {
        SessionManager.getInstance().getSession(event.getGuild()).setBalancer(balancer, event.getHook());
    }

    private void autoplayManager(String autoplay, StringSelectInteractionEvent event) {
        SessionManager.getInstance().getSession(event.getGuild()).autoplayManager(autoplay, event.getHook());
    }
}
