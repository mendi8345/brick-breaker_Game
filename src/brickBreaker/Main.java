package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		JFrame obg = new JFrame();
		Gameplay gameplay = new Gameplay();
		obg.setBounds(10, 10, 700, 600);
		obg.setTitle("Breakout Ballobg");
		obg.setResizable(false);
		obg.setVisible(true);
		obg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obg.add(gameplay);
	}

}
