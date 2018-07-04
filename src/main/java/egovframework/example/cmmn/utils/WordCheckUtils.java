package egovframework.example.cmmn.utils;

import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.springframework.stereotype.Component;

@Component("wordCheckUtils")
public class WordCheckUtils {

	public static final String DELIMITERS = " ,;/'\"\n\r.~!@#$%^&*()+|-=`[]";
	public static final int FILTER_WORD_MIN_LENGTH = 2;
	public static final int FILTER_WORD_MAX_LENGTH = 10;
	
	public String wordSplit(String inputword){
		
		String word = null;
		
		StringTokenizer st = new StringTokenizer(inputword, DELIMITERS, false);
		
		while(st.hasMoreElements()){
			word += st.nextToken().trim();
		}
		
		return word;
	}
	
}
