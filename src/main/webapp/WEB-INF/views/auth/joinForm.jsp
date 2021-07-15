<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
<!-- 	username=뭐시기&password=뭐시기&email=뭐시기&address=뭐시기 -> x-www-from... 데이터타입  MIME타입 -->
<!-- post 방식으로 요청 (body에...) -->
<!-- form action="/auth/join" 컨트롤러에 만들어 놓은 주소 넣기 / 같은 서버라서 http랑 포트번호 (풀 주소)안 적어도 됨-->
	<form action="/auth/join" method="post">

		<div class="form-group">
			<label for="username">User Name:</label> <input type="text" class="form-control" placeholder="Enter username" name="username"/>
		</div>
		<div class="form-group">
			<label for="password">Password:</label> <input type="password" class="form-control" placeholder="Enter password" name="password"/>
		</div>
		<div class="form-group">
			<label for="email">Email:</label> <input type="email" class="form-control" placeholder="Enter email" name="email"/>
		</div>
		
		<div class="form-group">			
			<input class="btn btn-outline-dark" type="button" onClick="goPopup();" value="주소찾기"/>
			<div id="list"></div>
			<div id="callBackDiv">
				<label for="address">Address:</label>
					<input type="text"  class="form-control" placeholder="Enter Address" name="address" id="address" readonly="readonly"/>
			</div>
		</div>
				
		<button type="submit" class="btn btn-outline-primary" id="btn-joinForm">Sign Up</button>
	</form>
	<br/>
</div>


<script>

function goPopup(){
	var pop = window.open("/juso","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
}


function jusoCallBack(roadFullAddr){
	let addressEL = document.querySelector("#address");
	addressEL.value = roadFullAddr;
	//console.log(addressEL);
}

</script>

<%@ include file="../layout/footer.jsp"%>


