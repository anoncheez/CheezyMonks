package core;
import org.tbot.methods.Players;
import org.tbot.methods.Settings;
import org.tbot.methods.Skills;
import org.tbot.methods.Widgets;
import org.tbot.methods.Skills.Skill;
import org.tbot.wrappers.WidgetChild;



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
		if((double)Skills.getCurrentLevel(Skill.Hitpoints)/Skills.getRealLevel(Skill.Hitpoints)<0.5){
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

	protected int getCombatStyle(){
		if(Widgets.openTab(Widgets.TAB_COMBAT)){
			WidgetChild child = Widgets.getWidget(593, (Settings.get(Settings.SETTING_COMBAT_STYLE)*4)+6);
			if(child!=null){
				String combatStyle = child.getText();
				int type;
				switch(combatStyle){
				case "Chop":
				case "Stab":
				case "Spike":
				case "Punch":
				case "Pound":
					type = 1;
					break;
				case "Slash":
				case "Smash":
				case "Hack":
				case "Kick":
				case "Impale":
				case "Lunge":
				case "Pummel":
					type = 2;
					break;
				case "Block":
				case "Fend":
					type = 3;
					break;
				case "Rapid":
				case "Accurate":
				case "Long Range":
					type = 4;
					break;
				default:
					type = -1; 
				}
				return type;	
			}
		}
		return -1;
	}
	
	protected int getCurrentXp(Skill skill){
		return Skills.getExperience(skill);
	}
	
	protected Skill typeToSkill(int type){
		switch(type){
			case 1:
				return Skill.Attack;
			case 2:
				return Skill.Strength;
			case 3:
				return Skill.Defence;
			case 4:
				return Skill.Ranged;
			default:
				return Skill.Strength;
		}
	}
}
