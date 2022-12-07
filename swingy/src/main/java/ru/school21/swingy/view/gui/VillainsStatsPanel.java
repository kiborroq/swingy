package ru.school21.swingy.view.gui;

import ru.school21.swingy.model.dto.VillainDto;
import ru.school21.swingy.model.helper.VillainHelper;
import ru.school21.swingy.util.ImageUtil;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VillainsStatsPanel extends JPanel {

	private static final ImageIcon AVATAR = ImageUtil.getImageIcon("/images/heroes/avatars/unknown.png");

	private JPanel wrapper;
	private JPanel villainStats;
	private JLabel avatar;
	private JTextField level;
	private JTextField experience;
	private JTextField attack;
	private JTextField hp;
	private JTextField name;

	public VillainsStatsPanel() {
		add(villainStats);
	}

	public void update(VillainDto villain) {
		setVillainName(villain.getName());
		setLevel(villain.getLevel());
		setAttack(villain.getAttack());
		setExperience(villain.getExperience());
		setHp(villain.getHp());
		setAvatar(VillainHelper.getImage(villain.getName()));
	}

	private void setVillainName(String name) {
		if (!this.name.getText().equals(name)) {
			this.name.setText(name);
		}
	}

	private void setAvatar(ImageIcon avatar) {
		if (avatar == null) {
			this.avatar.setIcon(AVATAR);
		} else {
			this.avatar.setIcon(avatar);
		}
	}

	private void setLevel(Integer level) {
		if (level != null && !this.level.getText().equals(level.toString())) {
			this.level.setText(level.toString());
		}
	}

	private void setExperience(Integer experience) {
		if (experience != null) {
			this.experience.setText(experience.toString());
		}
	}

	private void setAttack(Integer attack) {
		if (attack != null && !this.attack.getText().equals(attack.toString())) {
			this.attack.setText(attack.toString());
		}
	}

	private void setHp(Integer hp) {
		if (hp != null) {
			this.hp.setText(hp.toString());
		}
	}

}
