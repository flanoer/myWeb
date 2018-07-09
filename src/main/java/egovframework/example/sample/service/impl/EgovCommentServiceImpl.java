/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.example.sample.service.impl;

import java.util.List;
import java.util.Map;

import egovframework.example.sample.service.CommentVO;
import egovframework.example.sample.service.EgovCommentService;
import egovframework.example.sample.service.SampleVO;
import egovframework.example.sample.service.ValidationService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Class Name : EgovSampleServiceImpl.java
 * @Description : Sample Business Implement Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */

@Service("commentService")
public class EgovCommentServiceImpl extends EgovAbstractServiceImpl implements EgovCommentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovCommentServiceImpl.class);

	/** SampleDAO */
	// TODO ibatis 사용
	@Resource(name = "commentDAO")
	private CommentDAO commentDAO;
	
    @Resource(name="validService")
    private ValidationService validService;

	@Override
	public void insertComment(CommentVO cvo) throws Exception {
		commentDAO.insertComment(cvo);
	}

	@Override
	public void updateSample(SampleVO vo) throws Exception {
		commentDAO.updateRecordCmtCount(vo);
	}

	@Override
	public List<CommentVO> selectCommentByOriRecID(String orirec_id) throws Exception {
		return commentDAO.selectCommentByOriRecID(orirec_id);
	}

	@Override
	public void updateComment(CommentVO cvo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteComment(CommentVO cvo) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int selectSamplePwdCheck(CommentVO cvo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
