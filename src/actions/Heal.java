package actions;
import java.util.Random;
import org.tbot.methods.Camera;
import org.tbot.methods.NPCChat;
import org.tbot.methods.Npcs;
import org.tbot.methods.Players;
import org.tbot.methods.Skills;
import org.tbot.methods.Time;
import org.tbot.methods.Skills.Skill;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Tile;

public class Heal {
	private static final Area ABBOT_AREA = new Area(3058, 3483, 3059, 3486);
	private Random random;

	public Heal(){
		random = new Random();
	}

	public void execute(){
		NPC abbot = Npcs.getNearest("Abbot Langley");
		moveToAbbotArea(abbot);
		talkToAbbot(abbot);
	}


	private void talkToAbbot(NPC abbot){
		if(abbot!=null){
			while(Skills.getCurrentLevel(Skill.Hitpoints)!=Skills.getRealLevel(Skill.Hitpoints)){
				abbot.interact("Talk-to");
				Time.sleep(1200, 2000);
				while(Players.getLocal().isMoving()){
					Time.sleep(500);
				}
				Time.sleep(500, 1200);
				if(NPCChat.isChatOpen()){
					while(NPCChat.getOptionCount()==0){
						if(NPCChat.canContinue()){
							NPCChat.clickContinue();
						}
						Time.sleep(1200, 2000);
					}
					String[] options = NPCChat.getOptions();
					NPCChat.selectOption(options[0]);
					while(NPCChat.isChatOpen()){
						if(NPCChat.canContinue()){
							NPCChat.clickContinue();
						}
						Time.sleep(1200, 2000);
					}
				}
			}
		}
	}

	private void moveToAbbotArea(NPC abbot){
		if(abbot!=null && !ABBOT_AREA.contains(Players.getLocal().getLocation())){
			Tile[] abbotTiles = ABBOT_AREA.getTileArray();
			int tileNum = random.nextInt(abbotTiles.length);
			while(Walking.getRealDistanceTo(abbotTiles[tileNum])>2){
				Walking.findPath(abbotTiles[tileNum]).traverse();
				Time.sleep(300, 400);
				while(Players.getLocal().isMoving()){
					Time.sleep(500);
				}
			}
		}
		Camera.turnTo(abbot);
	}
}
