package Entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import Entities.PortalEntities.PortalFissler;
import Entities.StandardEnemies.IceBlock;
import Recources.Libraries.TileProperties;
import Recources.Libraries.Direction;
import Recources.Libraries.Tile;
import Recources.SpriteManager;
import Structures.Level;
import Structures.Position;
import Structures.Vector2;

public abstract class MovingEntity extends GameEntitiy implements Cloneable{
	
	public static final int moveSpeedMS = 200;

	public int _ImageLeftIndex, _ImageRightIndex, _ImageUpIndex, _ImageDownIndex;
	
	public Tile replacementTile;
	//Scale of 0-1
	public Vector2 movementOffset;
	
	public Vector2 portalShadowOffset;
	public Position portalShadowTarget;
	
	public boolean freezeUntilNextFrame;
	
	public MovingEntity(Position pos) {
		super(pos);
		movementOffset = new Vector2();
		replacementTile = Tile.Floor;
		
		relations[Tile.ToggleWall_Open.index] = TileProperties.Passable;
		relations[Tile.Fire.index] = TileProperties.Deadly;
		relations[Tile.Water.index] = TileProperties.Deadly;
		relations[Tile.Button_Blue.index] = TileProperties.Passable;
		relations[Tile.Button_Green.index] = TileProperties.Passable;
		relations[Tile.Button_Trap.index] = TileProperties.Passable;
		relations[Tile.Button_Red.index] = TileProperties.Passable;
		relations[Tile.Button_Yellow.index] = TileProperties.Passable;
		relations[Tile.Button_FunnelReverse.index] = TileProperties.Passable;
		relations[Tile.Bomb.index] = TileProperties.Destructable_Deadly;
		
		relations[Tile.Trap_Open.index] = TileProperties.Passable;
		relations[Tile.Trap_Closed.index] = TileProperties.Passable;
		
		relations[Tile.Teleport.index] = TileProperties.Passable;
		relations[Tile.Hint.index] = TileProperties.Passable;
		relations[Tile.Checkpoint.index] = TileProperties.Passable;
		
		
		relations[Tile.ForceFloor_Down.index] = TileProperties.Passable;
		relations[Tile.ForceFloor_Up.index] = TileProperties.Passable;
		relations[Tile.ForceFloor_Right.index] = TileProperties.Passable;
		relations[Tile.ForceFloor_Left.index] = TileProperties.Passable;
		relations[Tile.ForceFloor_Random.index] = TileProperties.Passable;
		
		relations[Tile.ExcursionFunnel_Down.index] = TileProperties.Passable;
		relations[Tile.ExcursionFunnel_Up.index] = TileProperties.Passable;
		relations[Tile.ExcursionFunnel_Right.index] = TileProperties.Passable;
		relations[Tile.ExcursionFunnel_Left.index] = TileProperties.Passable;
		relations[Tile.ExcursionFunnel_Random.index] = TileProperties.Passable;
		
		
		relations[Tile.Ice.index] = TileProperties.Passable;
		relations[Tile.Ice_NE.index] = TileProperties.Passable;
		relations[Tile.Ice_NW.index] = TileProperties.Passable;
		relations[Tile.Ice_SE.index] = TileProperties.Passable;
		relations[Tile.Ice_SW.index] = TileProperties.Passable;
	
		relations[Tile.ThinWall_N.index] = TileProperties.Passable;
		relations[Tile.ThinWall_E.index] = TileProperties.Passable;
		relations[Tile.ThinWall_S.index] = TileProperties.Passable;
		relations[Tile.ThinWall_W.index] = TileProperties.Passable;
		relations[Tile.ThinWall_SE.index] = TileProperties.Passable;
	
		
		relations[Tile.Light_Bridge.index] = TileProperties.Passable;
		relations[Tile.Laser.index] = TileProperties.Deadly;
		
	}
	
	public void halfUpdate(Level level){
		Tile tileOver = level.getTile(position);
		if(tileOver.isIce()){
			moveOnIce(level);
		}else if(tileOver.isForceFloor() || tileOver.isFunnel()){
			moveOnForceFloor(level);
		}
	}
	
	
	
	public boolean moveOnIce(Level level){
		Tile tileOver = level.getTile(position);
		if(tileOver.isIce() && !hasIceSkates()){
			if(!this.move(Tile.getIceDirection(tileOver, position.dir), level)){
				position.dir = Tile.getIceDirection(tileOver, position.dir).reverse();
			}
			return false;
		}
		return true;
	}
	
	public boolean moveOnForceFloor(Level level){
		Tile tileOver = level.getTile(position);
		if(tileOver.isForceFloor() && !hasForceFloorBoots()  || tileOver.isFunnel()){
			this.move(Tile.getForceFloorDirection(tileOver), level);
			return canMoveOnForceFloors();
		}else{
			return true;
		}
	}
	
/*	public boolean MovingFloorsUpdate(Level level){
		Tile tileOver = level.getTile(position);
		if(tileOver.isIce() && !hasIceSkates()){
			if(!this.move(Tile.getIceDirection(tileOver, position.dir), level)){
				position.dir = position.dir.reverse();
			}
			return false;
		}else if(tileOver.isForceFloor() && !hasForceFloorBoots()){
			this.move(Tile.getForceFloorDirection(tileOver), level);
			return canMoveOnForceFloors();
		}else{
			return true;
		}
	}
	*/
	public void logicUpdate(Level level){
		Tile tileOver = level.getTile(position);
		TileProperties tileOverProperty = relations[tileOver.index];
		
		if(freezeUntilNextFrame){
			return;
		}
		if(tileOver == Tile.Metal_Wall){
			return;
		}
		if(tileOver == Tile.Water && this.hasFlippers()){
			
		}else if(tileOver == Tile.Fire && this.hasFireBoots()){
			
		}else if(tileOverProperty == TileProperties.Deadly){
			isAlive=false;
			return;
		}else if(tileOverProperty == TileProperties.Destructable_Deadly ){
			
			isAlive=false;
			if(tileOver == Tile.Fire){
				level.setTile(position, Tile.Water);
			}else if(tileOver == Tile.Water){
				if(this instanceof IceBlock){
					level.setTile(position, Tile.Ice);	
				}else
				level.setTile(position, Tile.Dirt);	
			}else
			level.setTile(position, Tile.Floor);
			return;
		}
		else if(level.isTileMonsterOcupied(position) && this instanceof Player){
			isAlive=false;
			return;
		}
		
		
		
		if(tileOverProperty == TileProperties.Destructable){

			level.setTile(position, replacementTile);
		}
		
		if(forcedMoveUpdate(level))
		{}
		else if(tileOver.isIce() && !hasIceSkates()){
			moveOnIce(level);
		}else if(tileOver.isFunnel() && !canMoveOnForceFloors()){
			
		}else if(tileOver.isForceFloor()){
			if( this.canMoveOnForceFloors()){
				if(!makeMoveDecision(level)){
					moveOnForceFloor(level);
				}
				
			}else{
				moveOnForceFloor(level);
			}
		}else
		//if(this.MovingFloorsUpdate(level))
			makeMoveDecision(level);
		
	}
	
	public boolean forcedMoveUpdate(Level level){
		Tile tileOver = level.getTile(position);
		Position startPos = position;
		if(tileOver == Tile.Teleport){
			do{
				Position portalDestination = level.getNextReverseReadTile(position, tileOver);
				portalDestination.dir= position.dir;
				position = portalDestination;
			}while(!move(position.dir, level) && !position.equals(startPos));
			
			if(position.equals(startPos)){
				return false;}
			return true;
		}
		return false;
		
	}
	public abstract boolean makeMoveDecision(Level level);
	
	

	public void render(Graphics g, Vector2 offset, float scale, Level level){
		
		renderSprite(g, offset, scale, level, movementOffset, position);
		
		//drawShadow
		if(portalShadowOffset != null){
			
			renderSprite(g, offset, scale, level, portalShadowOffset, portalShadowTarget, getPortalShadowImage(level, portalShadowTarget.dir));
			
		}
	}
	
	public void renderSprite(Graphics g, Vector2 offset, float scale, Level level, Vector2 moveOffset, Position pos){
		renderSprite(g, offset, scale, level, moveOffset, pos, getImage(level, pos.dir));
		
	}
	
	public Image getImage(Level level, Direction dir){
		return SpriteManager.getSprite(getImageIndex(level, dir));
	}
	public Image getPortalShadowImage(Level level, Direction dir){
		return getImage(level, dir);
	}
	
	public int getImageIndex(Level level, Direction dir)
	{
		if(position.dir == null){
			return _ImageUpIndex;
		}
		
		switch (dir){
		case North:
			return _ImageUpIndex;
		case East:
			return _ImageRightIndex;
		case South:
			return _ImageDownIndex;
		case West:
			return _ImageLeftIndex;
		default:
			return _ImageDownIndex;
		}
	}
	
	
	public Boolean move(Direction dir, Level level){
		if(freezeUntilNextFrame){
			//return false;
		}
		
		Position target = level.getPostitionInDiretion(position, dir);
		boolean isGoingThroughPortal = false;
		
		if(level.directionLeadsToPortal(position, dir))
		{
			if(this instanceof PortalFissler){
				isAlive = false;
				return false;
			}
			isGoingThroughPortal = true;
			System.out.println("Move:  " + target.x + " " + target.y + " " + target.dir.name() + " " + level.getTile(target));			
			
			Position p = position.add(Position.direction(dir));
			p.dir = dir;
			this.onAttemptEnterPortal(p, dir);
		}else{
			onFailEnterPortal();
		}
		
		if(pushYeilds(target, level)){
		
			Tile targetTile = level.getTile(target);
			if(canMoveToTile(targetTile)){
				onMove(target, level);
				if((targetTile.isFunnel() || level.getTile(position).isFunnel()) && !isGoingThroughPortal){
					movementOffset =new Vector2().add(target).scale(-1).add(movementOffset.add(position));// position);		
				}else
					movementOffset =new Vector2().add(target).scale(-1).add(Position.direction(target.dir.reverse()).add(target));// position);
				this.position = target;
				
				
				if(isGoingThroughPortal)
				{
					OnEnterPortal(dir, target.dir);
					
					System.out.println("Move2:  " + target.x + " " + target.y + " " + target.dir.name() + " " + level.getTile(target));			
				}
				
				onCommitMove(target, level);
				
				return true;
			}
		}
		onFailEnterPortal();
		return false;
		
	}
	
	public void OnEnterPortal(Direction enter, Direction exit){}
	
	public void onCommitMove(Position target, Level level){
		if(level.getTile(target) == Tile.Button_Green){
			level.toggleGreen();
			return;
		}
		if(level.getTile(target) == Tile.Button_Blue){
			level.toggleBlue();
			return;
		}if(level.getTile(target) == Tile.Button_Red){
			level.toggleRed(target);
			return;
		}if(level.getTile(target) == Tile.Button_Yellow){
			level.toggleYellow(target.dir);
			return;
		}if(level.getTile(target) == Tile.Button_FunnelReverse){
			level.reverseFunnels();
			return;
		}
	}
	
	public Boolean pushYeilds(Position target, Level level){
		
		if(level.isTileBlocked(target)){
			return false;
		}
		
		if(isOverClosedTrap(level)){
			return false;
		}
		
		Tile t = level.getTile(target);
		if(!t.canEnterBlock(target.dir)){
			return false;
		}
		Tile on = level.getTile(position);
		if(!on.canLeaveBlock(target.dir)){
			return false;
		}
		return true;
	}
	
	public boolean isOverClosedTrap(Level level){
		Tile on = level.getTile(position);
		if(on == Tile.Trap_Closed){
			return true;
		}
		return false;
	}
	
	public Boolean canMoveToTile(Tile targetTile){
		if(targetTile == Tile.NULL_TILE){
			return false;
		}
		TileProperties prop = this.relations[targetTile.index];
		
		switch (prop){
		case Blocking:
			return false;
		case Passable:
			return true;
		case Deadly:
			return true;
		case Destructable:
			return true;
		case Destructable_Deadly:
			return true;
		default:
			return false;
		}
	}

	public void frameUpdate(int deltaTimeMS, Level level){
		Tile tile = level.getTile(position);
		float speed = moveSpeedMS;
		
		if(tile.isIce() && !hasIceSkates()){
			speed/=2;
		}if(tile.isForceFloor() && !hasForceFloorBoots()){
			speed/=2;
		}
		
		movementOffset = movementOffset.moveTowardsZero( speed, deltaTimeMS);
		
		if(portalShadowOffset != null)
		{
			portalShadowOffset = portalShadowOffset.moveTowardsZero(speed, deltaTimeMS);
			if(portalShadowOffset.x == 0 && portalShadowOffset.y == 0){
				onFailEnterPortal();
			}
			//System.out.println(portalShadowOffset.x + " " + portalShadowOffset.y);
		}
	}
	
	public void onMove(Position target, Level level){
		
	}
	
	public void onAttemptEnterPortal(Position pos, Direction dir){
		
		portalShadowOffset = Vector2.direction(dir.reverse());
		portalShadowTarget = pos;
		//System.out.println(portalShadowOffset.x + " " + portalShadowOffset.y);
	}
	
	public void onFailEnterPortal(){
		portalShadowOffset = null;
		portalShadowTarget = null;
	}
	
	public MovingEntity cloned(){
		try {
			return (MovingEntity) this.clone();//(MovingEntity) new getClass(); getClass().newInstance();
		}  catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean hasForceFloorBoots(){
		return false;
	}
	public boolean hasIceSkates(){
		return false;
	}public boolean hasFlippers(){
		return false;
	}public boolean hasFireBoots(){
		return false;
	}public boolean canMoveOnForceFloors(){
		return false;
	}
	
	@Override
	public void onStartFrame(Level level) {
		super.onStartFrame(level);
		freezeUntilNextFrame = false;
	}
}
