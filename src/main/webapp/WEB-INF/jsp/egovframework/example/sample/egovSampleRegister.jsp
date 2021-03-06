<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring"    uri="http://www.springframework.org/tags"%>
<%
  /**
  * @Class Name : egovSampleRegister.jsp
  * @Description : Sample Register 화면
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2009.02.01            최초 생성
  *
  * author 실행환경 개발팀
  * since 2009.02.01
  *
  * Copyright (C) 2009 by MOPAS  All right reserved.
  */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <c:set var="registerFlag" value="${empty sampleVO.id ? 'create' : 'modify'}"/>
    <title>Sample <c:if test="${registerFlag == 'create'}"><spring:message code="button.create" /></c:if>
                  <c:if test="${registerFlag == 'modify'}"><spring:message code="button.modify" /></c:if>
    </title>
    <link type="text/css" rel="stylesheet" href="<c:url value='/css/egovframework/sample.css'/>"/>
    
    <!--For Commons Validator Client Side-->
    <script type="text/javascript" src="<c:url value='/cmmn/validator.do'/>"></script>
    <validator:javascript formName="sampleVO" staticJavascript="false" xhtml="true" cdata="false"/>
    
    <script type="text/javaScript" language="javascript" defer="defer">
        <!--
        /* 글 목록 화면 function */
        function fn_egov_selectList() {
           	document.detailForm.action = "<c:url value='/egovSampleList.do'/>";
           	document.detailForm.submit();
        }
        
        /* 글 삭제 function */
        function fn_egov_delete() {
           	document.detailForm.action = "<c:url value='/deleteSample.do'/>";
           	document.detailForm.submit();
        }
        
        /* 글 등록 function */
        function fn_egov_save() {
        	frm = document.detailForm;
        	if(!validateSampleVO(frm)){
                return;
            }else{
            	frm.action = "<c:url value="${registerFlag == 'create' ? '/addSample.do' : '/updateSample.do'}"/>";
                frm.submit();
            }
        }
        
        -->
        
        if(${not empty errMsg}){
           	alert("${errMsg}");
        }
    </script>
    
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"/>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    
    <script>
		/* 자동완성 없애는 거 추가하기 */
				
		
		/* 만료일은 시작일보다 하루 더 뒤부터 선택할 수 있도록 */
		/* 사용여부에 따라 기간설정 사용유무 */
		function dp_func(e){
			if(e.val() == 'Y'){
				$("#sdate").datepicker().datepicker("enable");
				$("#edate").datepicker().datepicker("enable");
			}
			else{
				$("#sdate").datepicker().datepicker("disable");
				$("#edate").datepicker().datepicker("disable");
			}
		}
		
		$(document).ready(function(){
			if(${registerFlag == 'modify'}){
				var sdate = new Date("${sampleVO.sdate}"), edate = new Date("${sampleVO.edate}");
				$("#sdate").datepicker();/////////////////////
				$("#sdate").datepicker("setDate",sdate);///////////////////////////
				$("#sdate").datepicker("option","dateFormat","yy-mm-dd");
				$("#sdate").datepicker("option","minDate",0).on("change",function(){
					var date = $(this).datepicker('getDate');
					date.setDate(date.getDate()+1);
					$("#edate").datepicker("option","minDate",date);
				});
				$("#edate").datepicker();
				$("#edate").datepicker("setDate",edate);
				$("#edate").datepicker("option","dateFormat", "yy-mm-dd");	
			}
			if(${registerFlag == 'create'}){
				$("#noti_yn").on("change",function(){
					createDatePick(this); 
				});				
			}
			if(${empty errMsg}){
				if(${registerFlag == 'create'} && $("noti_yn").val() == 'N'){
					$("#sdate").datepicker().datepicker("disable");
					$("#edate").datepicker().datepicker("disable");
				}
				$("#noti_yn").on('change',function(){
					dp_func($("#noti_yn"));
				});	
			}
			else{//에러메시지가 있을 때
				if("${errMsg}".indexOf("공") != -1){
					dp_func($("#noti_yn"));
					if(${registerFlag == 'create'}){
						$("#noti_yn").on("change",function(){
							createDatePick(this); 
						});				
					};
				}
			}
		});
		
		$(function() {
			if(${registerFlag == 'create'}){
				$("#noti_yn").on("change",function(){
					createDatePick(this);
				});				
			}
		});
		
		$(document).ready(function(){
			if($("useYn").val() == "Y"){
				$("#usr_pwd").attr("disabled","disabled");
			};
			$("#useYn").on("change",function(){
				if($(this).val() == "Y" || $(this).val() == ""){
					$("#usr_pwd").attr("disabled","disabled");
				}
				else{
					$("#usr_pwd").removeAttr("disabled");
				}
			})
		});
		
		function createDatePick(selector){
			$("#sdate").datepicker("option","dateFormat", "yy-mm-dd");	
			$("#sdate").datepicker("option","minDate",0).on("change",function(){
				var date = $(this).datepicker('getDate');
				date.setDate(date.getDate()+1);
				$("#edate").datepicker("option","minDate",date);
			});	
			$("#edate").datepicker("option","dateFormat", "yy-mm-dd"); 
		};
		
	</script>
    
</head>
<body style="text-align:center; margin:0 auto; display:inline; padding-top:100px;">

    <div id="content_pop">
	<form:form commandName="sampleVO" id="detailForm" name="detailForm" method="post" enctype="multipart/form-data">
    	<!-- 타이틀 -->
    	<div id="title">
    		<ul>
    			<li><img src="<c:url value='/images/egovframework/example/title_dot.gif'/>" alt=""/>
                    <c:if test="${registerFlag == 'create'}"><spring:message code="button.create" /></c:if>
                    <c:if test="${registerFlag == 'modify'}"><spring:message code="button.modify" /></c:if>
                </li>
    		</ul>
    	</div>
    	<!-- // 타이틀 -->
    	<div id="table">
    	<table width="100%" border="1" cellpadding="0" cellspacing="0" style="bordercolor:#D3E2EC; bordercolordark:#FFFFFF; BORDER-TOP:#C2D0DB 2px solid; BORDER-LEFT:#ffffff 1px solid; BORDER-RIGHT:#ffffff 1px solid; BORDER-BOTTOM:#C2D0DB 1px solid; border-collapse: collapse;">
    		<colgroup>
    			<col width="150"/>
    			<col width="?"/>
    			<col width="150"/>
    			<col width="?"/>
    		</colgroup>
    		<c:if test="${registerFlag == 'modify'}">
        		<tr>
        			<td class="tbtd_caption"><label for="id"><spring:message code="title.sample.id" /></label></td>
        			<td class="tbtd_content" colspan="3">
        				<form:input path="id" cssClass="essentiality" maxlength="10" readonly="true" />
        			</td>
        		</tr>
    		</c:if>
    		<tr>
    			<td class="tbtd_caption"><label for="name"><spring:message code="title.sample.name" /></label></td>
    			<td class="tbtd_content" colspan="3">
    				<form:input path="name" maxlength="30" cssClass="txt"/>
    				&nbsp;
    			</td>
    		</tr>
    		<tr>
    			<td class="tbtd_caption"><label for="useYn"><spring:message code="title.sample.useYn" /></label></td>
    			<td class="tbtd_content" colspan="3">
    				<form:select path="useYn" cssClass="use">
    					<form:option value="Y" label="Yes" />
    					<form:option value="N" label="No" />
    				</form:select>
    			</td>
    		</tr>
    		<tr>
    			<td class="tbtd_caption"><label for="noti_yn"><spring:message code="title.sample.noti_yn" /></label></td>
    			<td class="tbtd_content" colspan="3">
    				<form:select path="noti_yn" cssClass="use">
    					<form:option value="N" label="No" />
    					<form:option value="Y" label="Yes" />
    				</form:select>
    			</td>
    		</tr>
    		<tr>
    			<c:if test="">
    			<td class="tbtd_caption"><label for="sdate"><spring:message code="title.sample.sdate" /></label></td>
    			<td class="tbtd_content">
    				<form:input path="sdate" cssClass="txt"/>
    			</td>
    			<td class="tbtd_caption"><label for="edate"><spring:message code="title.sample.edate" /></label></td>
    			<td class="tbtd_content">
    				<form:input path="edate" cssClass="txt"/>
    			</td>
    			</c:if>
    		</tr>
    		<tr>
    			<td class="tbtd_caption"><label for="description"><spring:message code="title.sample.description" /></label></td>
    			<td class="tbtd_content" colspan="3">
    				<form:textarea path="description" rows="5" cols="58" />&nbsp;
                </td>
    		</tr>
    		<tr>
    			<td class="tbtd_caption"><label for="regUser"><spring:message code="title.sample.regUser" /></label></td>
    			<td class="tbtd_content" colspan="3">
    				<c:if test="${registerFlag == 'modify' }">
	                    <c:if test="${not empty sessionScope.regUser and sessionScope.regUser == sampleVO.regUser}" var="isMember">
	        				<form:input path="regUser" maxlength="10" cssClass="essentiality" readonly="true" />
	        				&nbsp;
	                    </c:if>
	       				<c:if test="${not isMember }">
	       					<form:input path="regUser" maxlength="10" cssClass="essentiality" readonly="true" />
	       					&nbsp;
	       				</c:if>
       				</c:if>
                </td>
    		</tr>
    		<tr>
    			<td class="tbtd_caption"><label for="usr_pwd"><spring:message code="title.sample.usr_pwd" /></label></td>
    			<td class="tbtd_content" colspan="3">
    				<form:input path="usr_pwd" cssClass="txt" type="password"/>
    			</td>
    		</tr>
    		<tr>
    			<c:if test="${registerFlag == 'create' }" var="regiFlag">
	    			<td class="tbtd_caption"><label for="fileupload"><spring:message code="title.sample.fileupload" /></label></td>
	    			<td class="tbtd_content" colspan="3">
	    				<form:input path="fileupload" type="file"/>
	    				<span id="preView"></span><br/>
	    				<img src="#" id="imgView" style="visibility:hidden;width:60%;height:auto;"/>
	    				<script type="text/javascript">
		    				function readURL(input) {
		    					if (input.files && input.files[0]) {
		    						var reader = new FileReader();
		    						reader.onload = function(e) {
		    							$('#imgView').attr('src', e.target.result);
		    						}
		    						reader.readAsDataURL(input.files[0]);
		    						dataType = input.files[0].type;
		    						if(dataType.indexOf('image') == -1){
		    							alert("이미지 파일만 업로드 가능합니다!");
		    							reset($("#file"));
		    						}
		    						else{
		    							$("#imgView").css("visibility","visible");
		    							$("#preView").html("미리보기");
		    						}
		    					}
		    				}
		    				
		    				function reset(selector){
		    					selector.wrap("<form>").closest("form").get(0).reset();
		    					selector.unwrap();
		    				}
		    				$("#file").change(function() {
		    					readURL(this);
		    				});
	    				</script>
	    			</td>
    			</c:if>
			    <c:if test="${not regiFlag }">
	    			<td class="tbtd_caption"><label for="fileupload"><spring:message code="title.sample.fileupload" /></label></td>
	    			<td class="tbtd_content" colspan="3">
	    				<c:if test="${not empty ori_fname }" var="flag2">
			    				<img src="<c:url value='/fileUpload/${sampleVO.id }/${sto_fname }'/>" alt="${ori_fname }" style="width:50%; height:auto"/><br/>
			    				<ul style="list-style-type: none;">
			    					<li>파일 정보</li>
			    					<li>파일 이름 : ${ori_fname }</li>	
			    					<li>파일 크기 : ${fsize } kb</li>
			    				</ul>
	    				</c:if>
	    				<c:if test="${not flag2 }">
	    					파일에 대한 정보가 존재하지 않습니다.
	    				</c:if>
	    			</td>
    			</c:if>
    		</tr>
    	</table>
      </div>
    	<div id="sysbtn">
    		<ul>
    			<li>
                    <span class="btn_blue_l">
                        <a href="javascript:fn_egov_selectList();"><spring:message code="button.list" /></a>
                        <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                    </span>
                </li>
    			<li>
                    <span class="btn_blue_l">
                        <a href="javascript:fn_egov_save();">
                            <c:if test="${registerFlag == 'create'}"><spring:message code="button.create" /></c:if>
                            <c:if test="${registerFlag == 'modify'}"><spring:message code="button.modify" /></c:if>
                        </a>
                        <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                    </span>
                </li>
    			<c:if test="${registerFlag == 'modify'}">
                    <li>
                        <span class="btn_blue_l">
                            <a href="javascript:fn_egov_delete();"><spring:message code="button.delete" /></a>
                            <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                        </span>
                    </li>
    			</c:if>
    			<li>
                    <span class="btn_blue_l">
                        <a href="javascript:document.detailForm.reset();"><spring:message code="button.reset" /></a>
                        <img src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>" style="margin-left:6px;" alt=""/>
                    </span>
                </li>
            </ul>
    	</div>
	    <input type="hidden" name="flag" value='${registerFlag }'/>
	    <!-- 검색조건 유지 -->
	    <input type="hidden" name="searchCondition" value="<c:out value='${searchVO.searchCondition}'/>"/>
	    <input type="hidden" name="searchKeyword" value="<c:out value='${searchVO.searchKeyword}'/>"/>
	    <input type="hidden" name="pageIndex" value="<c:out value='${searchVO.pageIndex}'/>"/>
	</form:form>
	<jsp:include page="egovCommentPage.jsp"></jsp:include>
    </div>
</body>
</html>