import java.util.ArrayList;

public class Enemy extends Player {
	
	public Enemy(String name, boolean myTurn, ArrayList<Fortress> team) {
		super(name, myTurn);
		team = new ArrayList<>();
	}
	
	private void contructTeam(int Constant.getEnemyTeamSize) { // Not sure what this will look like 
		for(int i; i < Constant.getEnemyTeamSize; i ++) {
			this.getTeam().add(Enemy);
	}
		
	public void setDeath(Fortress dead) {
		this.getTeam().remove(dead);
	}
}
