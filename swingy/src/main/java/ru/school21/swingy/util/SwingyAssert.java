package ru.school21.swingy.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SwingyAssert {

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new RuntimeException(message);
		}
	}

	public static void notEmpty(Collection object, String message) {
		if (object == null || object.isEmpty()) {
			throw new RuntimeException(message);
		}
	}

}
