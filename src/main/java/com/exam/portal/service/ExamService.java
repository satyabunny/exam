package com.exam.portal.service;

import java.util.List;

import com.exam.portal.domain.ExamMaster;
import com.exam.portal.domain.Test;
import com.exam.portal.domain.UserInfo;
import com.exam.portal.dto.ResultDTO;
import com.exam.portal.dto.TestDTO;

public interface ExamService {
	
	public void exportExcel() throws Exception;
	
	public TestDTO getQuestions(UserInfo user, Test test, ExamMaster master);
	
	public List<ResultDTO> getResults();

}
