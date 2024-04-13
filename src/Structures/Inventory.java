package Structures;

import Recources.Libraries.Tile;

public class Inventory {
	public int greenKeys;
	public int redKeys;
	public int blueKeys;
	public int yellowKeys;

	public int chipCount;

	public void setInventory(int fire, int ice, int force, int flippers, int portal, int green, int red, int blue, int yellow){
		 hasFireBoots=fire==1; 
		 hasIceSkates=ice==1; 
		 hasForceBoots=force==1; 
		 hasFlippers=flippers==1;
		 hasPortalGun= portal==1;
		 
		 greenKeys = green;
		 redKeys = red;
		 blueKeys	 = blue;
		 yellowKeys = yellow;
		
	}
	
	
	public boolean hasFireBoots, hasIceSkates, hasForceBoots, hasFlippers;
	
	public boolean hasSinglePortalGun;
	public boolean hasPortalGun;
	
	public Inventory(){
		 hasFireBoots=false; hasIceSkates=false; hasForceBoots=false; hasFlippers=false;
	}
	
	public Boolean attemptCollect(Tile t){
		if(t == Tile.Key_Green){
			greenKeys++;
			return true;
		}else if(t == Tile.Key_Blue){
			blueKeys++;
			return true;
		}else if(t == Tile.Key_Yellow){
			yellowKeys++;
			return true;
		}else if(t == Tile.Key_Red){
			redKeys++;
			return true;
		}else if(t == Tile.Lock_Green && greenKeys > 0){
			//greenKeys--;
			return true;
		}else if(t == Tile.Lock_Blue && blueKeys > 0){
			blueKeys--;
			return true;
		}else if(t == Tile.Lock_Yellow && yellowKeys > 0){
			yellowKeys--;
			return true;
		}else if(t == Tile.Lock_Red && redKeys > 0){
			redKeys--;
			return true;
		}else if(t == Tile.Equipment_Fireboots){
			this.hasFireBoots = true;
			return true;
		}else if(t == Tile.Equipment_IceScates){
			this.hasIceSkates = true;
			return true;
		}else if(t == Tile.Equipment_Flippers){
			this.hasFlippers = true;
			return true;
		}else if(t == Tile.Equipment_ForceBoots){
			this.hasForceBoots = true;
			return true;
		}else if(t == Tile.Chip){
			return true;
		}else if(t == Tile.Portal_Gun){
			this.hasPortalGun = true;
			return true;
		}
		return false;
	}
	
	public void onStepThief(){
		hasFireBoots = false;
		hasFlippers = false;
		hasForceBoots = false;
		hasIceSkates = false;
		hasPortalGun = false;
	}
	
	@Override
	public String toString() {
		return 
					(hasFireBoots?1:0) + " " 
					+ (hasIceSkates?1:0) + " " 
					+ (hasForceBoots?1:0) + " "
					+ (hasFlippers?1:0) + " " 
					+ (hasPortalGun?1:0) + " " 
					+ (greenKeys) + " " 
					+ (redKeys) + " " 
					+ (blueKeys) + " " 
					+ (yellowKeys);
	}
}
