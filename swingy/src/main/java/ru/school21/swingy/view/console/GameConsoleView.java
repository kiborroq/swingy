package ru.school21.swingy.view.console;

import ru.school21.swingy.controller.GameController;
import ru.school21.swingy.event.ModelEvent;
import ru.school21.swingy.event.type.ApplicationModelEvent;
import ru.school21.swingy.event.type.GameModelEvent;
import ru.school21.swingy.event.type.HeroStatsModelEvent;
import ru.school21.swingy.event.type.LogModelEvent;
import ru.school21.swingy.model.GameModel;
import ru.school21.swingy.type.DirectionType;
import ru.school21.swingy.util.ConsoleUtils;
import ru.school21.swingy.view.GameView;

import java.util.List;

import static ru.school21.swingy.util.ConsoleUtils.chooseVariantNumber;
import static ru.school21.swingy.util.ConsoleUtils.printLine;
import static ru.school21.swingy.view.console.CommonConsolePrinter.printArtefactStats;
import static ru.school21.swingy.view.console.CommonConsolePrinter.printHeroStats;
import static ru.school21.swingy.view.console.CommonConsolePrinter.printMap;
import static ru.school21.swingy.view.console.CommonConsolePrinter.printVillainStats;

public class GameConsoleView implements GameView {

	private final GameModel model;
	private final GameController controller;

	private boolean mainLoop;
	private boolean subLoop;

	public GameConsoleView(GameModel model, GameController controller) {
		this.model = model;
		this.model.addListener(this, GameModelEvent.class);
		this.model.addListener(this, HeroStatsModelEvent.class);
		this.model.addListener(this, LogModelEvent.class);
		this.model.addListener(this, ApplicationModelEvent.class);
		this.controller = controller;
		this.mainLoop = true;
	}

	@Override
	public void publishEvent(ModelEvent modelEvent) {
		if (modelEvent instanceof LogModelEvent) {
			printLogs(((LogModelEvent) modelEvent).getLogs());
		} else if (modelEvent instanceof GameModelEvent) {
			switch ((GameModelEvent) modelEvent) {
				case FIGHT:
					fight();
					break;
				case ARTEFACT_FOUND:
					artefactFound();
					break;
			}
		}
	}

	@Override
	public void render() {
		while (mainLoop) {
			printHeroStats(model.getHeroStats());
			printMap(model.getMap());

			switch (chooseVariantNumber("North", "East", "South", "West", "Main menu")) {
				case 1:
					controller.doStep(DirectionType.NORTH);
					break;
				case 2:
					controller.doStep(DirectionType.EAST);
					break;
				case 3:
					controller.doStep(DirectionType.SOUTH);
					break;
				case 4:
					controller.doStep(DirectionType.WEST);
					break;
				case 5:
					mainMenu();
					break;
			}
		}
	}

	@Override
	public void stop() {
		mainLoop = false;
		subLoop = false;
	}

	private void printLogs(List<String> logs) {
		if (logs.isEmpty()) {
			return;
		}

		printLine();
		printLine(ConsoleUtils.Color.YELLOW, "Events: ");
		for (String log : logs) {
			printLine(" - " + log);
		}
	}

	private void fight() {
		printVillainStats(model.getVillainStats());

		if (chooseVariantNumber("Fight", "Flee") == 1) {
			controller.fight();
		} else {
			controller.flee();
		}
	}

	private void artefactFound() {
		printArtefactStats(model.getFoundArtefactStats());

		if (chooseVariantNumber("Take", "Throw") == 1) {
			controller.takeArtefact();
		} else {
			controller.throwArtefact();
		}
	}

	private void mainMenu() {
		subLoop = true;

		while (subLoop) {
			switch (chooseVariantNumber("Switch to gui", "Save", "Exit", "Back to game")) {
				case 1:
					controller.switchToGui();
					break;
				case 2:
					controller.save();
					break;
				case 3:
					controller.exit();
					break;
				case 4:
					subLoop = false;
					break;
			}
		}
	}
}
