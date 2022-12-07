package ru.school21.swingy.view.gui;

import ru.school21.swingy.model.dto.VillainDto;
import ru.school21.swingy.util.ImageUtil;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FightDialog extends JDialog {
	private JPanel villainStatsPanel;
	private VillainsStatsPanel villainsStats;
	private JButton fightButton;
	private JButton fleeButton;
	private JPanel buttonPanel;
	private JPanel dialogPanel;
	private JPanel villainStatsWrapper;

	private Runnable onFight;
	private Runnable onFlee;

	public FightDialog() {
		super((JFrame) null, "Fight", true);

		fightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onFight();
			}
		});

		fleeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onFlee();
			}
		});

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onFlee();
			}
		});
	}

	public void run(Component parent, VillainDto dto, Runnable onFight, Runnable onFlee) {
		setSize(300, 400);
		setLocationRelativeTo(parent);
		setResizable(false);
		setIconImage(ImageUtil.getImageIcon("/images/icons/swingy.png").getImage());

		villainsStats.update(dto);
		villainStatsWrapper.add(villainsStats);
		setContentPane(dialogPanel);

		this.onFight = onFight;
		this.onFlee = onFlee;

		pack();
		setVisible(true);
	}

	private void onFight() {
		new Thread(onFight).start();
		dispose();
	}

	private void onFlee() {
		new Thread(onFlee).start();
		dispose();
	}

}
