package ru.school21.swingy;

import ru.school21.swingy.util.HibernateUtil;

public class Main {
	public static void main(final String[] args) {
		HibernateUtil.getSessionFactory();
	}
}