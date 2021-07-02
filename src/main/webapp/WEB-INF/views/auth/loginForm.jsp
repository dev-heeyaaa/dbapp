<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form action="/auth/login" method="post">
		<div class="form-group">
			<label for="username">User Name:</label> <input type="text" class="form-control" placeholder="Enter Username" name="username" required="required"/>
		</div>

		<div class="form-group">
			<label for="password">Password:</label> <input type="password" class="form-control" placeholder="Enter password" name="password" required="required"/>
		</div>

		<button type="submit" class="btn btn-primary">Login</button>
	</form>
</div>
<%@ include file="../layout/footer.jsp"%>