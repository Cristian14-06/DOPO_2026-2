package domain;

public class Foot implements Collidable{
	private Circle circle;
	private boolean isKicking;
	private int powerKicking;

	public Circle getCircle() {
		return circle;
	}
		
	public boolean isKicking() {
		return isKicking;
	}
	
	public int getPowerKicking(){
		if (isKicking) {
			return powerKicking * 5;
		} else {
			return powerKicking;
		}
	}

	public void setKicking(boolean isKicking) {
		this.isKicking = isKicking;
	}
}
