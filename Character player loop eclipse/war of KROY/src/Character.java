import java.lang.Math;

abstract public class Character {
	
	private String name;
	private int health;
	private int damage;
	private int range;
	private boolean disabled;
	private Tile location;
	
	public Character(int health, int damage, int range, Tile spawn, String name) {
		this.health = health;
		this.damage = damage;
		this.range = range;
		this.location = spawn;
		this.disabled = false;
		this.name = name;
	}
	
	protected boolean inRange(Tile target) {
		if (Math.sqrt(Math.pow((this.location.MapX - target.MapX), 2) + (Math.pow((this.location.MapY - target.MapY), 2))) <= this.range) {
			return true;
		} else {
			return false;
		}
	}
	
	protected void death() {
		if(this.health == 0) {
			this.disabled = true;
		}
	}
	
	protected void takeDamage(int damageTaken) { 
		this.health = this.health - damageTaken;
		if(this.health < 0) {
			this.health = 0;
		}
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void setHealth(int newHealth) {
		this.health = newHealth;
	}
	
	public int getRange() {
		return this.range;

	}
	public int getDamage() {
		return this.damage;
	}
	
	public void setDamage(int newDamage) {
		this.damage = newDamage;
	}
	
	public Tile getLocation() {
		return this.location;
	}
	
	public void setLocation(Tile newLocation) {
		this.location = newLocation;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getDisabled() {
		return this.disabled;
	}
	
	public void setDisabled(boolean state) {
		this.disabled = state;
	}
}
