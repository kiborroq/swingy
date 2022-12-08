package ru.school21.swingy.view.console;

import ru.school21.swingy.controller.SelectHeroController;
import ru.school21.swingy.event.ModelEvent;
import ru.school21.swingy.event.type.HeroStatsModelEvent;
import ru.school21.swingy.event.type.SelectHeroModelEvent;
import ru.school21.swingy.model.SelectHeroModel;
import ru.school21.swingy.util.ConsoleUtils;
import ru.school21.swingy.view.SelectHeroView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.school21.swingy.util.ConsoleUtils.chooseVariantNumber;
import static ru.school21.swingy.util.ConsoleUtils.print;
import static ru.school21.swingy.util.ConsoleUtils.printFormat;
import static ru.school21.swingy.util.ConsoleUtils.printLine;
import static ru.school21.swingy.util.ConsoleUtils.readLine;
import static ru.school21.swingy.view.console.CommonConsolePrinter.printHeroStats;

public class SelectHeroConsoleView implements SelectHeroView {

	private final SelectHeroModel model;
	private final SelectHeroController controller;

	private boolean mainLoop = true;
	private boolean subLoop = true;

	public SelectHeroConsoleView(SelectHeroController controller, SelectHeroModel model) {
		this.model = model;
		this.model.addListener(this, SelectHeroModelEvent.class);
		this.model.addListener(this, HeroStatsModelEvent.class);
		this.controller = controller;
	}

	@Override
	public void publishEvent(ModelEvent modelEvent) {
		if (modelEvent == SelectHeroModelEvent.ERRORS_CHANGED) {
			Map<String, String> errors = model.getErrors();

			if (!errors.isEmpty()) {
				printLine();
				printLine(ConsoleUtils.Color.RED, "Errors: ");
				for (Map.Entry<String, String> error : errors.entrySet()) {
					printFormat("%s%n", error.getValue());
				}
			}
		} else if (modelEvent instanceof HeroStatsModelEvent) {
			printHeroStats(model.getHeroStats());
		}
	}

	@Override
	public void render() {
		while (mainLoop) {
			switch (chooseVariantNumber("Create hero", "Select a previously created hero", "Exit")) {
				case 1:
					createHeroMode();
					break;
				case 2:
					selectHeroMode();
					break;
				case 3:
					exit();
					break;
			}
		}
	}

	@Override
	public void stop() {
		mainLoop = false;
		subLoop = false;
	}


	private void createHeroMode() {
		subLoop = true;
		controller.createHeroMode();

		while (subLoop) {
			switch (chooseVariantNumber("Set hero name", "Set hero class", "Finish to create the hero", "Back")) {
				case 1:
					printLine();
					print("Input name: ");
					String name = readLine();
					controller.inputName(name);
					break;
				case 2:
					List<String> classes = new ArrayList<>(model.getHeroClasses());
					int classNumber = chooseVariantNumber(classes.toArray(new String[0]));
					controller.selectClazz(classes.get(classNumber - 1));
					break;
				case 3:
					controller.selectHero();
					break;
				case 4:
					subLoop = false;
					break;
			}
		}
	}

	private void selectHeroMode() {
		subLoop = true;
		controller.selectHeroMode();

		while (subLoop) {
			switch (chooseVariantNumber("Select hero", "Finish to select the hero", "Back")) {
				case 1:
					List<String> classes = new ArrayList<>(model.getHeroes());
					int heroNumber = chooseVariantNumber(classes.toArray(new String[0]));
					controller.selectName(classes.get(heroNumber - 1));
					break;
				case 2:
					controller.selectHero();
					break;
				case 3:
					subLoop = false;
					break;
			}
		}
	}

	private void exit() {
		if (chooseVariantNumber("Exit", "Cancel") == 1) {
			controller.exit();
		}
	}
}
