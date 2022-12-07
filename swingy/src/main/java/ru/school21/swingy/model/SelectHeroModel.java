package ru.school21.swingy.model;

import ru.school21.swingy.event.EventPublisher;

import java.util.Map;
import java.util.Set;

public interface SelectHeroModel extends HeroStatsModel, EventPublisher {

	void setHeroName(String name);
	void setHeroType(String clazz);
	void selectHeroName(String name);
	void selectHero();

	void selectHeroMode();
	void createHeroMode();

	Set<String> getHeroes();
	Set<String> getHeroClasses();
	Map<String, String> getErrors();

	void exit();
}
