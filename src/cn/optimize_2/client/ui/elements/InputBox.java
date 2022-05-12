package cn.optimize_2.client.ui.elements;

import cn.optimize_2.client.ui.Rect;
import cn.optimize_2.client.ui.clickable.ClickEntity;
import cn.optimize_2.utils.graphic.Color;
import cn.optimize_2.utils.graphic.Renderer;
import cn.optimize_2.utils.input.InputHandler;
import cn.optimize_2.utils.render.animate.Translate;
import cn.optimize_2.utils.string.ChatAllowedCharacters;
import cn.optimize_2.utils.timer.MsTimer;

public class InputBox {
	String text = "";
	String emptyText;
	boolean input;
	double x;
	double y;
	double width;
	double height;
	Color color;
	Color borderColor;
	Color textColor;
	boolean password;
	ClickEntity click;

	Translate anim = new Translate(0, 0);

	boolean hidden;

	boolean underline;

	Renderer renderer;

	MsTimer timer = new MsTimer();

	public InputBox(String text, double x, double y, double width, double height, Color color, Color textColor,
			Color borderColor, boolean password, Renderer renderer) {
		InputHandler.inputBoxes.add(this);
		this.emptyText = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.textColor = textColor;
		this.borderColor = borderColor;
		this.password = password;
		this.renderer = renderer;
		this.click = new ClickEntity(x, y, width, height, () -> {
		}, () -> {
		}, () -> {
		}, () -> {
		}, () -> {
		});
	}

	public void draw() {
		this.click.tick();
		if (click.isInArea()) {

			if (click.isLeftPressed()) {
				input = true;
			}
		} else {
			if (click.isLeftPressed()) {
				input = false;
			}
		}

		if (timer.sleep(500))
			underline = !underline;

		if (click.isInArea() || input) {
			anim.interpolate((float) width + 1, (float) height + 1, 0.3f);
		} else {
			anim.interpolate(0, 0, 0.3f);
		}

		new Rect(x, y, width, height, color, renderer).draw();

		new Rect(x, y - 1, anim.getX(), 1, borderColor, renderer).draw();
		new Rect(x + width, y, 1, anim.getY(), borderColor, renderer).draw();
		new Rect(x + width - anim.getX(), y + height, anim.getX(), 1, borderColor, renderer).draw();
		new Rect(x - 1, y + height - anim.getY(), 1, anim.getY(), borderColor, renderer).draw();

		String renderString = text;
		if (password)
			renderString = this.text.replaceAll("(?s).", "*");
		if (text.isEmpty() && !input) {
			renderer.drawText(emptyText, (int) (x + 2), (int) (y));
			// StringUtils.drawString(emptyText, x + 2, y + height / 2 - 2, textColor,
			// SizeType.Size16);
		} else {
			renderer.drawText(renderString +
					((underline && input) ? "_" : ""), (int) (x + 2), (int) (y));
		}
	}

	public void backspaceTyped() {
		if (input && !text.isEmpty()) {
			text = text.substring(0, text.length() - 1);
			return;
		}
	}

	public void keyTyped(int keyCode) {
		try {
			if (input && ChatAllowedCharacters.isAllowedCharacter((char) keyCode)
					&& renderer.getTextWidth(text + (char) keyCode) <= this.width - 15) {
				text += (char) keyCode;
			}
		} catch (NullPointerException e) {

		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
		click.setX(x);
	}

	public void setY(double y) {
		this.y = y;
		click.setY(y);
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
}
