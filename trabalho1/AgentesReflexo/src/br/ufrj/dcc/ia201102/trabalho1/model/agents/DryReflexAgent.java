package br.ufrj.dcc.ia201102.trabalho1.model.agents;

import br.ufrj.dcc.ia201102.trabalho1.model.Rule;
import br.ufrj.dcc.ia201102.trabalho1.model.sensors.Sensor;

public class DryReflexAgent extends Agent {

	public DryReflexAgent() {
		super("DryReflexAgent", new Sensor());
		
		ruleset.add(new Rule(Rule.Context.WET, Rule.Action.SUCK));
		ruleset.add(new Rule(Rule.Context.ROOM00, Rule.Action.MOVE_RIGHT));
		ruleset.add(new Rule(Rule.Context.ROOM01, Rule.Action.MOVE_DOWN));
		ruleset.add(new Rule(Rule.Context.ROOM11, Rule.Action.MOVE_LEFT));
		ruleset.add(new Rule(Rule.Context.ROOM10, Rule.Action.MOVE_UP));
	}
	
}
