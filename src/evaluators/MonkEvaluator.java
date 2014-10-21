package evaluators;

import org.tbot.methods.walking.Walking;
import org.tbot.util.Evaluator;
import org.tbot.wrappers.NPC;

public class MonkEvaluator implements Evaluator<NPC> {

	@Override
	public double evaluate(NPC npc) {
		if(npc.isInteractingWithLocalPlayer())
			return 0;
		return Walking.getRealDistanceTo(npc.getLocation());
	}

}
