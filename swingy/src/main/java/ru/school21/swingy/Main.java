package ru.school21.swingy;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
	public static void main(final String[] args) {
		SessionFactory sessionFactory = new Configuration()
				.buildSessionFactory();


	}
}