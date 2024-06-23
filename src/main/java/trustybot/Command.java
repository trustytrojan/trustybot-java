package trustybot;

import discord.client.DiscordClient;
import discord.resources.ApplicationCommand.ChatInputPayload;
import discord.structures.interactions.ChatInputInteraction;

public interface Command {
	void execute(final ChatInputInteraction interaction, final DiscordClient client);
	ChatInputPayload getData();
}
