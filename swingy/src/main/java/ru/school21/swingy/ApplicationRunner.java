package ru.school21.swingy;

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
import ru.school21.swingy.view.console.GameViewConsoleView;
import ru.school21.swingy.view.console.SelectHeroConsoleView;
import ru.school21.swingy.view.gui.GameGuiView;
import ru.school21.swingy.view.GameView;
import ru.school21.swingy.view.gui.SelectHeroGuiView;
import ru.school21.swingy.view.SelectHeroView;

import javax.swing.SwingUtilities;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationRunner implements EventListener {

	private final static ApplicationRunner INSTANCE;

	static {
		INSTANCE = new ApplicationRunner();
	}

	private SelectHeroView selectHeroView = null;
	private SelectHeroModel selectHeroModel;
	private GameView gameView = null;
	private GameModel gameModel;

	public static ApplicationRunner getInstance() {
		return INSTANCE;
	}

	public void run() {
		runSelectHero();
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				runSelectHero();
//			}
//		});
	}

	@Override
	public void publishEvent(ModelEvent modelEvent) {
		if (modelEvent == SelectHeroModelEvent.HERO_SELECTED) {
			selectHeroView.stop();
			runMainGame();
		} else if (modelEvent == ApplicationModelEvent.EXIT) {
			if (selectHeroView != null) {
				selectHeroView.stop();
			}

			if (gameView != null) {
				gameView.stop();
			}
		}
	}


	private void runSelectHero() {
		selectHeroModel = new SelectHeroModelImpl();
		selectHeroModel.addListener(this, SelectHeroModelEvent.class);
		selectHeroModel.addListener(this, ApplicationModelEvent.class);

		SelectHeroController controller = new SelectHeroController(selectHeroModel);
//		selectHeroView = new SelectHeroGuiView(controller, selectHeroModel);
		selectHeroView = new SelectHeroConsoleView(controller, selectHeroModel);
		selectHeroView.render();
	}

	private void runMainGame() {
		gameModel = new GameModelImpl(selectHeroModel.getHeroStats().getName());
		gameModel.addListener(this, ApplicationModelEvent.class);

		GameController controller = new GameController(gameModel);
//		gameView = new GameGuiView(gameModel, controller);
		gameView = new GameViewConsoleView(gameModel, controller);
		gameView.render();
	}
}
