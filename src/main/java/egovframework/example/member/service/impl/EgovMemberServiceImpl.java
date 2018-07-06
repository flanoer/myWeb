package egovframework.example.member.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import egovframework.example.member.service.EgovMemberService;
import egovframework.example.member.service.MemberVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("memberService")
public class EgovMemberServiceImpl extends EgovAbstractServiceImpl implements EgovMemberService{

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovMemberServiceImpl.class);
	
	@Resource(name = "memberDAO")
	private MemberDAO memberDAO;
	
	@Override
	public void insertMember(MemberVO vo) throws Exception {
		memberDAO.insertMember(vo);
	}

	@Override
	public int memberCheck(MemberVO vo) throws Exception {
		return memberDAO.memberCheck(vo);
	}
	
	
	
}
