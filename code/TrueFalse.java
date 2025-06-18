package onlineTest;

import java.io.Serializable;

public class TrueFalse implements Serializable {
	private static final long serialVersionUID = -7336204137516645226L;
	private int examId, questionNumber;
	private String text;
	private double points;
	private boolean answer;
	
	public TrueFalse(int examId, int questionNumber, String text, double points, boolean answer) {
		this.examId = examId;
		this.questionNumber = questionNumber;
		this.text = text;
		this.points = points;
		this.answer = answer;
	}

	
	public String toString() {
		String ans = "";
		if (answer == true) {
			ans += "True";
		}else {
			ans += "False";
		}
		return "Question Text: " + text + "\nPoints: " + points + "\nCorrect Answer: " + ans + "\n";
	}

	public int getExamId() {
		return examId;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}

	public String getText() {
		return text;
	}


	public double getPoints() {
		return points;
	}
	
	public boolean getAnswer() {
		return answer;
	}
	
}
