package actions;

import org.tbot.internal.AbstractScript;
import org.tbot.methods.Camera;
import org.tbot.methods.NPCChat;
import org.tbot.methods.Npcs;
import org.tbot.methods.Skills;
import org.tbot.methods.Time;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Tile;

import core.RandomGenerator;

public class Heal {

	public Heal(AbstractScript api){
		NPC abbot = Npcs.getNearest(2577);
		if(abbot!=null){
			api.log("Found abbot");
			if(abbot.isOnScreen()){
				Camera.turnTo(abbot);
			}else{
				moveToAbbotArea();
			}
			while(Skills.getCurrentLevel(Skill.Hitpoints)!=Skills.getRealLevel(Skill.Hitpoints)){
				abbot.interact("Talk-to");
				Time.sleep(500, 1200);
				if(NPCChat.isChatOpen()){
					while(NPCChat.getOptionCount()==0){
						if(NPCChat.canContinue()){
							NPCChat.clickContinue();
						}
						Time.sleep(500, 1200);
					}
					String[] options = NPCChat.getOptions();
					NPCChat.selectOption(options[0]);
					while(NPCChat.isChatOpen()){
						if(NPCChat.canContinue()){
							NPCChat.clickContinue();
						}
						Time.sleep(500, 1200);
					}
				}
			}
		}
	}

	private void moveToAbbotArea(){
		Area abbotArea = new Area(3058, 3483, 3059, 3486);
		Tile[] abbotTiles = abbotArea.getTileArray();
		if(abbotTiles.length==0){
		}else{
			int tileNum = RandomGenerator.getInstance().getGenerator().nextInt(abbotTiles.length);
			while(Walking.getRealDistanceTo(abbotTiles[tileNum])>2){
				Walking.findPath(abbotTiles[tileNum]).traverse();
				Time.sleep(3000, 4000);
			}
		}
	}
}
