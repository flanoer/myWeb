package egovframework.example.sample.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.sample.service.SampleVO;
import egovframework.example.sample.service.ValidationService;

@Service("validService")
public class ValidationServiceImpl implements ValidationService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

	String flag = "OK";
	
	@Override
	public String Validator(SampleVO vo) {
		
		if(!vo.getUsr_pwd().equals(vo.getUsr_pwd_check())){
			flag = "NG_PWCheck";
			return flag;
		}
		
		return flag;
	}

	@Override
	public String msgTrans(String flagNmsg) {
		
		if(flagNmsg.equals("NG_PWCheck")){
			flagNmsg = "비밀번호를 다시 입력하세요.";
			return flagNmsg;
		}
		
		return null;
	}

}
