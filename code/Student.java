package onlineTest;

import java.io.Serializable;

public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private double studentTotalScore = 0.0;
	
	public Student(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addPoints(double pts) {
		studentTotalScore += pts;
	}
	
	public double getStudentTotalScore() {
		return studentTotalScore;
	}
}
