package com.exam.portal.controller;

import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exam.portal.domain.Answer;
import com.exam.portal.domain.ExamConstants;
import com.exam.portal.domain.ExamMaster;
import com.exam.portal.domain.Question;
import com.exam.portal.domain.Test;
import com.exam.portal.domain.UserInfo;
import com.exam.portal.domain.UserTestStatus;
import com.exam.portal.dto.ErrorObject;
import com.exam.portal.dto.QuestionImageDTO;
import com.exam.portal.dto.ResultDTO;
import com.exam.portal.dto.ReturnHolder;
import com.exam.portal.dto.SaveQuestionDTO;
import com.exam.portal.dto.TestDTO;
import com.exam.portal.dto.TestStatus;
import com.exam.portal.repo.AnswerRepository;
import com.exam.portal.repo.ExamMasterRepository;
import com.exam.portal.repo.QuestionRepository;
import com.exam.portal.repo.TestRepository;
import com.exam.portal.repo.UserInfoRepository;
import com.exam.portal.repo.UserTestStatusRepository;
import com.exam.portal.service.ExamService;
import com.exam.portal.service.LoginService;

@CrossOrigin
@RestController
@RequestMapping(value="/exam")
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
	UserInfoRepository userInfoRepository;
	
	@Autowired
	TestRepository testRepository;
	
	@Autowired
	ExamMasterRepository examMasterRepository;
	
	@Autowired
	UserTestStatusRepository userTestStatusRepository;
	
	public static final String SAMPLE_XLSX_FILE_PATH = "/home/thrymr/Downloads/Untitled spreadsheet.xlsx";
	
	@RequestMapping(value="/uploadExcel", method=RequestMethod.POST)
	public ReturnHolder exportExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws InvalidFormatException, IOException {
		ReturnHolder returnHolder = new ReturnHolder();
		System.out.println(" =========== " + file);
		 InputStream stream = file.getInputStream();
		Workbook workbook = WorkbookFactory.create(stream);

		// Retrieving the number of sheets in the Workbook
		System.out.println(file + " === Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");


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
					if (question.getName() != null && !question.getName().isEmpty()) {
						questionRepository.save(question);
						answerRepository.saveAll(answerList);
						question.setQuestionType(paperType);
						question.setAnswerList(answerList);
						question.setAnswer(answerRepository.findByAnswerTextAndQuestion(correctAnswer,question));
						questionRepository.save(question);
					}
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
			Test test = testRepository.findByGivenBy(appUser);
			ExamMaster examMaster = examMasterRepository.findLatest();
			TestDTO dto = new TestDTO();
			if (test != null && test.getTestStatus().equals(TestStatus.COMPLETED)) {
				dto.setStatus(TestStatus.COMPLETED.name());
			} else {
				dto = examService.getQuestions(appUser, test, examMaster);
				if (examMaster != null && examMaster.getTotalTimeInMillis() != null) {
					dto.setStartTime((examMaster.getTotalTimeInMillis() - dto.getTimeRemaining()));
				} else {
					dto.setStartTime((3600000l - dto.getTimeRemaining()));
				}
			}
			holder.setAuthToken(xAuth);
			holder.setResult(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return holder;
	}
	
	@RequestMapping(value="/submit-question", method=RequestMethod.POST)
	public ReturnHolder saveAnswer(@RequestBody SaveQuestionDTO questionDTO, HttpServletRequest request) {
		ReturnHolder holder = new ReturnHolder();
		String xAuth = request.getHeader("authToken");
		try {
			UserInfo appUser = loginService.getUser(xAuth);
		if (questionDTO != null) {
			Test test = testRepository.findByGivenBy(appUser);
			if (questionDTO.getQuestionId() != null && questionDTO.getAnswerId() != null) {
				Question question = questionRepository.findByQuestionId(questionDTO.getQuestionId());
				if (question != null) {
					UserTestStatus userTestStatus = userTestStatusRepository.findByQuestionAndInfo(question, appUser);
					if (userTestStatus != null) {
						Answer answer = answerRepository.findByAnswerId(questionDTO.getAnswerId());
						userTestStatus.setAnswer(answer);
						userTestStatus.setIsAnswered(Boolean.TRUE);
						if (question.getAnswer().equals(answer)) {
							userTestStatus.setIsCorrectAnswered(Boolean.TRUE);
						}
						appUser.setRemaingTime(questionDTO.getTime());
						test.setCurrentQuestionId(questionDTO.getQuestionId());
						test.setTimeRemaining(questionDTO.getTime());
						userInfoRepository.save(appUser);
						userTestStatusRepository.save(userTestStatus);
						holder.setResult("Submitted");
					} else {
						holder = new ReturnHolder(false, new ErrorObject("err02", "Invalid request."));
					}
				} else {
					//question not found.
					holder = new ReturnHolder(false, new ErrorObject("err02", "Invalid request."));
				}
			} else if (questionDTO.getTime() != null) {
				test.setTimeRemaining(questionDTO.getTime());
				holder.setResult("Time Updated");
			}
			testRepository.save(test);
		} else {
			holder = new ReturnHolder(false, new ErrorObject("err02", "Invalid request."));
		}
		} catch (Exception e) {
			System.out.println("=========="+e.getMessage());
			holder = new ReturnHolder(false, new ErrorObject("err01", "Error Submitting question"));
			// TODO: handle exception
		}
		
		return holder;
	}
	
	@RequestMapping(value="/submit-test", method=RequestMethod.POST)
	public ReturnHolder submitTest(HttpServletRequest request) {
		String xAuth = request.getHeader("authToken");
		try {
			UserInfo appUser = loginService.getUser(xAuth);
			if (appUser != null) {
				Test test = testRepository.findByGivenBy(appUser);
				if (test != null) {
					test.setTestStatus(TestStatus.COMPLETED);
					testRepository.save(test);
				} else {
					return new ReturnHolder(false, new ErrorObject("err01", "No test found"));
				}
			}
		} catch (Exception e) {
			return new ReturnHolder(false, new ErrorObject("err02", "Exception while submit test"));
		}
		return new ReturnHolder();
	}
	
	@RequestMapping(value="/get-results", method=RequestMethod.GET)
	public ReturnHolder getResult() {
		ReturnHolder holder = new ReturnHolder();
		try {
			List<ResultDTO> dtos = examService.getResults();
			if (dtos != null && dtos.size() > 0) {
				holder.setResult(dtos);
			} else {
				holder = new ReturnHolder(false, new ErrorObject("err01", "No Result Available."));
			}
		} catch (Exception e) {
			holder = new ReturnHolder(false, new ErrorObject("err02", e.getMessage()));
		}
		return holder;
	}
	
	@RequestMapping(value="/get-image-questions", method = RequestMethod.GET)
	public ReturnHolder getImageQuestions() {
		ReturnHolder holder = new ReturnHolder();
		try {
			List<Object[]> questionList = questionRepository.getImageQuestions();
			List<QuestionImageDTO> dtos = new ArrayList<QuestionImageDTO>();
			if (questionList != null && !questionList.isEmpty()) {
				questionList.forEach(object -> {
					if (object[1] != null && !((String) object[1]).isEmpty()) {
						QuestionImageDTO dto = new QuestionImageDTO();
						dto.setQuestionId(((Integer)object[0]).longValue());
						dto.setName((String)object[1]);
						dtos.add(dto);
					}
				});
				holder.setResult(dtos);
			} else {
				holder = new ReturnHolder(false, new ErrorObject("err01", "No Questions"));
			}
			
		} catch (Exception e) {
			System.out.println(" ========== " + e.getMessage());
			holder = new ReturnHolder(false, new ErrorObject("err02", "Server Error."));
		}
		
		return holder;
	}
	
	@RequestMapping(value="/get-options", method=RequestMethod.GET)
	public ReturnHolder getOptions(@RequestParam("questionId") Long questionId) {
		ReturnHolder holder = new ReturnHolder();
		try {
			if (questionId != null ) {
				List<Answer> answers = answerRepository.findAnswersByQuestion(questionId);
				if (answers != null && !answers.isEmpty()) {
					List<QuestionImageDTO> dtos = new ArrayList<QuestionImageDTO>();
					answers.forEach(answer -> {
						QuestionImageDTO dto = new QuestionImageDTO();
						dto.setName(answer.getAnswerText());
						dto.setQuestionId(answer.getAnswerId());
						dtos.add(dto);
					});
					holder.setResult(dtos);
				} else {
					holder = new ReturnHolder(false, new ErrorObject("err03", "Options not found."));
				}
			} else {
				holder = new ReturnHolder(false, new ErrorObject("err02", "Invalid Request.."));
			}
		} catch (Exception e) {
			System.out.println(" ========== " + e.getMessage());
			holder = new ReturnHolder(false, new ErrorObject("err01", "Server Error"));
		}
		return holder;
	}
}
