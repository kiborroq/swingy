package ru.school21.swingy.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.school21.swingy.model.Game;
import ru.school21.swingy.model.Hero;
import ru.school21.swingy.model.Villain;

public class HibernateUtil {
	private static final SessionFactory SESSION_FACTORY;

	static {
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.build();

		Metadata metadata = new MetadataSources(serviceRegistry)
				.addAnnotatedClass(Hero.class)
				.addAnnotatedClass(Villain.class)
				.addAnnotatedClass(Game.class)
				.getMetadataBuilder()
				.build();

		SESSION_FACTORY = metadata.getSessionFactoryBuilder()
				.build();
	}

	public static SessionFactory getSessionFactory() {
		return SESSION_FACTORY;
	}
}
