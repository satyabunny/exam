package com.exam.portal.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.portal.domain.ExamConstants;
import com.exam.portal.domain.Question;
import com.exam.portal.domain.UserInfo;
import com.exam.portal.domain.UserTestStatus;
import com.exam.portal.dto.AnswerReponseDTO;
import com.exam.portal.dto.QuestionResponseDTO;
import com.exam.portal.dto.TestDTO;
import com.exam.portal.repo.QuestionRepository;
import com.exam.portal.repo.UserTestStatusRepository;
import com.exam.portal.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {
	
	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	UserTestStatusRepository userTestStatusRepository;
	
	@Override
	public void exportExcel() throws Exception {

	}

	@Override
	public TestDTO getQuestions(UserInfo user) {
		TestDTO dto = new TestDTO();
		
		List<Question> allQuestions = questionRepository.findByQuestionTypeIn(getExamConstants(user));
		
		List<Question> arthimaticQuestoins = allQuestions.stream()
				.filter(question -> question.getQuestionType().equals(ExamConstants.ARTHIMETICS))
				.collect(Collectors.toList());
		
		if (arthimaticQuestoins.size() > 0) {
			Collections.shuffle(arthimaticQuestoins);
			arthimaticQuestoins = arthimaticQuestoins.subList(0, arthimaticQuestoins.size() -1);
		}
		
		List<Question> reasoningQuestoins = allQuestions.stream()
				.filter(question -> question.getQuestionType().equals(ExamConstants.REASONING))
				.collect(Collectors.toList());
		if (reasoningQuestoins.size() > 0) {
			Collections.shuffle(reasoningQuestoins);
			reasoningQuestoins = reasoningQuestoins.subList(0, reasoningQuestoins.size() - 1);
		}
		
		List<Question> englishQuestoins = allQuestions.stream()
				.filter(question -> question.getQuestionType().equals(ExamConstants.ENGLISH))
				.collect(Collectors.toList());
		if (englishQuestoins.size() > 0) {
			Collections.shuffle(englishQuestoins);
			englishQuestoins = englishQuestoins.subList(0, englishQuestoins.size() -1);
		}
		
		List<Question> coreQuestoins = allQuestions.stream()
				.filter(question -> question.getQuestionType().equals(ExamConstants.valueOf(user.getCourse().name())))
				.collect(Collectors.toList());
		
		Collections.shuffle(coreQuestoins);
		if (coreQuestoins.size() > 0) {
			coreQuestoins = coreQuestoins.subList(0,coreQuestoins.size() - 1);
		}
		
		List<QuestionResponseDTO> arthimaticQuestionsDto = new ArrayList<QuestionResponseDTO>();
		arthimaticQuestoins.forEach(question -> {
			QuestionResponseDTO questionDto = new QuestionResponseDTO();
			List<AnswerReponseDTO> answerReponseDTOs = new ArrayList<>();
			questionDto.setQuestionId(question.getQuestionId());
			questionDto.setQustionText(question.getName());
			
			question.getAnswerList().forEach(answer -> {
				AnswerReponseDTO answerDto = new AnswerReponseDTO();
				answerDto.setAnswerId(answer.getAnswerId());
				answerDto.setAnswerText(answer.getAnswerText());
				answerReponseDTOs.add(answerDto);
			});
			questionDto.setAnswerList(answerReponseDTOs);
			arthimaticQuestionsDto.add(questionDto);
			saveUserTestStatus(user, question);
		});

		dto.setArthimaticQuestionsDto(arthimaticQuestionsDto);
		
		List<QuestionResponseDTO> reasoningQuestionsDto = new ArrayList<QuestionResponseDTO>();
		reasoningQuestoins.forEach(question -> {
			QuestionResponseDTO questionDto = new QuestionResponseDTO();
			List<AnswerReponseDTO> answerReponseDTOs = new ArrayList<>();
			questionDto.setQuestionId(question.getQuestionId());
			questionDto.setQustionText(question.getName());
			
			question.getAnswerList().forEach(answer -> {
				AnswerReponseDTO answerDto = new AnswerReponseDTO();
				answerDto.setAnswerId(answer.getAnswerId());
				answerDto.setAnswerText(answer.getAnswerText());
				answerReponseDTOs.add(answerDto);
			});
			questionDto.setAnswerList(answerReponseDTOs);
			reasoningQuestionsDto.add(questionDto);
			saveUserTestStatus(user, question);
		});
		
		dto.setReasoningQuestionsDto(reasoningQuestionsDto);
		
		List<QuestionResponseDTO> englishQuestionsDto = new ArrayList<QuestionResponseDTO>();
		englishQuestoins.forEach(question -> {
			QuestionResponseDTO questionDto = new QuestionResponseDTO();
			List<AnswerReponseDTO> answerReponseDTOs = new ArrayList<>();
			questionDto.setQuestionId(question.getQuestionId());
			questionDto.setQustionText(question.getName());
			
			question.getAnswerList().forEach(answer -> {
				AnswerReponseDTO answerDto = new AnswerReponseDTO();
				answerDto.setAnswerId(answer.getAnswerId());
				answerDto.setAnswerText(answer.getAnswerText());
				answerReponseDTOs.add(answerDto);
			});
			questionDto.setAnswerList(answerReponseDTOs);
			englishQuestionsDto.add(questionDto);
			saveUserTestStatus(user, question);
		});
		
		dto.setEnglishQuestionsDto(englishQuestionsDto);
		
		List<QuestionResponseDTO> coreQuestionsDto = new ArrayList<QuestionResponseDTO>();
		coreQuestoins.forEach(question -> {
			QuestionResponseDTO questionDto = new QuestionResponseDTO();
			List<AnswerReponseDTO> answerReponseDTOs = new ArrayList<>();
			questionDto.setQuestionId(question.getQuestionId());
			questionDto.setQustionText(question.getName());
			
			question.getAnswerList().forEach(answer -> {
				AnswerReponseDTO answerDto = new AnswerReponseDTO();
				answerDto.setAnswerId(answer.getAnswerId());
				answerDto.setAnswerText(answer.getAnswerText());
				answerReponseDTOs.add(answerDto);
			});
			questionDto.setAnswerList(answerReponseDTOs);
			coreQuestionsDto.add(questionDto);
			saveUserTestStatus(user, question);
		});
		dto.setCoreQuestionsDto(coreQuestionsDto);
		System.out.println(" ====== " + allQuestions.size());
		return dto;
	}
	
	public List<ExamConstants> getExamConstants(UserInfo user) {
		List<ExamConstants> examConstants = new ArrayList<ExamConstants>();
		examConstants.add(ExamConstants.ARTHIMETICS);
		examConstants.add(ExamConstants.REASONING);
		examConstants.add(ExamConstants.ENGLISH);
		examConstants.add(ExamConstants.valueOf(user.getCourse().name()));
		return examConstants;
	}
	
	public void saveUserTestStatus(UserInfo userInfo, Question question) {
		
		UserTestStatus status = new UserTestStatus();
		if (userInfo != null && question != null) {
			status.setInfo(userInfo);
			status.setQuestion(question);
			userTestStatusRepository.save(status);
		}
	}

}
