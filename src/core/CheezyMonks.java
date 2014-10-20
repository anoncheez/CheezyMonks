package core;

import java.util.GregorianCalendar;
import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.methods.Time;
import actions.Fight;
import actions.Flee;
import actions.Heal;

@Manifest(name = "CheezyMonks", authors = "anoncheez", version = 0.1, description = "Kills monks near edge")
public class CheezyMonks extends AbstractScript {
	private CheckState checkStates;
	private RandomAntiban antiban;
	private int antiBansDone;
	private long startTime;
	private int combatType;
	
	public CheezyMonks(){
		this.checkStates = new CheckState();
		this.combatType = checkStates.getCombatStyle();
		if(this.combatType==-1){
			log("Error determining combat type... terminating");
			setPaused(true);
		}
		this.antiban = new RandomAntiban(this.combatType);
		this.antiBansDone = 0;
		this.startTime = new GregorianCalendar().getTimeInMillis();
	}

	@Override
	public int loop() {
		antiBansDone = antiban.execute(antiBansDone, startTime);
		State state = checkStates.getState();
		switch (state) {
		
			case IDLE:
				log("Currently IDLE: Looking for a monk");
				new Fight(this);
				break;
		
			case FLEE:
				new Flee(this);
				break;
		
			case FIGHTING:
				Time.sleep(800,1600);
				break;
				
			case HEAL:
				log("Out of combat. We need to heal");
				new Heal(this);
				break;
			
			default:
				log("No State? Error");
				break;
		}
		return 1000;
	}

}

