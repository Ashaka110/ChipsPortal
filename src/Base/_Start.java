package Base;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;



public class _Start {

	public static final String gamename = "Chips Challenge";
	
	public static void main(String[] args) {
		System.out.println("running...");
		
		
		
		AppGameContainer appgc;
		try{	
			
			appgc = new AppGameContainer(new Game(gamename));
			appgc.setDisplayMode(Game.DISPLAY_WIDTH, Game.DISPLAY_HEIGHT,  false);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
		
	}

}
