<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring"    uri="http://www.springframework.org/tags"%>
<%
  /**
  * @Class Name : egovMemberLogin.jsp
  * @Description : Member Login 화면
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2018.07.04            최초 생성
  *
  * author 남기웅
  * since 2018.07.04
  *
  * Copyright (C) 2018 by 남기웅  All right reserved.
  */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Sample Login
    </title>
    <link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/sample.css'/>"/>
    
    <!--For Commons Validator Client Side-->
    <script type="text/javascript" src="<c:url value='/cmmn/validator.do'/>"></script>
    <validator:javascript formName="memberVO" staticJavascript="false" xhtml="true" cdata="false"/>
    
    <script type="text/javaScript" language="javascript" defer="defer">
        <!--
        
        /* 글 보기 function */
        function fn_egov_join() {
        	frm = document.joinForm;
        	if(!validateMemberVO(frm)){
                return;
            }else{
            	frm.action = "<c:url value='/egovMemberJoined.do'/>";
            	frm.submit();
            }
        }
        -->
        
        if(${not empty errMsg}){
        	alert("${errMsg}");
        }
    </script>
</head>
<body style="text-align:center; margin:0 auto; display:inline; padding-top:100px;">

<form:form commandName="memberVO" id="joinForm" name="joinForm" method="post">
    <div id="content_pop">
    	<!-- 타이틀 -->
    	<div id="title">
    		<ul>
    			<li><img src="<c:url value='/images/egovframework/example/title_dot.gif'/>" alt=""/>
                    <spring:message code="button.login" />
                </li>
    		</ul>
    	</div>
    	<!-- // 타이틀 -->
    	<div id="table">
    	<table width="100%" border="1" cellpadding="0" cellspacing="0" style="bordercolor:#D3E2EC; bordercolordark:#FFFFFF; BORDER-TOP:#C2D0DB 2px solid; BORDER-LEFT:#ffffff 1px solid; BORDER-RIGHT:#ffffff 1px solid; BORDER-BOTTOM:#C2D0DB 1px solid; border-collapse: collapse;">
    		<colgroup>
    			<col width="150"/>
    			<col width="?"/>
    		</colgroup>
    		<tr>
       			<td class="tbtd_caption"><label for="member_id"><spring:message code="title.member.member_id" /></label></td>
       			<td class="tbtd_content">
       				<form:input path="member_id" type="text" maxlength="20"/><form:errors path="member_id" />
       			</td>
       		</tr>
       		<tr>
       			<td class="tbtd_caption"><label for="member_pwd"><spring:message code="title.member.member_pwd" /></label></td>
       			<td class="tbtd_content">
       				<form:input path="member_pwd" type="password" maxlength="10"/><form:errors path="member_pwd" />
       			</td>
       		</tr>
       		<tr>
       			<td class="tbtd_caption"><label for="member_email"><spring:message code="title.member.member_email" /></label></td>
       			<td class="tbtd_content">
       				<form:input path="member_email" type="text" maxlength="30"/><form:errors path="member_email" />
       			</td>
       		</tr>
    	</table>
      </div>
    	<div id="sysbtn">
    		<ul>
    			<li>
                    <span class="btn_blue_l">
                        <a href="javascript:fn_egov_join();">
                            <spring:message code="button.complete" />
                        </a>
                        <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                    </span>
                </li>
            </ul>
    	</div>
    </div>
</form:form>
</body>
</html>