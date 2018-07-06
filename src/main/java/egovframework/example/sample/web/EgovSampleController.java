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
package egovframework.example.sample.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import egovframework.example.sample.service.CommentVO;
import egovframework.example.sample.service.EgovCommentService;
import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;
import egovframework.example.sample.service.ValidationService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springmodules.validation.commons.DefaultBeanValidator;

/**
 * @Class Name : EgovSampleController.java
 * @Description : EgovSample Controller Class
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

@Controller
public class EgovSampleController {

	/** EgovSampleService */
	@Resource(name = "sampleService")
	private EgovSampleService sampleService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** Validator */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;

	@Resource(name="validService")
	protected ValidationService validService;
	
	@Resource(name="commentService")
	private EgovCommentService commentService;
	
	/**
	 * 글 목록을 조회한다. (pageing)
	 * @param searchVO - 조회할 정보가 담긴 SampleDefaultVO
	 * @param model
	 * @return "egovSampleList"
	 * @exception Exception
	 */
	@RequestMapping(value="/egovSampleList.do")
	public String selectSampleList(@ModelAttribute("searchVO") SampleDefaultVO searchVO, ModelMap model) throws Exception {

		int noticetotcnt = sampleService.selectSampleNoticeListTotCnt(searchVO);
		
		/** EgovPropertyService.sample */
		searchVO.setPageUnit(propertiesService.getInt("pageUnit")-noticetotcnt);
		searchVO.setPageSize(propertiesService.getInt("pageSize"));

		/** pageing setting */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getPageUnit());
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> sampleList = sampleService.selectSampleList(searchVO);
		model.addAttribute("resultList", sampleList);

		int totCnt = sampleService.selectSampleListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);

		return "sample/egovSampleList";
	}

	/**
	 * 글 등록 화면을 조회한다.
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping(value="/addSampleView.do",method=RequestMethod.POST)
	public String addSampleView(@ModelAttribute("searchVO") SampleDefaultVO searchVO, 
								SampleVO sampleVO, Model model,
								@RequestParam("regUser") String regUser) throws Exception {
		sampleVO = new SampleVO();
		sampleVO.setRegUser(regUser);
		model.addAttribute("sampleVO", sampleVO);
		return "sample/egovSampleRegister";
	}

	/**
	 * 글을 등록한다.
	 * @param sampleVO - 등록할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	
	@RequestMapping(value="/addSample.do",method=RequestMethod.POST)
	public String addSample(@ModelAttribute("searchVO") SampleDefaultVO searchVO, 
							SampleVO sampleVO, HttpServletRequest req, 
							Model model, SessionStatus status, @RequestParam String flag)
	throws Exception {
		String flagNmsg = validService.Validator(sampleVO, flag);
		if(!flagNmsg.equalsIgnoreCase("ok")){
			model.addAttribute("sampleVO", sampleVO);
			model.addAttribute("errMsg", flagNmsg);
			return "sample/egovSampleRegister";
		}

		String result = sampleService.insertSample(sampleVO, req);
		if(Integer.parseInt(result) >= 1){
			sampleVO.setName("");
			sampleVO.setDescription("");
			sampleVO.setRegUser("");
			model.addAttribute("sampleVO", sampleVO);
			model.addAttribute("errMsg", "게시물에 욕설을 사용할 수 없습니다.");
			return "sample/egovSampleRegister";
		}
		
		status.setComplete();
		return "redirect:/egovSampleList.do";
	}

	/**
	 * 글 수정화면을 조회한다.
	 * @param id - 수정할 글 id
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param model
	 * @return "egovSampleRegister"
	 * @exception Exception
	 */
	@RequestMapping(value="/updateSampleView.do")
	public String updateSampleView(@RequestParam("selectedId") String id, 
								   @ModelAttribute("searchVO") SampleDefaultVO searchVO, 
								   Model model) throws Exception {
		SampleVO sampleVO = new SampleVO();
		sampleVO.setId(id);
		sampleVO = selectSample(sampleVO, searchVO);
		if(sampleVO.getUsr_pwd() != null){
			sampleVO.setUsr_pwd("");
			model.addAttribute(sampleVO);
			model.addAttribute(searchVO);
			return "sample/egovSamplePrivate";
		}
		
		model = fileInfo(model, sampleVO.getId());
		
		model.addAttribute(sampleVO);
		
		return "sample/egovSampleRegister";
	}

	@RequestMapping("updatePrivateSample.do")
	public String updatePrivateSample(@ModelAttribute("sampleVO") SampleVO sampleVO, 
									  @ModelAttribute("searchVO") SampleDefaultVO searchVO, 
									  Model model) throws Exception {
		
		int flag = selectSamplePwdCheck(sampleVO, searchVO);
		
//		model attribute 어노테이션을 통해 담겨져온 sampleVO의 id, pwd 와 id로 조회한 결과값 일치 여부 확인
		if(flag == 1){
			model = fileInfo(model, sampleVO.getId());
			model.addAttribute(selectSample(sampleVO, searchVO));
			return "sample/egovSampleRegister";
		}
		else{
			sampleVO.setUsr_pwd("");
			model.addAttribute(sampleVO);
			return "sample/egovSamplePrivate";
		}
	}
	
	/**
	 * 글을 조회한다.
	 * @param sampleVO - 조회할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return @ModelAttribute("sampleVO") - 조회한 정보
	 * @exception Exception
	 */
	public SampleVO selectSample(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO) throws Exception {
		return sampleService.selectSample(sampleVO);
	}
	
	public int selectSamplePwdCheck(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO) throws Exception {
		return sampleService.selectSamplePwdCheck(sampleVO);
	}
	
	public Model fileInfo(Model model, String id) throws Exception {
		Map<String,Object> fileInfo = sampleService.selectOneFile(id);
		if(fileInfo != null){
			String ori_fname = String.valueOf(fileInfo.get("ORI_FNAME"));
			String sto_fname = String.valueOf(fileInfo.get("STO_FNAME"));
			String fsize = String.valueOf(fileInfo.get("FSIZE"));
			model.addAttribute("ori_fname",ori_fname);
			model.addAttribute("sto_fname",sto_fname);
			model.addAttribute("fsize",fsize);
		}
		return model;
	}

	/**
	 * 글을 수정한다.
	 * @param sampleVO - 수정할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping("/updateSample.do")
	public String updateSample(@ModelAttribute("searchVO") SampleDefaultVO searchVO, SampleVO sampleVO, 
							   BindingResult bindingResult, Model model, SessionStatus status, @RequestParam String flag) throws Exception {
		
		String flagNmsg = validService.Validator(sampleVO, flag);
		
		if(!flagNmsg.equalsIgnoreCase("OK")){
			model.addAttribute("sampleVO", sampleVO);
			model.addAttribute("errMsg", flagNmsg);
			return "sample/egovSampleRegister";
		}
		
		sampleService.updateSample(sampleVO);
		status.setComplete();
		return "forward:/egovSampleList.do";
	}

	/**
	 * 글을 삭제한다.
	 * @param sampleVO - 삭제할 정보가 담긴 VO
	 * @param searchVO - 목록 조회조건 정보가 담긴 VO
	 * @param status
	 * @return "forward:/egovSampleList.do"
	 * @exception Exception
	 */
	@RequestMapping("/deleteSample.do")
	public String deleteSample(SampleVO sampleVO, @ModelAttribute("searchVO") SampleDefaultVO searchVO, 
							   Model model,	SessionStatus status) throws Exception {
		
		sampleService.deleteSample(sampleVO);
		status.setComplete();
		return "forward:/egovSampleList.do";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/commentList.do",produces="application/json; charset=utf8")
	@ResponseBody
	public ResponseEntity commentList(@ModelAttribute("CommentVO") CommentVO cvo, HttpServletRequest request)
	throws Exception{
		
		HttpHeaders responseHeaders = new HttpHeaders();
        ArrayList<HashMap> hmlist = new ArrayList<HashMap>();
        
     // 해당 게시물 댓글
        List<CommentVO> commentVO = commentService.selectCommentByOriRecID(cvo.getOrirec_id());
        
        if(commentVO.size() > 0){
            for(int i=0; i<commentVO.size(); i++){
                HashMap hm = new HashMap();
                hm.put("cmt_content", commentVO.get(i).getCmt_content());
                hm.put("regi_id", commentVO.get(i).getRegi_id());
                hm.put("regidate", commentVO.get(i).getRegidate());
                
                hmlist.add(hm);
            }
            
        }
        
        JSONArray json = new JSONArray(hmlist);
        System.out.println(json.toString());
        return new ResponseEntity(json.toString(), responseHeaders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/insertComment.do")
    @ResponseBody
    public String insertComment(@ModelAttribute("CommentVO") CommentVO cvo, HttpSession session) throws Exception{
        
    	cvo.setRegi_id((String)session.getAttribute("regUser"));
    	if(session.getAttribute("regUser") == null){
    		return "error";
    	}
        commentService.insertComment(cvo);
        
        return "success";
    }
}
