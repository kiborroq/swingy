package ru.school21.swingy.view.gui;

import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

public class SpritePanel extends JPanel {
	private final Image background;
	private final Image foreground;
	private final int backgroundSize;
	private final int foregroundSize;
	private final boolean flipForeground;

	public SpritePanel(Image background, Image foreground, int backgroundSize, int foregroundSize, boolean flipForeground, Border border) {
		setPreferredSize(new Dimension(backgroundSize, backgroundSize));
		setBorder(border);

		this.background = background;
		this.foreground = foreground;
		this.backgroundSize = backgroundSize;
		this.foregroundSize = foregroundSize;
		this.flipForeground = flipForeground;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (background != null) {
			g.drawImage(background, 0, 0, backgroundSize, backgroundSize, this);
		}

		if (foreground != null) {
			int width = foregroundSize;
			int height = foregroundSize;

			int iHeight = foreground.getHeight(this);
			int iWidth = foreground.getWidth(this);
			if (iWidth > iHeight) {
				double times = iWidth / (double) iHeight;
				height = (int) (foregroundSize / times);
			} else if (iHeight > iWidth) {
				double times = (iHeight / (double) iWidth);
				width = (int) (foregroundSize / times);
			}

			int y = (backgroundSize - height) / 2;
			int x = (backgroundSize - width) / 2;

			if (flipForeground) {
				g.drawImage(foreground, x + width, y, -width, height, this);
			} else {
				g.drawImage(foreground, x, y, width, height, this);
			}
		}
	}
}
