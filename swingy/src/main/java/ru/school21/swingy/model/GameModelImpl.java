package ru.school21.swingy.model;

import lombok.Value;
import ru.school21.swingy.event.AbstractEventPublisher;
import ru.school21.swingy.event.ModelEvent;
import ru.school21.swingy.event.type.ApplicationModelEvent;
import ru.school21.swingy.event.type.GameModelEvent;
import ru.school21.swingy.event.type.HeroStatsModelEvent;
import ru.school21.swingy.event.type.LogModelEvent;
import ru.school21.swingy.model.dto.ArtefactDto;
import ru.school21.swingy.model.dto.VillainDto;
import ru.school21.swingy.model.entity.AbstractCharacter;
import ru.school21.swingy.model.entity.Villain;
import ru.school21.swingy.model.helper.ArtefactHelper;
import ru.school21.swingy.model.helper.FightHelper;
import ru.school21.swingy.model.helper.GameMapHelper;
import ru.school21.swingy.model.helper.HeroHelper;
import ru.school21.swingy.model.helper.RandomHelper;
import ru.school21.swingy.model.repository.HeroHibernateRepository;
import ru.school21.swingy.model.repository.HeroRepository;
import ru.school21.swingy.model.dto.HeroStatsDto;
import ru.school21.swingy.model.dto.MapDto;
import ru.school21.swingy.model.entity.GameMap;
import ru.school21.swingy.model.entity.Hero;
import ru.school21.swingy.type.DirectionType;
import ru.school21.swingy.util.HibernateUtil;
import ru.school21.swingy.util.UnmodifiableUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class GameModelImpl extends AbstractEventPublisher implements GameModel {

	private final static Set<Class<? extends ModelEvent>> SUPPORTED_EVENTS = UnmodifiableUtils.set(
			GameModelEvent.class,
			HeroStatsModelEvent.class,
			ApplicationModelEvent.class,
			LogModelEvent.class);

	private final HeroRepository heroRepository;
	private final Hero hero;

	private GameMap gameMap;
	private AbstractCharacter [][] map;
	private Fight currentFight;

	public GameModelImpl(String name) {
		heroRepository = new HeroHibernateRepository();

		hero = HibernateUtil.execute(new Callable<Hero>() {
			@Override
			public Hero call() {
				return heroRepository.findByName(name);
			}
		});

		gameMap = hero.getGameMap();
		if (gameMap == null) {
			createGameMap();
		}

		fillMap();
	}

	@Override
	protected Set<Class<? extends ModelEvent>> getSupportedEventTypes() {
		return SUPPORTED_EVENTS;
	}

	@Override
	public HeroStatsDto getHeroStats() {
		return HeroHelper.map(hero);
	}

	@Override
	public MapDto getMap() {
		MapDto.MapItem [][] itemsArray = new MapDto.MapItem[map.length][map[0].length];
		for (int i = 0; i < itemsArray.length; ++i) {
			for (int j = 0; j < itemsArray[i].length; ++j) {
				AbstractCharacter character = map[i][j];
				if (character instanceof Hero) {
					itemsArray[i][j] = new MapDto.HeroItem(((Hero) character).getClazz());
				} else if (character instanceof Villain) {
					itemsArray[i][j] = new MapDto.VillainItem(character.getName());
				}
			}
		}

		List<List<MapDto.MapItem>> items = new ArrayList<>();
		for (MapDto.MapItem [] row : itemsArray) {
			items.add(UnmodifiableUtils.list(row));
		}

		return MapDto.builder()
				.map(UnmodifiableUtils.list(items))
				.terrain(gameMap.getTerrain())
				.heroX(gameMap.getHeroX())
				.heroY(gameMap.getHeroY())
				.lastStepDirection(gameMap.getLastStepDirection())
				.build();
	}

	@Override
	public void doStep(DirectionType direction) {
		switch (direction) {
			case EAST:
				gameMap.setLastStepDirection(direction);
				moveHero(gameMap.getHeroX() + 1, gameMap.getHeroY());
				break;
			case WEST:
				gameMap.setLastStepDirection(direction);
				moveHero(gameMap.getHeroX() - 1, gameMap.getHeroY());
				break;
			case NORTH:
				moveHero(gameMap.getHeroX(), gameMap.getHeroY() + 1);
				break;
			case SOUTH:
				moveHero(gameMap.getHeroX(), gameMap.getHeroY() - 1);
				break;
		}
	}

	@Override
	public void fight() {
		Villain villain = currentFight.getVillain();

		boolean createNewMap = false;
		boolean artefactFound = false;
		if (FightHelper.fight(hero, villain, currentFight.getEvents())) {
			int beforeLevel = hero.getLevel();
			HeroHelper.addExperience(hero, villain.getExperience());
			currentFight.getEvents().add("Your experience is raised up");
			if (hero.getLevel() > beforeLevel) {
				currentFight.getEvents().add("You level up");
				createNewMap = true;
			}

			artefactFound = villain.getArtefact() != null;

			publishEvent(HeroStatsModelEvent.UPDATED);

			if (!createNewMap) {
				map[currentFight.getY()][currentFight.getX()] = null;
				gameMap.getVillains().remove(villain);
				moveHero(villain.getPosX(), villain.getPosY());
			}
		} else {
			publishEvent(GameModelEvent.LOST);
			publishEvent(HeroStatsModelEvent.UPDATED);
			createNewMap = true;
		}

		publishEvent(new LogModelEvent(currentFight.getEvents()));

		if (artefactFound) {
			publishEvent(GameModelEvent.ARTEFACT_FOUND);
		}

		if (createNewMap) {
			createNewGame();
		}
	}

	@Override
	public void flee() {
		if (RandomHelper.yes()) {
			publishEvent(new LogModelEvent("You success to flee"));
		} else {
			currentFight.getEvents().add("You failed to flee. You will fight");
			fight();
		}
	}

	@Override
	public void takeArtefact() {
		ArtefactDto newArtefact = currentFight.getArtefact();
		publishEvent(new LogModelEvent(String.format("You have taken new artefact '%s'", newArtefact.getName())));
		switch (newArtefact.getType()) {
			case HELM:
				hero.setHelm(newArtefact.getName());
				break;
			case ARMOR:
				hero.setArmor(newArtefact.getName());
				break;
			case WEAPON:
				hero.setWeapon(newArtefact.getName());
				break;
		}

		publishEvent(HeroStatsModelEvent.UPDATED);
	}

	@Override
	public void throwArtefact() {
		publishEvent(new LogModelEvent(String.format("You have thrown new artefact '%s'", currentFight.getArtefact().getName())));
	}

	@Override
	public void save() {
		HibernateUtil.execute(new Runnable() {
			@Override
			public void run() {
				heroRepository.merge(hero);
				heroRepository.save(hero);
			}
		});
		publishEvent(new LogModelEvent("Game have been saved"));
	}

	@Override
	public void exit() {
		publishEvent(ApplicationModelEvent.EXIT);
	}

	@Override
	public void switchToGui() {
		publishEvent(ApplicationModelEvent.SWITCH_TO_GUI);
	}

	@Override
	public void switchToConsole() {
		publishEvent(ApplicationModelEvent.SWITCH_TO_CONSOLE);
	}

	@Override
	public VillainDto getVillainStats() {
		if (currentFight == null) {
			return null;
		}

		Villain villain = currentFight.getVillain();
		return VillainDto.builder()
				.name(villain.getName())
				.level(villain.getLevel())
				.attack(villain.getAttack())
				.experience(villain.getExperience())
				.hp(villain.getHp())
				.build();
	}

	@Override
	public ArtefactDto getFoundArtefactStats() {
		return currentFight != null ? currentFight.getArtefact() : null;
	}

	private void moveHero(int x, int y) {
		if (x >= 0 && x < gameMap.getSize() && y >=0 && y < gameMap.getSize()) {
			AbstractCharacter character = map[y][x];
			if (character instanceof Villain) {
				Villain villain = (Villain) character;
				currentFight = new Fight(villain, x, y, ArtefactHelper.getByName(villain.getArtefact()));
				publishEvent(GameModelEvent.MAP_CHANGED);
				publishEvent(GameModelEvent.FIGHT);
			} else {
				map[gameMap.getHeroY()][gameMap.getHeroX()] = null;
				gameMap.setHeroX(x);
				gameMap.setHeroY(y);
				map[gameMap.getHeroY()][gameMap.getHeroX()] = hero;
				publishEvent(GameModelEvent.MAP_CHANGED);
			}
		} else {
			createNewGame();
		}
	}

	private void createNewGame() {
		HeroHelper.restoreHp(hero);
		publishEvent(HeroStatsModelEvent.UPDATED);

		createGameMap();
		fillMap();
		publishEvent(GameModelEvent.MAP_CHANGED);

		String newMapLog = "You enter in new zone:\n" +
				"- type: '%s'\n" +
				"- size: %dx%d\n" +
				"- villain number: %d";
		publishEvent(new LogModelEvent(String.format(newMapLog,
				gameMap.getTerrain(), gameMap.getSize(), gameMap.getSize(), gameMap.getVillains().size())));
	}

	private void createGameMap() {
		gameMap = GameMapHelper.create(hero.getLevel());
		hero.setGameMap(gameMap);
	}

	private void fillMap() {
		map = new AbstractCharacter[gameMap.getSize()][gameMap.getSize()];
		map[gameMap.getHeroY()][gameMap.getHeroX()] = hero;
		for (Villain villain : gameMap.getVillains()) {
			map[villain.getPosY()][villain.getPosX()] = villain;
		}
	}


	@Value
	private static class Fight {
		Villain villain;
		int x;
		int y;
		ArtefactDto artefact;
		List<String> events = new ArrayList<>();
	}
}
