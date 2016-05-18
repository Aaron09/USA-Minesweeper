package minesweeper;


import java.awt.GridLayout;
import javax.swing.JPanel;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JButton;


public class mineScreen extends JPanel{
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 4120450237039990654L;
	
		private int w = 600;
		private int h = 600;
		int diffWidth = 16;
		int diffHeight = 16;
		Random gen = new Random();
		int bombChance = 0;
		int bombs = 0;
		int totalClicks = 0;
		mineButton testForMine = new mineButton();
		ArrayList<generalButton>list = new ArrayList<generalButton>(diffWidth*diffHeight);
		
		public mineScreen(){
		
			//creates the panel that holds the bomb grid
			setLayout(new GridLayout(diffWidth,diffHeight));
	
			//sets the analogous array list and fills bomb grid
			genBombs(list, diffWidth, diffHeight, bombs, gen);
			bombAmountSetter(list, diffWidth, diffHeight, testForMine, bombs);
			
			//adds the mine and nonMine buttons to the mineScreen panel
			addButtons(list, diffWidth, diffHeight);
			
			//sets the original revealed locations
			setOriginals(list, diffWidth, diffHeight, testForMine);
			
			//allows the finished bomb panel to be seen and adds it to the JFrame mineScreen
			setVisible(true);
			
		}
		public void revealBoard(ArrayList<generalButton>list){
			for(int i=0; i<list.size(); i++){
				list.get(i).iconSetter();
			}
		}
		
		public int countRemainingBombs(ArrayList<generalButton>list, int initBombs){
			int totalFlagged = 0;
			for(int i=0; i<list.size(); i++){
				if(list.get(i).getFlagged() == true){
					totalFlagged++;
				}
			}
			return initBombs - totalFlagged;
				
		}
		public ArrayList<generalButton> resetList(){
				return new ArrayList<generalButton>(diffWidth*diffHeight);
		}
		public void clearPanel(ArrayList<generalButton>list){
			for(int i=0; i<list.size(); i++){
				this.remove((JButton)list.get(i));
			}
			
		}
		public int countInitialBombs(ArrayList<generalButton>list){
			int BOMBS = 0;
			for (int i=0; i<list.size(); i++){
				if(list.get(i).getID() == 2){
					BOMBS++;
				}
			}
			return BOMBS;
		}
		
		public void genBombs(ArrayList<generalButton>list, int width, int height, int bombs, Random gen){
			for (int i=0; i<width*height; i++){
				if(gen.nextInt(5) == 4){
					list.add(new mineButton());
				}else{
					list.add(new nonMineButton(bombs));
				}
			}
		}
		
		public void bombAmountSetter(ArrayList<generalButton>list, int width, int height, mineButton mine, int bombs){
			for (int i=0; i<width*height; i++){
				if(list.get(i).getID() == 1){
					int total = setBombsNum(list, i, mine, bombs, width, height);
					list.set(i, new nonMineButton(total));
				}
			}
		}
		
		public void addButtons(ArrayList<generalButton>list, int width, int height){
			for (int i=0; i<width*height; i++){
				this.add((JButton) list.get(i));
			}
		
		}
		
		public void setOriginals(ArrayList<generalButton>list, int width, int height, mineButton mine){
			int y = (-width)-(width/3);
			while(y<width*3){
				for (int j = ((width*height)/2)-y; j<((width*height)/2)-y+(width/3); j++){
					if(list.get(j).getID() == 1){
						list.get(j).iconSetter();
					}
				}
				y+=16;
			}	
		}
		public static int setBombsNum(ArrayList<generalButton>list, int i, mineButton mineB, int bTotal, int dWidth, int dHeight){
			
			if(i==0){
				bTotal = bombTopLeft(list,i,mineB,bTotal,dWidth);
			}
			else if(i>0 && i<dWidth-1){
				bTotal = bombTopRow(list,i,mineB,bTotal,dWidth);
			}
			else if(i==dWidth-1){
				bTotal = bombTopRight(list,i,mineB,bTotal,dWidth);
			}
			else if(i==(dWidth*dHeight)-dWidth){
				bTotal = bombBottomLeft(list,i,mineB,bTotal,dWidth);
			}
			else if(i>(dWidth*dHeight)-dWidth && i<(dWidth*dHeight)-1){
				bTotal = bombBottomRow(list,i,mineB,bTotal,dWidth);
			}
			else if(i==(dWidth*dHeight)-1){
				bTotal = bombBottomRight(list,i,mineB,bTotal,dWidth);
			}
			else if(i==dWidth||i==dWidth*2||i==dWidth*3||i==dWidth*4
					||i==dWidth*5||i==dWidth*6||i==dWidth*7||i==dWidth*8
					||i==dWidth*9||i==dWidth*10||i==dWidth*11||i==dWidth*12
					||i==dWidth*13||i==dWidth*14||i==dWidth*15){
				bTotal = bombLeftCol(list,i,mineB,bTotal,dWidth);
			}
			else if(i==((dWidth*2)-1)||i==((dWidth*3)-1)||i==((dWidth*4)-1)
					||i==((dWidth*5)-1)||i==((dWidth*6)-1)||i==((dWidth*7)-1)
					||i==((dWidth*8)-1)||i==((dWidth*9)-1)||i==((dWidth*10)-1)
					||i==((dWidth*11)-1)||i==((dWidth*12)-1)||i==((dWidth*13)-1)
					||i==((dWidth*14)-1)||i==((dWidth*15)-1)||i==((dWidth*16)-1)){
				bTotal = bombRightCol(list,i,mineB,bTotal,dWidth);
			}
			else{
				bTotal = bombElsewhere(list,i,mineB,bTotal,dWidth);
			}
			return bTotal;
			
		}
		
		
		/*public static int setBombsNum(ArrayList<generalButton>list, int i, mineButton mineB, int bTotal, int dWidth){
		
			if(i==0){
				bTotal = bombTopLeft(list,i,mineB,bTotal,dWidth);
			}
			else if(i>0 && i<15){
				bTotal = bombTopRow(list,i,mineB,bTotal,dWidth);
			}
			else if(i==15){
				bTotal = bombTopRight(list,i,mineB,bTotal,dWidth);
			}
			else if(i==240){
				bTotal = bombBottomLeft(list,i,mineB,bTotal,dWidth);
			}
			else if(i>240 && i<255){
				bTotal = bombBottomRow(list,i,mineB,bTotal,dWidth);
			}
			else if(i==255){
				bTotal = bombBottomRight(list,i,mineB,bTotal,dWidth);
			}
			else if(i==16||i==32||i==48||i==64||i==80||i==96||i==112||i==128||i==144||i==160||i==176||i==192||i==208||i==224||i==240){
				bTotal = bombLeftCol(list,i,mineB,bTotal,dWidth);
			}
			else if(i==31||i==47||i==63||i==79||i==95||i==111||i==127||i==143||i==159||i==175||i==191||i==207||i==223||i==239||i==255){
				bTotal = bombRightCol(list,i,mineB,bTotal,dWidth);
			}
			else{
				bTotal = bombElsewhere(list,i,mineB,bTotal,dWidth);
			}
			return bTotal;
			
		}*/
		public static int bombTopLeft(ArrayList<generalButton>list, int i, mineButton mineB, int bTotal, int dWidth){
			if (list.get(i+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			return bTotal;
		}
		public static int bombTopRight(ArrayList<generalButton>list, int i, mineButton mineB, int bTotal, int dWidth){
			if (list.get(i-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			return bTotal;
		}
		public static int bombTopRow(ArrayList<generalButton>list, int i, mineButton mineB, int bTotal, int dWidth){
			if (list.get(i+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			return bTotal;
		}
		public static int bombBottomRight(ArrayList<generalButton>list, int i, mineButton mineB, int bTotal, int dWidth){
			if (list.get(i-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			return bTotal;
		}
		public static int bombBottomLeft(ArrayList<generalButton>list, int i, mineButton mineB, int bTotal, int dWidth){
			if (list.get(i+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			return bTotal;
		}
		public static int bombBottomRow(ArrayList<generalButton>list, int i, mineButton mineB, int bTotal, int dWidth){
			if (list.get(i+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			return bTotal;
		}
		public static int bombLeftCol(ArrayList<generalButton>list, int i, mineButton mineB, int bTotal, int dWidth){
			if (list.get(i+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			return bTotal;
		}
		public static int bombRightCol(ArrayList<generalButton>list, int i, mineButton mineB, int bTotal, int dWidth){
			if (list.get(i-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			return bTotal;
		}
		public static int bombElsewhere(ArrayList<generalButton>list, int i, mineButton mineB, int bTotal, int dWidth){
			if (list.get(i+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i+dWidth-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth+1).getID() == 2){
				bTotal = bTotal + 1;
			}
			if (list.get(i-dWidth-1).getID() == 2){
				bTotal = bTotal + 1;
			}
			return bTotal;
		}
		
		
		public int getHeight(){
			return h;
		}
		public int getWidth(){
			return w;
		}
}
