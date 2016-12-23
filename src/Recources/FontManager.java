package Recources;


import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

public class FontManager {
	public static Font fonta;
	
	static final String fontLocation = "res/Fonts/Commodore Pixelized v1.2.ttf";
	
	
	static TrueTypeFont font;
	static TrueTypeFont font2;
	
	public static void initFonts(){
		 // load a default java font
	    Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
	    
	    font = new TrueTypeFont((java.awt.Font) awtFont, false);
	         
	    // load font from a .ttf file
	    try {
	        InputStream inputStream =  ResourceLoader.getResourceAsStream(fontLocation);
	         
	        Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
	        awtFont2 = awtFont2.deriveFont(24f); // set font size
	        font2 = new TrueTypeFont(awtFont2, false);
	             
	    } catch (Exception e) {
	        e.printStackTrace();
	    }  
	}
	
	public static void setFontSize(float size){
		// load font from a .ttf file
	    try {
	        InputStream inputStream =  ResourceLoader.getResourceAsStream(fontLocation);
	         
	        Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
	        awtFont2 = awtFont2.deriveFont(size); // set font size
	        font2 = new TrueTypeFont(awtFont2, false);
	             
	    } catch (Exception e) {
	        e.printStackTrace();
	    }  
	}
	
	public static int getStringLength(String str){
		return font2.getWidth(str);
	}
	
	public static void drawString (int x, int y, String str, Color col){
		font2.drawString(x, y, str, col);
	}
}
