package ru.school21.swingy.view.console;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.school21.swingy.model.dto.ArtefactDto;
import ru.school21.swingy.model.dto.HeroStatsDto;
import ru.school21.swingy.model.dto.MapDto;
import ru.school21.swingy.model.dto.VillainDto;
import ru.school21.swingy.type.ArtefactType;
import ru.school21.swingy.util.ConsoleUtils;
import ru.school21.swingy.util.StringUtils;

import java.util.List;

import static ru.school21.swingy.util.ConsoleUtils.colorize;
import static ru.school21.swingy.util.ConsoleUtils.printFormat;
import static ru.school21.swingy.util.ConsoleUtils.printLine;
import static ru.school21.swingy.util.StringUtils.join;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonConsolePrinter {

	private static final ConsoleUtils.Color HERO = ConsoleUtils.Color.BLUE;
	private static final ConsoleUtils.Color HERO_ITEM = ConsoleUtils.Color.BLUE_BACKGROUND;

	private static final ConsoleUtils.Color VILLAIN = ConsoleUtils.Color.RED;
	private static final ConsoleUtils.Color VILLAIN_ITEM = ConsoleUtils.Color.RED_BACKGROUND;

	private static final ConsoleUtils.Color ARTEFACT = ConsoleUtils.Color.MAGENTA;

	public static void printHeroStats(HeroStatsDto statsDto) {
		printLine();

		printLine(HERO, "Hero stats:");
		printLine(" - name: " + StringUtils.toStr(statsDto.getName()));
		printLine(" - clazz: " + StringUtils.toStr(statsDto.getClazz()));
		printLine(" - level: " + StringUtils.toStr(statsDto.getLevel()));

		if (statsDto.getExperience() != null) {
			printLine(" - experience: " + join("/", statsDto.getExperience(), statsDto.getTargetExperience()));
		}

		printLine(" - attack: " + StringUtils.toStr(statsDto.getAttack()));
		printLine(" - defense: " + StringUtils.toStr(statsDto.getDefense()));

		if (statsDto.getHp() != null) {
			printLine(" - hit points: " + join("/", statsDto.getHp(), statsDto.getMaxHp()));
		}

		String helmStr = "";
		ArtefactDto helmDto = statsDto.getHelm();
		if (helmDto != null) {
			helmStr = String.format("%s (+%d hit points)", helmDto.getName(), helmDto.getIncrease());
		}

		String weaponStr = "";
		ArtefactDto weaponDto = statsDto.getWeapon();
		if (weaponDto != null) {
			weaponStr = String.format("%s (+%d attack)", weaponDto.getName(), weaponDto.getIncrease());
		}

		String armorStr = "";
		ArtefactDto armorDto = statsDto.getArmor();
		if (armorDto != null) {
			armorStr = String.format("%s (+%d defense)", armorDto.getName(), armorDto.getIncrease());
		}

		printLine(HERO, "Hero artefacts:");
		printLine(" - helm: " + helmStr);
		printLine(" - weapon: " + weaponStr);
		printLine(" - armor: " + armorStr);
	}

	public static void printVillainStats(VillainDto villainDto) {
		printLine();

		printLine(VILLAIN, "Villain stats:");
		printLine(" - name: " + StringUtils.toStr(villainDto.getName()));
		printLine(" - level: " + StringUtils.toStr(villainDto.getLevel()));
		printLine(" - experience: " + villainDto.getExperience());
		printLine(" - attack: " + villainDto.getAttack());
		printLine(" - hit points: " + villainDto.getHp());
	}

	public static void printArtefactStats(ArtefactDto artefactDto) {
		printLine();

		printLine(ARTEFACT, "Artefact stats:");
		printLine(" - name: " + StringUtils.toStr(artefactDto.getName()));

		ArtefactType type = artefactDto.getType();
		printLine(" - type: " + StringUtils.toStr(type));
		printFormat(" - %s: +%s%n", StringUtils.toStr(type.getSkill()), StringUtils.toStr(artefactDto.getIncrease()));
	}

	public static void printMap(MapDto mapDto) {
		printLine();

		List<List<MapDto.MapItem>> items = mapDto.getMap();

		for (int j = items.size() - 1; j >= 0; j--) {
			StringBuilder stringBuilder = new StringBuilder();

			List<MapDto.MapItem> itemRow = items.get(j);
			for (int i = 0; i < itemRow.size(); i++) {
				MapDto.MapItem item = itemRow.get(i);
				if (item instanceof MapDto.HeroItem) {
					stringBuilder.append(colorize("  ", HERO_ITEM));
				} else if (item instanceof MapDto.VillainItem) {
					stringBuilder.append(colorize("  ", VILLAIN_ITEM));
				} else {
					stringBuilder.append(colorize("  ", ConsoleUtils.Color.GREEN_BACKGROUND));
				}
			}

			printLine(stringBuilder.toString());
		}
	}

}
