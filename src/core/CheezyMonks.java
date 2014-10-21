package core;

import java.awt.Color;
import java.awt.Graphics;
import java.util.GregorianCalendar;

import org.tbot.bot.TBot;
import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Skills;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.Time;

import actions.Fight;
import actions.Flee;
import actions.Heal;

@Manifest(name = "CheezyMonks", authors = "anoncheez", version = 0.2, description = "Kills monks near edge")
public class CheezyMonks extends AbstractScript implements PaintListener {
	private CheckState checkStates;
	private RandomAntiban antiban;
	private long antiBansDone;
	private int startCombatExp;
	private int startHpExp;
	private long startTime;
	private int combatType;
	
	public CheezyMonks(){
		this.checkStates = new CheckState();
		this.combatType = checkStates.getCombatStyle();
		if(this.combatType==-1){
			log("Error determining combat type... terminating");
			setPaused(true);
		}
		this.startCombatExp = checkStates.getCurrentXp(checkStates.typeToSkill(combatType));
		this.startHpExp = checkStates.getCurrentXp(Skill.Hitpoints);
		this.antiban = new RandomAntiban(this.combatType);
		this.antiBansDone = 0;
		this.startTime = new GregorianCalendar().getTimeInMillis();
		Time.sleep(1000);
	}

	@Override
	public int loop() {
		antiBansDone = antiban.execute(antiBansDone, startTime);
		State state = checkStates.getState();
		switch (state) {
		
			case IDLE:
				new Fight();
				break;
		
			case FLEE:
				new Flee();
				break;
		
			case FIGHTING:
				Time.sleep(800,1600);
				break;
				
			case HEAL:
				new Heal();
				break;
			
			default:
				log("No State? Error");
				break;
		}
		onRepaint(TBot.getBot().getCanvas().getGraphics());
		return 1000;
	}

	@Override
	public void onRepaint(Graphics g) {
		g.setColor(Color.RED);
		long secondsExpired = ((new GregorianCalendar().getTimeInMillis() - startTime)/1000);
		double combatXpPerHour = ((double)(checkStates.getCurrentXp(checkStates.typeToSkill(combatType)) 
				- this.startCombatExp) / secondsExpired)*3600;
		double hpXpPerHour = ((double)(checkStates.getCurrentXp(Skill.Hitpoints)
				- this.startHpExp) / secondsExpired)*3600;
		int currentCombatLevel = Skills.getRealLevel(checkStates.typeToSkill(combatType));
		int currentHpLevel = Skills.getRealLevel(Skill.Hitpoints);
		String combatString = checkStates.typeToSkill(combatType).name() + " lvl: " +  currentCombatLevel;
		String hpString = "Hitpoints lvl: " + currentHpLevel;
		String combatXpString = String.format("XP/h: %.2f", combatXpPerHour);
		String hpXpString = String.format("XP/h: %.2f", hpXpPerHour);
		g.drawString(combatString, 20, 290);
		g.drawString(hpString, 330, 290);
		g.drawString(combatXpString, 20, 320);
		g.drawString(hpXpString, 330, 320);
	}

}

