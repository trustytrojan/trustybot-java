package trustybot;
import java.util.List;
import java.util.Map;

import org.reflections.Reflections;

import discord.client.BotDiscordClient;
import discord.enums.GatewayIntent;
import discord.resources.ApplicationCommand.ChatInputPayload;
import discord.resources.guilds.Guild;
import discord.structures.interactions.ChatInputInteraction;
import discord.structures.interactions.Interaction;

public final class Trustybot extends BotDiscordClient {
	@SuppressWarnings("unchecked")
	private static final Map<String, Command> commands =
		Map.ofEntries(
			new Reflections("trustybot.commands").getSubTypesOf(Command.class).stream()
				.map(c -> Map.entry(c.getSimpleName(), Util.invokeDefaultConstructor(c)))
				.toArray(Map.Entry[]::new));

	private Trustybot(final String token) {
		super(token, true);

		gateway.connectAndIdentify(
			GatewayIntent.GUILDS
		);
	}

	private static List<ChatInputPayload> commandData;

	@Override
	protected void onReady() {
		System.out.println("Logged in as " + clientUser.getTag() + '!');
		commandData = commands.values().stream().map(Command::getData).toList();
	}

	@Override
	protected void onGuildCreate(final Guild guild) {
		guild.commands.set(commandData);
	}

	@Override
	protected void onInteractionCreate(final Interaction interaction) {
		try {
			if (interaction instanceof final ChatInputInteraction cii) {
				commands.get(cii.commandName).execute(cii, this);
			}
		} catch (final Exception e) {
			interaction.reply("**this is an error**\n```java\n" + Util.stackTraceOf(e) + "```");
		}
	}

	public static void main(final String[] args) {
		new Trustybot(discord.util.Util.readFile("token"));
	}
}