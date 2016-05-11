<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="edu.sjsu.digitalLibrary.prj.models.MongoBook" %>
<jsp:include page="header.jsp" />

<body>
<% MongoBook Book = (MongoBook)request.getAttribute("bookdetails"); 
%>
<div class="content wow fadeInUp">
	<div class="container">
		<div class="row">
			<ul class="breadcrumb text-center">
				<li><a href="home.html">Home</a></li>
				<li><a href="books.html">Books</a></li>
				<li class="active">${bookdetails.singleCategory}</li>				
			</ul>

			<div class="divider">
			      <img class="img-responsive" src="<c:url value="assets/images/shadow.png" />" alt="">
			</div><!-- /.divider -->
		</div>
		<div class="row inner-top-xs single-book-block">
			<div class="col-md-9 col-md-push-3">
				<!-- .primary block -->
				<div class="single-book primary-block">
	<div class="row">
		<div class="col-md-5 col-sm-5">
			<div class="book-cover">
				<img width="268" height="408" alt="" src="${bookdetails.getImage()}">
			</div><!-- /.book-cover -->
			<div class="share-button">
				
				<!-- Go to www.addthis.com/dashboard to customize your tools -->
                <div class="addthis_native_toolbox inner-top-vs"></div>
			</div>
			<div class="list-unstyled link-block inner-top-50">
				<a href="#customer-reviews" class='customer-review'><i class="icon fa fa-comment"></i><span class="customer-review">Customer Reviews(7) &darr; </span></a>
			</div>
		</div>
		<div class="col-md-7 col-sm-7">
			<div class="featured-book-heading">
				<h1 class="title">${bookdetails.title}</h1>
				<input type="hidden" id="bookId" value="${bookdetails.bookId}"></input>
				<input type="hidden" id="redirectTo" value="${redirectTo}"></input>
				<input type="hidden" id="redirectToBuy" value="${redirectToBuy}"></input>
				<input type="hidden" id="owner" value="${owner}"></input>
				<p class="singl-book-author">
					by<br>
					<c:forEach var="authorValue" items="${bookdetails.getAuthors()}">
							<a href="#">${authorValue}</a><br>
					</c:forEach>
					<%-- <ul>
						<c:forEach var="authorValue" items="${bookdetails.getAuthors()}">
								<a href="#">${authorValue}</a>
						</c:forEach>
					</ul> --%>
				</p>
			</div>

			<div class="row">
				<div class="col-md-3">
					<p class="single-book-price">$15.99</p>
				</div>
				<div class="col-md-9">
					<div class="add-cart-button btn-group">
						<!-- <button class="btn btn-single btn-sm" type="button" data-toggle="dropdown">
							<img src="assets/images/add-to-cart.png" alt="">
						</button>
						<button class="btn btn-single btn-uppercase" type="button">Add to cart</button> -->
						
						<% if(null != session.getAttribute("USERNAME")) {%> 
							<a class="btn btn-single btn-uppercase" onclick="return makeRequest()" role="button">Make a Request</a>
					    	<%-- <td colspan="2" align="right"><a class="btn btn-primary" href="${pageContext.request.contextPath}/requestbook/${bookdetails.bookId}" role="button">Make a request</a> --%>
					    <% } else {%>					   
							<a class="btn btn-single" href="#" data-toggle="modal" data-target="#modal-login-big" role="button">Login to make a request</a>
					    <% }%>
						
						
					</div>
				</div>
			</div>


			<div class="description"><p>${bookdetails.description}</p></div>
				</div>
			</div>
		</div>			    <!-- /.primary block -->

				<div class="divider inner-top-xs">
                    <img src="assets/images/shadow.png" class="img-responsive" alt=""/>
				</div>

				 <div class="module wow fadeInUp">
				    <div class="module-heading home-page-module-heading">
				        <h2 class="module-title home-page-module-title"><span>Related Products</span></h2>
				        <p class="see-all-link"><a href="#">See All</a> &rarr;</p>
				    </div>
				    <div class="module-body">
					    <div class="row text-center">
					     <!-- .related product -->
					     <div class="col-md-3 col-sm-4">
	<div class="book">
		<a href="single-book.html"><div class="book-cover">
			<img width="140" height="212" alt="" src="assets/images/blank.gif" data-echo="assets/images/book-covers/01.jpg">
		</div></a>
		<div class="book-details clearfix">
			<div class="book-description">
				<h3 class="book-title"><a href="single-book.html">The Brief Wondrous Life of  Oscar Wao</a></h3>
				<p class="book-subtitle">by <a href="single-book.html">Cormac McCarthy</a></p>
			</div>
			<div class="actions">
				<span class="book-price price">$14.75</span>

				<div class="cart-action"> 
					<a class="add-to-cart" title="Add to Cart" href="javascript:void(0);">Add to Cart</a>       

				</div>
			</div>
		</div>
	</div>
</div>

<div class="col-md-3 col-sm-4">
	<div class="book">
		<a href="single-book.html"><div class="book-cover">
			<img width="140" height="212" alt="" src="assets/images/blank.gif" data-echo="assets/images/book-covers/02.jpg">
		</div></a>
		<div class="book-details clearfix">
			<div class="book-description">
				<h3 class="book-title"><a href="single-book.html">Doctor Sleep </a></h3>
				<p class="book-subtitle">by <a href="single-book.html">Stephen King</a></p>
			</div>
			<div class="actions">
				<span class="book-price price">$14.45</span>
				<div class="cart-action"> 
					<a class="add-to-cart" title="Add to Cart" href="javascript:void(0);">Add to Cart</a>       

				</div>
			</div>
		</div>
	</div>
</div>

<div class="col-md-3 col-sm-4">
	<div class="book">
		<a href="single-book.html"><div class="book-cover">
			<img width="140" height="212" alt="" src="assets/images/blank.gif" data-echo="assets/images/book-covers/03.jpg">
		</div></a>
		<div class="book-details clearfix">
			<div class="book-description">
				<h3 class="book-title"><a href="single-book.html">Wonder Hardcover</a></h3>
				<p class="book-subtitle">by <a href="single-book.html">R. J. Palacio</a></p>
			</div>
			<div class="actions">
				<span class="book-price price">$9.59</span>
				<div class="cart-action"> 
					<a class="add-to-cart" title="Add to Cart" href="javascript:void(0);">Add to Cart</a>       

				</div>
			</div>
		</div>
	</div>
</div>

<div class="col-md-3 col-sm-4">
	<div class="book">
		<a href="single-book.html"><div class="book-cover">
			<img width="140" height="212" alt="" src="assets/images/blank.gif" data-echo="assets/images/book-covers/04.jpg">
		</div></a>
		<div class="book-details clearfix">
			<div class="book-description">
				<h3 class="book-title"><a href="single-book.html">The Brief Wondrous  Life of Oscar</a></h3>
				<p class="book-subtitle">by <a href="single-book.html"> Hezy Theme</a></p>
			</div>
			<div class="actions">
				<span class="book-price price">$12.00</span>
				<div class="cart-action"> 
					<a class="add-to-cart" title="Add to Cart" href="javascript:void(0);">Add to Cart</a>       

				</div>
			</div>
		</div>
	</div>
</div>

<div class="col-md-3 col-sm-4">
	<div class="book">
		<a href="single-book.html"><div class="book-cover">
			<img width="140" height="212" alt="" src="assets/images/blank.gif" data-echo="assets/images/book-covers/05.jpg">
		</div></a>
		<div class="book-details clearfix">
			<div class="book-description">
				<h3 class="book-title"><a href="single-book.html">How Dogs Love Us: A  Neuroscientist and His Dog </a></h3>
				<p class="book-subtitle">by <a href="single-book.html">Gregory Berns </a></p>
			</div>
			<div class="actions">
				<span class="book-price price">$7.95</span>
				<div class="cart-action"> 
					<a class="add-to-cart" title="Add to Cart" href="javascript:void(0);">Add to Cart</a>       

				</div>
			</div>
		</div>
	</div>
</div>

<div class="col-md-3 col-sm-4">
	<div class="book">
		<a href="single-book.html"><div class="book-cover">
			<img width="140" height="212" alt="" src="assets/images/blank.gif" data-echo="assets/images/book-covers/10.jpg">
		</div></a>
		<div class="book-details clearfix">
			<div class="book-description">
				<h3 class="book-title"><a href="single-book.html">From Kant to Husserl</a></h3>
				<p class="book-subtitle">by <a href="single-book.html">Cormac McCarthy </a></p>
			</div>
			<div class="actions">
				<span class="book-price price">$101.00</span>
				<div class="cart-action"> 
					<a class="add-to-cart" title="Add to Cart" href="javascript:void(0);">Add to Cart</a>       

				</div>
			</div>
		</div>
	</div>
</div>

<div class="col-md-3 col-sm-4">
	<div class="book">
		<a href="single-book.html"><div class="book-cover">
			<img width="140" height="212" alt="" src="assets/images/blank.gif" data-echo="assets/images/book-covers/11.jpg">
		</div></a>
		<div class="book-details clearfix">
			<div class="book-description">
				<h3 class="book-title"><a href="single-book.html">Doctor Sleep</a></h3>
				<p class="book-subtitle">by <a href="single-book.html">Stephen King</a></p>
			</div>
			<div class="actions">
				<span class="book-price price">$19.45</span>
				<div class="cart-action"> 
					<a class="add-to-cart" title="Add to Cart" href="javascript:void(0);">Add to Cart</a>       

				</div>
			</div>
		</div>
	</div>
</div>

<div class="col-md-3 col-sm-4">
	<div class="book">
		<a href="single-book.html"><div class="book-cover">
			<img width="140" height="212" alt="" src="assets/images/blank.gif" data-echo="assets/images/book-covers/12.jpg">
		</div></a>
		<div class="book-details clearfix">
			<div class="book-description">
				<h3 class="book-title"><a href="single-book.html">Wonder Hardcover</a></h3>
				<p class="book-subtitle">by <a href="single-book.html">R.J.Palacio</a></p>
			</div>
			<div class="actions">
				<span class="book-price price">$7.11</span>
				<div class="cart-action"> 
					<a class="add-to-cart" title="Add to Cart" href="javascript:void(0);">Add to Cart</a>       

				</div>
			</div>
		</div>
	</div>
</div>					    <!-- /.related product -->
					    </div>
					</div>
				 </div>

				 <div class="divider inner-top-xs">
                    <img src="assets/images/shadow.png" class="img-responsive" alt="" />
				</div>

				<div class="module wow fadeInUp" id="customer-reviews">
				    <div class="module-heading home-page-module-heading margin-top-10">
				        <h2 class="module-title home-page-module-title "><span>Customer Reviews(3)</span></h2>
				        
				    </div>
				    <!-- .customer reviews -->
					    <div class="module-body inner-top-xs" id="reviews">
	<ul class="list-unstyled review-list">
		<li class="box effect">
			<section class="paper">
				<div class="tape"></div>
				<div class="head"></div>
				<div class="row review-content">
					<div class="col-md-2 col-sm-2">
						<div class="customer-image-text"><span>hezy</span></div>

					</div>
					<div class="col-md-10 col-sm-10">
						<h5 class="customer-name inner-right-xs">Hezy Theme</h5>
						<p class="posted-date">21 Jun,2015 3:34 pm</p>
						<p class="text">I have a jQuery diamond wipe animation plugin that works on much the same principles - just need to find the time to write it up and publish it.<br>
							Again, the result of a designer making crazy demands, but it's pleasantly surprising how much  we can do today, where once all you could say was "I'm sorry.
							, the web just doesn't work like that..."</p>
							<a href="#" class="reply-review">Reply</a> 
						</div>
					</div>
					<div class="bottom"></div>
				</section>
			</li>

			<li>
				<section class="paper">
					<div class="tape"></div>
					<div class="head"></div>
					<div class="row review-content">
						<div class="col-md-2 col-sm-2">
							<div class="customer-image-text"><img src="assets/images/customers/1.jpg" alt=""></div>

						</div>
						<div class="col-md-10 col-sm-10">
							<h5 class="customer-name inner-right-xs">Kris Morgan</h5>
							<p class="posted-date">21 Jun,2015 3:34 pm</p>
							<p class="text">I have a jQuery diamond wipe animation plugin that works on much the same principles - just need to find the time to write it up and publish it.
								Again, the result of a designer making crazy demands, but it's pleasantly surprising how much we can do today, where once all you could say was "I'm sorry.
								, the web just doesn't work like that..."</p>
								<a href="#" class="reply-review">Reply</a> 
							</div>
						</div>
						<div class="bottom"></div>

					</section>
				</li>


				<li>
					<section class="paper">
						<div class="tape"></div>
						<div class="head"></div>
						<div class="row review-content">
							<div class="col-md-2 col-sm-2">
								<div class="customer-image-text"><img src="assets/images/customers/2.jpg" alt=""></div>

							</div>
							<div class="col-md-10 col-sm-10">
								<h5 class="customer-name inner-right-xs">Arsen Cox</h5>
								<p class="posted-date">21 Jun,2015 3:34 pm</p>
								<p class="text">I have a jQuery diamond wipe animation plugin that works on much the same principles - just need to find the time to write it up and publish it.
									Again, the result of a designer making crazy demands, but it's pleasantly surprising how much we can do today, where once all you could say was "I'm sorry.
									, the web just doesn't work like that..."</p>
									<a href="#" class="reply-review">Reply</a> 
								</div>
							</div>
							<div class="bottom"></div>
						</section>
					</li>



				</ul>
			</div>					<!-- /.customer reviews -->
				</div>

			</div><!-- /.col -->
			<div class="col-md-3 col-md-pull-9">
				<aside class="sidebar">
					<div class="sidebar-module">
						<div class="sidebar-module-heading">
							<h4 class="sidebar-module-title">You are looking at</h4>
						</div>
						<div class="sidebar-module-body clearfix">
							<ul class="list-unstyled filter-list">						
								<li>
									<a class="pull-right remove-filter" title="Cancel" href="#">
									<i class="fa fa-times"></i>
									</a>
									${bookdetails.singleCategory}
								</li>
							</ul>
						</div>
					</div>
					
					<!-- ============================================== BOOKS BY SUBJECT ============================================== -->
<div class="sidebar-module">
	<div class="sidebar-module-heading">
		<h4 class="sidebar-module-title">Books by Subject</h4>
	</div>
	<div class="sidebar-module-body clearfix">
		<ul class="list-unstyled">
			<li><a href="single-book.html">Art, Architecture &amp; Photography</a></li>
			<li><a href="single-book.html">Bibles &amp; Bible Studies</a></li>
			<li><a href="single-book.html">Biographies</a></li>
			<li><a href="single-book.html">Business &amp; Money</a></li>
			<li><a href="single-book.html">Children's Books</a></li>
			<li><a href="single-book.html">Computing &amp; Internet</a></li>
			<li><a href="single-book.html">Cookbooks, Food &amp; Wine</a></li>
			<li><a href="single-book.html">Crafts &amp; Hobbies</a></li>
			<li><a href="single-book.html">Diet &amp; Health</a></li>
			<li><a href="single-book.html">Education &amp; Teaching</a></li>
			<li><a href="single-book.html">Fiction &amp; Literature</a></li>
			<li><a href="single-book.html">Graphic Novels</a></li>
			<li><a href="single-book.html">History</a></li>
			<li><a href="single-book.html">Home &amp; Garden</a></li>
			<li><a href="single-book.html">Humor</a></li>
			<li><a href="single-book.html">Libros en espa?ol</a></li>
			<li><a href="single-book.html">Medicine</a></li>
			<li><a href="single-book.html">Mystery &amp; Crime</a></li>
			<li><a href="single-book.html">Nonfiction</a></li>
			<li><a href="single-book.html">Politics &amp; Current Events</a></li>
			<li><a href="single-book.html">Psychology</a></li>
			<li><a href="single-book.html">Religion</a></li>
			<li><a href="single-book.html">Reference</a></li>
			<li><a href="single-book.html">Romance</a></li>
			<li><a href="single-book.html">Science &amp; Nature</a></li>
			<li><a href="single-book.html">Science Fiction &amp; Fantasy</a></li>
			<li><a href="single-book.html">Self-Improvement</a></li>
			<li><a href="single-book.html">Sports &amp; Adventure</a></li>

		</ul>
	</div>
</div>
<!-- ============================================== BOOKS BY SUBJECT : END ============================================== -->					
				   <!-- ============================================== BEST BOOKS ============================================== -->
<div class="sidebar-module">
	<div class="sidebar-module-heading">
		<h4 class="sidebar-module-title">Best Books</h4>
	</div>
	<div class="sidebar-module-body clearfix">
		<ul class="list-unstyled">
			<li><a href="single-book.html">Art, Architecture &amp; Photography</a></li>
			<li><a href="single-book.html">Bibles &amp; Bible Studies</a></li>
			<li><a href="single-book.html">Biographies</a></li>
			<li><a href="single-book.html">Business &amp; Money</a></li>
			<li><a href="single-book.html">Children's Books</a></li>
			<li><a href="single-book.html">Computing &amp; Internet</a></li>
			<li><a href="single-book.html">Cookbooks, Food &amp; Wine</a></li>
			<li><a href="single-book.html">Crafts &amp; Hobbies</a></li>
			<li><a href="single-book.html">Diet &amp; Health</a></li>
			<li><a href="single-book.html">Education &amp; Teaching</a></li>
			<li><a href="single-book.html">Fiction &amp; Literature</a></li>
			<li><a href="single-book.html">Graphic Novels</a></li>
			<li><a href="single-book.html">History</a></li>
			<li><a href="single-book.html">Home &amp; Garden</a></li>
			<li><a href="single-book.html">Humor</a></li>

		</ul>
	</div>
</div>
<!-- ============================================== BEST BOOKS : END ============================================== -->				</aside>
			</div><!-- /.col -->
		</div><!-- /.row -->
	</div><!-- /.container -->
</div><!-- /.content -->
</body>

<script>

	function makeRequest(){
		
		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/Distributed-Library/checkOrders",
			dataType : 'json',
			complete : function(data) {
				
				if (data.responseJSON.successFlag == "Y") {
					var ctx = "${pageContext.request.contextPath}";
					document.location.href=ctx+"/requestbook/${bookdetails.bookId}";
				
				} else if (data.responseJSON.successFlag == "N" || data.responseJSON.successFlag == "L") {
						
					alert(data.responseJSON.errorMessage);
					
				}
			}
		});
		
	}

</script>