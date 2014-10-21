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
		if(FightingMonk()){
			if(NeedToFlee()){
				return State.FLEE;
			}else{
				return State.FIGHTING;
			}
		}else{
			//we are not fighting
			if(NeedHealing()){
				return State.HEAL;
			}else{
				return State.IDLE;
			}
		}
	}

	public boolean Interacting() {
		if(Players.getLocal().getInteractingEntity() != null){
			return true;	
		}
		return false;
	}

	public boolean FightingMonk(){
		if(Interacting()){
			//we are interacting with a monk
			if(Players.getLocal().getInteractingEntity().getName().compareTo("Monk")==0){
				//if we can see health bar of monk we are interacting with, we are in combat
				return Players.getLocal().getInteractingEntity().isHealthBarVisible();
			}
		}
		return false;
	}

	public boolean NeedHealing(){
		if((double)Skills.getCurrentLevel(Skill.Hitpoints)/Skills.getRealLevel(Skill.Hitpoints)<0.5){
			return true;
		}
		return false;
	}

	public boolean NeedToFlee(){
		//monks can hit 1 only
		if(Skills.getCurrentLevel(Skill.Hitpoints)<5){
			return true;
		}
		return false;
	}

	public int getCombatStyle(){
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
	
	public int getCurrentXp(Skill skill){
		return Skills.getExperience(skill);
	}
	
	public Skill typeToSkill(int type){
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
