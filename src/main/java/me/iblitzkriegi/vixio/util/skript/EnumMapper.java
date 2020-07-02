package me.iblitzkriegi.vixio.util.skript;

import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.localization.Language;
import ch.njol.util.Validate;
import ch.njol.yggdrasil.Fields;
import me.iblitzkriegi.vixio.util.ReflectionUtils;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EnumMapper<E extends Enum<E>> {

	private static final HashMap<String, String> LANG_MAP =
			ReflectionUtils.getField(Language.class, null, "english");

	private Class<E> clazz;
	private Map<String, E> parseMap = new HashMap<>();
	private String node;

	public static <C extends Enum<C>> SimpleType<C> register(Class<C> clazz, String name, String pattern) {
		Validate.notNull(clazz, name, pattern);
		return register(clazz, name, pattern, clazz.getSimpleName().toLowerCase(Locale.ENGLISH));
	}

	public static <C extends Enum<C>> SimpleType<C> register(Class<C> clazz, String name, String pattern, String node) {
		Validate.notNull(clazz, name, pattern, node);
		final EnumMapper<C> PARSER = new EnumMapper<>(clazz, node);
		SimpleType<C> type = new SimpleType<C>(clazz, name, pattern) {

			@Override
			public C parse(String name, ParseContext context) {
				return PARSER.parse(name);
			}

			@Override
			public String toString(C value, int flags) {
				return PARSER.toString(value, flags);
			}

			@Override
			public boolean canParse(ParseContext pc) {
				return true;
			}

		};
		type.serializer(PARSER.getSerializer());
		return type;
	}

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
		for (Map.Entry<String, E> entry : parseMap.entrySet()) {
			if (entry.getValue() == e) {
				return entry.getKey();
			}
		}
		return e.name();
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

	public Serializer<E> getSerializer() {
		return new Serializer<E>() {
			@Override
			public Fields serialize(E o) throws NotSerializableException {
				Fields fields = new Fields();
				fields.putObject("name", o.name());
				return fields;
			}

			@Override
			public void deserialize(E o, Fields f) throws StreamCorruptedException, NotSerializableException {
			}

			@Override
			protected E deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
				try {
					return Enum.valueOf(clazz, (String) fields.getObject("name"));
				} catch (IllegalArgumentException | ClassCastException e) {
					throw new StreamCorruptedException();
				}
			}

			@Override
			public boolean mustSyncDeserialization() {
				return false;
			}

			@Override
			protected boolean canBeInstantiated() {
				return false;
			}
		};
	}

}
