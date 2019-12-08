package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	public int map[][];
	public int bricWidth;
	public int bricHeigth;

	public MapGenerator(int row, int col) {
		this.map = new int[row][col];
		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[0].length; j++) {
				this.map[i][j] = 1;
			}
		}
		this.bricWidth = 540 / col;// 180
		this.bricHeigth = 150 / row;// 21
	}

	public void draw(Graphics2D g) {
		for (int i = 0; i < this.map.length; i++) {
			for (int j = 0; j < this.map[0].length; j++) {
				if (this.map[i][j] > 0) {
					g.setColor(Color.white);
					g.fillRect((j * this.bricWidth) + 80, (i * this.bricHeigth) + 50, this.bricWidth, this.bricHeigth);

					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect((j * this.bricWidth) + 80, (i * this.bricHeigth) + 50, this.bricWidth, this.bricHeigth);
				}
			}
		}
	}

	public void setBrickValue(int value, int row, int col) {
		this.map[row][col] = value;
	}

}
