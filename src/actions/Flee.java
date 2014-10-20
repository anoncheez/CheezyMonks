package actions;

import org.tbot.internal.AbstractScript;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.Tile;

import core.RandomGenerator;

public class Flee {

	public Flee(AbstractScript api){
		Walking.setRun(true);
		Time.sleep(200);
		Area fleeArea = new Area(3050, 3469, 3053, 3471);
		if(fleeArea.contains(Players.getLocal().getLocation())){
			Time.sleep(1000);
			return;
		}
		Tile[] fleeTiles = fleeArea.getTileArray();
		if(fleeTiles.length==0){
			api.log("WTF NO FLEE TILES WE'RE GOING TO DIE!!!");
		}else{
			api.log("Fleeing");
			int tileNum = RandomGenerator.getInstance().getGenerator().nextInt(fleeTiles.length);
			while(fleeTiles[tileNum].distance()>5){
				Walking.findPath(fleeTiles[tileNum]).traverse();
				Time.sleep(3000, 4000);
			}
			Walking.setRun(false);
		}
		Time.sleep(1000, 2000);
	}
}
