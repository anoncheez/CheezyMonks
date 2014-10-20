package actions;

import org.tbot.internal.AbstractScript;
import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.NPC;

import evaluators.MonkEvaluator;
import filters.MonksNotInCombat;

public class Fight {

    public Fight(AbstractScript api) {
    	NPC monk = Npcs.getBest(new MonkEvaluator(), new MonksNotInCombat());
    	if(monk!=null){
    		if(monk.isOnScreen()){
    			monk.interact("Attack");
    			while(Players.getLocal().isMoving()){
    				Time.sleep(200);
    			}
    		}else{
    			Walking.findPath(monk).traverse();
    			while(Players.getLocal().isMoving()){
    				Time.sleep(200);
    			}
    			monk.interact("Attack");
    			while(Players.getLocal().isMoving()){
    				Time.sleep(200);
    			}
    		}
    	}
    }
    
    
	
}
