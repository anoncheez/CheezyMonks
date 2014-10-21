package actions;

import org.tbot.methods.Camera;
import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.NPC;

import evaluators.MonkEvaluator;
import filters.MonksNotInCombat;

public class Fight {

	public Fight() {
		boolean fightNotFound = true;
		while(fightNotFound){
			NPC monk = Npcs.getBest(new MonkEvaluator(), new MonksNotInCombat());
			if(monk!=null){
				if(monk.isOnScreen()){
					Camera.turnTo(monk);
					monk.interact("Attack");
					while(Players.getLocal().isMoving()){
						Time.sleep(200);
					}
					fightNotFound = false;
				}else{
					Path path = Walking.findPath(monk);
					if(path!=null){
						path.traverse();
					}
					while(Players.getLocal().isMoving()){
						Time.sleep(200);
					}
				}
			}else{
				//all monks are dead
				Time.sleep(800, 1200);
			}
		}
	}



}
