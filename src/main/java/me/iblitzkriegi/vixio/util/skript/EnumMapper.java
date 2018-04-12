package me.iblitzkriegi.vixio.util.skript;

import ch.njol.skript.localization.Language;
import ch.njol.util.Validate;
import me.iblitzkriegi.vixio.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EnumMapper<E extends Enum<E>> {

	private static final HashMap<String, String> LANG_MAP =
			ReflectionUtils.getField(Language.class, null, "english");

	private Class<E> clazz;
	private Map<String, E> parseMap = new HashMap<>();
	private String node;

	public EnumMapper(Class<E> clazz) {
		this(clazz, clazz.getSimpleName().toLowerCase(Locale.ENGLISH));
	}

	public EnumMapper(Class<E> clazz, String node) {
		Validate.notNull(clazz, node);
		this.node = node;
		this.clazz = clazz;
		map();
	}

	public void map() {
		for (E e : clazz.getEnumConstants()) {
			String lowercase = e.name().toLowerCase(Locale.ENGLISH);
			String replaced =  lowercase.replace("_", " ");
			LANG_MAP.put(node + "." + lowercase, replaced);
			parseMap.put(replaced, e);
		}
	}

	public E parse(String s) {
		return parseMap.get(s);
	}

	public String toString(E e, int flags) {
		return parseMap.entrySet().stream()
				.filter(entry -> entry.getValue() == e)
				.map(entry -> entry.getValue().name())
				.findFirst()
				.orElse(e.name());
	}

	public String getAllNames() {
		StringBuilder builder = new StringBuilder();
		boolean first = true;
		for (E e : clazz.getEnumConstants()) {
			if (!first) {
				builder.append(", ");
			}
			builder.append(toString(e, 0));
			first = false;
		}
		return builder.toString();
	}

}
