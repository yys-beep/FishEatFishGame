package application;

public class player {
	
	private String name;
	private int highScore;
	private int ranking;
	
	public player (int ranking,String name,int highScore) {
		this.ranking = ranking;
		this.name = name;
		this.highScore = highScore;
	}

	public int getRanking() {
		return ranking;
	}
	
	public String getName() {
		return name;
	}
	
	public int getHighScore() {
		return highScore;
	}
}
