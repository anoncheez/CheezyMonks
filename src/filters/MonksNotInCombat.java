package filters;

import org.tbot.util.Filter;
import org.tbot.wrappers.NPC;

public class MonksNotInCombat implements Filter<NPC> {

	@Override
	public boolean accept(NPC npc) {
		if(npc.getName().compareTo("Monk")==0){
			if(!npc.isHealthBarVisible()){
				return true;
			}
		}
		return false;
	}

}
