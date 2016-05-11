<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />
<html>

<body>

<div class="container-fluid">
	<form class="form-horizontal col-md-offset-4 col-md-4" id="resetnewPassword" action="resetnewPassword" method="post" commandName="resetnewPassword">		
		<div class="panel panel-primary">
			<div class="panel-heading">Reset password</div>
			
			<input name="message" id="message" value="${message}" type="hidden">
			<input name="id" id="id" value="${id}" type="hidden">
			<input name="flag" id="flag" value="${Flag}" type="hidden">
			<hr>
			<!-- Text input-->
			<div class="form-group">
			  <label class="col-md-3 control-label" for="textinput">New Password</label>  
			  <div class="col-md-8">
				  <input id="newPassword" name="newPassword" type="password" placeholder="Enter new newPassword" class="form-control input-md" required="true">
				  
			  </div>
			</div>
			
			<!-- Text input-->
			<div class="form-group">
			  <label class="col-md-3 control-label" for="textinput">Confirm Password</label>  
			  <div class="col-md-8">
				  <input id="confirmnewPassword" name="confirmnewPassword" type="password" placeholder="Confirm newPassword" class="form-control input-md" required="true">
				
			  </div>
			</div>
			
			<div class="form-group">
			  <label class="col-md-3 control-label" for="singlebutton"></label>
			  <div class="col-md-8">
			    <button id="resetnewPassword" name="singlebutton" class="btn btn-primary" onclick="return passwordMatch()" type="button">Reset newPassword</button>
			  </div>
			  
			</div>
		</div>
	</div>
	</form>
</div>
</body>

<script>

$(document).ready(function() {
	
	var flag = document.getElementById('flag').value;
	var msg = document.getElementById('message').value;
	var z = document.getElementById('id').value;
	
	if(msg != null && msg!="" && flag=="S"){
		alert(msg);
		document.location.href="/Distributed-Library";
	}
	else if(msg!= null && msg!="" && flag=="E"){
		alert(msg);
		document.location.href="/Distributed-Library";
	}else if(msg != null && msg!="" && (flag=="" || flag == null)){
		alert(msg);
		document.location.href="/Distributed-Library";
	}
	
	event.preventDefault();
});	

	
	function passwordMatch(){
		
		
		
		var enterednewPassword = document.getElementById('newPassword').value;
		var confirmnewPassword = $("#confirmnewPassword").val();

		if(enterednewPassword == "" || confirmnewPassword == ""){
			alert("Please enter password");
		}else{
			
			if(enterednewPassword != confirmnewPassword){
				alert("Your entered newPassword and confirmed newPassword should be same !\n");
				return false;
			}else{
	 			document.getElementById("resetnewPassword").submit();
			}
			
		}
		

		
	}

</script>
</html>