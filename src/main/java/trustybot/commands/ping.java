package trustybot.commands;

import discord.client.DiscordClient;
import discord.resources.ApplicationCommand.ChatInputPayload;
import discord.structures.interactions.ChatInputInteraction;
import trustybot.Command;

public final class ping implements Command {
	public void execute(final ChatInputInteraction interaction, final DiscordClient client) {
		interaction.reply("`" + client.gateway.getPing() + "ms`");
	}

	public ChatInputPayload getData() {
		return new ChatInputPayload("ping", "ping");
	}
}
