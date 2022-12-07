package ru.school21.swingy.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtils {

	public static Integer toInt(String str) {
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> String toStr(T object) {
		if (object == null) {
			return "";
		}

		return object.toString();
	}

	public static <T> String join(String separator, T ... elements) {
		if (elements == null || elements.length == 0) {
			return "";
		}

		return Arrays.stream(elements)
				.filter(new Predicate<T>() {
					@Override
					public boolean test(T element) {
						return element != null;
					}
				})
				.map(new Function<T, String>() {
					@Override
					public String apply(T element) {
						return toStr(element);
					}
				})
				.collect(Collectors.joining(separator));
	}

}
