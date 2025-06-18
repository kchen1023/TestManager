package onlineTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Exam implements Serializable {
	private static final long serialVersionUID = -4259078476492746639L;
	private int examId;
	private double studentPoints, maxScore, minScore;
	private String title;	
	private ArrayList<Object> allQuestions;
	//studentName, score for this exam
	private HashMap<String, Double> studentScores = new HashMap<>();
	//question number, points received
	private HashMap<Integer, Double> ptsFromQuestion;

	public Exam(int examId, String title) {
		this.examId = examId;
		this.title = title;
		allQuestions = new ArrayList<Object>();
	}
	
	public Exam() {
		this.examId = 0;
		this.title = "";
	}
	
	public int getExamId() {
		return examId;
	}
	
	public String getTitle() {
		return title;
	}

	public void addQuestion(Object question) {
		allQuestions.add(question);
	}
	
	public ArrayList<Object> getAllQuestions(){
		return allQuestions;
	}
	
	public String getKey() {
		String ans = "";
		for (int i = 0; i < allQuestions.size(); i++) {
			ans += allQuestions.get(i).toString();
		}
		return ans;
	}
	
	//when a new student is added, their score is 0.0
	public void addStudent(String studentName) {
		if (!studentScores.containsKey(studentName)) {
			studentPoints = 0.0;
			studentScores.put(studentName, studentPoints); 
			ptsFromQuestion = new HashMap<>();
		}
	}
	
	//gets student's score based on key value pair in map
	public double getStudentScore(String studentName) {
		return studentScores.get(studentName);
	}
	
	//get student's ptsFromQuestion
	public double getPtsFromQuestion(int questionNumber) {
		return ptsFromQuestion.get(questionNumber);
	}
	
	public double getMaxScore() {
		for (Double max : studentScores.values()) {
			if (max > maxScore) {
				maxScore = max;
			}
		}
		return maxScore;
	}
	
	public double getMinScore() {
		for (Double min : studentScores.values()) {
			if (min < minScore) {
				minScore = min;
			}
		}
		return minScore;
	}
	
	public double getAvgScore() {
		double avgScore = 0, num = 0;
		for (Double score : studentScores.values()) {
			avgScore += score;
			num++;
		}
		return avgScore / num;
	}
	
	//for tf questions
	public void response(String studentName, int questionNumber, boolean answer) {
		double pointsGotten = 0.0;
		for (int i = 0; i < allQuestions.size(); i++) {
			Object retrieved = allQuestions.get(i);
			//if question is tf, cast object as tf
			if (retrieved instanceof TrueFalse) {
				//if same question numbers
				if (((TrueFalse) retrieved).getQuestionNumber() == questionNumber) {
					//if student answer matches key
					if (((TrueFalse) retrieved).getAnswer() == answer) {
						studentPoints += ((TrueFalse) retrieved).getPoints();
						pointsGotten += ((TrueFalse) retrieved).getPoints();
					}
				}
			}
		}
		studentScores.put(studentName, studentPoints);
		ptsFromQuestion.put(questionNumber, pointsGotten);
	}
	
	//for mc and fb
	public void response(String studentName, int questionNumber, String[] answer) {
		double pointsGotten = 0.0;
		for (int i = 0; i < allQuestions.size(); i++) {
			Object retrieved = allQuestions.get(i);
			//if question is mc, cast object as mc
			if (retrieved instanceof MultChoice) {
				if (((MultChoice) retrieved).getQuestionNumber() == questionNumber) {
					int answerLength = ((MultChoice) retrieved).getAnswerLength(), numCorrect = 0;
					String[] retMC = ((MultChoice) retrieved).getAnswer();
					for (String q1 : retMC) {
						for (String q2 : answer) {
							if (q1.equals(q2)) {
								numCorrect++;
							}
						}
					}
					if (numCorrect == answerLength) {
						studentPoints += ((MultChoice) retrieved).getPoints();
						pointsGotten += ((MultChoice) retrieved).getPoints();
					}
				}
			}
			//if question is fb, cast object as fb
			if (retrieved instanceof FillBlanks) {
				if (((FillBlanks) retrieved).getQuestionNumber() == questionNumber) {
					//total points / entries in array = points per blank
					double ptsPerEntry = ((FillBlanks) retrieved).getPoints() / ((FillBlanks) retrieved).getAnswerLength();
					String[] retFB = ((FillBlanks) retrieved).getAnswer();
					for (String q1 : retFB) {
						for (String q2 : answer) {
							if (q1.equals(q2)) {
								studentPoints += ptsPerEntry;
								pointsGotten += ptsPerEntry;
							}
						}
					}
				}
			}
		}
		studentScores.put(studentName, studentPoints);
		ptsFromQuestion.put(questionNumber, pointsGotten);
	}
}
