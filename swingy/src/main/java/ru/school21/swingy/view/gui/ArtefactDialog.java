package ru.school21.swingy.view.gui;

import ru.school21.swingy.model.dto.ArtefactDto;
import ru.school21.swingy.model.helper.ArtefactHelper;
import ru.school21.swingy.util.ImageUtil;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ArtefactDialog extends JDialog {
	private JPanel contentPane;
	private JPanel buttonPanel;
	private JButton takeButton;
	private JButton throwButton;
	private JLabel avatar;
	private JTextField name;
	private JTextField type;
	private JTextField increase;
	private JLabel increaseLabel;

	private Runnable onTake;
	private Runnable onThrow;

	public ArtefactDialog() {
		super((JFrame) null, "Artefact", true);

		takeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onTake();
			}
		});

		throwButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onThrow();
			}
		});

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onThrow();
			}
		});
	}

	public void run(Component parent, ArtefactDto dto, Runnable onTake, Runnable onThrow) {
		setSize(350, 400);
		setLocationRelativeTo(parent);
		setResizable(false);
		setIconImage(ImageUtil.getImageIcon("/images/icons/swingy.png").getImage());
		setContentPane(contentPane);

		this.name.setText(dto.getName());
		this.type.setText(dto.getType().name());
		this.avatar.setIcon(ArtefactHelper.getImageByName(dto.getName()));
		this.increaseLabel.setText(dto.getType().getSkill());
		this.increase.setText("+" + dto.getIncrease());

		this.onTake = onTake;
		this.onThrow = onThrow;

		pack();
		setVisible(true);
	}

	private void onTake() {
		onTake.run();
		dispose();
	}

	private void onThrow() {
		onThrow.run();
		dispose();
	}

}
