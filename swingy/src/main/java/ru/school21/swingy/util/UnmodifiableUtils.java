package ru.school21.swingy.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UnmodifiableUtils {

	@SafeVarargs
	public static <T> Set<T> set(T ... elems) {
		if (elems == null || elems.length == 0) {
			return Collections.emptySet();
		}

		Set<T> set = new HashSet<>();
		Collections.addAll(set, elems);

		return Collections.unmodifiableSet(set);
	}

	@SafeVarargs
	public static <T> List<T> list(T ... elems) {
		if (elems == null || elems.length == 0) {
			return Collections.emptyList();
		}

		List<T> list = new ArrayList<>();
		Collections.addAll(list, elems);

		return Collections.unmodifiableList(list);
	}

	public static <T> List<T> list(List<T> list) {
		if (list == null || list.size() == 0) {
			return Collections.emptyList();
		}

		return Collections.unmodifiableList(list);
	}

}
