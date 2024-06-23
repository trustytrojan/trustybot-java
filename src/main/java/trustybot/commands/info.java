package trustybot.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import discord.client.DiscordClient;
import discord.resources.ApplicationCommand.ChatInputPayload;
import discord.structures.ApplicationCommandOption.Type;
import discord.structures.Embed;
import discord.structures.ApplicationCommandOption.NonSubcommandPayload;
import discord.structures.interactions.ChatInputInteraction;
import discord.structures.interactions.Interaction;
import trustybot.Command;

public class info implements Command {
	public void execute(final ChatInputInteraction interaction, final DiscordClient client) {
		final var response = new Interaction.Response();
		response.embeds = new ArrayList<>();

		CompletableFuture.allOf(
			interaction.options.getUser("user").thenAccept(user -> {
				if (user == null)
					return;
				final var embed = new Embed();
				embed.title = (user.getDiscriminator() == 0) ? user.getUsername() : user.getTag();
				embed.thumbnail = user.avatar.getURL();
				embed.addField("Mention", "<@" + user.getId() + '>');
				embed.addField("Account created", "<t:" + user.getCreatedInstant().getEpochSecond() + ":R>");
				response.embeds.add(embed);
			}),

			interaction.options.getChannel("channel").thenAccept(channel -> {
				if (channel == null)
					return;
				final var embed = new Embed();
				final var guild = channel.getGuild().join();
				if (guild != null)
					embed.setAuthor(guild.getName(), guild.icon.getURL());
				embed.title = "Channel in " + channel.getName();
				embed.url = channel.getUrl();
				embed.addField("Created", "<t:" + channel.getCreatedInstant().getEpochSecond() + ":R>");
				response.embeds.add(embed);
			}),

			interaction.options.getRole("role").thenAccept(role -> {
				if (role == null)
					return;
				final var embed = new Embed();
				final var guild = role.getGuild().join();
				embed.setAuthor(guild.getName(), guild.icon.getURL());
				embed.color = role.getColor().intValue();
				embed.title = "Role in " + role.getName();
				embed.addField("Mention", "<@&" + role.getId() + '>');
				embed.addField("Created", "<t:" + role.getCreatedInstant().getEpochSecond() + ":R>");
				response.embeds.add(embed);
			})
		).join();

		if (response.embeds.isEmpty()) {
			response.content = "no options were provided!";
			response.ephemeral = true;
		}

		interaction.reply(response);
	}

	public ChatInputPayload getData() {
		return new ChatInputPayload("info", "get info on something", List.of(
			new NonSubcommandPayload(Type.USER, "user", "get info on a user"),
			new NonSubcommandPayload(Type.CHANNEL, "channel", "get info on a channel (that i can see)"),
			new NonSubcommandPayload(Type.ROLE, "role", "get info on a role (only in a server)")
		));
	}
}
