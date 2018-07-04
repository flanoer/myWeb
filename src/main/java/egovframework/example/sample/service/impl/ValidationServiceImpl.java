package egovframework.example.sample.service.impl;

import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleVO;
import egovframework.example.sample.service.ValidationService;

@Service("validService")
public class ValidationServiceImpl implements ValidationService{
	
	@Resource(name="sampleService")
	private EgovSampleService sampleService;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);
	private static final String DELIMITERS = " ,;/'\"\n\r.~!?@#$%^&*()+|-=`[]";

	String msg = "OK";
	
	@Override
	public String Validator(SampleVO vo, String flag) {
		SampleVO testVO;
		if(flag.equalsIgnoreCase("modify")){
			try {
				testVO = sampleService.selectSample(vo);
				if(vo.getUseYn().equalsIgnoreCase("N") && vo.getUsr_pwd().isEmpty()){
					return msg = msgTrans("USRPWD");
				}
				if(!vo.getUsr_pwd().equals(testVO.getUsr_pwd())){
					return msg = msgTrans("PWCheck");
				}
				if(vo.getNoti_yn().equalsIgnoreCase("y") && (vo.getSdate().isEmpty() || vo.getEdate().isEmpty())){
					return msg = msgTrans("MUSTDATEPICK");
				}
			} catch (Exception e) {e.printStackTrace();}
		}
		else{
			return msg = createVali(vo);
		}
		return msg;
	}

	public String createVali(SampleVO vo){
		if(vo.getUseYn().equalsIgnoreCase("N") && vo.getUsr_pwd().isEmpty()){
			return msg = msgTrans("USRPWD");
		}
		if(vo.getNoti_yn().equalsIgnoreCase("y") && (vo.getSdate().isEmpty() || vo.getEdate().isEmpty())){
			return msg = msgTrans("MUSTDATEPICK");
		}
		return msg;
	}
	
	@Override
	public String msgTrans(String flagNmsg) {
		switch(flagNmsg){
			case "PWCheck": flagNmsg = "비밀번호를 올바르지 않습니다.";
				return flagNmsg;
			case "USRPWD": flagNmsg = "사용여부가 N일 때는 비밀번호를 꼭 입력하세요.";
				return flagNmsg;
			case "MUSTDATEPICK": flagNmsg = "공지사항을 입력할 때는 기간을 꼭 설정하세요.";
				return flagNmsg;	
			default: return flagNmsg;
		}
	}
	
	@Override
	public String wordSplit(String inputword){
		
		String word = new String();
		
		StringTokenizer st = new StringTokenizer(inputword, DELIMITERS, false);
		
		while(st.hasMoreElements()){
			word += st.nextToken();
		}
		
		return word;
	}

}
