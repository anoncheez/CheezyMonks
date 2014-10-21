package actions;

import java.util.Random;

import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.Tile;

public class Flee {
	private static final Area FLEE_AREA = new Area(3050, 3469, 3053, 3471);
	private Random random;

	public Flee(){
		this.random = new Random();		
	}
	
	public void execute(){
		if(atFleeArea()){
			return;
		}
		moveToFleeArea();
	}
	
	private boolean atFleeArea(){
		if(FLEE_AREA.contains(Players.getLocal().getLocation())){
			return true;
		}
		return false;
	}
	
	private void moveToFleeArea(){
		if(Walking.setRun(true)){
			Tile[] fleeTiles = FLEE_AREA.getTileArray();
			int tileNum = random.nextInt(fleeTiles.length);
			while(fleeTiles[tileNum].distance()>5){
				Walking.findPath(fleeTiles[tileNum]).traverse();
				Time.sleep(3000, 4000);
			}
			if(Walking.setRun(false)){
				Time.sleep(1000, 2000);
			}
		}
	}
	
}
