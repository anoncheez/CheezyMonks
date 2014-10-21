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
		
	}
	
	public void execute(){
		NPC monk = Npcs.getBest(new MonkEvaluator(), new MonksNotInCombat());
		if(monk!=null){
			moveToMonk(monk);
			attackMonk(monk);
		}else{
			//all monks are dead, script will go back into fight after 1sec
		}
	}
	
	private void attackMonk(NPC monk){
		if(monk!=null && monk.isOnScreen()){
			Camera.turnTo(monk);
			monk.interact("Attack");
			while(Players.getLocal().isMoving()){
				Time.sleep(200);
			}
		}
	}
	
	private void moveToMonk(NPC monk){
		if(monk!=null && !monk.isOnScreen()){
			Path path = Walking.findPath(monk);
			if(path!=null){
				path.traverse();
			}
			while(Players.getLocal().isMoving()){
				Time.sleep(200);
			}
		}
	}



}
