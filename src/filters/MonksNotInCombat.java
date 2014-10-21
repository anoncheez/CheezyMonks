package filters;

import org.tbot.util.Filter;
import org.tbot.wrappers.NPC;

public class MonksNotInCombat implements Filter<NPC> {

	@Override
	public boolean accept(NPC npc) {
		if(npc.getName().equalsIgnoreCase("Monk")){
			if(!npc.isHealthBarVisible()){
				return true;
			}
		}
		return false;
	}

}
