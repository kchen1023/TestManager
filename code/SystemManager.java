package onlineTest;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import java.io.*;

public class SystemManager implements Manager {
	private ArrayList<Exam> allExams = new ArrayList<>();
	private ArrayList<Student> allStudents = new ArrayList<>();
	private String[] letterGrades;
	private double[] cutoffs;
	
	@Override
	public boolean addExam(int examId, String title) {
		Exam exam = new Exam(examId, title);
		//if exam exists, don't add
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				return false;
			}
		}
		//if doesn't exist, add
		allExams.add(exam);
		return true;
	}

	@Override
	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		Exam found = null;
		TrueFalse tf = new TrueFalse(examId, questionNumber, text, points, answer);
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		found.addQuestion(tf);
	}

	@Override
	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		Exam found = null;
		MultChoice mc = new MultChoice(examId, questionNumber, text, points, answer);
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		found.addQuestion(mc);
	}

	@Override
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		Exam found = null;
		FillBlanks fb = new FillBlanks(examId, questionNumber, text, points, answer);
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		found.addQuestion(fb);
	}

	@Override
	public String getKey(int examId) {
		String ans = "";
		Exam found = null;
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		return ans += found.getKey();
	}

	@Override
	public boolean addStudent(String name) {
		Student student = new Student(name);
		allStudents.add(student);
		return true;
	}

	@Override
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		Exam found = null;
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		found.addStudent(studentName);
		found.response(studentName, questionNumber, answer);
	}

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		Exam found = null;
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		found.addStudent(studentName);
		found.response(studentName, questionNumber, answer);
	}

	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		Exam found = null;
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		found.addStudent(studentName);
		found.response(studentName, questionNumber, answer);
	}

	@Override
	public double getExamScore(String studentName, int examId) {
		Exam found = null;
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		return found.getStudentScore(studentName);
	}

	@Override
	public String getGradingReport(String studentName, int examId) {
		String ans = "";
		Exam found = null;
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		
		ArrayList<Object> allQuestions = found.getAllQuestions();
		double total = 0.0;
		int number = 0;
		for (Object stuff : allQuestions) {
			if (stuff instanceof TrueFalse) {
				stuff = (TrueFalse) stuff;
				ans += "Question #" + ++number + " " + found.getPtsFromQuestion(number) + " points out of " + ((TrueFalse) stuff).getPoints() + "\n";
				total += ((TrueFalse) stuff).getPoints();
			}
			if (stuff instanceof MultChoice) {
				stuff = (MultChoice) stuff;
				ans += "Question #" + ++number + " " + found.getPtsFromQuestion(number) + " points out of " + ((MultChoice) stuff).getPoints() + "\n";
				total += ((MultChoice) stuff).getPoints();
			}
			if (stuff instanceof FillBlanks) {
				stuff = (FillBlanks) stuff;
				ans += "Question #" + ++number + " " + found.getPtsFromQuestion(number) + " points out of " + ((FillBlanks) stuff).getPoints() + "\n";
				total += ((FillBlanks) stuff).getPoints();
			}
		}
		ans += "Final Score: " + found.getStudentScore(studentName) + " out of " + total;
		return ans;
	}

	@Override
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		this.letterGrades = letterGrades;
		this.cutoffs = cutoffs;

	}

	@Override
	public double getCourseNumericGrade(String studentName) {
		double courseNumericGrade = 0.0;
		int numExams = 0;
		for (Exam stuff : allExams) {
			courseNumericGrade += stuff.getStudentScore(studentName);
			numExams++;
		}
		return courseNumericGrade / numExams;
	}

	@Override
	public String getCourseLetterGrade(String studentName) {
		String grade = null;
		for (int i = cutoffs.length - 1; i > 1 ; i--) {
			if (getCourseNumericGrade(studentName) >= cutoffs[i] && getCourseNumericGrade(studentName) < cutoffs[i - 1]) {
				grade = letterGrades[i];
			}else if (getCourseNumericGrade(studentName) >= cutoffs[0]) {
				grade = letterGrades[0];
			}
		}
		return grade;
	}

	@Override
	public String getCourseGrades() {
		String ans = "";
		//arraylist of names only sorted
		ArrayList<String> studentNames = new ArrayList<>();
		for (Student person : allStudents) {
			studentNames.add(person.getName());
		}
		Collections.sort(studentNames);
		for (String name : studentNames) {
			ans += name + " " +  getCourseNumericGrade(name) + " " + getCourseLetterGrade(name) + "\n";
		}
		return ans;
	}

	@Override
	public double getMaxScore(int examId) {
		Exam found = null;
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		return found.getMaxScore();
	}

	@Override
	public double getMinScore(int examId) {
		Exam found = null;
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		return found.getMinScore();
	}

	@Override
	public double getAverageScore(int examId) {
		Exam found = null;
		for (Exam stuff : allExams) {
			if (examId == stuff.getExamId()) {
				found = stuff;
			}
		}
		return found.getAvgScore();
	}

	@Override
	public void saveManager(Manager manager, String fileName) {
		File file = new File(fileName);
		try{
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(manager);
			output.close();
		} catch (Exception e) {
			System.out.println("err saving");
		}
	}

	@Override
	public Manager restoreManager(String fileName) {
		File file = new File(fileName);
		SystemManager manager = new SystemManager();
		try {
			if (!file.exists()) {
				return new SystemManager();
			}else {
				//manager = (SystemManager) input.readObject;
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
				manager = (SystemManager) input.readObject();
				input.close();
				//return manager;
			}
		} catch (Exception e) {
			System.out.println("err restoring");
		}
		//return null;
		return manager;
	}
}
