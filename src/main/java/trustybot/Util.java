package trustybot;

import java.io.PrintWriter;
import java.io.StringWriter;

final class Util {
	static <T> T invokeDefaultConstructor(final Class<T> clazz) {
		try {
			return clazz.getConstructor().newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	static String stackTraceOf(final Exception e) {
		final var sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
