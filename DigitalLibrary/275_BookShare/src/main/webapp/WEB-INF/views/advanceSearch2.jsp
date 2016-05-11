<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
.dropdown.dropdown-lg .dropdown-menu {
	margin-top: -1px;
	padding: 6px 20px;
}

.input-group-btn .btn-group {
	display: flex !important;
}

.btn-group .btn {
	border-radius: 0;
	margin-left: -1px;
}

.btn-group .btn:last-child {
	border-top-right-radius: 4px;
	border-bottom-right-radius: 4px;
}

.btn-group .form-horizontal .btn[type="submit"] {
	border-top-left-radius: 4px;
	border-bottom-left-radius: 4px;
}

.form-horizontal .form-group {
	margin-left: 0;
	margin-right: 0;
}

.form-group .form-control:last-child {
	border-top-left-radius: 4px;
	border-bottom-left-radius: 4px;
}

@media screen and (min-width: 768px) {
	#adv-search {
		width: 500px;
		margin: 0 auto;
	}
	.dropdown.dropdown-lg {
		position: static !important;
	}
	.dropdown.dropdown-lg .dropdown-menu {
		min-width: 500px;
	}
}
</style>


<jsp:include page="headerClean.jsp" />
<div>&nbsp;</div>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<div class="container">
	<div class="row">
		<div class="col-md-12">
			<div class="input-group" id="adv-search">
				<input type="text" class="form-control"
					placeholder="Search for Books" id="title" />
				<div class="input-group-btn">
					<div class="btn-group" role="group">
						<div class="dropdown dropdown-lg">
							<button type="button" class="btn btn-default dropdown-toggle"
								style="height: 34px" data-toggle="dropdown"
								aria-expanded="false">
								<span class="caret"></span>
							</button>
							<div class="dropdown-menu dropdown-menu-right" role="menu">
								<form:form id="advanceSearch" class="form-horizontal"
									commandName="advanceSearchDetails"
									modelAttribute="advanceSearchDetails">

									<div class="form-group">
										<label for="contain">Author</label> <input
											class="form-control" type="text" id="author" style="width: 100%" />
									</div>


									<div class="form-group">
										<label for="contain">Publisher</label> <input
											class="form-control" type="text" id="publisher" style="width: 100%" />
									</div>

									<div class="form-group">
										<label for="contain">Description</label> <input
											class="form-control" type="text" id="description" style="width: 100%" />
									</div>


									<%-- 				<c:forEach items="${advanceSearchDetails.getCatie()}" var="result" varStatus="status"> --%>
									<%-- 					<form:checkbox style="margin-left:35px" path="catie" value="result.getSelected()}" name="result[${status.index}].getCat().getName()" id="result[${status.index}].getCat().getName()" ></form:checkbox> --%>
									<%-- 					<label> ${result.getCat().getName()}</label> --%>
									<%-- 				</c:forEach> --%>



									<div>
										<button style="margin-left: 150px" id="submit" name="submit"
											class="btn btn-primary" type="button" value="Go"
											onclick="advSearch();">Submit</button>
										<div>
											<font color="red"><form:errors /></font>
										</div>
									</div>

									<label></label>

								</form:form>
							</div>
						</div>
						<button type="button" class="btn btn-primary" style="height: 34px" onclick="advSearch();">
							<span class="glyphicon glyphicon-search" style="top: -3px;"
								aria-hidden="true" ></span>
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<div>
	<div class="row">
		


		<div class="module inner-top-50 wow fadeInUp" id="best-seller">
			<div class="module-heading  home-page-module-heading">
				<h2 class="module-title home-page-module-title">
					<span>Best Sellers</span>
				</h2>
			</div>
			<!-- /.module-heading -->
			<div class="module-body">
				<div class="books full-width">
					<div class="clearfix text-center" id="searchResults">
						<!-- .best month -->
						
						

					

						

						

						<!-- /.best month -->
					</div>
					<!-- /.text-center -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.module-body -->
		</div>
		<!-- /.module -->



	</div>
	<!-- /.row -->
</div>

<script>
	function advSearch() {
		var title = $("#title").val();
		var author = $("#author").val();
		var publisher = $("#publisher").val();
		var description = $("#description").val();
		

		$.ajax({
			url : '/Distributed-Library/advanceSearchAJAX',
			data : {
				title : title,
				author : author,
				publisher : publisher,
				description : description,
				category : ""
			},
			error : function() {
				$('#info').html('<p>An error has occurred</p>');
			},
			dataType : 'json',
			success : function(data) {
				//console.log(data);
				var myvar ="";
				for(var i=0;i<data.length;i++){
					console.log(data[i]);
				
					 myvar += '<div class=\'col-md-3 col-sm-4\'>'+
					'				<div class=\'book\'>'+
					'					<a href=\'/Distributed-Library/showbook/'+data[i].bookId+'\'><div class=\'book-cover\'>'+
					'							<img width=\'140\' height=\'212\' alt=\'\''+
					'								src="'+data[i].image+'"'+
					'								data-echo=\'assets/images/book-covers/01.jpg\'>'+
					'						</div></a>'+
					'					<div class=\'book-details clearfix\'>'+
					'						<div class=\'book-description\'>'+
					'							<h3 class=\'book-title\'>'+
					'								<a href=\'single-book.html\'>'+data[i].title+'</a>'+
					'							</h3>'+
					'							<p class=\'book-subtitle\'>'+
					'								by <a href=\'single-book.html\'>'+data[i].publisher+'</a>'+
					'							</p>'+
					'						</div>'+
					'						<div class=\'actions\'>'+
					'							<span class=\'book-price price\'>'+data[i].isbn+'</span>'+
					''+
					'							<div class=\'cart-action\'>'+
					'								<a class=\'add-to-cart\' title=\'Add to Cart\''+
					'									href=\'javascript:void(0);\'>Add to Cart</a>'+
					''+
					'							</div>'+
					'						</div>'+
					'					</div>'+
					'				</div>'+
					'			</div>';
						
					
				}
					
						
				//$("#searchResults").html(myvar);
				$("#searchResults").html(myvar);

			},
			type : 'GET'
		});
	}
</script>

<script>
	jQuery(document)
			.ready(
					function() {
						var options = {

							url : function(phrase) {
								return "/Distributed-Library/getSearchSuggetion?searchTerm='"
										+ $("#title").val() + "'";
							},

							getValue : function(element) {
								return element.name;
							},

							ajaxSettings : {
								dataType : "json",
							},

							requestDelay : 400
						};
						$("#title").easyAutocomplete(options);
					});
</script>

<script>
	jQuery(document)
			.ready(
					function() {
						var options = {

							url : function(phrase) {
								return "/Distributed-Library/getSearchSuggetion?searchTerm='"
										+ $("#author").val() + "'";
							},

							getValue : function(element) {
								return element.author;
							},

							ajaxSettings : {
								dataType : "json",
							},

							requestDelay : 400
						};
						$("#author").easyAutocomplete(options);
					});
</script>


<script>
	jQuery(document)
			.ready(
					function() {
						var options = {

							url : function(phrase) {
								return "/Distributed-Library/getSearchSuggetion?searchTerm='"
										+ $("#publisher").val() + "'";
							},

							getValue : function(element) {
								return element.publisher;
							},

							ajaxSettings : {
								dataType : "json",
							},

							requestDelay : 400
						};
						$("#publisher").easyAutocomplete(options);
						$('.form-group div').css('width','100%')

					});
</script>
<style>
.easy-autocomplete-container {
    left: 0;
    position: absolute;
    width: 100%;
    z-index: 200;
    MARGIN-TOP: 33px;
}
.form-group div{
width: 100%
}
</style>
