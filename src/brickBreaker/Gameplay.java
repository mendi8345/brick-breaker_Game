package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private boolean ProxyLazyValue = false;
	private int score = 0;

	private int totalBricks = 21;// The number of bricks

	private Timer timer;
	private int delay = 8;

	private int playerX = 320;// Player Position

	private int ballposX = 120;// Position the ball across the screen
	private int ballposY = 350;// Position the ball along the screen

	private int ballXdir = -1;// A variable that counts the ball progresses across the screen
	private int ballYdir = -1;// A variable that counts the ball progresses along the screen
	private boolean play = false;// Game status is active or not
	private MapGenerator map;// A class which contains a two-dimensional array of bricks.Columns and rows

	public Gameplay() {
		this.map = new MapGenerator(3, 7);
		// Add a listener who can listen to keyEvent
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		this.timer = new Timer(this.delay, this);
		this.timer.start();
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		this.map.draw((Graphics2D) g);

		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);

		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + this.score, 590, 30);

		g.setColor(Color.green);
		g.fillRect(this.playerX, 550, 100, 8);

		g.setColor(Color.yellow);
		g.fillOval(this.ballposX, this.ballposY, 20, 20);

		if (this.totalBricks <= 0) {
			this.play = false;
			this.ballXdir = 0;
			this.ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You won: ", 260, 300);

			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press enter to restart", 230, 350);
		}

		if (this.ballposY > 570) {
			this.play = false;
			this.ballXdir = 0;
			this.ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game over, Scores " + this.score, 190, 300);

			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press enter to restart", 230, 350);
		}
		g.dispose();
	}

	// What happens from the moment a button is pressed which is set as the key to
	// the event
	@Override
	public void actionPerformed(ActionEvent e) {
		this.timer.start();
		if (this.play) {
			// אם הכדור נוגע בשחקן
			if (new Rectangle(this.ballposX, this.ballposY, 20, 20)
					.intersects(new Rectangle(this.playerX, 550, 100, 8))) {
				// הפוך כיוון כדור
				this.ballYdir = -this.ballYdir;
			}
			A: for (int i = 0; i < this.map.map.length; i++) {
				for (int j = 0; j < this.map.map[0].length; j++) {
					if (this.map.map[i][j] > 0) {

						int brickX = (j * this.map.bricWidth) + 80;
						int brickY = (i * this.map.bricHeigth) + 50;
						int bricWidth = this.map.bricWidth;
						int bricHeigth = this.map.bricHeigth;
						// Brick placement in the game
						Rectangle rectangle = new Rectangle(brickX, brickY, bricWidth, bricHeigth);
						Rectangle ballRect = new Rectangle(this.ballposX, this.ballposY, 20, 20);
						Rectangle bricRect = rectangle;

						// If the ball meets the brick, reset the brick to zero.That means you will
						// break the brick
						if (ballRect.intersects(bricRect)) {
							this.map.setBrickValue(0, i, j);
							this.totalBricks--;
							this.score += 5;

							// After hitting the brick, a ball movement changes accordingly
							if (((this.ballposX + 19) <= bricRect.x)
									|| ((this.ballposX + 1) >= (bricRect.x + bricRect.width))) {
								this.ballXdir = -this.ballXdir;
							} else {
								this.ballYdir = -this.ballYdir;
							}
							break A;
						}
					}
				}
			}

			this.ballposX += this.ballXdir;
			this.ballposY += this.ballYdir;

			if (this.ballposX <= 0) {
				this.ballXdir = -this.ballXdir;
			}
			if (this.ballposY <= 0) {
				this.ballYdir = -this.ballYdir;
			}
			if (this.ballposX > 670) {
				this.ballXdir = -this.ballXdir;
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (this.playerX >= 600) {
				this.playerX = 600;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (this.playerX <= 10) {
				this.playerX = 10;
			} else {
				moveLeft();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!this.play) {
				this.play = true;
				this.ballposX = 120;
				this.ballposY = 350;
				this.ballXdir = -1;
				this.ballYdir = -2;
				this.playerX = 310;
				this.score = 0;
				this.totalBricks = 21;
				this.map = new MapGenerator(3, 7);

			} else {
				moveLeft();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void moveRight() {
		this.play = true;
		this.playerX += 20;
	}

	public void moveLeft() {
		this.play = true;
		this.playerX -= 20;
	}
}
