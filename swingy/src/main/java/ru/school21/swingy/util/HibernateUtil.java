package ru.school21.swingy.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.school21.swingy.exception.SwingyRuntimeException;
import ru.school21.swingy.model.entity.GameMap;
import ru.school21.swingy.model.entity.Hero;
import ru.school21.swingy.model.entity.Villain;

import javax.persistence.EntityManager;
import java.util.concurrent.Callable;

public class HibernateUtil {
	private static final EntityManager ENTITY_MANAGER;
	private static final SessionFactory SESSION_FACTORY;

	static {
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.build();

		Metadata metadata = new MetadataSources(serviceRegistry)
				.addAnnotatedClass(Hero.class)
				.addAnnotatedClass(Villain.class)
				.addAnnotatedClass(GameMap.class)
				.getMetadataBuilder()
				.build();

		SESSION_FACTORY = metadata.getSessionFactoryBuilder()
				.build();

		ENTITY_MANAGER = SESSION_FACTORY.createEntityManager();
	}

	public static EntityManager getEntityManager() {
		return ENTITY_MANAGER;
	}

	public static <T> T execute(Callable<T> callable) {
		ENTITY_MANAGER.getTransaction().begin();

		T result;
		try {
			result = callable.call();
			ENTITY_MANAGER.getTransaction().commit();
		} catch (Exception e) {
			ENTITY_MANAGER.getTransaction().rollback();
			throw new SwingyRuntimeException(e);
		}

		return result;
	}

	public static void execute(Runnable runnable) {
		ENTITY_MANAGER.getTransaction().begin();

		try {
			runnable.run();
			ENTITY_MANAGER.getTransaction().commit();
		} catch (Exception e) {
			ENTITY_MANAGER.getTransaction().rollback();
			throw new SwingyRuntimeException(e);
		}
	}
}
