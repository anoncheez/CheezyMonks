package core;
import org.tbot.methods.Players;
import org.tbot.methods.Skills;
import org.tbot.methods.Skills.Skill;
import org.tbot.wrappers.Area;



public class CheckState {
	static final Area MONK_AREA = new Area(3044, 3482, 3059, 3498);

	public CheckState(){}

	public State getState(){
		if(!atMonkArea()){
			return State.WALK_TO_MONKS;
		}
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
	
	private boolean atMonkArea(){
		if(MONK_AREA.contains(Players.getLocal().getLocation())){
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
