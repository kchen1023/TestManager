package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import onlineTest.*;

public class TestCases {

//	@Test
//	public void addExam() {
//	Exam exam1 = new Exam(118035390, "Kristin");
//	Exam exam2 = new Exam(101010101, "Test");
//	System.out.println(exam1 + "\n" + exam2);
//	}
//	
//	@Test
//	public void addTF() {
//		//Exam exam1 = new Exam(118035390, "Kristin");
//		SystemManager manager = new SystemManager();
//		manager.addExam(118035390, "Kristin");
//		manager.addTrueFalseQuestion(118035390, 1, "question text", 2.0, true);
//		manager.getKey(118035390);
//	}
	
	@Test
	public void testStudent() {
		Student student = new Student("Kristin");
		assertEquals("Kristin", student.getName());
		
		student.addPoints(50.5);
		student.addPoints(30);
		assertEquals(80.5, student.getStudentTotalScore(), 0);
	}
	
	@Test 
	public void testTFQuestions() {
		TrueFalse tf = new TrueFalse(1, 2, "Java is spelled with 4 letters.", 3.0, true);
		assertEquals(1, tf.getExamId());
		assertEquals(2, tf.getQuestionNumber());
		assertEquals("Java is spelled with 4 letters.", tf.getText());
		assertEquals(3.0, tf.getPoints(), 0);
		assertTrue(tf.getAnswer());
	}
	
	@Test
	public void testMCQs() {
		String[] answers = {"A", "D"};
		MultChoice mc = new MultChoice(3, 4, "Choose A and D", 6.0, answers);
		
		assertEquals(3, mc.getExamId());
		assertEquals(4, mc.getQuestionNumber());
		assertEquals("Choose A and D", mc.getText());
		assertEquals(6.0, mc.getPoints(), 0);
		assertArrayEquals(answers, mc.getAnswer());
		assertEquals(2, mc.getAnswerLength());
	}
	
	@Test
	public void testFillInBlanks() {
		String[] answers = {"a", "b", "c"};
		FillBlanks fb = new FillBlanks(8, 7, "First three letters of alphabet?", 6.0, answers);
		
		assertEquals(8, fb.getExamId());
		assertEquals(7, fb.getQuestionNumber());
		assertEquals("First three letters of alphabet?", fb.getText());
		assertEquals(6.0, fb.getPoints(), 0);
		assertArrayEquals(answers, fb.getAnswer());
		assertEquals(3, fb.getAnswerLength(), 0);
	}
	
	@Test 
	public void testExamScoring() {
		Exam exam = new Exam(56, "TEST");
		TrueFalse tf = new TrueFalse(56, 1, "Q1", 1.0, true);
		exam.addQuestion(tf);
		
		exam.addStudent("Mimi");
		exam.response("Mimi", 1, true);
		assertEquals(1.0, exam.getStudentScore("Mimi"), 0);
		
		exam.addStudent("Momo");
		exam.response("Momo", 1, false);
		assertEquals(0.0, exam.getStudentScore("Momo"), 0);
	}
	
	@Test
	public void testSystemManager() {
		SystemManager manager = new SystemManager();
		
		assertTrue(manager.addExam(100, "eksam"));
		manager.addTrueFalseQuestion(100, 1, "TF", 2.0, true);
		manager.addStudent("Mama");
		manager.answerTrueFalseQuestion("Mama", 100, 1, true);
		assertEquals(2.0, manager.getExamScore("Mama", 100), 0);
 	}
}
