package com.exam.portal.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exam.portal.domain.Answer;
import com.exam.portal.domain.ExamConstants;
import com.exam.portal.domain.Question;
import com.exam.portal.domain.UserInfo;
import com.exam.portal.domain.UserTestStatus;
import com.exam.portal.dto.ErrorObject;
import com.exam.portal.dto.ReturnHolder;
import com.exam.portal.dto.SaveQuestionDTO;
import com.exam.portal.dto.TestDTO;
import com.exam.portal.repo.AnswerRepository;
import com.exam.portal.repo.QuestionRepository;
import com.exam.portal.repo.UserTestStatusRepository;
import com.exam.portal.service.ExamService;
import com.exam.portal.service.LoginService;

@RestController
public class ExamController {
	
	@Autowired
	ExamService examService;
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	UserTestStatusRepository userTestStatusRepository;
	
	public static final String SAMPLE_XLSX_FILE_PATH = "/home/thrymr/Downloads/Untitled spreadsheet.xlsx";
	
	@RequestMapping(value="/uploadExcel", method=RequestMethod.POST)
	public ReturnHolder exportExcel(HttpServletRequest request) throws InvalidFormatException, IOException {
		ReturnHolder returnHolder = new ReturnHolder();
		Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));

		// Retrieving the number of sheets in the Workbook
		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");


		// Getting the Sheet at index zero
		Sheet sheet = workbook.getSheetAt(0);
		String sheetName = sheet.getSheetName();
		
		if (sheetName != null) {
			ExamConstants paperType = ExamConstants.valueOf(sheetName);
			if (paperType != null) {
				
				// Create a DataFormatter to format and get each cell's value as String
				DataFormatter dataFormatter = new DataFormatter();
				
				// 1. You can obtain a rowIterator and columnIterator and iterate over
				// them
				System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
				Iterator<Row> rowIterator = sheet.rowIterator();
				String correctAnswer ="";
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (row.getRowNum() > 0) {
					Question question = new Question();
					List<Answer> answerList = new ArrayList<Answer>();
					
						
					
					// Now let's iterate over the columns of the current row
					Iterator<Cell> cellIterator = row.cellIterator();
					
					while (cellIterator.hasNext()) {
						Answer answer = new Answer();
						Cell cell = cellIterator.next();
						String cellValue = "";
						if (cell.getColumnIndex() == 0 ) {
							cellValue = dataFormatter.formatCellValue(cell);
							if (cellValue != null && !cellValue.isEmpty()) {
								question.setName(cellValue);
							}
						}
						if (cell.getColumnIndex() == 1) {
							cellValue = dataFormatter.formatCellValue(cell);
							if (cellValue != null && !cellValue.isEmpty()) {
								correctAnswer = cellValue;
							}
						}
						if (cell.getColumnIndex() > 1) {
							cellValue = dataFormatter.formatCellValue(cell);
							answer.setAnswerText(cellValue);
							answer.setQuestion(question);
							answerList.add(answer);
						}
						System.out.print(cellValue + "\t");
					}
					questionRepository.save(question);
					answerRepository.saveAll(answerList);
					question.setQuestionType(paperType);
					question.setAnswerList(answerList);
					question.setAnswer(answerRepository.findByAnswerTextAndQuestion(correctAnswer,question));
					questionRepository.save(question);
					System.out.println();
				}
				}
			} else {
				returnHolder = new ReturnHolder(false, new ErrorObject("err02", "No Exam exists with SheetName.."));
			}
		} else {
			returnHolder = new ReturnHolder(false, new ErrorObject("err03", "enter a proper Sheet Name."));
		}

		return returnHolder;
		
	}
	@RequestMapping(value="/get-questions",method=RequestMethod.POST)
	public ReturnHolder getQuestions(HttpServletRequest request) {
		ReturnHolder holder = new ReturnHolder();
		String xAuth = request.getHeader("authToken");
		try {
			UserInfo appUser = loginService.getUser(xAuth);
			TestDTO dto = examService.getQuestions(appUser);
			holder.setAuthToken(xAuth);
			holder.setResult(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return holder;
	}
	
	@RequestMapping(value="/save-answer", method=RequestMethod.POST)
	public ReturnHolder saveAnswer(SaveQuestionDTO questionDTO, HttpServletRequest request) {
		ReturnHolder holder = new ReturnHolder();
		String xAuth = request.getHeader("authToken");
		try {
			UserInfo appUser = loginService.getUser(xAuth);
		if (questionDTO != null) {
			if (questionDTO.getQutionId() != null && questionDTO.getAnswerId() != null) {
				Question question = questionRepository.findByQuestionId(questionDTO.getQutionId());
				UserTestStatus userTestStatus = userTestStatusRepository.findByQuestionAndInfo(question, appUser);
				Answer answer = answerRepository.findByAnswerId(questionDTO.getAnswerId());
				userTestStatus.setAnswer(answer);
				userTestStatus.setIsAnswered(Boolean.TRUE);
				if (question.getAnswer().equals(answer)) {
					userTestStatus.setIsCorrectAnswered(Boolean.TRUE);
				}
				userTestStatusRepository.save(userTestStatus);
			}
		}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return holder;
	}
}
