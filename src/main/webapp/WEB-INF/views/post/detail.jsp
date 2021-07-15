<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<c:if test="${sessionScope.principal.id == postEntity.user.id}">
		<a href="/post/${postEntity.id}/updateForm" class="btn btn-outline-warning">수정</a>
		<form style="display: inline-block" onsubmit="deletePost()">
			<button id="btn-delete" class="btn btn-outline-danger" type="submit">삭제</button>
		</form>
	</c:if>

	<br /> <br />

	<div>
		글 번호 : <span>${postEntity.id}</span> 작성자 : <span><i>${postEntity.user.username}</i></span>
	</div>
	<br />
	<div var=>
		<h3>${postEntity.title}</h3>
	</div>
	<hr />
	<div>
		<div>${postEntity.content}</div>
	</div>
	<hr />

	<div class="card">
		<form>
			<div class="card-body">
				<textarea id="reply-content" class="form-control" rows="1"></textarea>
			</div>
			<div class="card-footer">
				<button type="button" id="btn-reply-save"
					class="btn btn-outline-primary">등록</button>
			</div>
		</form>
	</div>
	<br />

	<div class="card">
		<div class="card-header">
			<b>댓글 리스트</b>
		</div>
		<ul id="reply-box" class="list-group">
			<li id="reply-1"
				class="list-group-item d-flex justify-content-between">
				<div>댓글입니다</div>
				<div class="d-flex">
					<div class="font-italic">
						작성자 : <span>${postEntity.user.username}</span> &nbsp;
					</div>
					<button class="badge">삭제</button>
				</div>
			</li>
		</ul>
	</div>
	<br />
</div>

<script>
async function deletePost(){
	event.preventDefault(); // 새로고침 실행되는 거 막기
	let response = await fetch("/post/${postEntity.id}", {
		method: "delete"

	});

	let parseResponse = await response.text(); // 통신으로 받은 게 문자열 (json이 아니라)
	console.log(parseResponse);

	if(parseResponse === "ok"){
		location = "/"
	}else{
		alert("삭제 실패");
	}
}	
</script>

<%@ include file="../layout/footer.jsp"%>