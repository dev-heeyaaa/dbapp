<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"  %>
<div class="container">

	<c:forEach var="post" items="${postsEntity}"> <!-- requestScope-->
		<div class="card">
		  <div class="card-body">
		    <h4 class="card-title">${post.title}</h4> <!-- pageScope -->
		    <a href="/post/${post.id}" class="btn btn-outline-primary">상세보기</a>
		  </div>
		</div><!-- End Of Card -->
		<br>
	</c:forEach>

	
</div><!-- End Of Container -->
<%@ include file="../layout/footer.jsp" %>