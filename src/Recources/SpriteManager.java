package Recources;

import org.newdawn.slick.Image;

public class SpriteManager {
	
	public static Image[] sprites;
	public static Image[] numberSprites;
	
	public static final int maxSprite = 256;
	public static final int maxNumberSprite = 15;
	
	public static Image getSprite(int i){
		try{
			return sprites[i];
		}catch(Exception e){
			System.out.println("Sprite not found: " + i);
			return null;
		}
	}
	
	public static Image getNumberSprite(int i){
		return numberSprites[i];
	}
	
	public static void loadSprites(){
		sprites = new Image[maxSprite];
		
		for (int c=0; c<sprites.length; c++){
			try{
				Image i = new Image("res/"+ c+ ".png");
				i.setFilter(Image.FILTER_NEAREST);
				sprites[c]= i;
			}catch(Exception e){
				System.out.println("Sprite not found: " + c);
			}
		}
		
		numberSprites = new Image[maxNumberSprite];
		
        for (int c=0; c<numberSprites.length; c++){
            try{
            	numberSprites[c]= new Image("res/n-"+ c+ ".png");
	        }catch(Exception e){
				System.out.println("Sprite not found: n-" + c);
	        }
        }
	}
}
