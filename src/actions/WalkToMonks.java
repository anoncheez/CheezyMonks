package actions;

import java.util.Random;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.Tile;

public class WalkToMonks {
	private static final Area ARRIVE_AREA = new Area(3050, 3483, 3053, 3498);
	private Random random;
	
	public WalkToMonks(){
		random = new Random();
	}
	public void execute(){
		moveToMonkArea();
	}
	
	private void moveToMonkArea(){
		if(!ARRIVE_AREA.contains(Players.getLocal().getLocation())){
			Tile[] arriveTiles = ARRIVE_AREA.getTileArray();
			int tileNum = random.nextInt(arriveTiles.length);
			while(Walking.getRealDistanceTo(arriveTiles[tileNum])>2){
				Walking.findPath(arriveTiles[tileNum]).traverse();
				Time.sleep(300, 400);
				while(Players.getLocal().isMoving()){
					Time.sleep(500);
				}
			}
		}
	}
}
