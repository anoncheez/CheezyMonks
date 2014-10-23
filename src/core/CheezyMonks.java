package core;

import java.awt.Graphics;
import java.util.GregorianCalendar;

import org.tbot.bot.TBot;
import org.tbot.graphics.MouseTrail;
import org.tbot.graphics.SkillPaint;
import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Walking;
import actions.Fight;
import actions.Flee;
import actions.Heal;
import actions.WalkToMonks;

@Manifest(name = "CheezyMonks", authors = "anoncheez", version = 1.0 , description = "Kills monks, can heal and flee.")
public class CheezyMonks extends AbstractScript implements PaintListener {
	private SkillPaint sp = new SkillPaint();  
	private MouseTrail mt = new MouseTrail();
	private Fight fight = new Fight();
	private Flee flee = new Flee();
	private Heal heal = new Heal();
	private WalkToMonks walk = new WalkToMonks();
	private CheckState checkStates = new CheckState();
	private RandomAntiban antiban = new RandomAntiban();
	private long antiBansDone;
	private long startTime;
	
	public CheezyMonks(){
		this.antiBansDone = 0;
		this.startTime = new GregorianCalendar().getTimeInMillis();
		Time.sleep(1000);
	}

	@Override
	public int loop() {
		if(Walking.getRunEnergy()>20){
			Walking.setRun(true);
		}
		antiBansDone = antiban.execute(antiBansDone, startTime);
		State state = checkStates.getState();
		switch (state) {
			case WALK_TO_MONKS:
				walk.execute();
				break;
			case IDLE:
				fight.execute();
				break;
		
			case FLEE:
				flee.execute();
				break;
		
			case FIGHTING:
				Time.sleep(800,1600);
				break;
				
			case HEAL:
				heal.execute();
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
		long minsExpired = ((new GregorianCalendar().getTimeInMillis() - startTime)/1000/60);
		String timeString = String.format("Total Run Time %dmins", minsExpired);
		g.drawString(timeString, 95, 470);
		this.sp.draw(g);
		this.mt.draw(g);
	}

}

