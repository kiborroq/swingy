package ru.school21.swingy.model.repository;

import ru.school21.swingy.model.entity.Hero;
import ru.school21.swingy.util.HibernateUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class HeroHibernateRepository implements HeroRepository {

	private static EntityManager entityManager;

	public HeroHibernateRepository() {
		entityManager = HibernateUtil.getEntityManager();
	}

	@Override
	public List<Hero> findAllHeroes() {
		return entityManager.createQuery("SELECT hero FROM Hero hero ORDER BY hero.lastUseDate DESC ", Hero.class)
				.getResultList();
	}

	@Override
	public Hero findByName(String name) {
		return entityManager.createQuery("SELECT hero FROM Hero hero LEFT JOIN FETCH hero.gameMap WHERE hero.name = :name", Hero.class)
				.setParameter("name", name)
				.getSingleResult();
	}

	@Override
	public void save(Hero hero) {
		entityManager.persist(hero);
	}

	@Override
	public void merge(Hero hero) {
		entityManager.merge(hero);
	}

}
