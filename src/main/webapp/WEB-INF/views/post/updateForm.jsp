<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>
<div class="container">

		<form onsubmit="updatePost()">
			<div class="form-group">

				<input id="title" value ="${postEntity.title}" type="text" class="form-control" placeholder="Enter Title"
					name="title" required="required" />
			</div>

			<div class="form-group">
				<textarea id="content" rows="10" class="form-control" name="content">
					${postEntity.content}
				</textarea>
			</div>
			
			<button type="submit" class="btn btn-outline-primary"
				id="btn-saveForm">글쓰기 완료</button>
		</form>
	<br />
</div>
<script>
	async function updatePost(){
		event.preventDefault();
		
		let title = document.querySelector("#title").value;
		let content = document.querySelector('#content').value;
		
		let updateDto = {
				title:title,
				content:content
		};
		
		let response = await fetch("/post/${postEntity.id}", {
			method: "put",
			body: JSON.stringify(updateDto),
			headers: {
				"Content-Type":"application/json; charset=utf-8"
			}
		});
		
		let parseResponse = await response.text();
		
		if(parseResponse === "ok"){
			location.href = "/post/${postEntity.id}";
		}
	}

      $('#content').summernote({
        placeholder: 'Enter Content🎈',
        tabsize: 2,
        height: 400
      });
    </script>
<%@ include file="../layout/footer.jsp"%>