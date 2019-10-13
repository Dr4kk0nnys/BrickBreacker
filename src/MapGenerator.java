import java.awt.*;

class MapGenerator {

	int[][] map;
	int brickWidth;
	int brickHeight;

	MapGenerator(int row, int column){
		map = new int[row][column];

		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				map[i][j] = 1;
			}
		}

		this.brickWidth = 540/column;
		this.brickHeight = 150/row;
	}

	void draw(Graphics2D graphics){
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				if (map[i][j] > 0){
					graphics.setColor (Color.WHITE);
					graphics.fillRect (j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

					graphics.setStroke (new BasicStroke (3));
					graphics.setColor (Color.BLACK);
					graphics.drawRect (j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
	}

	void setBrickValue (int row, int column){
		map[row][column] = 0;
	}

}
