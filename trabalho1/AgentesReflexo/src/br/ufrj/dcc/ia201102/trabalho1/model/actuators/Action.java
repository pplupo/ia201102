package br.ufrj.dcc.ia201102.trabalho1.model.actuators;

import br.ufrj.dcc.ia201102.trabalho1.model.environment.Room;

public interface Action {
	public String execute(Room room);
	public int cost();
}
