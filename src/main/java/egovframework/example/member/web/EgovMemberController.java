package egovframework.example.member.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import egovframework.example.member.service.EgovMemberService;
import egovframework.example.member.service.MemberVO;
import egovframework.example.sample.service.SampleVO;
import egovframework.example.sample.service.ValidationService;

@Controller
public class EgovMemberController {
	
	@Resource(name="memberService")
	EgovMemberService memberService;
	
	@Resource(name="validService")
	protected ValidationService validService;
	
	@RequestMapping(value="/egovMember.do")
	public String member(Model model, MemberVO vo){
		
		return "member/egovMemberLogin";
	}
	
	@RequestMapping(value="/egovMemberLogin.do")
	public String memberLogin(Model model, MemberVO vo, HttpSession session) throws Exception{
		
		int flag = memberService.memberCheck(vo);
		
		if(flag == 0){
			model.addAttribute("memberVO", vo);
			model.addAttribute("errMsg", "아이디 혹은 비밀번호가 일치하지 않습니다.");
			
			return "member/egovMemberLogin";
		}
		session.setAttribute("regUser", vo.getMember_id());
		return "forward:/egovSampleList.do";
	}
	
	@RequestMapping(value="/egovMemberLogout.do")
	public String memberLogout(HttpSession session) throws Exception{
		session.invalidate();
		return "redirect:/egovSampleList.do";
	}
	
	@RequestMapping(value="/egovMemberJoining.do")
	public String memberJoining(Model model, MemberVO mvo) throws Exception{
		
		model.addAttribute("memberVO",mvo);
		return "member/egovMemberJoin";
	}
	
	@RequestMapping(value="/egovMemberJoined.do",method=RequestMethod.POST)
	public String memberJoined(Model model, MemberVO mvo, HttpSession session) throws Exception{
		
		memberService.insertMember(mvo);
		
		session.setAttribute("regUser", mvo.getMember_id());
		return "redirect:/egovSampleList.do";
	}
}
