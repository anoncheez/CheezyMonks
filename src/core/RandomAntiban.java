package core;

import java.util.GregorianCalendar;
import org.tbot.methods.Camera;
import org.tbot.methods.Mouse;
import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.wrappers.WidgetChild;

public class RandomAntiban {
	private int combatStyle;
	
	public RandomAntiban(int combatStyle){
		this.combatStyle = combatStyle;
	}
	
	public int execute(int antiBansDone, long startTime){
		// only run at the rate of 2 anti bans per minute with a 10% chance to execute
		if(antiBansPerMin(antiBansDone, startTime) <= 2 && RandomGenerator.getInstance().getGenerator().nextInt(100) < 10){
			doRandomAntiBan();
			antiBansDone++;
		}
		return antiBansDone;
	}
	
	public void doRandomAntiBan(){
		int random = RandomGenerator.getInstance().getGenerator().nextInt(10);
		switch(random){
			case 0:
			case 1:
				Camera.rotateRandomly();
				break;
			case 2:
			case 3:
				Camera.rotateAndTiltRandomly();
				break;
			case 4:
			case 5:
			case 6:
				if (Mouse.moveRandomly())
					Time.sleep(500,3000);
				break;
			case 7:
			case 8:
				if(Widgets.openTab(Widgets.TAB_STATS)){
					WidgetChild child = Widgets.getWidget(320, combatStyle);
					if(Mouse.move(child.getCenterLocation(), (child.getWidth()/2)-5, (child.getHeight()/2)-5)){
						Time.sleep(3000, 6000);
					}
					if(Widgets.openTab(getRandomTab())){
						Time.sleep(1000,1500);
					}
				}
				break;
			case 9:
				if(Widgets.openTab(Widgets.TAB_STATS)){
					WidgetChild child = Widgets.getWidget(320, 9);
					if(Mouse.move(child.getCenterLocation(), (child.getWidth()/2)-5, (child.getHeight()/2)-5)){
						Time.sleep(3000, 6000);
					}
					if(Widgets.openTab(getRandomTab())){
						Time.sleep(1000,1500);
					}
				}
				break;
		}
	}
	
	public long antiBansPerMin(int antiBansDone, long startTime){
		if(antiBansDone == 0){
			return 0;
		}else{
			long totalRuntimeMins = ((new GregorianCalendar().getTimeInMillis() - startTime)/1000)/60;
			return (totalRuntimeMins/antiBansDone);
		}
	}
	
	public int getRandomTab(){
		int random = RandomGenerator.getInstance().getGenerator().nextInt(2);
		if(random==0)
			return Widgets.TAB_COMBAT;
		if(random==1)
			return Widgets.TAB_INVENTORY;
		return Widgets.TAB_INVENTORY;
	}
	
}
