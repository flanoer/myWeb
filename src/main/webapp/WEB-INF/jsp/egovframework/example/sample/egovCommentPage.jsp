<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>
<%@ taglib prefix="spring"    uri="http://www.springframework.org/tags"%>
<%
  /**
  * @Class Name : egovCommentPage.jsp
  * @Description : Comment Page 화면
  * @Modification Information
  *
  *   수정일         수정자                   수정내용
  *  -------    --------    ---------------------------
  *  2018.07.06            최초 생성
  *
  * author 남기웅
  * since 2018.07.06
  *
  * Copyright (C) 2018 by 남기웅  All right reserved.
  */
%>
    
<!--For Commons Validator Client Side-->
<script type="text/javascript" src="<c:url value='/cmmn/validator.do'/>"></script>
<validator:javascript formName="commentVO" staticJavascript="false" xhtml="true" cdata="false"/>
<div style="float:left;">
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
    	if(!validateCommentVO(frm)){
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
	<form id="commentForm" name="commentForm" method="post">
		<br/>
		<br/>
		<div>
			<div>
				<span><strong>Comments</strong></span> <span id="comment_Count"></span>
			</div>
			<div>
				<table width="680px" border="1" cellpadding="0" cellspacing="0" style="bordercolor: #D3E2EC; bordercolordark: #FFFFFF; BORDER-TOP: #C2D0DB 2px solid; BORDER-LEFT: #ffffff 1px solid; BORDER-RIGHT: #ffffff 1px solid; BORDER-BOTTOM: #C2D0DB 1px solid; border-collapse: collapse;">
					<tr>
						<td><textarea id="cmt_content" name="cmt_content" style="width: 660px" rows="3" cols="30" placeholder="댓글을 입력하세요"></textarea>
							<br/>
							<div>
								<span class="btn_blue_l"><a href="#"
									onClick="javascript:fn_comment();">등록</a> <img
									src="<c:url value='/images/egovframework/example/btn_bg_r.gif'/>"
									style="margin-left: 6px;" alt="" /> </span>
							</div></td>
					</tr>
				</table>
			</div>
		</div>
		<input type="hidden" id="orirec_id" name="orirec_id" value="${sampleVO.id }" />
	</form>
	<div>
		<form id="commentListForm" name="commentListForm" method="post">
			<div id="commentList"></div>
		</form>
	</div>
<script type="text/javascript">
	/*
	 * 댓글 등록하기(Ajax)
	 */
	function fn_comment(){
	    $.ajax({
	        type:'POST',
	        url : "<c:url value='/insertComment.do'/>",
	        data:$("#commentForm").serialize(),
	        success : function(data){
	            if(data=="success")
	            {
	                getCommentList();
	                $("#cmt_content").val("");
	            }
	        },
	        error:function(request,status,error){
	            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	       }
	        
	    });
	}
	 
	/**
	 * 초기 페이지 로딩시 댓글 불러오기
	 */
	$(function(){
	    
	    getCommentList();
	    
	});
	 
	/**
	 * 댓글 불러오기(Ajax)
	 */
	function getCommentList(){
	    
	    $.ajax({
	        type:'GET',
	        url : "<c:url value='/commentList.do'/>",
	        dataType : "json",
	        data:$("#commentForm").serialize(),
	        contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
	        success : function(data){
	            
	            var html = "";
	            var cCnt = data.length;
	            
	            if(data.length > 0){
	                
	                for(i=0; i<data.length; i++){
	                    html += "<div>";
	                    html += "<div><table><h6><strong>"+data[i].regi_id+"님이 "+data[i].regidate+"에 작성한 댓글 입니다.</strong></h6>";
	                    html += data[i].cmt_content + "<tr><td></td></tr>";
	                    html += "</table></div>";
	                    html += "</div>";
	                }
	                
	            } else {
	                
	                html += "<div>";
	                html += "<div><table><h6><strong>등록된 댓글이 없습니다.</strong></h6>";
	                html += "</table></div>";
	                html += "</div>";
	                
	            }
	            
	            $("#comment_Count").html(cCnt);
	            $("#commentList").html(html);
	            
	        },
	        error:function(request,status,error){
	        	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	        }
	        
	    });
	}
	 
	</script>
</div>