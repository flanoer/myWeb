package egovframework.example.sample.service;

import java.util.List;

public interface ValidationService {
	
	public String Validator(SampleVO vo, String flag);

	public String msgTrans(String flagNmsg);

	public String wordSplit(String inputword);
	
}
