package actions;

import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.Tile;

import core.RandomGenerator;

public class Flee {

	public Flee(){
		Area fleeArea = new Area(3050, 3469, 3053, 3471);
		if(fleeArea.contains(Players.getLocal().getLocation())){
			Time.sleep(1000);
			return;
		}
		if(Walking.setRun(true)){
			Tile[] fleeTiles = fleeArea.getTileArray();
			int tileNum = RandomGenerator.getInstance().getGenerator().nextInt(fleeTiles.length);
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
