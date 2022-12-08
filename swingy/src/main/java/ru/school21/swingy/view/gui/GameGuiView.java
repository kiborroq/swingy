package ru.school21.swingy.view.gui;

import ru.school21.swingy.controller.GameController;
import ru.school21.swingy.event.ModelEvent;
import ru.school21.swingy.event.type.GameModelEvent;
import ru.school21.swingy.event.type.LogModelEvent;
import ru.school21.swingy.model.GameModel;
import ru.school21.swingy.model.dto.ArtefactDto;
import ru.school21.swingy.model.dto.MapDto;
import ru.school21.swingy.model.dto.VillainDto;
import ru.school21.swingy.type.DirectionType;
import ru.school21.swingy.util.ImageUtil;
import ru.school21.swingy.view.GameView;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

public class GameGuiView extends JFrame implements GameView {
	private JPanel heroPanel;
	private HeroStatsGuiView heroStats;
	private JPanel gamePanel;
	private JPanel heroStatsWrapper;
	private JPanel heroArtifactsWrapper;
	private JPanel heroStatsPanel;
	private JPanel fieldPanel;
	private JPanel buttonsPanel;
	private JButton northButton;
	private JButton westButton;
	private JButton eastButton;
	private JButton southButton;
	private JScrollPane scrollMapPanel;
	private JPanel mapPanel;
	private JTextArea logsTextArea;
	private JPanel logsPanel;
	private JScrollPane logsScrollPanel;
	private JPanel manageButtonsPanel;
	private JButton consoleModeButton;
	private JButton saveButton;
	private MapPanel mapPanelView;
	private JPanel coverMapPanel;

	private final JFrame thisFrame = this;

	private final GameModel model;
	private final GameController controller;

	public GameGuiView(GameModel model, GameController controller) {
		this.model = model;
		this.model.addListener(this, GameModelEvent.class);
		this.model.addListener(this, LogModelEvent.class);
		this.controller = controller;

		eastButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.doStep(DirectionType.EAST);
			}
		});

		westButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.doStep(DirectionType.WEST);
			}
		});

		northButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.doStep(DirectionType.NORTH);
			}
		});

		southButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.doStep(DirectionType.SOUTH);
			}
		});

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.save();
			}
		});

		consoleModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.switchToConsole();
			}
		});

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				scrollToHero();
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int answer = JOptionPane.showConfirmDialog(thisFrame, "Do you want to save progress?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
				if (answer != JOptionPane.CANCEL_OPTION) {
					if (answer == JOptionPane.YES_OPTION) {
						controller.save();
					}
					controller.exit();
				}
			}
		});
	}

	private void createUIComponents() {
		mapPanelView = new MapPanel(820);
		mapPanel = mapPanelView;
	}

	@Override
	public void render() {
		updateMap();
		heroStats.setModel(model);
		heroStatsWrapper.add(heroStats);
		setContentPane(gamePanel);

		setTitle("Swingy");
		setSize(1500, 920);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setIconImage(ImageUtil.getImageIcon("/images/icons/swingy.png").getImage());
		setLocationRelativeTo(null);
		setVisible(true);

		scrollMapPanel.getVerticalScrollBar().setUnitIncrement(20);
		scrollToHero();
	}

	@Override
	public void stop() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.dispose();
	}

	@Override
	public void publishEvent(ModelEvent modelEvent) {
		if (modelEvent instanceof GameModelEvent) {
			switch ((GameModelEvent) modelEvent) {
				case MAP_CHANGED:
					updateMap();
					scrollToHero();
					break;
				case FIGHT:
					VillainDto villainDto = model.getVillainStats();
					if (villainDto != null) {
						FightDialog dialog = new FightDialog();
						dialog.run(thisFrame, villainDto,
								new Runnable() {
									@Override
									public void run() {
										controller.fight();
									}
								},
								new Runnable() {
									@Override
									public void run() {
										controller.flee();
									}
								});
					}
					break;
				case LOST:
					JOptionPane.showMessageDialog(thisFrame, "You have been lost", "Fight", WARNING_MESSAGE);
					break;
				case ARTEFACT_FOUND:
					ArtefactDto artefactDto = model.getFoundArtefactStats();
					if (artefactDto != null) {
						ArtefactDialog dialog = new ArtefactDialog();
						dialog.run(thisFrame, artefactDto,
								new Runnable() {
									@Override
									public void run() {
										controller.takeArtefact();
									}
								},
								new Runnable() {
									@Override
									public void run() {
										controller.throwArtefact();
									}
								});
					}
					break;
			}
		} else if (modelEvent instanceof LogModelEvent) {
			writeLogs(((LogModelEvent) modelEvent).getLogs());
		}
	}

	private void updateMap() {
		MapDto dto = model.getMap();
		mapPanelView.update(dto);
		mapPanel.revalidate();
		mapPanel.repaint();
	}

	private void scrollToHero() {
		Dimension heroDim = mapPanelView.getHeroDim();
		scrollMapPanel.getHorizontalScrollBar().setValue((int) (heroDim.getWidth() - scrollMapPanel.getHorizontalScrollBar().getModel().getExtent() / 2));
		scrollMapPanel.getVerticalScrollBar().setValue((int) (heroDim.getHeight() - scrollMapPanel.getVerticalScrollBar().getModel().getExtent() / 2));
		scrollMapPanel.validate();
		scrollMapPanel.repaint();
	}

	private void writeLogs(List<String> logs) {
		if (logs == null || logs.isEmpty()) {
			return;
		}

		for (String log : logs) {
			logsTextArea.append(log);
			logsTextArea.append("\n");
		}
		logsTextArea.append("\n");
	}
}
