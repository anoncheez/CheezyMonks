package core;
import org.tbot.methods.Players;
import org.tbot.methods.Skills;
import org.tbot.methods.Skills.Skill;



public class CheckState {

	public CheckState(){}

	public State getState(){
		if(fightingMonk()){
			if(needToFlee()){
				return State.FLEE;
			}else{
				return State.FIGHTING;
			}
		}else{
			//we are not fighting
			if(needHealing()){
				return State.HEAL;
			}else{
				return State.IDLE;
			}
		}
	}

	private boolean interacting() {
		if(Players.getLocal().getInteractingEntity() != null){
			return true;	
		}
		return false;
	}

	private boolean fightingMonk(){
		if(interacting()){
			//we are interacting with a monk
			if(Players.getLocal().getInteractingEntity().getName().equals("Monk")){
				//if we can see health bar of monk we are interacting with, we are in combat
				return Players.getLocal().getInteractingEntity().isHealthBarVisible();
			}
		}
		return false;
	}

	private boolean needHealing(){
		if((double)Skills.getCurrentLevel(Skill.Hitpoints)/Skills.getRealLevel(Skill.Hitpoints)<0.4){
			return true;
		}
		return false;
	}

	private boolean needToFlee(){
		//monks can hit 1 only
		if(Skills.getCurrentLevel(Skill.Hitpoints)<5){
			return true;
		}
		return false;
	}
	
}
