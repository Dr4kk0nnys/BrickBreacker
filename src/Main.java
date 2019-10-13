import javax.swing.*;

class Main {

	public static void main (String[] args) {

		JFrame mainWindow = new JFrame (); // mainWindow == obj
		GameWindow gameWindow = new GameWindow ();

		mainWindow.setBounds (10, 10, 700, 600);
		mainWindow.setTitle ("Brick breaker");
		mainWindow.setResizable (false);
		mainWindow.setVisible (true);
		mainWindow.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

		mainWindow.add (gameWindow);
	}
}
