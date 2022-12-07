package ru.school21.swingy.model.helper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import ru.school21.swingy.model.dto.ArtefactDto;
import ru.school21.swingy.model.dto.HeroStatsDto;
import ru.school21.swingy.model.entity.Hero;
import ru.school21.swingy.util.ImageUtil;
import ru.school21.swingy.util.JsonUtils;

import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeroHelper {

	private static final String HEROES_CONFIG = "/json/heroes.json";
	private static final Map<String, HeroInfo> HERO_BY_CLAZZ;
	private static final Map<Integer, Integer> EXPERIENCE_BY_LEVEL;

	static {
		HERO_BY_CLAZZ =  new HashMap<>();

		List<HeroInfo> heroInfos = JsonUtils.readList(HEROES_CONFIG, HeroInfo.class);
		for (HeroInfo hero : heroInfos) {
			HERO_BY_CLAZZ.put(hero.getClazz(), hero);
		}

		EXPERIENCE_BY_LEVEL =  new HashMap<>();
	}

	public static ImageIcon getAvatar(String clazz) {
		return HERO_BY_CLAZZ.get(clazz).getAvatar();
	}

	public static ImageIcon getMiniature(String clazz) {
		return HERO_BY_CLAZZ.get(clazz).getMiniature();
	}

	public static Map<String, Hero> getByClazzMap() {
		return HERO_BY_CLAZZ.values().stream()
				.collect(Collectors.toMap(new Function<HeroInfo, String>() {
					@Override
					public String apply(HeroHelper.HeroInfo heroInfo) {
						return heroInfo.getClazz();
					}
				}, new Function<HeroHelper.HeroInfo, Hero>() {

					@Override
					public Hero apply(HeroHelper.HeroInfo heroInfo) {
						Hero hero = new Hero();
						hero.setClazz(heroInfo.getClazz());
						hero.setLevel(1);
						hero.setAttack(heroInfo.getAttack());
						hero.setDefense(heroInfo.getDefense());
						hero.setHp(heroInfo.getHp());
						hero.setMaxHp(heroInfo.getHp());
						hero.setExperience(0);

						return hero;
					}
				}));
	}

	public static void addExperience(Hero hero, int experience) {
		HeroInfo info = HERO_BY_CLAZZ.get(hero.getClazz());

		int exp = hero.getExperience() + experience;
		int targetExp = getTargetExperience(hero.getLevel());
		while (exp > targetExp) {
			exp = exp - targetExp;
			hero.setLevel(hero.getLevel() + 1);

			hero.setAttack((int) (hero.getAttack() * (1.0 + info.getAttackIncrease() / 100.0)));
			hero.setDefense((int) (hero.getDefense() * (1.0 + info.getDefenseIncrease() / 100.0)));
			hero.setMaxHp((int) (hero.getMaxHp() * (1.0 + info.getHpIncrease() / 100.0)));

			targetExp = getTargetExperience(hero.getLevel());
		}

		hero.setExperience(exp);
	}

	private static Integer getTargetExperience(Integer level) {
		if (level == null) {
			return null;
		}

		Integer experience = EXPERIENCE_BY_LEVEL.get(level);
		if (experience == null) {
			experience = (int) ((level * 1000) + (level - 1) * (level - 1) * 450);
			EXPERIENCE_BY_LEVEL.put(level, experience);
		}

		return experience;
	}

	public static HeroStatsDto map(Hero hero) {
		ArtefactDto weapon = ArtefactHelper.getByName(hero.getWeapon());
		ArtefactDto helm = ArtefactHelper.getByName(hero.getHelm());
		ArtefactDto armor = ArtefactHelper.getByName(hero.getArmor());

		return HeroStatsDto.builder()
				.name(hero.getName())
				.clazz(hero.getClazz())
				.level(hero.getLevel())
				.experience(hero.getExperience())
				.targetExperience(HeroHelper.getTargetExperience(hero.getLevel()))
				.attack(increase(hero.getAttack(), weapon))
				.defense(increase(hero.getDefense(), armor))
				.maxHp(increase(hero.getMaxHp(), helm))
				.hp(hero.getHp())
				.weapon(weapon)
				.helm(helm)
				.armor(armor)
				.build();
	}

	private static Integer increase(Integer skill, ArtefactDto artefact) {
		if (skill != null && artefact != null) {
			return skill + artefact.getIncrease();
		}

		return skill;
	}

	public static void restoreHp(Hero hero) {
		hero.setHp(hero.getMaxHp() + ArtefactHelper.getIncreaseByName(hero.getHelm()));
	}

	@Value
	private static class HeroInfo {
		String clazz;
		String gender;
		String description;
		ImageIcon avatar;
		ImageIcon miniature;
		Integer attack;
		Integer hp;
		Integer defense;
		Integer attackIncrease;
		Integer defenseIncrease;
		Integer hpIncrease;

		@JsonCreator
		public HeroInfo(@JsonProperty("clazz") String clazz,
						@JsonProperty("gender") String gender,
						@JsonProperty("description") String description,
						@JsonProperty("avatar") String avatar,
						@JsonProperty("miniature") String miniature,
						@JsonProperty("attack") Integer attack,
						@JsonProperty("hp") Integer hp,
						@JsonProperty("defense") Integer defense,
						@JsonProperty("attackIncrease") Integer attackIncrease,
						@JsonProperty("defenseIncrease") Integer defenseIncrease,
						@JsonProperty("hpIncrease") Integer hpIncrease) {
			this.clazz = clazz;
			this.gender = gender;
			this.description = description;
			this.avatar = ImageUtil.getImageIcon(avatar);
			this.miniature = ImageUtil.getImageIcon(miniature);
			this.attack = attack;
			this.hp = hp;
			this.defense = defense;
			this.attackIncrease = attackIncrease;
			this.defenseIncrease = defenseIncrease;
			this.hpIncrease = hpIncrease;
		}
	}

}
