<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">

		<form action="/post" method="post">
			<div class="form-group">

				<input type="text" class="form-control" placeholder="Enter Title"
					name="title" required="required" />
			</div>

			<div class="form-group">
				<textarea rows="10" class="form-control" name="content" id="summernote"></textarea>
			</div>
			
			<button type="submit" class="btn btn-outline-primary"
				id="btn-saveForm">글쓰기 완료</button>
		</form>
	<br />
</div>
<script>
      $('#summernote').summernote({
        placeholder: 'Enter Content🎈',
        tabsize: 2,
        height: 400
      });
    </script>
<%@ include file="../layout/footer.jsp"%>