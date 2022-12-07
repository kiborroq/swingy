package ru.school21.swingy.view.gui;

import ru.school21.swingy.event.EventListener;
import ru.school21.swingy.event.ModelEvent;
import ru.school21.swingy.event.type.HeroStatsModelEvent;
import ru.school21.swingy.model.HeroStatsModel;
import ru.school21.swingy.model.dto.ArtefactDto;
import ru.school21.swingy.model.dto.HeroStatsDto;
import ru.school21.swingy.model.helper.ArtefactHelper;
import ru.school21.swingy.model.helper.HeroHelper;
import ru.school21.swingy.util.ImageUtil;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HeroStatsGuiView extends JPanel implements EventListener {
	private static final ImageIcon AVATAR = ImageUtil.getImageIcon("/images/heroes/avatars/unknown.png");

	private JLabel avatar;
	private JTextField clazz;
	private JTextField level;
	private JTextField experience;
	private JPanel heroStat;
	private JTextField attack;
	private JTextField defense;
	private JTextField hp;
	private JTextField name;
	private JPanel wrapper;
	private JPanel heroArtifacts;
	private JLabel weapon;
	private JLabel helm;
	private JLabel armor;
	private JLabel attackIncrease;
	private JLabel hpIncrease;
	private JLabel defenseIncrease;

	private HeroStatsModel model;

	public HeroStatsGuiView() {
		avatar.setIcon(AVATAR);
		add(heroStat);
	}

	public void setModel(HeroStatsModel model) {
		this.model = model;
		this.model.addListener(this, HeroStatsModelEvent.class);
		updateHeroStats();
	}

	@Override
	public void publishEvent(ModelEvent modelEvent) {
		if (modelEvent instanceof HeroStatsModelEvent) {
			updateHeroStats();
		}
	}

	private void updateHeroStats() {
		HeroStatsDto hero = model.getHeroStats();
		setHeroName(hero.getName());
		setLevel(hero.getLevel());
		setAttack(hero.getAttack());
		setDefense(hero.getDefense());
		setExperience(hero.getExperience(), hero.getTargetExperience());
		setHp(hero.getHp(), hero.getMaxHp());

		String heroClass = hero.getClazz();
		setClazz(heroClass);
		setAvatar(heroClass != null ? HeroHelper.getAvatar(heroClass) : AVATAR);

		setWeapon(hero.getWeapon());
		setHelm(hero.getHelm());
		setArmor(hero.getArmor());
	}

	private void setHeroName(String name) {
		if (!this.name.getText().equals(name)) {
			this.name.setText(name);
		}
	}

	private void setClazz(String clazz) {
		if (!this.clazz.getText().equals(clazz)) {
			this.clazz.setText(clazz);
		}
	}

	private void setAvatar(ImageIcon avatar) {
		if (avatar == null) {
			this.avatar.setIcon(AVATAR);
		} else if (!this.avatar.getIcon().equals(avatar)) {
			this.avatar.setIcon(avatar);
		}
	}

	private void setLevel(Integer level) {
		if (level != null && !this.level.getText().equals(level.toString())) {
			this.level.setText(level.toString());
		}
	}

	private void setExperience(Integer experience, Integer targetExperience) {
		if (experience != null && targetExperience != null) {
			this.experience.setText(experience + " / " + targetExperience);
		}
	}

	private void setAttack(Integer attack) {
		if (attack != null && !this.attack.getText().equals(attack.toString())) {
			this.attack.setText(attack.toString());
		}
	}

	private void setDefense(Integer defense) {
		if (defense != null && !this.defense.getText().equals(defense.toString())) {
			this.defense.setText(defense.toString());
		}
	}

	private void setHp(Integer hp, Integer maxHp) {
		if (hp != null && maxHp != null) {
			this.hp.setText(hp + " / " + maxHp);
		}
	}

	private void setWeapon(ArtefactDto weapon) {
		if (weapon != null) {
			this.weapon.setIcon(ArtefactHelper.getImageByName(weapon.getName()));
			this.attackIncrease.setText(String.format("+%d attack", weapon.getIncrease()));
		} else {
			this.weapon.setIcon(null);
			this.attackIncrease.setText("");
		}
	}

	private void setHelm(ArtefactDto helm) {
		if (helm != null) {
			this.helm.setIcon(ArtefactHelper.getImageByName(helm.getName()));
			this.hpIncrease.setText(String.format("+%d hp", helm.getIncrease()));
		} else {
			this.helm.setIcon(null);
			this.hpIncrease.setText("");
		}
	}

	private void setArmor(ArtefactDto armor) {
		if (armor != null) {
			this.armor.setIcon(ArtefactHelper.getImageByName(armor.getName()));
			this.defenseIncrease.setText(String.format("+%d defense", armor.getIncrease()));
		} else {
			this.armor.setIcon(null);
			this.defenseIncrease.setText("");
		}
	}

}
