package Recources;

import org.newdawn.slick.Graphics;

public class NumberRenderer {
	
	public static int numberWidth = 34;
	
	
	
	public static void drawstat(Graphics g, int num, int y) {
        int a = num/100;
        int b = (num-(a*100))/10;
        int c = (num-(a*100)-(b*10));
        
        if (a==0){
            a=10;
            if(b==0){
                b=10;
                if (c==0){
                	a=11;
                }
            }
        }
        int x=668;
        try{
        g.drawImage(SpriteManager.getNumberSprite(b), x+34, y);
        g.drawImage(SpriteManager.getNumberSprite(c), x+ 2*34, y);
        g.drawImage(SpriteManager.getNumberSprite(a), x, y);
        }catch(Exception e){}
        if (num<0){
        	g.drawImage(SpriteManager.getNumberSprite(12), x, y);
        }
        
    }
}
