import java.util.ArrayList;

public class Human extends Player {
	
	public Human(String name, boolean myTurn, ArrayList<FireEngine> team) {
		super(name, myTurn);
		team = new ArrayList<FireEngine>();
	}
	
	private int resolveHumanDeath() {
		return this.getTeam().size();
	}
	
	private void constructTeam(int size) {
		for(int i = 0; i < size; i ++) {
			// not sure how this will look
		}
	}
	
	public void setDeath(FireEngine dead) {
		this.getTeam().remove(dead);
	}
}
