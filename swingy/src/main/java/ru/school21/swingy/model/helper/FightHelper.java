package ru.school21.swingy.model.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.school21.swingy.model.entity.Hero;
import ru.school21.swingy.model.entity.Villain;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FightHelper {

	public static boolean fight(Hero hero, Villain villain, List<String> events) {
		int heroHp = hero.getHp();
		int heroDefense = hero.getDefense() + ArtefactHelper.getIncreaseByName(hero.getArmor());
		int heroAttack = hero.getAttack() + ArtefactHelper.getIncreaseByName(hero.getWeapon());

		int villainLevel = villain.getLevel();
		String villainName = villain.getName();
		int villainHp = villain.getHp();
		int villainAttack = villain.getAttack();

		events.add(String.format("Fight with '%s': level[%d] hp[%d], attack[%d]", villainName, villainLevel, villainHp, villainAttack));
		if (RandomHelper.yes()) {
			events.add("You starts first");
			villainHp = Math.max(0, villainHp - heroAttack);
		} else {
			events.add(String.format("'%s' starts first", villainName));
		}

		while (heroHp > 0 && villainHp > 0) {
			if (heroDefense > 0) {
				if (heroDefense <= villainAttack) {
					events.add(String.format("'%s' brakes your defense", villainName));
					heroHp = heroHp - (villainAttack - heroDefense);
				}

				heroDefense = Math.max(0, heroDefense - villainAttack);
			} else {
				heroHp = Math.max(0, heroHp - villainAttack);
			}

			if (heroHp <= 0) {
				events.add(String.format("'%s' strikes a fatal blow", villainName));
				break;
			}

			villainHp = Math.max(0, villainHp - heroAttack);
		}
		hero.setHp(heroHp);

		if (heroHp <= 0) {
			events.add(String.format("Seems '%s' wins", villainName));
			return false;
		}

		events.add("You strikes a fatal blow. WIN!!!");

		return true;
	}

}
