package trustybot;

import java.util.Map;

import sj.Sj;
import sj.SjObject;
import sj.SjSerializable;

public class TGuild implements SjSerializable {
	public static class ChannelSetting {
		public String id;

		@Override
		public String toString() {
			return (id != null) ? ("<#" + id + '>') : "(not set)";
		}
	}

	public String embedColor = "ff00ff";
	public final ChannelSetting loggingChannel = new ChannelSetting();

	public TGuild() {}

	public TGuild(final SjObject o) {
		embedColor = o.getString("embed_color");
		loggingChannel.id = o.getString("logging_channel");
	}

	@Override
	public String toJsonString() {
		return Sj.write(Map.of(
			"embed_color", embedColor,
			"logging_channel", loggingChannel.id
		));
	}

	@SuppressWarnings("unchecked")
	public static Map<String, TGuild> loadFromFile(final String path) {
		return Map.ofEntries(
			Sj.parseObject(discord.util.Util.readFile(path)).entrySet().stream()
				.map(e -> Map.entry(e.getKey(), new TGuild((SjObject) e.getValue())))
				.toArray(Map.Entry[]::new));
	}

	public static void saveToFile(final String path, final Map<String, TGuild> tguilds) {
		discord.util.Util.writeFile(path, Sj.write(tguilds));
	}
}
