package ru.school21.swingy.view.gui;

import lombok.SneakyThrows;
import ru.school21.swingy.controller.SelectHeroController;
import ru.school21.swingy.event.ModelEvent;
import ru.school21.swingy.event.type.HeroStatsModelEvent;
import ru.school21.swingy.event.type.SelectHeroModelEvent;
import ru.school21.swingy.model.SelectHeroModel;
import ru.school21.swingy.util.ImageUtil;
import ru.school21.swingy.view.SelectHeroView;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SelectHeroGuiView extends JFrame implements SelectHeroView {
	private JComboBox<String> createClazz;
	private JTextField name;
	private JPanel createWrapper;
	private JPanel createHeroPanel;
	private JPanel cHeroStatsPanel;
	private JPanel createHero;
	private HeroStatsGuiView cHeroStats;
	private JButton createButton;
	private JPanel selectWrapper;
	private JPanel sHeroStatsPanel;
	private JPanel selectHeroPanel;
	private JPanel selectHero;
	private JPanel tabsPanel;
	private HeroStatsGuiView sHeroStats;
	private JButton selectButton;
	private JLabel createClazzWarn;
	private JLabel createNameWarn;
	private JLabel selectHeroWarn;
	private JList<String> heroesList;
	private JScrollPane selectHeroPane;
	private JTabbedPane tabs;
	private HeroStatsGuiView currentHeroStats;

	private final SelectHeroController controller;
	private final SelectHeroModel model;

	public SelectHeroGuiView(SelectHeroController controller, SelectHeroModel model) {
		this.controller = controller;
		this.model = model;
		this.model.addListener(this, SelectHeroModelEvent.class);

		cHeroStats.setModel(model);
		sHeroStats.setModel(model);

		createClazz.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				controller.selectClazz((String) e.getItem());
			}
		});

		name.addActionListener(new ActionListener() {
			@SneakyThrows
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.inputName(name.getText());
			}
		});

		name.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {

			}

			@Override
			public void focusLost(FocusEvent e) {
				controller.inputName(name.getText());
			}
		});

		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.selectHero();
			}
		});

		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.selectHero();
			}
		});

		tabs.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (tabs.getSelectedIndex() == 0) {
					currentHeroStats = cHeroStats;
					model.addListener(cHeroStats, HeroStatsModelEvent.class);
					model.removeListener(sHeroStats, HeroStatsModelEvent.class);
					controller.createHeroMode();
				} else {
					currentHeroStats = sHeroStats;
					model.addListener(sHeroStats, HeroStatsModelEvent.class);
					model.removeListener(cHeroStats, HeroStatsModelEvent.class);
					controller.selectHeroMode();
				}
			}
		});

		heroesList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				controller.selectName(heroesList.getSelectedValue());
			}
		});

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int answer = JOptionPane.showConfirmDialog(null,"Do you want exit?", "Exit", JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					controller.exit();
				}
			}
		});
	}

	public void render() {
		DefaultComboBoxModel<String> createModel = new DefaultComboBoxModel<>();
		for (String heroClass : model.getHeroClasses()) {
			createModel.addElement(heroClass);
		}
		createModel.setSelectedItem(null);
		createClazz.setModel(createModel);

		Set<String> heroes = model.getHeroes();

		if (!heroes.isEmpty()) {
			DefaultComboBoxModel<String> heroesModel = new DefaultComboBoxModel<>();
			for (String name : heroes) {
				heroesModel.addElement(name);
			}
			heroesList.setModel(heroesModel);
		} else {
			tabs.setEnabledAt(1, false);
		}

		setTitle("Create/select hero");
		setSize(500, 540);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(ImageUtil.getImageIcon("/images/icons/swingy.png").getImage());

		currentHeroStats = cHeroStats;
		model.addListener(cHeroStats, HeroStatsModelEvent.class);
		model.removeListener(sHeroStats, HeroStatsModelEvent.class);

		createWrapper.add(cHeroStats);
		selectWrapper.add(sHeroStats);
		setContentPane(tabsPanel);

		setVisible(true);
	}

	@Override
	public void stop() {
		this.dispose();
	}

	@Override
	public void publishEvent(ModelEvent modelEvent) {
		if (modelEvent == SelectHeroModelEvent.ERRORS_CHANGED) {
			Map<String, String> errors = model.getErrors();
			this.createNameWarn.setText(Optional.ofNullable(errors.get("name")).orElse(""));
			this.createClazzWarn.setText(Optional.ofNullable(errors.get("clazz")).orElse(""));
		}
	}
}
