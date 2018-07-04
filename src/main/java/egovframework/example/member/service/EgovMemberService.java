package egovframework.example.member.service;

import egovframework.example.sample.service.MemberVO;

public interface EgovMemberService {
	
	/**
	 * 회원가입한다.
	 * @param vo - 가입할 정보가 담긴 MemberVO
	 * @return 등록 결과
	 * @exception Exception
	 */
	void insertMember(MemberVO vo) throws Exception;
	
}
