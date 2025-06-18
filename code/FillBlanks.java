package onlineTest;

import java.io.Serializable;
import java.util.Arrays;

public class FillBlanks implements Serializable {
	private static final long serialVersionUID = -3232177112935019594L;
	private int examId, questionNumber;
	private String text;
	private double points;
	private String[] answer;
	
	public FillBlanks(int examId, int questionNumber, String text, double points, String[] answer) {
		this.examId = examId;
		this.questionNumber = questionNumber;
		this.text = text;
		this.points = points;
		this.answer = answer;
	}
	
	public String toString() {
		Arrays.sort(answer);
		String ans = "[" + answer[0];
		for (int i = 1; i < answer.length; i++) {
			ans += ", " + answer[i];
		}
		ans += "]";
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
	
	public String[] getAnswer() {
		return answer;
	}
	
	public double getAnswerLength() {
		return answer.length;
	}
}
