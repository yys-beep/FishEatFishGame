package application;

public class gameHis {
	
	private Integer num;
	private String date;
	private String time;
	private String score;
	private String level;
	private String fish;
	
	public gameHis(int num,String date,String time,String score,String level,String fish) {
		this.num = num;
		this.date = date;
		this.time = time;
		this.score = score;
		this.level = level;
		this.fish = fish;
	}
	
	public int getNum() {
		return num;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getTime() {
		return time;
	}
	
	public String getScore() {
		return score;
	}
	
	public String getLevel() {
		return level;
	}
	
	public String getFish() {
		return fish;
	}

}
