package listener;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import util.Constants;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class MALMusicListener extends ListenerAdapter {



    public MALMusicListener() {

    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
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
        }
    }

    private void addUser(SlashCommandInteractionEvent event) {
        event.reply("I'm alive? I can't add " + event.getOption(Constants.options.USERNAME, OptionMapping::getAsString) + " right now, but I will some day soon!").queue();
    }

    private void removeUser(SlashCommandInteractionEvent event) {
        event.reply("I'm alive? I can't remove " + event.getOption(Constants.options.USERNAME, OptionMapping::getAsString) + " right now, but I will some day soon!").queue();
    }

    private void listUsers(SlashCommandInteractionEvent event) {
        event.reply("I'm alive? I can't list users right now, but I will some day soon!").queue();
    }
}
