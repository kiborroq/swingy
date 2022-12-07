package ru.school21.swingy.model.repository;

import ru.school21.swingy.model.entity.Hero;

import java.util.List;

public interface HeroRepository {

	List<Hero> findAllHeroes();
	void save(Hero hero);
	Hero findByName(String name);
	void merge(Hero hero);

}
