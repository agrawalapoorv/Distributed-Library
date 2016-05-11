<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>



<%@ page session="true"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="imports.jsp" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<!-- ============================================== HEADER ============================================== -->



<style>
#ajaxSpinnerContainer {
	display: none;
	z-index: 2147483647;
}

.logo {
	font-size: 38px;
	line-height: 18px;
	font-family: 'OleoScriptBold';
	color: white;
	margin-top: 12px;
}

.formClass {
	margin-top: 12px;
	background: #fafbf8;
	border: 1px solid rgba(0, 0, 0, 0);
	font-size: 14px;
	height: 40px;
	padding-left: 20px;
	padding-right: 20px;
	width: 140%;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	-webkit-box-shadow: 0 2px 3px rgba(0, 0, 0, 0.17);
	-moz-box-shadow: 0 2px 3px rgba(0, 0, 0, 0.17);
	box-shadow: 0 2px 3px rgba(0, 0, 0, 0.17);
	border-bottom: 3px solid #edefeb;
	color: #b1b5a9;
}
</style>

<style type="text/css">
	div#map_container{
		width:550px;
		height:350px;
	}
	</style> 


<nav class="navbar navbar-default navbar-fixed-top"
	style="background-color: #599806;border-color:#599806; min-height: 70px;">

<div class="container-fluid">

	<div class="navbar-header">

		<a class="navbar-brand" href="${pageContext.request.contextPath}">

			<img style="max-width: 100px; margin-top: -24px;" alt="Brand"
			src="<c:url value="/resources/images/book-logo.png"/>">

		</a>

	</div>



	<!-- Brand and toggle get grouped for better mobile display -->

	<div class="navbar-header">

		<button type="button" class="navbar-toggle collapsed"
			data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
			onclick="loadmap();">

			<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span>

			<span class="icon-bar"></span> <span class="icon-bar"></span>

		</button>



		<a class="navbar-brand" href="${pageContext.request.contextPath}">

			<h1 class="logo">Digital Library</h1>

		</a>

	</div>



	<!-- Collect the nav links, forms, and other content for toggling -->

	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

		<form class="navbar-form navbar-left" role="search"
			action="${pageContext.request.contextPath}/search" method="get"
			style="width: 50%">

			<div class="form-group" style="width: 100%">
				<table style="width: 100%">
					<tr>
						<td style="width: 80%"><input type="text"
							class="form-control formClass" placeholder="Search"
							name="searchbox" id="searchbox" style="width: 100%" /></td>
						<td style="width: 20%" align="right">
							<button type="submit" class="btn btn-default"
								style="margin-top: 12px; width: 90%">Search</button>
						</td>
					</tr>
				</table>



			</div>


		</form>
		<ul class="nav navbar-nav navbar-right">

			<%--       <li><a href="${pageContext.request.contextPath}/advanceSearch">Advance Search</a></li> --%>

			<%--       <li><a href="${pageContext.request.contextPath}/bookhome">Sell</a></li> --%>





			<c:choose>

				<c:when
					test="${sessionScope.USERNAME != undefined && sessionScope.USERNAME != null  && sessionScope.USERNAME != '' }">

					<%-- <c:when test="${sessionScope.USERNAME != ''}"> --%>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Hi

							${sessionScope.USERNAME} <span class="caret"></span>

					</a>

						<ul class="dropdown-menu" role="menu">

							<li><a
								href="${pageContext.request.contextPath}/showuser/${sessionScope.USERID}">Profile</a></li>

							<%--     <li><a href="${pageContext.request.contextPath}/transactions">View Transactions</a></li> --%>

							<%--     <li><a href="${pageContext.request.contextPath}/requestbook">Make a request</a></li> --%>

							<%-- <li><a href="${pageContext.request.contextPath}/logout">Signout</a></li> --%>

							<li><a href="#" onclick="signout()">Signout</a></li>
							<li><a
								href="${pageContext.request.contextPath}/payment/${sessionScope.USERID}">Payment</a></li>

						</ul></li>

				</c:when>



				<c:otherwise>

					<li><a data-toggle="modal" data-target="#modal-login-big"
						href="#">Login/Register</a></li>

				</c:otherwise>

			</c:choose>

		</ul>

	</div>

</div>

</nav>

<div class="container-fluid" style="margin-top: 60px"></div>



<input name="activationCodeHiddenInput" id="activationCodeHiddenInput"
	value="activationCodeHiddenInput" type="hidden">

<header class="header" style="height: 50px">
<div class="header-nav animate-dropdown" style="height: 100%">
	<div class="container">
		<div class="nav-bg-class">
			<!-- ============================================================= NAVBAR PRIMARY ============================================================= -->
			<nav class="yamm navbar navbar-primary animate-dropdown"
				role="navigation">
			<div class="navbar-header">
				<button id="btn-navbar-primary-collapse" type="button"
					class="navbar-toggle" data-toggle="collapse"
					data-target="#navbar-primary-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>
			<!-- /.navbar-header -->
			<div class="collapse navbar-collapse" id="navbar-primary-collapse">
				<ul class="nav navbar-nav">

					<li class="hidden-sm"><a
						href="${pageContext.request.contextPath}/openAdvancedSearch">Advanced
							Search</a></li>		
							
					<c:if test="${sessionScope.USERID != null}">
						<li class="hidden-sm"><a
						href="${pageContext.request.contextPath}/payment/${sessionScope.USERID}"> Payment Details </a></li>	
						
						<li class="hidden-sm"><a
						href="${pageContext.request.contextPath}/showuser/${sessionScope.USERID}"> My Profile </a></li>
						
						<li class="hidden-sm"><a
						href="${pageContext.request.contextPath}/showOrders/${sessionScope.USERID}"> My Orders </a></li>	
					</c:if>			
					
					
				</ul>
				<!-- /.nav -->
			</div>
			<!-- /.collapse navbar-collapse --> </nav>
			<!-- /.yamm -->
			<!-- ============================================================= NAVBAR PRIMARY : END ============================================================= -->
		</div>
		<!-- /.nav-bg-class -->
	</div>
	<!-- /.container -->

</div>
<!-- /.header-nav --> <!-- <header class="header"> <nav

class="navbar navbar-bookshop navbar-static-top" role="navigation"> -->


</header>
<!-- /.container -->

<!-- /.navbar -->

<!-- Modal -->

<div id="modal-login-big" class="modal login fade hidden-xs"
	tabindex="-1" role="dialog" aria-hidden="true">

	<div class="modal-dialog">

		<div class="modal-content">

			<div class="modal-body">

				<div class="text-center">

					<ul id="myTab" class="nav nav-tabs">

						<li class="active"><a href="#signin" data-toggle="tab">Sign

								In</a></li>

						<li class=""><a href="#signup" id="regBtn" data-toggle="tab">Register</a></li>

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
										required="true" onblur="checkUserActivation()">



								</div>

								<div class="form-group">

									<label for="exampleInputPassword1" class="sr-only">Password</label>

									<input type="password"
										class="form-control bookshop-form-control" id="password"
										name="password" placeholder="Enter password here"
										required="true">



								</div>





								<div class="form-group" id="activationCodeInputDiv">

									<label for="activationCodeInput" class="sr-only">Activation

										Code</label> <input type="text"
										class="form-control bookshop-form-control"
										id="activationCodeInput" name="activationCodeInput"
										placeholder="Enter activation code here">

								</div>



								<button id="singlebutton" name="singlebutton"
									class="btn btn-primary btn-uppercase" type="submit">Sign

									In</button>

								<!-- <button type="button" class="btn btn-primary btn-uppercase">Login</button> -->

								<a href="#" class='forgot-password' onclick="forgotPassword()">forgot

									password</a>

							</form>





						</div>



						<div class="tab-pane fade"
							style="overflow-y: scroll; height: 500px;" id="signup">



							<form role="form" class="inner-top-50 form-horizontal"
								id="register">

								<div class="form-group">

									<label for="userName" class="sr-only">Name</label> <input
										type="text" class="form-control bookshop-form-control"
										id="userName" name="userName"
										placeholder="Enter your name here" required="true">



								</div>



								<div class="form-group">

									<label for="dob" class="sr-only">Date Of Birth</label> <input
										type="text" class="form-control bookshop-form-control"
										id="dob" name="dob"
										placeholder="Enter your date of birth in (mm/dd/yyyy) format"
										required="true">



								</div>



								<div class="form-group">

									<label for="email" class="sr-only">Email</label> <input
										type="email" class="form-control bookshop-form-control"
										id="emailId" name="emailId"
										placeholder="Enter your email address here" required="true">

								</div>



								<div class="form-group">

									<label for="phone" class="sr-only">Phone</label> <input
										type="number" class="form-control bookshop-form-control"
										id="phone" name="phone"
										placeholder="Enter your phone number here" required="true">



								</div>





								<div class="form-group">

									<label for="category" class="sr-only">Category</label>

									<div id="categoryDiv">

									</div>

								</div>



								<div class="form-group">

									<label for="parentId" class="sr-only">Email of Family

										member previously joined</label> <input type="email"
										class="form-control bookshop-form-control" id="parentId"
										name="parentId"
										placeholder="Enter email of Family member previously joined">



								</div>



								<div class="form-group">

									<label for="street" class="sr-only">Street Name</label> <input
										type="text" class="form-control bookshop-form-control"
										id="street" name="street" placeholder="Enter street name"
										required="true">



								</div>



								<div class="form-group">

									<label for="aptNo" class="sr-only">Apt. #</label> <input
										type="text" class="form-control bookshop-form-control"
										id="aptNo" name="aptNo" placeholder="Enter your Apartment No."
										required="true">



								</div>



								<div class="form-group">

									<label for="city" class="sr-only">City</label> <input
										type="text" class="form-control bookshop-form-control"
										id="city" name="city" placeholder="Enter City" required="true">



								</div>



								<div class="form-group">

									<label for="state" class="sr-only">State</label> <select
										id="state" name="state" style="width: 346px; height: 38px;"
										placeholder="select state" required="true">

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

									<label for="country" class="sr-only">Country</label> <input
										type="text" class="form-control bookshop-form-control"
										id="country" name="state" placeholder="Enter Country"
										required="true" readonly="readonly" value="US">



								</div>



								<div class="form-group">

									<label for="zipcode" class="sr-only">Zipcode</label> <input
										type="text" class="form-control bookshop-form-control"
										id="zipcode" name="zipcode" placeholder="Enter your Zipcode"
										required="true">



								</div>



								<div class="form-group">

									<label for="userPassword" class="sr-only">Password</label> <input
										type="password" class="form-control bookshop-form-control"
										id="userPassword" name="userPassword"
										placeholder="Enter password here" required="true">



								</div>



								<div class="form-group">

									<label for="confirmPassword" class="sr-only">Confirm

										Password</label> <input type="password"
										class="form-control bookshop-form-control"
										id="confirmPassword" name="confirmPassword"
										placeholder="Confirm your password here" required="true">



								</div>

								<button id="singlebutton" name="singlebutton"
									class="btn btn-primary btn-uppercase" type="button"
									onclick="resize();">Select Region</button>

			
								<div class="form-group" style="display: none;" id ="map-div">
									<jsp:include page="map.jsp"></jsp:include>
								</div>


<div>&nbsp;</div>

								<div class="form-group">

									<label for="selectedRegion" class="sr-only">Selected
										Region</label> <input type="text"
										class="form-control bookshop-form-control" id="selectedRegion"
										name="selectedRegion" placeholder="Region" required="true"
										readonly="readonly" value=""> <input type="hidden"
										id="selectedRegionId" />


								</div>





								<button id="singlebutton" name="singlebutton"
									class="btn btn-primary btn-uppercase" type="submit">Register</button>

								<!-- <button type="button" class="btn btn-primary btn-uppercase">Login</button> -->

								<!-- <a href="#" class='forgot-password'>forgot password</a> -->


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

<!-- /.modal -->

<!-- Modal -->

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
	$(document)

			.ready(

					function() {

						$(document).ajaxStart(function() {

							$("#ajaxSpinnerContainer").show();

						}).ajaxStop(function() {

							$("#ajaxSpinnerContainer").hide();

						});

						$("#activationCodeInputDiv").hide();

						$

								.ajax({

									type : "GET",

									contentType : "application/json",

									url : "/Distributed-Library/categories",

									complete : function(data) {

										if (data != '') {

											var html = "<select id='category' name='category' multiple='multiple' style='width:346px; height:38px;'>";

											$

													.each(

															data.responseJSON,

															function(index, val) {

																html += "<option value=''"+index+"' title='"+val.name+"'>"

																		+ val.name

																		+ "</option>";

															});

											html += "</select>";

											$("#categoryDiv").html(html);

											$("#category").multiselect();

										}

									}

								})

						$('#login')

								.submit(

										function(event) {

											var errorMsg = "";

											if ($("#activationCodeInputDiv")

											.is(":visible")) {

												var x = document

														.getElementById('activationCodeInput').value;

												var y = document

														.getElementById('activationCodeHiddenInput').value;

												if (x == "") {

													errorMsg = "Please provide account activation code you received via mail to proceed. \nYou will receive mail shortly if you haven't received yet. \nThanks for your patience.";

												} else {

													if (x != y) {

														errorMsg = "Please enter correct Activation code to proceed";

													}

												}

											}

											console.log("");

											if (errorMsg != "") {

												alert(errorMsg);

											} else {

												var search = {}

												search["userEmail"] = $(

												"#userEmail").val();

												search["password"] = $(

												"#password").val();

												$

														.ajax({

															type : "POST",

															contentType : "application/json",

															url : "/Distributed-Library/login",

															data : JSON

															.stringify(search),

															dataType : 'json',

															complete : function(

															data) {

																if (data.responseJSON.successFlag == "Y") {

																	location

																			.reload(true);

																} else {

																	alert(data.responseJSON.errorMessage);

																}

															}

														});

											}

											event.preventDefault();

										})

						$('#register')

								.submit(

										function(event) {

											var array_of_checked_values = $(

											"#category")

											.multiselect("getChecked")

											.map(

											function() {

												return (this.title ? this.title

												: this.description);

											}).get();

											var categoriesArray = "";

											for (var i = 0; i < array_of_checked_values.length; i++) {

												categoriesArray += array_of_checked_values[i]

														+ ","

											}

											var categories = categoriesArray

											.substring(

											0,

											categoriesArray.length - 1);

											var zipcode = $("#zipcode").val();

											var isValidZip = /(^\d{5}$)|(^\d{5}-\d{4}$)/

											.test(zipcode);

											var birthdate = $("#dob").val();

											var isValidBirthDate = /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/

											.test(birthdate);

											var errorMessage = "";

											if (!isValidZip) {

												errorMessage += "enter valid zip\n";

											}

											if (!isValidBirthDate) {

												errorMessage += "enter valid birthdate\n";

											}

											var enteredPassword = $(

											"#userPassword").val();

											var confirmPassword = $(

											"#confirmPassword").val();

											if (enteredPassword != confirmPassword) {

												errorMessage += "Your entered password and confirmed password should be same !\n";

											}
											var regionId = $(

											"#selectedRegionId").val();
											console.log($("#selectedRegionId").val());
										
											if (regionId == "") {

												errorMessage += "Select the region !\n";

											}
											if (errorMessage != "") {

												alert(errorMessage);

											} else {

												var signupData = {};

												signupData["userName"] = $(

												"#userName").val();

												signupData["dob"] = $("#dob")

												.val();

												signupData["emailId"] = $(

												"#emailId").val();

												signupData["phone"] = $(

												"#phone").val();

												signupData["category"] = $(

												"#category").val();

												signupData["parentId"] = $(

												"#parentId").val();

												signupData["street"] = $(

												"#street").val();

												signupData["aptNo"] = $(

												"#aptNo").val();

												signupData["city"] = $("#city")

												.val();

												signupData["state"] = $(

												"#state").val();

												signupData["country"] = $(

												"#country").val();

												signupData["zipcode"] = $(

												"#zipcode").val();

												signupData["userPassword"] = $(

												"#userPassword").val();

												signupData["confirmPassword"] = $(

												"#confirmPassword")

												.val();
												signupData["regionId"] = $(

												"#selectedRegionId")

												.val();

												signupData["category"] = categories;

												$

														.ajax({

															type : "POST",

															contentType : "application/json; charset=utf-8",

															url : "/Distributed-Library/signup",

															data : JSON

																	.stringify(signupData),

															dataType : "json",

															complete : function(

															response) {

																var data = JSON

																		.parse(response.responseText);

																if (data.successFlag == "Y") {

																	alert(data.successMessage);

																	location

																			.reload(true);

																} else {

																	alert(data.errorMessage);

																}

															}

														});

											}

											event.preventDefault();

										})

					});

	function signout() {

		$.ajax({

			type : "GET",

			contentType : "application/json",

			url : "/Distributed-Library/logout",

			complete : function(data) {

				location.reload(true);
				
				document.location.href="/Distributed-Library";

			}
		
		

		})
		
		event.PreventDefault();

	}

	function checkUserActivation() {

		var userData = {};

		userData["userEmail"] = $("#userEmail").val();

		$.ajax({

			type : "POST",

			contentType : "application/json",

			url : "/Distributed-Library/checkUserAccountActivation",

			data : JSON.stringify(userData),

			dataType : 'json',

			complete : function(data) {

				if (data != null && data.status == 200

				&& data.responseJSON.successFlag == "Y") {

					var jsonObj = JSON.parse(data.responseJSON.successMessage);

					if (jsonObj.flag == "N") {

						alert(jsonObj.activationCode);

						$("#activationCodeHiddenInput").val(

						"" + jsonObj.activationCode);

						alert($("#activationCodeHiddenInput").val());

						$("#activationCodeInputDiv").show();

					}

				}

			}

		})

	}

	function forgotPassword() {

		var userEmail = $("#userEmail").val();

		if (userEmail == "" || userEmail == null) {

			alert("Please enter your email address");

		} else {

			var search = {}

			search["userEmail"] = $("#userEmail").val();

			$.ajax({

				type : "POST",

				contentType : "application/json",

				url : "/Distributed-Library/sendEmail",

				data : JSON.stringify(search),

				dataType : 'json',

				complete : function(data) {

					if (data.responseJSON.successFlag == "Y") {

						location.reload(true);

					} else if (data.responseJSON.successFlag == "M") {

						alert(data.responseJSON.successMessage);

						location.reload(true);

					} else if (data.responseJSON.successFlag == "E") {

						alert(data.responseJSON.errorMessage);

					}

				}

			});

		}

	}
</script>


<script>
	jQuery(document)
			.ready(
					function() {
						var options = {

							url : function(phrase) {
								return "/Distributed-Library/getSearchSuggetion?searchTerm='"
										+ $("#searchbox").val() + "'";
							},

							getValue : function(element) {
								return element.name;
							},

							ajaxSettings : {
								dataType : "json",
							},

							requestDelay : 400
						};
						$("#searchbox").easyAutocomplete(options);
					});
</script>
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDakgiM9LXXqxtfMUSobwfzdc4ztIjyvZ0"></script>

<script>
   

      // In the following example, markers appear when the user clicks on the map.
      // Each marker is labeled with a single alphabetical character.
   
      
      function initialize(mylocation) {
    	  
    	
    	 var labels = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
    	      var labelIndex = 0;
    	      var markers= [];
    	  
        var santaclara = {  lat: 37.35410789999999,lng: -121.95523559999998};
        //37.3541° N, 121.9552° W -- santa clara
        var sjsu = {lat: 37.338208,lng: -121.886329};
        //37.3352° N, 121.8811° W -- sjsu
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 12,
          center: mylocation
        });
       // addMarker(santaclara, map);
       // addMarker(sjsu, map);
         var geocoder = new google.maps.Geocoder();
        var address = $("#street").val()+" "+$("#aptNo").val()+" "+$("#city").val()+" "+$("#state").val()+" "+$("#zipcode").val();
		var myloc;
        geocoder.geocode( { 'address': address}, function(results, status) {

          if (status == google.maps.GeocoderStatus.OK) {
            var latitude = results[0].geometry.location.lat();
            var longitude = results[0].geometry.location.lng();
             myloc = {lat: latitude,lng: longitude};
            var image = {
            	    url: 'http://icons.iconarchive.com/icons/icons-land/vista-map-markers/48/Map-Marker-Bubble-Chartreuse-icon.png',
            	    // This marker is 20 pixels wide by 32 pixels high.
            	    //size: new google.maps.Size(20, 32),
            	    // The origin for this image is (0, 0).
            	    origin: new google.maps.Point(0, 0),
            	    // The anchor for this image is the base of the flagpole at (0, 32).
            	    anchor: new google.maps.Point(0, 32)
            	  };
            addMarkerImg(myloc, map,image);

          }
        });
  $.ajax({
  			url : '/Distributed-Library/getAllRegionsAJAX',
  			data : {
  				city : $("#city").val()
  			},
  			error : function() {
  				$('#info').html('<p>An error has occurred</p>');
  			},
  			dataType : 'json',
  			success : function(data) {
  				 var loc = [];
  				for(var i=0;i<data.length;i++){
  					console.log(data[i].longitude);
  					console.log(data[i].id);
  					console.log(data[i].latitude);
  					console.log(data[i].name);
  			        loc.push({  lat: data[i].longitude,lng: data[i].latitude});
  			        addMarker(loc[i], map,data[i].name,data[i].id);
  			      

  				}
  				map.markers = markers;
			     // alert(map.markers.length);
			      for(var i=0;i<map.markers.length; i++ ) {
			      //alert(getDistance(myloc,markers[i].position));
			      }
  			}
  			});

      
       
      // Adds a marker to the map.
      function addMarker(location, map,name,id) {
        // Add the marker at the clicked location, and add the next-available label
        // from the array of alphabetical characters.
        var name = name;
        var id = id;
        var marker = new google.maps.Marker({
          position: location,
          label: labels[labelIndex++ % labels.length],
          map: map
        });
        var infowindow = new google.maps.InfoWindow({
            content: "Selected"
          });
        marker.addListener('click', function() {
        	console.log(name);
				$("#selectedRegion").val(name);
				$("#selectedRegionId").val(id);

				
        });
        markers.push(marker);

      }
      function addMarkerImg(location, map,image) {
          // Add the marker at the clicked location, and add the next-available label
          // from the array of alphabetical characters.
          var marker = new google.maps.Marker({
            position: location,
            label: labels[labelIndex++ % labels.length],
            map: map,
            icon: image
          });
          
         // markers.push(marker);

        }
      
//         // This event listener calls addMarker() when the map is clicked.
//         google.maps.event.addListener(map, 'click', function (event){
//           var lat = event.latLng.lat();
//           var lng = event.latLng.lng();
//           var R = 6371; // radius of earth in km
//           var distances = [];
//           var closest = -1;
//           map.markers = markers;
//           for( i=0;i<map.markers.length; i++ ) {
//               var mlat = map.markers[i].position.lat();
//               var mlng = map.markers[i].position.lng();
//               var dLat  = rad(mlat - lat);
//               var dLong = rad(mlng - lng);
//               var a = Math.sin(dLat/2) * Math.sin(dLat/2) +
//                   Math.cos(rad(lat)) * Math.cos(rad(lat)) * Math.sin(dLong/2) * Math.sin(dLong/2);
//               var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
//               var d = R * c;
//               distances[i] = d;
//               if ( closest == -1 || d < distances[closest] ) {
//                   closest = i;
//               }
//           }
//           alert(map.markers[closest].label);
//         });
        function rad(x) {return x*Math.PI/180;}

        // Add a marker at the center of the map.
        
        google.maps.event.addDomListener(document.getElementById('regBtn'), 'click', initialize);
//         google.maps.event.addListenerOnce(map, 'idle', function() {
//         	   google.maps.event.trigger(map, 'resize');
//         	});

  }
      
      var rad = function(x) {
    	  return x * Math.PI / 180;
    	};

    	
    	var getDistance = function(p1, p2) {
    		console.log(p1);
    		console.log(p2);
    	  var R = 6378137; // Earths mean radius in meter
    	  var dLat = rad(p2.lat() - p1.lat);
    	  var dLong = rad(p2.lng() - p1.lng);
    	  var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    	    Math.cos(rad(p1.lat)) * Math.cos(rad(p2.lat())) *
    	    Math.sin(dLong / 2) * Math.sin(dLong / 2);
    	  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    	  var d = R * c;
    	  return d/1000; // returns the distance in meter
    	};
    	
      function resize(){
    	  $("#selectedRegionId").val("");
    	  $("#selectedRegionId").val("");
    		 $("#map-div").hide(1000);
    	  $.ajax({
    			url : '/Distributed-Library/validateAddressAJAX',
    			data : {
    				 	street1:$("#street").val(),
    	  				street2:$("#aptNo").val(),
						city:	$("#city").val(),
						state: 	$("#state").val(),
						country:$("#country").val(),
						zipcode:$("#zip").val()
    			},
    			error : function(e) {
    				console.log(e);
    			},
    			success : function(data) {
    			

    					if(data == 'Y'){
    						 $("#map-div").show(1000);
    	    				 $('#map-div div').css('width','250px');
    	    				 $('#map-div div').css('heigth','250px');
    				         var geocoder = new google.maps.Geocoder();

    						 var address = $("#street").val()+" "+$("#aptNo").val()+" "+$("#city").val()+" "+$("#state").val()+" "+$("#zipcode").val();
    					  		var myloc1;
    					          geocoder.geocode( { 'address': address}, function(results, status) {
    					        	  var latitude = results[0].geometry.location.lat();
    					              var longitude = results[0].geometry.location.lng();
    					        	  myloc = {lat: latitude,lng: longitude};
    					        	  initialize(myloc);
    					          });
    						  
    					}
    					else{
    						alert("Please enter the valid address");
    					}
    				}
    			
    			
    			});
    	
//window.fireEvent('resize');
      }
     
      //window.addEvent();
      
      
      
    </script>
