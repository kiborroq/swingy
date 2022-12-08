package ru.school21.swingy.app;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.school21.swingy.controller.GameController;
import ru.school21.swingy.controller.SelectHeroController;
import ru.school21.swingy.event.ModelEvent;
import ru.school21.swingy.event.type.ApplicationModelEvent;
import ru.school21.swingy.model.GameModel;
import ru.school21.swingy.model.GameModelImpl;
import ru.school21.swingy.model.SelectHeroModel;
import ru.school21.swingy.model.SelectHeroModelImpl;
import ru.school21.swingy.event.EventListener;
import ru.school21.swingy.event.type.SelectHeroModelEvent;
import ru.school21.swingy.view.console.GameConsoleView;
import ru.school21.swingy.view.console.SelectHeroConsoleView;
import ru.school21.swingy.view.gui.GameGuiView;
import ru.school21.swingy.view.GameView;
import ru.school21.swingy.view.gui.SelectHeroGuiView;
import ru.school21.swingy.view.SelectHeroView;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationRunner implements EventListener {

	private final static ApplicationRunner INSTANCE;

	static {
		INSTANCE = new ApplicationRunner();
	}

	private ApplicationMode mode = null;
	private ApplicationStage stage = null;

	private SelectHeroView selectHeroView = null;
	private SelectHeroModel selectHeroModel = null;
	private GameView gameView = null;
	private GameModel gameModel = null;

	public static ApplicationRunner getInstance() {
		return INSTANCE;
	}

	public void run(ApplicationMode mode) {
		this.mode = mode;
		this.stage = ApplicationStage.SELECT_HERO;
		solveWhatToDo();
	}

	@Override
	public void publishEvent(ModelEvent modelEvent) {
		if (modelEvent == SelectHeroModelEvent.HERO_SELECTED) {
			stage = ApplicationStage.MAIN_GAME;
			solveWhatToDo();
		} else if (modelEvent instanceof ApplicationModelEvent) {
			switch ((ApplicationModelEvent) modelEvent) {
				case EXIT:
					closeAll();
					break;
				case SWITCH_TO_GUI:
					if (mode != ApplicationMode.GUI) {
						mode = ApplicationMode.GUI;
						solveWhatToDo();
					}
					break;
				case SWITCH_TO_CONSOLE:
					if (mode != ApplicationMode.CONSOLE) {
						mode = ApplicationMode.CONSOLE;
						solveWhatToDo();
					}
					break;
			}
		}
	}

	private void solveWhatToDo() {
		closeAll();

		switch (stage) {
			case SELECT_HERO:
				runSelectHero();
				break;
			case MAIN_GAME:
				runMainGame();
				break;
		}
	}

	private void runSelectHero() {
		selectHeroModel = new SelectHeroModelImpl();
		selectHeroModel.addListener(this, SelectHeroModelEvent.class);
		selectHeroModel.addListener(this, ApplicationModelEvent.class);

		SelectHeroController controller = new SelectHeroController(selectHeroModel);

		switch (mode) {
			case GUI:
				selectHeroView = new SelectHeroGuiView(controller, selectHeroModel);
				break;
			case CONSOLE:
				selectHeroView = new SelectHeroConsoleView(controller, selectHeroModel);
				break;
		}

		selectHeroView.render();
	}

	private void runMainGame() {
		gameModel = new GameModelImpl(selectHeroModel.getHeroStats().getName());
		gameModel.addListener(this, ApplicationModelEvent.class);

		GameController controller = new GameController(gameModel);

		switch (mode) {
			case GUI:
				gameView = new GameGuiView(gameModel, controller);
				break;
			case CONSOLE:
				gameView = new GameConsoleView(gameModel, controller);
				break;
		}

		gameView.render();
	}

	private void closeAll() {
		if (selectHeroView != null) {
			selectHeroView.stop();
		}

		if (gameView != null) {
			gameView.stop();
		}
	}
}
