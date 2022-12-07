package ru.school21.swingy.model.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import ru.school21.swingy.model.entity.GameMap;
import ru.school21.swingy.model.entity.Villain;
import ru.school21.swingy.type.DirectionType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GameMapHelper {

	public static GameMap create(int heroLevel) {
		int mapSize = (heroLevel - 1) * 5 + 10 - (heroLevel % 2);
		int heroX = mapSize / 2;
		int heroY = mapSize / 2;

		GameMap gameMap = new GameMap();
		gameMap.setSize(mapSize);
		gameMap.setHeroX(heroX);
		gameMap.setHeroY(heroY);
		gameMap.setTerrain(TerrainHelper.generate());
		gameMap.setLastStepDirection(DirectionType.EAST);

		int villainsNumber = getVillainsNumber(mapSize);
		Set<Villain> villains = new HashSet<>(villainsNumber);

		List<Pair<Integer, Integer>> availableCoordinates = getCoordinates(mapSize, heroX, heroY);

		int villainLevel = heroLevel + 2;
		double expIncrease = 1.5;
		double artefactProbability = getArtefactProbability(heroLevel, villainLevel);
		double numberRate = 0.05;
		int villainCount = (int) (villainsNumber * numberRate);
		boolean added = villains.addAll(createVillains(villainLevel, expIncrease, artefactProbability, villainCount, availableCoordinates));

		villainLevel = heroLevel + 1;
		expIncrease = 1.2;
		artefactProbability = getArtefactProbability(heroLevel, villainLevel);
		numberRate = 0.10 + (added ? 0 : numberRate);
		villainCount = (int) (villainsNumber * numberRate);
		added = villains.addAll(createVillains(villainLevel, expIncrease, artefactProbability, villainCount, availableCoordinates));

		if (heroLevel == 1) {
			expIncrease = getExperienceIncrease(heroLevel);
			artefactProbability = getArtefactProbability(heroLevel, villainLevel);
			villainCount = villainsNumber - villains.size();
			villains.addAll(createVillains(1, expIncrease, artefactProbability, villainCount, availableCoordinates));
		} else {
			villainLevel = heroLevel;
			expIncrease = getExperienceIncrease(heroLevel);
			artefactProbability = getArtefactProbability(heroLevel, villainLevel);
			numberRate = 0.35 + (added ? 0 : numberRate);
			villainCount = (int) (villainsNumber * numberRate);
			added = villains.addAll(createVillains(villainLevel, expIncrease, artefactProbability, villainCount, availableCoordinates));

			villainLevel = heroLevel - 1;
			expIncrease = getExperienceIncrease(heroLevel);
			artefactProbability = getArtefactProbability(heroLevel, villainLevel);
			numberRate = 0.30 + (added ? 0 : numberRate);
			villainCount = (int) (villainsNumber * numberRate);
			villains.addAll(createVillains(villainLevel, expIncrease, artefactProbability, villainCount, availableCoordinates));

			if (VillainHelper.hasBefore(heroLevel - 1)) {
				while (villains.size() < villainsNumber) {
					villainLevel = RandomHelper.before(heroLevel - 1);
					expIncrease = getExperienceIncrease(heroLevel);
					artefactProbability = getArtefactProbability(heroLevel, villainLevel);
					villains.addAll(createVillains(villainLevel, expIncrease, artefactProbability, 1, availableCoordinates));
				}
			}
		}

		gameMap.getVillains().addAll(villains);

		return gameMap;
	}


	private static List<Villain> createVillains(int villainLevel,
												double experienceIncrease,
												double artefactProbability,
												int count,
												List<Pair<Integer, Integer>> availableCoordinates) {
		if (count == 0 || !VillainHelper.has(villainLevel)) {
			return new ArrayList<>();
		}

		List<Villain> villains = new ArrayList<>();
		for (int i = 0; i < count; ++i) {
			Pair<Integer, Integer> coordinate = availableCoordinates.remove(RandomHelper.before(availableCoordinates.size()));
			boolean addArtefact = RandomHelper.yesWithProbability(artefactProbability);
			villains.add(VillainHelper.create(villainLevel, coordinate.getLeft(), coordinate.getRight(), experienceIncrease, addArtefact));
		}

		return villains;
	}

	private static List<Pair<Integer, Integer>> getCoordinates(int size, int busyX, int busyY) {
		List<Pair<Integer, Integer>> coordinates = new ArrayList<>();
		for (int x = 0; x < size; ++x) {
			for (int y = 0; y < size; ++y) {
				if (x != busyX && busyY != y) {
					coordinates.add(Pair.of(x, y));
				}
			}
		}

		return coordinates;
	}

	private static double getExperienceIncrease(int level) {
		if (level == 1) {
			return 5;
		}

		if (level == 2) {
			return 2;
		}

		if (level == 3) {
			return 1.5;
		}

		if (level == 4) {
			return 1.2;
		}

		if (level == 5) {
			return 1.1;
		}

		return 1;
	}

	private static double getArtefactProbability(int heroLevel, int villainLevel) {
		int diff = heroLevel - villainLevel;

		if (diff > 1) {
			return 0.30;
		}

		if (diff == 1) {
			return 0.25;
		}

		if (diff == 0) {
			return 0.15;
		}

		if (diff == -1) {
			return 0.25;
		}

		return 0.30;
	}

	private static int getVillainsNumber(int mapSize) {
		double increase = RandomHelper.between(1, 9) * 0.1 + 1;
		return (int) (mapSize * increase);
	}

}
