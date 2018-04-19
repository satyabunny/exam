package com.exam.portal.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.portal.domain.ExamConstants;
import com.exam.portal.domain.Question;
import com.exam.portal.domain.UserInfo;
import com.exam.portal.domain.UserTestStatus;
import com.exam.portal.dto.AnswerReponseDTO;
import com.exam.portal.dto.QuestionResponseDTO;
import com.exam.portal.dto.ResultDTO;
import com.exam.portal.dto.TestDTO;
import com.exam.portal.repo.QuestionRepository;
import com.exam.portal.repo.UserInfoRepository;
import com.exam.portal.repo.UserTestStatusRepository;
import com.exam.portal.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {
	
	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	UserTestStatusRepository userTestStatusRepository;
	
	@Autowired
	UserInfoRepository userInfoRepository;
	
	@Override
	public void exportExcel() throws Exception {

	}

	@Override
	public TestDTO getQuestions(UserInfo user) {
		TestDTO dto = new TestDTO();
		
		List<Question> allQuestions = questionRepository.findByQuestionTypeIn(getExamConstants(user));
		
		List<Question> arthimaticQuestoins = getFilteredQuestions(allQuestions, ExamConstants.ARTHIMETICS);
		
		arthimaticQuestoins = shuffleQuestions(arthimaticQuestoins, null);
		
		List<Question> reasoningQuestoins = getFilteredQuestions(allQuestions, ExamConstants.REASONING);
		reasoningQuestoins = shuffleQuestions(reasoningQuestoins, null);;
		
		List<Question> englishQuestoins = getFilteredQuestions(allQuestions, ExamConstants.ENGLISH);
		englishQuestoins = shuffleQuestions(englishQuestoins, null);
		
		List<Question> coreQuestoins = getFilteredQuestions(allQuestions, 
				ExamConstants.valueOf(user.getCourse().name()));
		
		coreQuestoins = shuffleQuestions(coreQuestoins, null);
		
		List<QuestionResponseDTO> arthimaticQuestionsDto = new ArrayList<QuestionResponseDTO>();

		dto.setArthimaticQuestionsDto(getQuestionsDto(arthimaticQuestoins, arthimaticQuestionsDto, user));
		
		List<QuestionResponseDTO> reasoningQuestionsDto = new ArrayList<QuestionResponseDTO>();
		
		dto.setReasoningQuestionsDto(getQuestionsDto(reasoningQuestoins, reasoningQuestionsDto, user));
		
		List<QuestionResponseDTO> englishQuestionsDto = new ArrayList<QuestionResponseDTO>();
		
		dto.setEnglishQuestionsDto(getQuestionsDto(englishQuestoins, englishQuestionsDto, user));
		
		List<QuestionResponseDTO> coreQuestionsDto = new ArrayList<QuestionResponseDTO>();

		dto.setCoreQuestionsDto(getQuestionsDto(coreQuestoins, coreQuestionsDto, user));
		System.out.println(" ====== " + allQuestions.size());
		return dto;
	}
	
	public List<Question> getFilteredQuestions(List<Question> questions, ExamConstants examConstant) {
		return questions.stream()
				.filter(question -> question.getQuestionType().equals(examConstant))
				.collect(Collectors.toList());
	}
	
	public List<Question> shuffleQuestions(List<Question> questions, Integer size) {
		if (questions.size() > 0) {
			Collections.shuffle(questions);
			questions = questions.subList(0, (size != null ? size-1 : questions.size() -1));
		}
		return questions;
	}
	
	public List<QuestionResponseDTO> getQuestionsDto(List<Question> questions,
			List<QuestionResponseDTO> questionResponseDTOs, UserInfo user) {
		questions.forEach(question -> {
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
			questionResponseDTOs.add(questionDto);
			saveUserTestStatus(user, question);
		});
		return questionResponseDTOs;
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

	@Override
	public List<ResultDTO> getResults() {
		List<ResultDTO> dtos = new ArrayList<ResultDTO>();
		Map<Long, Integer> resultMap = new HashMap<Long, Integer>();
		List<Object[]> results = questionRepository.getResults();
		if (results != null && results.size() > 0) {
			results.forEach(result -> {
				resultMap.put((Long)result[1], (Integer)result[0]);
			});
		} else {
			return dtos;
		}
		List<UserInfo> userInfos =userInfoRepository.findByUserInfoIDIn(resultMap.keySet());
		
		for (UserInfo user : userInfos) {
			ResultDTO dto = new ResultDTO();
			dto.setCollegeCode(user.getCollegeCode());
			dto.setEmail(user.getEmail());
			dto.setCourse(user.getCourse().name());
			dto.setUserName(user.getName());
			dto.setMarks(resultMap.get(user.getUserInfoID()).doubleValue());
			dtos.add(dto);
		}
		return dtos;
	}

}
