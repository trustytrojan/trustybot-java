package trustybot.commands;

import java.util.List;

import discord.client.DiscordClient;
import discord.resources.ApplicationCommand.ChatInputPayload;
import discord.structures.ApplicationCommandOption.Type;
import discord.structures.ApplicationCommandOption.NonSubcommandPayload;
import discord.structures.ApplicationCommandOption.SubcommandPayload;
import discord.structures.interactions.ChatInputInteraction;
import trustybot.Command;

public class config implements Command {
	public void execute(final ChatInputInteraction interaction, final DiscordClient client) {
		
	}

	public ChatInputPayload getData() {
		return new ChatInputPayload("config", "view/modify your server configuration", List.of(
			new SubcommandPayload("view", "view this server's configuration"),
			new SubcommandPayload("set", "set a configuration value", List.of(
				new NonSubcommandPayload(Type.STRING, "embed_color", "change the default color of my embeds"),
				new NonSubcommandPayload(Type.CHANNEL, "logging_channel", "set the channel i send server logs to")
			))
		));
	}
}
