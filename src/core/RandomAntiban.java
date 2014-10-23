package core;

import java.awt.Rectangle;
import java.util.GregorianCalendar;
import java.util.Random;

import org.tbot.methods.Camera;
import org.tbot.methods.Mouse;
import org.tbot.methods.Settings;
import org.tbot.methods.Time;
import org.tbot.methods.Widgets;
import org.tbot.wrappers.WidgetChild;

public class RandomAntiban {
	private int combatStyle;
	private Random random;
	private final int STAT_START_X = 548;
	private final int STAT_START_Y = 206;
	private final int STAT_HEIGHT = 30;
	private final int STAT_WIDTH = 61;
	private final int STAT_SPACING_Y = 2; 
	private final int STAT_SPACING_X = 0;

	public RandomAntiban(){
		this.combatStyle = getCombatStyle();
		this.random = new Random();
	}

	public long execute(long antiBansDone, long startTime){
		// only run at the rate of 2 anti bans per minute with a 10% chance to execute
		if(antiBansPerMin(antiBansDone, startTime) <= 2 && random.nextInt(100) < 10){
			doRandomAntiBan();
			antiBansDone++;
		}
		return antiBansDone;
	}

	public void doRandomAntiBan(){
		Rectangle bounds;
		int r = random.nextInt(10);
		switch(r){
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
			if (Mouse.moveRandomly())
				Time.sleep(500,3000);
			break;
		case 6:
		case 7:
		case 8:
			OpenStatsMenu();
			bounds = mouseOverStat(combatStyle);
			if(Mouse.move((int) bounds.getCenterX(),(int) bounds.getCenterY(), bounds.width/2,bounds.height/2)){
				Time.sleep(3000, 6000);
			}
			if(Widgets.openTab(getRandomTab())){
				Time.sleep(1000,1500);
			}
			break;
		case 9:
			OpenStatsMenu();
			bounds = mouseOverStat(8);
			if(Mouse.move((int) bounds.getCenterX(),(int) bounds.getCenterY(), bounds.width/2,bounds.height/2)){
				Time.sleep(3000, 6000);
			}
			if(Widgets.openTab(getRandomTab())){
				Time.sleep(1000,1500);
			}
			break;
		}
	}

	private void OpenStatsMenu(){
		if(!Widgets.isOpen(Widgets.TAB_STATS)){
			Widgets.openTab(Widgets.TAB_STATS);
		}
	}

	private long antiBansPerMin(long antiBansDone, long startTime){
		if(antiBansDone == 0){
			return 0;
		}else{
			long totalRuntimeMins = ((new GregorianCalendar().getTimeInMillis() - startTime)/1000)/60;
			return (totalRuntimeMins/antiBansDone);
		}
	}

	private int getRandomTab(){
		int r = random.nextInt(13);
		switch(r){
		case 0:
			return Widgets.TAB_COMBAT;
		case 1:
			return Widgets.TAB_STATS;
		case 2:
			return Widgets.TAB_QUESTS;
		case 3:
			return Widgets.TAB_INVENTORY;
		case 4:
			return Widgets.TAB_EQUIPMENT;
		case 5:
			return Widgets.TAB_PRAYER;
		case 6:
			return Widgets.TAB_MAGIC;
		case 7:
			return Widgets.TAB_CLAN;
		case 8:
			return Widgets.TAB_FRIENDS;
		case 9:
			return Widgets.TAB_IGNORE;
		case 10:
			return Widgets.TAB_OPTIONS;
		case 11:
			return Widgets.TAB_EMOTES;
		case 12:
			return Widgets.TAB_MUSIC;
		}
		return Widgets.TAB_INVENTORY;
	}

	private Rectangle mouseOverStat(int index){
		int column = index / 8;
		int row = index % 8;
		int x = STAT_START_X + (column * STAT_SPACING_X) + (column * STAT_WIDTH);
		int y = STAT_START_Y + (row * STAT_SPACING_Y) + (row * STAT_HEIGHT);
		return new Rectangle(x, y, STAT_WIDTH, STAT_HEIGHT);
	}

	private int getCombatStyle(){
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
					type = 0;
					break;
				case "Slash":
				case "Smash":
				case "Hack":
				case "Kick":
				case "Impale":
				case "Lunge":
				case "Pummel":
					type = 1;
					break;
				case "Block":
				case "Fend":
					type = 2;
					break;
				case "Rapid":
				case "Accurate":
				case "Long Range":
					type = 3;
					break;
				default:
					type = -1; 
				}
				return type;	
			}
		}
		return -1;
	}

}
