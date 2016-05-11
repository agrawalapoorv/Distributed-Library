<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>
<%@ page import="edu.sjsu.digitalLibrary.prj.models.MongoBook" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- <jsp:include page="imports.jsp" /> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>

<% MongoBook Book = (MongoBook)request.getAttribute("bookdetails"); 
%>
	<div id="wrapper">
		<div id="page-content-wrapper" class="st-pusher">
			<div class="st-pusher-after"></div>

			<!-- ============================================== HEADER ============================================== -->

			<jsp:include page="header.jsp" />
			<div id="ajaxSpinnerContainer">
				<img src="<c:url value="/resources/images/Preloader_2.gif" />"
					id="ajaxSpinnerImage" title="working..." />
			</div>
			<!-- ============================================== HEADER : END ============================================== -->
			<div class="home-page">
				<div class="content">

					<div class="home-slider outer-bottom-vs">
						<!-- ========================================== SECTION – HERO : START ========================================= -->
						<div id="hero">
							<div id="owl-main" class="owl-carousel owl-theme">
								<div class="item">
									<div class="container">
										<div class="content">
											<div class="row">
												<div class="col-md-7 col-sm-12 col-xs-12">
													<div class="book-in-shelf">
														<div class="book-shelf">
															<div class="book-cover slider-book-cover bk-cover m-t-20">
																<img class="img-responsive" alt=""
																	src="<c:url value="/resources/images/slider-images/01.png" />">
																<div class="fade"></div>
															</div>
															<!-- /.book-cover -->
														</div>
														<!-- /.book-shelf -->
													</div>
													<!-- /.book-in-shelf -->
												</div>
												<!-- /.col -->

												<div class="col-md-5 col-sm-12 col-xs-12">
													<div class="clearfix caption vertical-center text-left">
														<div class="slider-caption-heading">
															<h1 class="slider-title">
																<span class="fadeInDown-1 main">Hezy Theme:</span> <span
																	class="fadeInDown-2 sub">What We Can All Do to
																	Save America's Future</span>
															</h1>
														</div>
														<!-- /.slider-caption-heading -->
														<div class="clearfix slider-button hidden-xs fadeInDown-3">
															<a class="btn btn-primary btn-uppercase" role="button"
																href="#">view more</a>
														</div>
														<!-- /.slider-price -->
													</div>
													<!-- /.slider-caption -->
												</div>
												<!-- /.col -->


											</div>
											<!-- /.row -->

										</div>
										<!-- /.content.caption -->
									</div>
									<!-- /.container -->
								</div>
								<!-- /.item -->

								<div class="item">
									<div class="container">
										<div class="content">
											<div class="row">
												<div class="col-md-7 col-sm-12 col-xs-12">
													<div class="book-in-shelf">
														<div class="book-shelf">
															<div class="book-cover slider-book-cover bk-cover m-t-20">
																<img class="img-responsive" alt=""
																	src="<c:url value="/resources/images/blank.gif" />">
																<div class="fade"></div>
															</div>
															<!-- /.book-cover -->
														</div>
														<!-- /.book-shelf -->
													</div>
													<!-- /.book-in-shelf -->
												</div>
												<!-- /.col -->

												<div class="col-md-5 col-sm-12 col-xs-12">
													<div class="clearfix caption vertical-center text-left">
														<div class="slider-caption-heading">
															<h1 class="slider-title">
																<span class="fadeInDown-1 main">Hezy Theme:</span> <span
																	class="fadeInDown-2 sub">What We Can All Do to
																	Save America's Future</span>
															</h1>
														</div>
														<!-- /.slider-caption-heading -->
														<div class="clearfix slider-button hidden-xs fadeInDown-3">
															<a class="btn btn-primary btn-uppercase" role="button"
																href="#">view more</a>
														</div>
														<!-- /.slider-price -->
													</div>
													<!-- /.slider-caption -->
												</div>
												<!-- /.col -->


											</div>
											<!-- /.row -->

										</div>
										<!-- /.content.caption -->
									</div>
									<!-- /.container -->
								</div>
								<!-- /.item -->


								<div class="item">
									<div class="container">
										<div class="content">
											<div class="row">
												<div class="col-md-7 col-sm-12 col-xs-12">
													<div class="book-in-shelf">
														<div class="book-shelf">
															<div class="book-cover slider-book-cover bk-cover m-t-20">
																<img class="img-responsive" alt=""
																	src="<c:url value="/resources/images/blank.gif" />">
																<div class="fade"></div>
															</div>
															<!-- /.book-cover -->
														</div>
														<!-- /.book-shelf -->
													</div>
													<!-- /.book-in-shelf -->
												</div>
												<!-- /.col -->

												<div class="col-md-5 col-sm-12 col-xs-12">
													<div class="clearfix caption vertical-center text-left">
														<div class="slider-caption-heading">
															<h1 class="slider-title">
																<span class="fadeInDown-1 main">Hezy Theme:</span> <span
																	class="fadeInDown-2 sub">What We Can All Do to
																	Save America's Future</span>
															</h1>
														</div>
														<!-- /.slider-caption-heading -->
														<div class="clearfix slider-button hidden-xs fadeInDown-3">
															<a class="btn btn-primary btn-uppercase" role="button"
																href="#">view more</a>
														</div>
														<!-- /.slider-price -->
													</div>
													<!-- /.slider-caption -->
												</div>
												<!-- /.col -->


											</div>
											<!-- /.row -->

										</div>
										<!-- /.content.caption -->
									</div>
									<!-- /.container -->
								</div>
								<!-- /.item -->

							</div>
							<!-- /# owl-main -->
						</div>
						<!-- /#hero -->
						<!-- ========================================== SECTION – HERO : END ========================================= -->
					</div>
					<!-- /.home-slider -->

					<div class="container">
						<!-- ============================================== BANNERS ============================================== -->
						<div class="wide-banners wow fadeInUp">
							<div class="row">

								<div class="col-md-4 col-sm-6">
									<div class="wide-banner cnt-strip">
										<div class="strip strip-text">
											<div class="strip-inner text-center">
												<h2 class="title">Shop Sale</h2>
												<p class='subtitle'>The sale don't stop up to 75% off!</p>
											</div>
											<!-- /.strip-inner -->
										</div>
										<!-- /.strip -->
									</div>
									<!-- /.wide-banner -->
								</div>
								<!-- /.col -->

								<div class="col-md-4 hidden-sm">

									<div class="wide-banner cnt-strip">
										<div class="image">
											<img class="img-responsive"
												src="<c:url value="/resources/images/wide-banners/banner1.png" />"
												alt="">
										</div>
										<div class="strip on-strip strip-text">
											<div class="strip-inner text-center">
												<h2 class="title">Latest Products</h2>
												<p class='subtitle'>New Arrivals</p>
											</div>
											<!-- /.strip-inner -->
										</div>
										<!-- /.strip -->

									</div>
									<!-- /.wide-banner -->

								</div>
								<!-- /.col -->

								<div class="col-md-4 col-sm-6">
									<div class="wide-banner cnt-strip">
										<div class="strip strip-text">
											<div class="strip-inner text-center">
												<h2 class="title">Read the blog</h2>
												<p class='subtitle'>Latest hot news</p>
											</div>
											<!-- /.strip-inner -->
										</div>
										<!-- /.strip -->

									</div>
									<!-- /.wide-banner -->
								</div>
								<!-- /.col -->



							</div>
							<!-- /.row -->
						</div>
						<!-- /.wide-banners -->
						<!-- ============================================== BANNERS : END ============================================== -->
						<div class="divider inner-vs">
							<img class="img-responsive"
								src="<c:url value="/resources/images/shadow.png" />" alt="">
						</div>
						<!-- /.divider -->

						<!-- ============================================== Recommendation for user ============================================== -->
						
						<c:if test="${fn:length(recommendedForYou) gt 0}">
						<section class="best-seller wow fadeInUp">
						<div id="best-seller" class="module">
							<div class="module-heading home-page-module-heading">
								<h2 class="module-title home-page-module-title">
									<span>Trending Books</span>
								</h2>
							</div>
							<!-- /.module-heading -->
							<div class="module-body">
								<div class="row books full-width">
									<div class="clearfix text-center">
									
									
									<c:forEach items="${recommendedForYou}" var="recommendedBook">
								        <div class="col-md-3 col-sm-6">
											<div class="book">
												<a href="single-book.html">
													<div class="book-cover">
														<img width="140" height="212" alt=""
															src="${recommendedBook.getImage()}">
<!-- 														<div class="tag"> -->
<!-- 															<span>sale</span> -->
<!-- 														</div> -->
													</div>
												</a>
												<div class="book-details clearfix">
													<div class="book-description">
														<h3 class="book-title">
															<a href="${pageContext.request.contextPath}/showbook/${recommendedBook.getBookId()}">${recommendedBook.title}</a>
														</h3>
														<p class="book-subtitle">
															by <c:forEach var="authorValue" items="${recommendedBook.getAuthors()}">
																	<a href="#">${authorValue}</a><br>
															   </c:forEach>
														</p>
													</div>
						
												</div>
											</div>
										</div>
								    </c:forEach>

								</div>
							</div>
						</div>
						</section>
						</c:if>
						<!-- ============================================== BEST SELLER : END ============================================== -->
						
						
						<!-- ============================================== BEST SELLER (Trending books)============================================== -->
						
						<c:if test="${fn:length(recommCatBooks) gt 0}">
						<section class="best-seller wow fadeInUp">
						<div id="best-seller" class="module">
							<div class="module-heading home-page-module-heading">
								<h2 class="module-title home-page-module-title">
									<span>Inspired By ${sessionScope.USERNAME}'s shopping trends</span>
								</h2>
							</div>
							<!-- /.module-heading -->
							<div class="module-body">
								<div class="row books full-width">
									<div class="clearfix text-center">
									
									
									<c:forEach items="${recommCatBooks}" var="recommCatBooks">
								        <div class="col-md-3 col-sm-6">
											<div class="book">
												<a href="single-book.html">
													<div class="book-cover">
														<img width="140" height="212" alt=""
															src="${recommCatBooks.getImage()}"
															>
														<div class="tag">
															<span>sale</span>
														</div>
													</div>
												</a>
												<div class="book-details clearfix">
													<div class="book-description">
														<h3 class="book-title">
															<a href="${pageContext.request.contextPath}/showbook/${recommCatBooks.getBookId()}">${recommCatBooks.title}</a>
														</h3>
														<p class="book-subtitle">
															by <c:forEach var="authorValue" items="${recommCatBooks.getAuthors()}">
																	<a href="#">${authorValue}</a><br>
															   </c:forEach>
														</p>
													</div>
											
												</div>
											</div>
										</div>
								    </c:forEach>

								</div>
							</div>
						</div>
						</section>
						</c:if>
						<!-- ============================================== BEST SELLER : END ============================================== -->
					</div>
					<!-- /.container -->

					<!-- ============================================== TESTIMONIAL ============================================== -->
					<section
						class="customer-testimonial wow fadeInUp outer-bottom-xs light-bg">
					<div id="testimonial" class="module container inner-top-xs">
						<div class="module-heading home-page-module-heading">
							<h2 class="module-title home-page-module-title">
								<span>Customer testimonals</span>
							</h2>
						</div>
						<!-- /.module-heading -->
						<div class="module-body">
							<div class="row">
								<div class="col-md-10 center-block">
									<div class="row">
										<div class="col-md-6 col-sm-6">
											<div class="testimonial-block">
												<div class="inner-testimonial-block">
													<div class="blockquote">
														<div class="inner-left-xl">
															<blockquote>
																<p>Phasellus viverra nulla ut metus varius laoreet.
																	Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi
																	vel augue. Curabitur ullamcorper ultricies nisi. Nam
																	eget dui. Etiam rhoncus.</p>
															</blockquote>
														</div>
														<!-- /.inner-left-xl -->
													</div>
													<!-- /.blockquote -->
												</div>
												<!-- /.inner-testimonial-block -->
												<div class="row m-t-20">
													<div class="col-md-10 author-block center-block">
														<div class="row">
															<div class="col-md-4 col-xs-5">
																<div class='image'>
																	<img class="img-responsive"
																		src="<c:url value="/resources/images/testimonials/author1.png" />"
																		alt="">
																</div>
																<!-- /.image -->
															</div>
															<!-- /.col -->
															<div class="col-md-8 col-xs-7">
																<h5 class="author-name outer-top-xs">Philip Anthony
																	Hopkins</h5>
																<span>Coca-Cola</span>
															</div>
															<!-- /.col -->
														</div>
														<!-- /.row -->
													</div>
													<!-- /.col -->
												</div>
												<!-- /.row -->
											</div>
											<!-- /.testimonial-block -->
										</div>
										<!-- /.col -->

										<div class="col-md-6 col-sm-6">
											<div class="testimonial-block">
												<div class="inner-testimonial-block">
													<div class="blockquote">
														<div class="inner-left-xl">
															<blockquote>
																<p>Nam quam nunc, blandit vel, luctus pulvinar,
																	hendrerit id, lorem. Maecenas nec odio et ante
																	tincidunt tempus. Donec vitae sapien ut libero
																	venenatis faucibus.</p>
															</blockquote>
														</div>
														<!-- /.inner-left-xl -->
													</div>
													<!-- /.blockquote -->
												</div>
												<!-- /.inner-testimonial-block -->
												<div class="row m-t-20">
													<div class="col-md-10 author-block center-block">
														<div class="row">
															<div class="col-md-4 col-xs-5">
																<div class='image'>
																	<img class="img-responsive"
																		src="<c:url value="/resources/images/testimonials/author2.png" />"
																		alt="">
																</div>
																<!-- /.image -->
															</div>
															<!-- /.col -->
															<div class="col-md-8 col-xs-7">
																<h5 class="author-name outer-top-xs">Steve Jobs</h5>
																<span>Coca-Cola</span>
															</div>
															<!-- /.col -->
														</div>
														<!-- /.row -->
													</div>
													<!-- /.col -->
												</div>
												<!-- /.row -->
											</div>
											<!-- /.testimonial-block -->

										</div>
										<!-- /.col -->
									</div>
									<!-- /.row -->
								</div>
								<!-- /.col -->

							</div>
							<!-- /.row -->
						</div>
						<!-- /.module-body -->
					</div>
					<!-- /.module --> </section>
					<!-- ============================================== TESTIMONIAL : END ============================================== -->

					<section class="latest-product wow fadeInUp">
					<div id="latest-product" class="module container inner-top-xs">
						<div
							class="module-heading home-page-module-heading inner-bottom-vs">
							<h2 class="module-title home-page-module-title">
								<span>Latest products</span>
							</h2>
						</div>
						<div class="module-body">
							<!-- ============================================== LATEST PRODUCT ============================================== -->

							<div class="book-shelf inner-bottom glass-shelf">
								<div class="row">
									<div class="col-md-10 col-sm-10 center-block clearfix">
										<div class="col-md-3 col-sm-4">
											<div class="book-cover bk-cover product-book-cover">
												<img class="img-responsive" alt=""
													src="<c:url value="/resources/images/blank.gif" />"
													width="182" height="273">
												<div class="fade"></div>
											</div>
											<!-- /.book-cover -->
										</div>
										<!-- /.col -->
										<div class="col-md-3 col-sm-4 hidden-xs">
											<div class="book-cover bk-cover product-book-cover">
												<img class="img-responsive" alt=""
													src="<c:url value="/resources/images/blank.gif" />"
													width="182" height="273">
												<div class="fade"></div>
											</div>
											<!-- /.book-cover -->
										</div>
										<!-- /.col -->
										<div class="col-md-3 col-sm-4 hidden-xs">
											<div class="book-cover bk-cover product-book-cover">
												<img class="img-responsive" alt=""
													src="<c:url value="/resources/images/blank.gif" />"
													width="182" height="273">
												<div class="fade"></div>
											</div>
											<!-- /.book-cover -->
										</div>
										<!-- /.col -->
										<div class="col-md-3 hidden-sm hidden-xs">
											<div class="book-cover bk-cover product-book-cover">
												<img class="img-responsive" alt=""
													src="<c:url value="/resources/images/blank.gif" />"
													width="182" height="273">
												<div class="fade"></div>
											</div>
											<!-- /.book-cover -->
										</div>
										<!-- /.col -->
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-md-10 center-block marketing-block">
									<h2 class="text-center">
										<span>We Empower WordPress Developers With
											Design-Driven</span> <span>Themes And A Classy Experience
											Their Clients</span> <span> Will Just Love</span>
									</h2>

									<div class="divider inner-xs">
										<img class="img-responsive"
											src="<c:url value="/resources/images/shadow.png" />" alt="">
									</div>
									<!-- /.divider -->

									<div class="row wow fadeInUp features-block">
										<div class="col-xs-12 col-sm-6 feature-block">
											<div class="media inner-bottom-xs">
												<div class="media-body">
													<h4 class="media-heading">Post-ironic sweater</h4>
													<p>Vinyl carles 3 wolf moon put a bird on it post
														ironic authentic single-origin coffee quinoa chillwave.
														Irony fingerstache thundercats.</p>
													<a href="#" class="find-more">Find out more &rarr;</a>
												</div>
												<div class="media-right media-middle icon-media">
													<div class="icon-block">
														<span class="fa-stack fa-lg"> <i
															class="fa fa-circle fa-stack-2x"></i> <i
															class="fa fa-eye fa-stack-1x fa-inverse text-center"></i>
														</span>
													</div>
													<!-- /.icon-block -->
												</div>
												<!-- /.media-right -->
											</div>
											<!-- /.media -->
											<hr />
										</div>
										<!-- /.feature-block -->

										<div class="col-xs-12 col-sm-6 feature-block">
											<div class="media inner-bottom-xs">
												<div class="media-body">
													<h4 class="media-heading">Lorem ipsum</h4>
													<p>Maecenas tempus, tellus eget condimentum rhoncus,
														sem quam semper libero, sit amet adipiscing sem neque sed
														ipsum.</p>
													<a href="#" class="find-more">Find out more &rarr;</a>
												</div>
												<div class="media-right media-middle icon-media">
													<div class="icon-block">
														<span class="fa-stack fa-lg"> <i
															class="fa fa-circle fa-stack-2x"></i> <i
															class="fa fa-mobile fa-stack-1x fa-inverse text-center"></i>
														</span>
													</div>
													<!-- /.icon-block -->
												</div>
												<!-- /.media-right -->
											</div>
											<!-- /.media -->
											<hr />
										</div>
										<!-- /.feature-block -->

										<div class="col-xs-12 col-sm-6 feature-block">
											<div class="media inner-top-xs inner-bottom-xs">
												<div class="media-body">
													<h4 class="media-heading">Brunch vegan 8-bit</h4>
													<p>Cras dapibus. Vivamus elementum semper nisi. Aenean
														vulputate eleifend tellus. Aenean leo ligula, porttitor
														eu, consequat vitae, eleifend ac.</p>
													<a href="#" class="find-more">Find out more &rarr;</a>
												</div>
												<div class="media-right media-middle icon-media">
													<div class="icon-block">
														<span class="fa-stack fa-lg"> <i
															class="fa fa-circle fa-stack-2x"></i> <i
															class="fa fa-lightbulb-o fa-stack-1x fa-inverse text-center"></i>
														</span>
													</div>
													<!-- /.icon-block -->
												</div>
												<!-- /.media-right -->
											</div>
											<!-- /.media -->
											<hr class="visible-xs" />
										</div>
										<!-- /.feature-block -->


										<div class="col-xs-12 col-sm-6 feature-block">
											<div class="media inner-top-xs inner-bottom-xs">
												<div class="media-body">
													<h4 class="media-heading">Donec pede justo</h4>
													<p>Cum sociis natoque penatibus et magnis dis
														parturient montes, nascetur ridiculus mus.</p>
													<a href="#" class="find-more">Find out more &rarr;</a>
												</div>
												<div class="media-right media-middle icon-media">
													<div class="icon-block">
														<span class="fa-stack fa-lg"> <i
															class="fa fa-circle fa-stack-2x"></i> <i
															class="fa fa-sliders fa-stack-1x fa-inverse text-center"></i>
														</span>
													</div>
													<!-- /.icon-block -->
												</div>
												<!-- /.media-right -->
											</div>
											<!-- /.media -->
											<hr class="visible-xs" />
										</div>
										<!-- /.feature-block -->
									</div>
									<!-- /.features-block -->
								</div>
							</div>

							<!-- ============================================== LATEST PRODUCT : END ============================================== -->
							<!-- ============================================== IMAGE BLOCK ============================================== -->
							<div class="image-block wow fadeInUp inner-top-sm">
								<div class='row'>
									<div class="col-md-4 col-sm-6">
										<div class="banners">
											<div class="banner green-banner">
												<div class='image'>
													<img class="img-responsive"
														src="<c:url value="/resources/images/blank.gif" />"
														alt="">
												</div>
												<!-- /.image -->
												<div class='caption'>
													<h2 class='title'>sale</h2>
													<hr>
													<p>The sale don't stop up to 75% off!</p>
												</div>
												<!-- /.caption -->
											</div>
											<!-- /.banner -->
										</div>
										<!-- /.banners -->
									</div>
									<!-- /.col -->

									<div class="col-md-4 col-sm-6 hidden-xs">
										<div class="banners">
											<div class="banner black-banner">
												<div class='image'>
													<img class="img-responsive"
														src="<c:url value="/resources/images/blank.gif" />"
														alt="">
												</div>
												<!-- /.image -->
												<div class='caption'>
													<h2 class='title'>Games</h2>
													<hr>
													<p>The sale don't stop up to 75% off!</p>
												</div>
												<!-- /.caption -->
											</div>
											<!-- /.banner -->
										</div>
										<!-- /.banners -->
									</div>
									<!-- /.col -->

									<div class="col-md-4 hidden-xs hidden-sm">
										<div class="banners">
											<div class="banner orange-banner">
												<div class='image'>
													<img class="img-responsive"
														src="<c:url value="/resources/images/blank.gif" />"
														 alt="">
												</div>
												<!-- /.image -->
												<div class='caption'>
													<h2 class='title'>lookbook</h2>
													<hr>
													<p>Take a look at the upcoming trends</p>
												</div>
												<!-- /.caption -->
											</div>
											<!-- /.banner -->
										</div>
										<!-- /.banners -->
									</div>
									<!-- /.col -->
								</div>
								<!-- /.row -->
							</div>
							<!-- /.image-block -->
							<!-- ============================================== IMAGE BLOCK : END ============================================== -->
						</div>
					</div>
					</section>
	<div id="map">
								</div>
					<!-- ============================================== FROM BLOG ============================================== -->
					<section class="blog wow fadeInUp">
					<div id="blog" class="module container">
						<div class="module-heading home-page-module-heading">
							<h2 class="module-title home-page-module-title">
								<span>From the blog</span>
							</h2>
						</div>
						<!-- /.module-heading -->
						<div class="module-body inner-top-50">
							<div class="row">
								<div class="col-md-6 col-sm-6">
									<div class="row">
										<div class="col-md-5 col-sm-5 blog-image">
											<a href="#"><img
												src="<c:url value="/resources/images/blank.gif" />"
												 alt=""
												class="blog1" width="199" height="177"></a>
										</div>
										<div class="col-md-7 col-sm-7 blog-info">
											<h4 class="blog-heading">
												<a href="#">Cras dapibus. Vivamus elementum semper nisi.
													Aenean vulputate eleifend</a>
											</h4>
											<p class="comment-count">12 comments</p>
											<p class="text">Phasellus viverra nulla ut metus varius
												laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies
												nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam
												eget dui. Etiam rhoncus.</p>
										</div>
										<!-- /.col -->
									</div>
									<!-- /.row -->

								</div>
								<!-- /.col -->

								<div class="col-md-6 col-sm-6">
									<div class="row">
										<div class="col-md-5 col-sm-5 blog-image">
											<a href="#"><img
												src="<c:url value="/resources/images/blank.gif" />"
												 alt=""
												class="blog2" width="199" height="177"></a>
										</div>
										<div class="col-md-7 col-sm-7 blog-info">
											<h4 class="blog-heading">
												<a href="#">Cras dapibus. Vivamus elementum semper nisi.
													Aenean vulputate eleifend</a>
											</h4>
											<p class="comment-count">12 comments</p>
											<p class="text">Aenean imperdiet. Etiam ultricies nisi
												vel augue. Curabitur ullamcorper ultricies nisi. Nam eget
												dui. Etiam rhoncus.</p>
										</div>
										<!-- /.col -->
									</div>
									<!-- /.row -->

								</div>
								<!-- /.col -->


							</div>
							<!-- /.row -->
						</div>
						<!-- /.module-body -->
					</div>
					<!-- /.module --> </section>
					<!-- ============================================== FROM BLOG : END ============================================== -->
				</div>
				<!-- /.content -->
			</div>
			<!-- /.home-page -->


			<!-- ============================================== FOOTER ============================================== -->

			<jsp:include page="footer.jsp" />

			<!-- ============================================== FOOTER : END ============================================== -->

		</div>
		<!-- /.st-pusher -->
		<!-- ============================================== TOGGLE RIGHT CONTENT ============================================== -->

		<jsp:include page="toggleCart.jsp" />

		<!-- ============================================== TOGGLE RIGHT CONTENT : END ============================================== -->



	</div>
	<!-- /#wrapper -->



	<%-- <div id="myCarousel" class="carousel slide" data-ride="carousel" data-interval="2000">
	      <!-- Indicators -->
	      <ol class="carousel-indicators">
	        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
	        <li data-target="#myCarousel" data-slide-to="1"></li>
	        <li data-target="#myCarousel" data-slide-to="2"></li>
	      </ol>
	      
	      <!-- Wrapper for slides -->
	      <div class="carousel-inner" role="listbox">
	        <div class="item active">
	          <img src="${pagedetails.books[1].pictureId}" alt="First slide" width="460" height="345">
	          <div class="container">
	            <div class="carousel-caption">
	              <p><a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/showbook/${pagedetails.books[1].bookId}" role="button">Buy Now</a></p>
	            </div>
	          </div>
	        </div>
	        
	        <div class="item">
	          <img class="second-slide" src="${pagedetails.books[2].pictureId}" alt="Second slide">
	          <div class="container">
	            <div class="carousel-caption">
	              <p><a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/showbook/${pagedetails.books[2].bookId}" role="button">Buy Now</a></p>
	            </div>
	          </div>
	        </div>
	
	        <div class="item">
	          <img class="third-slide" src="${pagedetails.books[3].pictureId}" alt="Third slide">
	          <div class="container">
	            <div class="carousel-caption">
					<p><a class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/showbook/${pagedetails.books[3].bookId}" role="button">Buy Now</a></p>
	            </div>
	          </div>
	        </div>
	      </div>
	
	      <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
	        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
	        <span class="sr-only">Previous</span>
	      </a>
	
	      <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
	        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
	        <span class="sr-only">Next</span>
	      </a>
	    </div><!-- /.carousel -->

	<div class="container-fluid" style="margin-top:15px">  
		<div class="row-fluid">
			<div class="container-fluid">
			<c:forEach items="${pagedetails.books}" var="result">
				<c:if test="${result.getStatus() != 'Sold'}">
					<div class="col-md-2">
						<a href="${pageContext.request.contextPath}/showbook/${result.bookId}">
							<img class="img-thumbnail" src="${result.pictureId}" width="300" height="200">
						</a>
						<h4>
					    	<a href="#">${result.title}</a>
						</h4>
						<p>Price: $${result.getPrice()}</p>
					</div>
				</c:if>
			</c:forEach>
			</div>
		</div>
	</div> --%>
</body>


</html>


