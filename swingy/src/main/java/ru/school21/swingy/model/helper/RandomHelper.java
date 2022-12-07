package ru.school21.swingy.model.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomHelper {

	private static final Random RANDOM = new Random(UUID.randomUUID().hashCode());

	public static int between(int from, int toExclusive) {
		return RANDOM.nextInt(toExclusive - from) + from;
	}

	public static int before(int max) {
		return RANDOM.nextInt(max);
	}

	public static boolean yes() {
		return RANDOM.nextBoolean();
	}

	public static boolean yesWithProbability(double probability) {
		return RANDOM.nextInt(100) < (int) (probability * 100);
	}

}
