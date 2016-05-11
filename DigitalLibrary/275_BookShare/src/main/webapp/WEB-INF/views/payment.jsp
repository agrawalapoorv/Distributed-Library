<%@    taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div class="container">
    <div class="col-sm-6">
        <fieldset>
        	
        	<form role="form" id="paymentForm" method="post" name="paymentForm" commandname="paymentDetails">
            <legend>Payment</legend>
            <div class="form-group">
                <label for="name" class="sr-only">Card Holder's Name</label>
                <div class="controls">
                    <input type="text" id="name" name="name" class="form-control" pattern="\w+ \w+.*" title="First and last name" Placeholder="Firstname Lastname" required="">
                </div>
            </div>
            <div class="form-group">
                <label for="cardNumber" class="sr-only">Card Number</label>
                <div class="controls">
                    
                    	<input type="text" id="cardNumber" name="cardNumber" class="form-control" autocomplete="off" pattern="\d{16}" Placeholder="16 digit Card number" required="">
        
                </div>
            </div>
            <div class="form-group">
                <label for="validTillMonth" class="sr-only">Card Expiry Date</label>
                <div class="controls">
                    <div class="row">
                        <div class="col-md-9">
                            <select class="form-control" name="validTillMonth" id="validTillMonth" name="validTillMonth">
                                <option value="01">January</option>
                                <option value="02">February</option>
                                <option value="03">March</option>
                                <option value="04">April</option>
                                <option value="05">May</option>
                                <option value="06">June</option>
                                <option value="07">July</option>
                                <option value="08">August</option>
                                <option value="09">September</option>
                                <option value="10">October</option>
                                <option value="11">November</option>
                                <option value="12">December</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <select class="form-control" name="validTillYear" id="validTillYear" name="validTillYear">
                                <option>2016</option>
                                <option>2017</option>
                                <option>2018</option>
                                <option>2019</option>
                                <option>2020</option>
                                <option>2021</option>
                                <option>2022</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="cvv" class="sr-only">Card CVV</label>
                <div class="controls">
                    <div class="row">
                        <div class="col-md-3">
                            <input type="text" class="form-control" id="cvv" name="cvv" autocomplete="off" maxlength="3" pattern="\d{3}" title="Three digits at back of your card" required="">
                        </div>
                        <div class="col-md-8"></div>
                    </div>
                </div>
            </div>
            <div class="form-group">
              
              <div class="controls">
                <button type="button" class="btn btn-primary" onclick="submitPayment()">Submit</button> 
                <button type="button" class="btn btn-default">Cancel</button>
              </div>
            </div>
            </form>
        </fieldset>
    </div>
</div>
<hr>

<script>

	function submitPayment(){
		document.getElementById('paymentForm').action = "${pageContext.request.contextPath}/payment";
		document.getElementById('paymentForm').submit();
	}

</script>
