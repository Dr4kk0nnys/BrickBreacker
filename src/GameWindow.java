import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JPanel implements KeyListener, ActionListener {
	private boolean play;
	private int score;
	private int totalBricks;
	private Timer timer;

	private int playerX;

	private int ballPositionX;
	private int ballPositionY;
	private int ballXDirection;
	private int ballYDirection;

	private MapGenerator mapGenerator;

	GameWindow (){
		mapGenerator = new MapGenerator (3, 7);

		this.play = false;
		this.score = 0;
		this.totalBricks = 21;
		int delay = 4;

		this.playerX = 310;
		this.ballPositionX = 120;
		this.ballPositionY = 350;
		this.ballXDirection = -1;
		this.ballYDirection = -2;

		addKeyListener (this);
		setFocusable (true);
		setFocusTraversalKeysEnabled (false);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics graphics){
		// Background color =>
		graphics.setColor (Color.BLACK);
		graphics.fillRect (1, 1, 692, 692);

		// Drawing the map =>
		mapGenerator.draw ((Graphics2D) graphics);

		// Borders =>
		graphics.setColor (Color.YELLOW);
		graphics.fillRect (0, 0, 3, 592);
		graphics.fillRect (0, 0, 692, 3);
		graphics.fillRect (695, 0, 10, 592);

		// Covering an annoying white line =>
		graphics.setColor (Color.BLACK);
		graphics.fillRect (693, 3, 4, 592);

		// Score =>
		graphics.setColor (Color.WHITE);
		graphics.setFont (new Font("serif", Font.BOLD, 25));
		graphics.drawString ("" + this.score, 590, 30);

		// Paddle =>
		graphics.setColor (Color.CYAN);
		graphics.fillRect (playerX, 550, 100, 8);

		// The ball =>
		graphics.setColor (Color.WHITE);
		graphics.fillOval (ballPositionX, ballPositionY, 20, 20);

		this.checkLose (graphics);
		this.checkWin (graphics);

		graphics.dispose ();
	}

	@Override
	public void actionPerformed (ActionEvent e) {
		this.timer.start ();

		if(this.play){
			if(new Rectangle (this.ballPositionX, this.ballPositionY, 20, 20).intersects (new Rectangle (this.playerX, 550, 100, 8))){
				this.ballYDirection = -this.ballYDirection;
			}

			A: for(int i = 0; i < this.mapGenerator.map.length; i++){
				for(int j = 0; j < this.mapGenerator.map[0].length; j++){
					if(this.mapGenerator.map[i][j] > 0){

						int brickX = j * this.mapGenerator.brickWidth + 80;
						int brickY = i * this.mapGenerator.brickHeight + 50;
						int brickWidth = this.mapGenerator.brickWidth;
						int brickHeight = this.mapGenerator.brickHeight;

						Rectangle rectangle = new Rectangle (brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRectangle = new Rectangle (this.ballPositionX, this.ballPositionY, 20, 20);

						if(ballRectangle.intersects (rectangle)){
							this.mapGenerator.setBrickValue (i, j);
							this.totalBricks--;
							this.score += 5;

							if(ballPositionX + 19 <= rectangle.x || ballPositionX + 1 >= rectangle.x + rectangle.width){
								ballXDirection = -ballXDirection;
							} else {
								ballYDirection = -ballYDirection;
							}

							break A;
						}
					}
				}
			}

			this.ballPositionX += this.ballXDirection;
			this.ballPositionY += this.ballYDirection;

			if(this.ballPositionX < 0){
				this.ballXDirection = -this.ballXDirection;
			}

			if(this.ballPositionY < 0){
				this.ballYDirection = -this.ballYDirection;
			}

			if(this.ballPositionX > 670){
				this.ballXDirection = -this.ballXDirection;
			}
		}

		repaint ();
	}

	@Override
	public void keyTyped (KeyEvent e) { }

	@Override
	public void keyPressed (KeyEvent e) {
		if(e.getKeyCode () == KeyEvent.VK_RIGHT){ // If the user pressed the right arrow ("==>")
			if(this.playerX >= 600){ // And it is not on the map wall
				this.playerX = 600;
			} else {
				moveRight();
			}
		} else if(e.getKeyCode () == KeyEvent.VK_LEFT){ // If the user pressed the left arrow ("<==")
			if(this.playerX < 10){
				this.playerX = 10;
			} else {
				moveLeft();
			}
		} else if(e.getKeyCode () == KeyEvent.VK_ENTER && !this.play){ // If the user pressed enter, and it did won or lost
			restartGame ();
		}
	}

	@Override
	public void keyReleased (KeyEvent e) { }

	private void moveRight(){
		this.play = true;
		this.playerX += 20;
	}

	private void moveLeft(){
		this.play = true;
		this.playerX -= 20;
	}

	private void checkLose(Graphics graphics){
		if(this.ballPositionY > 570){ // If you actually lost the game
			this.play = false;
			this.ballXDirection = 0;
			this.ballYDirection = 0;

			graphics.setColor (Color.RED);
			graphics.setFont (new Font("serif", Font.BOLD, 30));
			graphics.drawString ("Game over, Scores: " + this.score, 190, 300);
			graphics.drawString ("Press enter to Restart", 190, 350);
		}
	}

	private void checkWin(Graphics graphics){
		if(this.totalBricks == 0){ // If you actually won the game
			this.play = false;
			this.ballXDirection = 0;
			this.ballYDirection = 0;

			graphics.setColor (Color.GREEN);
			graphics.setFont (new Font("serif", Font.BOLD, 35));
			graphics.drawString ("You won!", 190, 300);
			graphics.drawString ("Press enter to Restart", 190, 350);
		}
	}

	private void restartGame(){
		this.play = true;
		this.ballPositionX = 120;
		this.ballPositionY = 350;
		this.ballXDirection = -1;
		this.ballYDirection = -2;
		this.playerX = 310;
		this.score = 0;
		this.totalBricks = 21;
		this.mapGenerator = new MapGenerator (3, 7);

		repaint ();
	}
}