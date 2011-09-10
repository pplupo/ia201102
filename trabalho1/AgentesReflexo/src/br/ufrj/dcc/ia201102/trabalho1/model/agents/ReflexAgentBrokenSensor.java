package br.ufrj.dcc.ia201102.trabalho1.model.agents;

import br.ufrj.dcc.ia201102.trabalho1.model.Rule;
import br.ufrj.dcc.ia201102.trabalho1.model.sensors.BrokenSensor;

public class ReflexAgentBrokenSensor extends Agent {

	public ReflexAgentBrokenSensor() {
		super("ReflexAgentBrokenSensor", new BrokenSensor());
		ruleset.add(new Rule(Rule.Context.DIRTY, Rule.Action.CLEAN));
		ruleset.add(new Rule(Rule.Context.ROOM00, Rule.Action.MOVE_RIGHT));
		ruleset.add(new Rule(Rule.Context.ROOM01, Rule.Action.MOVE_DOWN));
		ruleset.add(new Rule(Rule.Context.ROOM11, Rule.Action.MOVE_LEFT));
		ruleset.add(new Rule(Rule.Context.ROOM10, Rule.Action.MOVE_UP));
	}
	
}
