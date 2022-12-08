package ru.school21.swingy;

import ru.school21.swingy.app.ApplicationMode;
import ru.school21.swingy.app.ApplicationRunner;
import ru.school21.swingy.util.ConsoleUtils;

public class Main {
	public static void main(final String[] args) {
		if (args.length != 1) {
			ConsoleUtils.printLine(ConsoleUtils.Color.RED, "Run program with mode ('console' or 'gui')");
		} else {
			ApplicationRunner.getInstance().run(ApplicationMode.valueOf(args[0].toUpperCase()));
		}
	}
}