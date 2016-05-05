<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- ============================================== HEADER ============================================== -->



<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
	      		<a class="navbar-brand" href="${pageContext.request.contextPath}">
	        		<img style="max-width:50px; margin-top: -15px;" alt="Brand" src="http://blindlibrary.utah.gov/images/logoBook.gif">
	      		</a>
	    	</div>
	    
	    	<!-- Brand and toggle get grouped for better mobile display -->
	    	<div class="navbar-header">
	      		<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
			        <span class="sr-only">Toggle navigation</span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
	      		</button>
	      
	      		<a class="navbar-brand" href="${pageContext.request.contextPath}">Digital Library</a>
	    	</div>
	    
	    	<!-- Collect the nav links, forms, and other content for toggling -->
	    	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">	      
	      		<form class="navbar-form navbar-left" role="search" action="${pageContext.request.contextPath}/search" method="get">
	        		<div class="form-group">
	          			<input type="text" class="form-control" placeholder="Search" name="searchbox">
	        		</div>
	        		<button type="submit" class="btn btn-default">Submit</button>
	      		</form>
	      		
	      		
	      		
	      		
	      		

	      		<ul class="nav navbar-nav navbar-right">
<%-- 			      	<li><a href="${pageContext.request.contextPath}/advanceSearch">Advance Search</a></li> --%>
<%-- 			      	<li><a href="${pageContext.request.contextPath}/bookhome">Sell</a></li> --%>


					<c:choose>
					<c:when
						test="${sessionScope.USERNAME != undefined && sessionScope.USERNAME != null  && sessionScope.USERNAME != '' }">
						<%-- <c:when test="${sessionScope.USERNAME != ''}"> --%>
						<li class="dropdown">
				          	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Hi ${sessionScope.USERNAME} <span class="caret"></span></a>
				          	<ul class="dropdown-menu" role="menu">
					            <li><a href="${pageContext.request.contextPath}/showuser/${sessionScope.USERID}">Profile</a></li>
<%-- 							    <li><a href="${pageContext.request.contextPath}/transactions">View Transactions</a></li> --%>
<%-- 							    <li><a href="${pageContext.request.contextPath}/requestbook">Make a request</a></li> --%>
							    <li><a href="${pageContext.request.contextPath}/logout">Signout</a></li>
				          	</ul>
			       		</li>
					</c:when>

					<c:otherwise>
						<li><a data-toggle="modal" data-target="#modal-login-big" href="#">Login/Register</a></li>
					</c:otherwise>
				</c:choose>
				</ul>
			</div>  
		</div>
	</nav>




<!-- <header class="header"> <nav
	class="navbar navbar-bookshop navbar-static-top" role="navigation"> -->

<!-- /.container --> </nav><!-- /.navbar --> <!-- Modal -->
<div id="modal-login-big" class="modal login fade hidden-xs"
	tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<div class="text-center">
					<ul id="myTab" class="nav nav-tabs">
						<li class="active"><a href="#signin" data-toggle="tab">Sign
								In</a></li>
						<li class=""><a href="#signup" data-toggle="tab">Register</a></li>
					</ul>
					<!-- /.login-list -->
					<!-- <form role="form" class="inner-top-50" id="login" action="login" method="post" commandName="logindetails"> -->

					<div id="myTabContent" class="tab-content">

						<div class="tab-pane fade active in" id="signin">

							<form role="form" class="inner-top-50 form-horizontal" id="login">
								<div class="form-group">
									<label for="exampleInputEmail1" class="sr-only">Email
										address</label> <input type="email"
										class="form-control bookshop-form-control" id="userEmail"
										name="userEmail" placeholder="Enter Email ID here"
										required="true">
							
								</div>
								<div class="form-group">
									<label for="exampleInputPassword1" class="sr-only">Password</label>
									<input type="password"
										class="form-control bookshop-form-control" id="password"
										name="password" placeholder="Enter password here"
										required="true">
									
								</div>

								<button id="singlebutton" name="singlebutton"
									class="btn btn-primary btn-uppercase" type="submit">Sign
									In</button>
								<!-- <button type="button" class="btn btn-primary btn-uppercase">Login</button> -->
								<a href="#" class='forgot-password'>forgot password</a>
							</form>


						</div>

						<div class="tab-pane fade" style="overflow-y:scroll;height:400px;" id="signup">

							<form role="form" class="inner-top-50 form-horizontal" id="register">
								<div class="form-group">
									<label for="userName" class="sr-only">Name</label> <input type="text"
										class="form-control bookshop-form-control" id="userName"
										name="userName" placeholder="Enter your name here"
										required="true">
							
								</div>
								
								<div class="form-group">
									<label for="dob" class="sr-only">Date Of Birth</label>
									<input type="text"
										class="form-control bookshop-form-control" id="dob"
										name="dob" placeholder="Enter your date of birth in (mm/dd/yyyy) format"
										required="true">
									
								</div>
								
								<div class="form-group">
									<label for="email" class="sr-only">Email</label>
									<input type="email"
										class="form-control bookshop-form-control" id="emailId"
										name="emailId" placeholder="Enter your email address here"
										required="true">
								</div>		
										
								<div class="form-group">
									<label for="phone" class="sr-only">Phone</label>
									<input type="number"
										class="form-control bookshop-form-control" id="phone"
										name="phone" placeholder="Enter your phone number here"
										required="true">
									
								</div>

								
								<div class="form-group">
									<label for="category" class="sr-only">Category</label>
									<div id="categoryDiv">
										<!-- <select id="category" name="category" multiple="multiple" style="width:346px; height:38px;">
											<option value="1">Option 1</option>
											<option value="2">Option 2</option>
											<option value="3">Option 3</option>
											<option value="4">Option 4</option>
											<option value="5">Option 5</option>
										</select> -->
									</div>
								</div>
								
								<div class="form-group">
									<label for="parentId" class="sr-only">Email of Family member previously joined</label>
									<input type="email"
										class="form-control bookshop-form-control" id="parentId"
										name="parentId" placeholder="Enter email of Family member previously joined"
										required="true">
									
								</div>
								
								<div class="form-group">
									<label for="street" class="sr-only">Street Name</label>
									<input type="text"
										class="form-control bookshop-form-control" id="street"
										name="street" placeholder="Enter street name"
										required="true">
									
								</div>
								
								<div class="form-group">
									<label for="aptNo" class="sr-only">Apt. #</label>
									<input type="text"
										class="form-control bookshop-form-control" id="aptNo"
										name="aptNo" placeholder="Enter your Apartment No."
										required="true">
									
								</div>
								
								<div class="form-group">
									<label for="city" class="sr-only">City</label>
									<input type="text"
										class="form-control bookshop-form-control" id="city"
										name="city" placeholder="Enter City"
										required="true">
									
								</div>
								
								<div class="form-group">
									<label for="state" class="sr-only">State</label>									
									<select id="state" name="state" style="width:346px; height:38px;" placeholder="select state">
										<option value="" disabled selected>select state</option>
										<option value="AL">Alabama</option>
										<option value="AK">Alaska</option>
										<option value="AZ">Arizona</option>
										<option value="AR">Arkansas</option>
										<option value="CA">California</option>
										<option value="CO">Colorado</option>
										<option value="CT">Connecticut</option>
										<option value="DE">Delaware</option>
										<option value="DC">District Of Columbia</option>
										<option value="FL">Florida</option>
										<option value="GA">Georgia</option>
										<option value="HI">Hawaii</option>
										<option value="ID">Idaho</option>
										<option value="IL">Illinois</option>
										<option value="IN">Indiana</option>
										<option value="IA">Iowa</option>
										<option value="KS">Kansas</option>
										<option value="KY">Kentucky</option>
										<option value="LA">Louisiana</option>
										<option value="ME">Maine</option>
										<option value="MD">Maryland</option>
										<option value="MA">Massachusetts</option>
										<option value="MI">Michigan</option>
										<option value="MN">Minnesota</option>
										<option value="MS">Mississippi</option>
										<option value="MO">Missouri</option>
										<option value="MT">Montana</option>
										<option value="NE">Nebraska</option>
										<option value="NV">Nevada</option>
										<option value="NH">New Hampshire</option>
										<option value="NJ">New Jersey</option>
										<option value="NM">New Mexico</option>
										<option value="NY">New York</option>
										<option value="NC">North Carolina</option>
										<option value="ND">North Dakota</option>
										<option value="OH">Ohio</option>
										<option value="OK">Oklahoma</option>
										<option value="OR">Oregon</option>
										<option value="PA">Pennsylvania</option>
										<option value="RI">Rhode Island</option>
										<option value="SC">South Carolina</option>
										<option value="SD">South Dakota</option>
										<option value="TN">Tennessee</option>
										<option value="TX">Texas</option>
										<option value="UT">Utah</option>
										<option value="VT">Vermont</option>
										<option value="VA">Virginia</option>
										<option value="WA">Washington</option>
										<option value="WV">West Virginia</option>
										<option value="WI">Wisconsin</option>
										<option value="WY">Wyoming</option>
									</select>
									
									<!-- <input type="text"
										class="form-control bookshop-form-control" id="state"
										name="state" placeholder="Enter State"
										required="true"> -->
									
								</div>
								
								<div class="form-group">
									<label for="country" class="sr-only">Country</label>
									<input type="text"
										class="form-control bookshop-form-control" id="country"
										name="state" placeholder="Enter Country"
										required="true" readonly="readonly" value="US">
									
								</div>
								
								<div class="form-group">
									<label for="zipcode" class="sr-only">Zipcode</label>
									<input type="text"
										class="form-control bookshop-form-control" id="zipcode"
										name="zipcode" placeholder="Enter your Zipcode"
										required="true">
									
								</div>
								
								<div class="form-group">
									<label for="userPassword" class="sr-only">Password</label>
									<input type="password"
										class="form-control bookshop-form-control" id="userPassword"
										name="userPassword" placeholder="Enter password here"
										required="true">
									
								</div>
								
								<div class="form-group">
									<label for="confirmPassword" class="sr-only">Confirm Password</label>
									<input type="password"
										class="form-control bookshop-form-control" id="confirmPassword"
										name="confirmPassword" placeholder="Confirm your password here"
										required="true">
									
								</div>
									
								
								<button id="singlebutton" name="singlebutton"
									class="btn btn-primary btn-uppercase" type="submit">Register</button>
								<!-- <button type="button" class="btn btn-primary btn-uppercase">Login</button> -->
								<a href="#" class='forgot-password'>forgot password</a>
							</form>

						</div>



					</div>

				</div>
			</div>
			<!-- /.modal-body -->
			<div class="modal-footer">
				<div class="text-center">
					<ul class='social-list text-center'>
						<!-- <li><a href="#" class="facebook"></a></li>
                        <li><a href="#" class="google-plus"></a></li>
                        <li><a href="#" class="twitter"></a></li>
                        <li><a href="#" class="pinterest"></a></li> -->
					</ul>
					<!-- /.social-list -->
				</div>
			</div>
			<!-- /.modal-footer -->
			<a href="#" data-dismiss="modal" class="remove-icon"><i
				class="fa fa-times"></i></a>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal --> <!-- Modal -->
<div id="modal-login-small"
	class="modal fade login login-xs hidden-sm hidden-lg" tabindex="-1"
	role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<div class="text-center">
					
					<form role="form" class="inner-top-50">
						<div class="form-group">
							<label for="entername" class="sr-only">Email</label> <input
								type="email" class="form-control bookshop-form-control"
								id="entername" placeholder="Hezy Theme">
						</div>
						<div class="form-group">
							<label for="enterpassword" class="sr-only">Password</label> <input
								type="password" class="form-control bookshop-form-control"
								id="enterpassword">
						</div>

						<button type="button"
							class="btn btn-primary btn-block btn-uppercase">Login</button>
						<a href="#" class='forgot-password'>forgot password</a>
					</form>
				</div>
			</div>
			<!-- /.modal-body -->
			<div class="modal-footer">
				<div class="text-center">
					<ul class='social-list text-center'>
						<li><a href="#" class="facebook"></a></li>
						<li><a href="#" class="google-plus"></a></li>
						<li><a href="#" class="twitter"></a></li>
						<li><a href="#" class="pinterest"></a></li>
					</ul>
					<!-- /.social-list -->
				</div>
			</div>
			<!-- /.modal-footer -->
			<a href="#" data-dismiss="modal" class="remove-icon"><i
				class="fa fa-times"></i></a>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<!-- </header> -->


<!-- ============================================== HEADER : END ============================================== -->

<script type="text/javascript">
	
	$(document).ready(
				
			function() {
				
				$.ajax({
					
					type : "GET",
					contentType : "application/json",
					url : "/Distributed-Library/categories",
					complete : function(data) {
						
						
						 if(data !=''){
				             
				            	  var html = "<select id='category' name='category' multiple='multiple' style='width:346px; height:38px;'>";
				            	  
				            	  
				            	  $.each( data.responseJSON, function(index, val) {
				            		  
				            		html += "<option value=''"+index+"' title='"+val.name+"'>"+val.name+"</option>";  
				            	    
				            	  });
				            	  
				            	  html += "</select>";
				            	  
				            	  $("#categoryDiv").html(html);
				  				  $("#category").multiselect();
				         } 
						
					}
					
				})
				
				$('#login').submit(
						function(event) {
													
							var search = {}
							search["userEmail"] = $("#userEmail").val();
							search["password"] = $("#password").val();
							$.ajax({
								type : "POST",
								contentType : "application/json",
								url : "/Distributed-Library/login",
								data : JSON.stringify(search),
								dataType : 'json',
								complete : function(data) {
									
									alert(JSON.stringify(data));
									
									if (data.responseJSON.successFlag == "Y") {
										
										location.reload(true);
									
									} else {
																				
										alert(data.responseJSON.ErrorMessage);
									}
								}
							});
							event.preventDefault();
						})
									
						
				$('#register').submit(
					function(event){
						
						var array_of_checked_values = $("#category").multiselect("getChecked").map(function(){
							
							   return (this.title ? this.title : this.description );
							
						}).get();
						
						var categoriesArray = "";
						for(var i=0;i<array_of_checked_values.length;i++){
							categoriesArray += array_of_checked_values[i] + ","
						}
						
						var categories = categoriesArray.substring(0, categoriesArray.length-1);
						
						var signupData = {};
						
						signupData["userName"] = $("#userName").val();
						signupData["dob"] = $("#dob").val();
						signupData["emailId"] = $("#emailId").val();
						signupData["phone"] = $("#phone").val();
						signupData["category"] = $("#category").val();
						signupData["parentId"] = $("#parentId").val();
						signupData["street"] = $("#street").val();
						signupData["aptNo"] = $("#aptNo").val();
						signupData["city"] = $("#city").val();
						signupData["state"] = $("#state").val();
						signupData["country"] = $("#country").val();
						signupData["zipcode"] = $("#zipcode").val();
						signupData["userPassword"] = $("#userPassword").val();
						signupData["confirmPassword"] = $("#confirmPassword").val();
						signupData["category"] = categories;
						
						$.ajax({
							type : "POST",
							contentType : "application/json; charset=utf-8",
							url : "/Distributed-Library/signup",
							data : JSON.stringify(signupData),
							dataType : "json",
							complete : function(response) {
								
								var data = JSON.parse(response.responseText);
								
								if (data.successFlag == "Y") {
																		
									alert(data.successMessage);
									location.reload(true);
								
								} else {
																			
									alert(data.errorMessage);
								}
							}
						}); 
						event.preventDefault();						
				
				})	
			});
			
				
</script>