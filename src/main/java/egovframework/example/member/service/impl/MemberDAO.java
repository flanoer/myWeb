package egovframework.example.member.service.impl;

import org.springframework.stereotype.Repository;

import egovframework.example.sample.service.MemberVO;
import egovframework.example.sample.service.SampleVO;
import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Repository("memberDAO")
public class MemberDAO extends EgovAbstractDAO{
	
	public void insertMember(MemberVO vo) throws Exception {
		insert("memberDAO.insertMember", vo);;
	}
	
}
