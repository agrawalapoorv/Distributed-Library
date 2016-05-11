package edu.sjsu.digitalLibrary.prj.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import redis.clients.jedis.Jedis;

import com.fasterxml.jackson.annotation.JsonView;

import edu.sjsu.digitalLibrary.prj.dao.JPAAddressDAO;
import edu.sjsu.digitalLibrary.prj.dao.JPABookDAO;
import edu.sjsu.digitalLibrary.prj.dao.JPACategoryDAO;
import edu.sjsu.digitalLibrary.prj.dao.JPALandingPageDAO;
import edu.sjsu.digitalLibrary.prj.dao.JPARegionDAO;
import edu.sjsu.digitalLibrary.prj.dao.JPASearchDAO;
import edu.sjsu.digitalLibrary.prj.dao.JPAUserDAO;
import edu.sjsu.digitalLibrary.prj.jsonview.Views;
import edu.sjsu.digitalLibrary.prj.models.JsonResponse;
import edu.sjsu.digitalLibrary.prj.models.LandingPage;
import edu.sjsu.digitalLibrary.prj.models.LoginSamplee;
import edu.sjsu.digitalLibrary.prj.models.MongoBook;
import edu.sjsu.digitalLibrary.prj.models.Registration;
import edu.sjsu.digitalLibrary.prj.models.RegistrationJsonPojo;
import edu.sjsu.digitalLibrary.prj.models.address;
import edu.sjsu.digitalLibrary.prj.models.category;
import edu.sjsu.digitalLibrary.prj.models.internalCategory;
import edu.sjsu.digitalLibrary.prj.models.region;
import edu.sjsu.digitalLibrary.prj.models.searchSuggetion;
import edu.sjsu.digitalLibrary.prj.models.user;
import edu.sjsu.digitalLibrary.prj.utils.CheckSession;
import edu.sjsu.digitalLibrary.prj.utils.PlayPP;
import edu.sjsu.digitalLibrary.prjservices.SearchServiceImpl;

@SuppressWarnings("unused")
@Controller
public class FirstController {

	int passwordDiff = 0;
	// private internalCategory homepageModel;
	private user userModel;
	// private book bookModel;
	// private category categoryModel;
	private LandingPage landingPage;
	HttpSession session;
	private static Jedis jedis;

	@Autowired
	private SearchServiceImpl searchService;
	// hellod
	@Autowired
	private HttpSession httpSession;

	@Autowired
	private CheckSession sessionService;

	// 1.Creating the u.i for user sign up page
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView initN() {
		userModel = new user();
		return new ModelAndView("signup", "userdetails", userModel);

	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	@ResponseBody
	public List<category> getActiveCategories() {
		JPACategoryDAO obj = new JPACategoryDAO();
		List<category> categories = obj.getCategories();
		return categories;
	}

	private static final Random random = new Random();
	private static final String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ234567890";

	public static String getToken(int length) {
		StringBuilder token = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			token.append(CHARS.charAt(random.nextInt(CHARS.length())));
		}
		return token.toString();
	}

	@RequestMapping(value = "/showuser/{userId}", method = RequestMethod.GET)
	public ModelAndView showBook(@PathVariable int userId) {

		if (!sessionService.checkAuth()) {
			System.out.println("chk class wrked!");
			LoginSamplee login = new LoginSamplee();

			return new ModelAndView("login", "logindetails", login);

		}

		ModelAndView mv = new ModelAndView();
		userModel = new user();

		JPAUserDAO obj = new JPAUserDAO();
		userModel = obj.getUser(userId);

		mv.addObject("userdetails", userModel);
		mv.setViewName("showuser");

		return mv;
	}
	
	/// Apoorv for getting books to show when user is not logged in.
	
	
	
	
	
	
	

	@RequestMapping(value = "/signup/{userId}", method = RequestMethod.GET)
	public ModelAndView edituser(@PathVariable int userId, HttpServletRequest request) {

		ModelAndView mv = new ModelAndView();
		userModel = new user();

		JPAUserDAO obj = new JPAUserDAO();
		userModel = obj.getUser(userId);
		mv.addObject("path", "../editprofile");
		mv.addObject("userdetails", userModel);
		mv.setViewName("profileedit");

		return mv;
	}

	@JsonView(Views.Public.class)
	@ResponseBody
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public JsonResponse initN1(@RequestBody RegistrationJsonPojo registration) {

		try {

			Registration registrationModel = new Registration();

			registrationModel.setUserName(registration.getUserName());
			registrationModel.setDob(registration.getDob());
			registrationModel.setEmailId(registration.getEmailId());
			registrationModel.setPhone(registration.getPhone());
			registrationModel.setCategory(registration.getCategory());
			registrationModel.setParentId(registration.getParentId());
			registrationModel.setStreet(registration.getStreet());
			registrationModel.setAptNo(registration.getAptNo());
			registrationModel.setCity(registration.getCity());
			registrationModel.setState(registration.getState());
			registrationModel.setCountry(registration.getCountry());
			registrationModel.setZip(registration.getZipcode());
			registrationModel.setUserPassword(registration.getUserPassword());
			registrationModel.setConfirmPassword(registration.getConfirmPassword());
			registrationModel.setRegionid(registration.getRegionId());

			String msg = null;
			JsonResponse jsonResponseAjax = new JsonResponse();

			// ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"id","id",
			// "id can not be empty.");
			// ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"name","name",
			// "name not be empty");
			// ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,
			// "emailId", "emailId", "emailId cant be empty");
			// ValidationUtils.r

			passwordDiff = 1;
			if (!registrationModel.getUserPassword().equals(registrationModel.getConfirmPassword()))
				passwordDiff = -1;

			/// Address Verification

			ProcessBuilder p = new ProcessBuilder("curl", "-X", "POST", "https://api.easypost.com/v2/addresses", "-u",
					"Dp8stkYIT525FJjgvk5bXg:", "-d", "verify_strict[]=delivery", "-d",
					"address[street1]=" + registrationModel.getStreet(), "-d",
					"address[street2]=" + registrationModel.getAptNo(), "-d",
					"address[city]=" + registrationModel.getCity(), "-d",
					"address[state]=" + registrationModel.getState(), "-d",
					"address[country]=" + registrationModel.getCountry(), "-d",
					"address[zip]=" + registrationModel.getZip());

			Process process = p.start();

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			System.out.println("Addess is:");
			String s;
			int count = 0;

			s = stdInput.readLine();
			System.out.println("Response for address is: " + s);

			if (process.isAlive()) {
				process.destroy();

			}

			// converting response to json object
			JSONObject jsonResponse = new JSONObject(s);

			JPAUserDAO tempEmail = new JPAUserDAO();

			/*
			 * for (ObjectError e : bindingResult.getAllErrors())
			 * System.out.println("this is error: " + e.getDefaultMessage());
			 */

			int parentId = tempEmail.getExistingEmail(registrationModel.getParentId());
			System.out.println("Parent email is: " + registrationModel.getParentId() + " , id is: " + parentId);

			if (tempEmail.getExistingEmail(registrationModel.getEmailId()) > 0) {

				jsonResponseAjax.setSuccessFlag("N");
				jsonResponseAjax.setErrorMessage("user with this email already exists");
				return jsonResponseAjax;

			}
			/*
			 * if (bindingResult.hasErrors() ||
			 * ((!registrationModel.getParentId().equals("")) && parentId == 0)
			 * || passwordDiff == -1) { System.out.println(bindingResult);
			 * System.out.println("Error in form: " +
			 * registrationModel.getDob()); //returning the errors on same page
			 * if any errors..
			 * 
			 * return new ModelAndView("signup", "userdetails",
			 * registrationModel); }
			 */
			if (jsonResponse.has("error")) {
				jsonResponseAjax.setSuccessFlag("N");
				jsonResponseAjax.setErrorMessage("Provided Address is Invalid..");
				return jsonResponseAjax;
			} else {

				// User Table Entry
				System.out.println("user model details here --" + registrationModel.getDob());

				registrationModel.setActiveUser(0);
				// System.out.println(PlayPP.sha1(registrationModel.getPassword()));
				registrationModel.setUserPassword(PlayPP.sha1(registrationModel.getUserPassword()));
				// Date d = new Date();

				user registerUser = new user();
				registerUser.setActive(registrationModel.getActiveUser());
				registerUser.setCategory(registrationModel.getCategory());

				DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
				format.setTimeZone(TimeZone.getTimeZone("GMT-8"));

				java.util.Date date = format.parse(registrationModel.getDob());

				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				registerUser.setDob(sqlDate);
				registerUser.setEmailId(registrationModel.getEmailId());
				registerUser.setName(registrationModel.getUserName());
				registerUser.setPassword(registrationModel.getUserPassword());
				// change here
				registerUser.setParentId(parentId);
				registerUser.setPhone(registrationModel.getPhone());
				String activation_code = getToken(6);
				registerUser.setActivationCode(activation_code);
				registerUser.setRegionId(registrationModel.getRegionid());

				JPAUserDAO obj = new JPAUserDAO();
				int newUserId = obj.insert(registerUser);
				registerUser.setId(newUserId);

				System.out.println("UserId Added: " + newUserId);
				// User Table Entry Ends/////////////////////////////////
				// System.out.println("Apt#: " + registrationModel.getAptNo());

				// address Entry
				registrationModel.setActive(1);
				address newUserAddress = new address();

				newUserAddress.setUserId(newUserId);
				newUserAddress.setStreet(registrationModel.getStreet());
				newUserAddress.setAptNo(registrationModel.getAptNo());
				newUserAddress.setCity(registrationModel.getCity());
				newUserAddress.setState(registrationModel.getState());
				newUserAddress.setCountry(registrationModel.getCountry());
				newUserAddress.setZip(registrationModel.getZip());
				newUserAddress.setAttachmentId(registrationModel.getAttachmentId());
				newUserAddress.setActive(registrationModel.getActive());

				JPAAddressDAO objAddress = new JPAAddressDAO();
				int newAddressId = objAddress.insert(newUserAddress);
				newUserAddress.setId(newAddressId);
				System.out.println("AddressId Added: " + newAddressId);
				// address Entry Ends

				String username = registrationModel.getUserName();

				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");

				Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("noreplydigitalbookshare@gmail.com", "cmpe295b");
					}
				});

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("noreplydigitalbookshare@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(registrationModel.getEmailId()));
				message.setSubject("Your Digital Library account activation code");
				
				message.setText("Dear "+username+"," +
    					"\n\nHere is Your Activation code : " + activation_code+
    					"\n\nRegards,\n\nDigital Book Share Team\n\nPLEASE DO NOT REPLY TO THIS EMAIL");

				message.setText("Dear " + username + "," + "\n\nHere is Your Activation code : " + activation_code);

				Transport.send(message);

				System.out.println("Done");

				JsonResponse jsonResponseObj = new JsonResponse();

				jsonResponseObj.setSuccessFlag("Y");
				jsonResponseObj.setSuccessMessage("Sign up success ! Activation code is sent to you via email");

				return jsonResponseObj;

			}

		} catch (Exception e) {

			System.out.println("Exception in FirstController " + e.getMessage());
			e.printStackTrace();
			JsonResponse jsonResponseAjax = new JsonResponse();
			jsonResponseAjax.setSuccessFlag("E");
			jsonResponseAjax.setErrorMessage("Error Occurred while processing request");
			return jsonResponseAjax;

		}

	}

	/*
	 * @RequestMapping(value = "/signup",method = RequestMethod.POST) public
	 * ModelAndView initN1(@ModelAttribute("userdetails")Registration
	 * registrationModel, BindingResult bindingResult, HttpServletRequest
	 * request, HttpServletResponse response) {
	 * 
	 * 
	 * try { String msg=null;
	 * 
	 * //ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"id","id",
	 * "id can not be empty."); //
	 * ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"name","name",
	 * "name not be empty");
	 * //ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "emailId",
	 * "emailId", "emailId cant be empty"); //ValidationUtils.r
	 * 
	 * passwordDiff = 1;
	 * if(!registrationModel.getPassword().equals(registrationModel.
	 * getConfirmPassword())) passwordDiff = -1;
	 * 
	 * 
	 * ///Address Verification
	 * 
	 * 
	 * ProcessBuilder p=new ProcessBuilder("curl", "-X","POST",
	 * "https://api.easypost.com/v2/addresses", "-u", "Dp8stkYIT525FJjgvk5bXg:",
	 * "-d", "verify_strict[]=delivery", "-d", "address[street1]="+
	 * registrationModel.getStreet(), "-d", "address[street2]=" +
	 * registrationModel.getAptNo() , "-d", "address[city]=" +
	 * registrationModel.getCity(), "-d", "address[state]=" +
	 * registrationModel.getState() ,"-d", "address[country]=" +
	 * registrationModel.getCountry(), "-d", "address[zip]=" +
	 * registrationModel.getZip());
	 * 
	 * 
	 * Process process = p.start();
	 * 
	 * BufferedReader stdInput = new BufferedReader(new
	 * InputStreamReader(process.getInputStream()));
	 * 
	 * BufferedReader stdError = new BufferedReader(new
	 * InputStreamReader(process.getErrorStream()));
	 * 
	 * 
	 * System.out.println("Addess is:"); String s; int count = 0;
	 * 
	 * s = stdInput.readLine(); System.out.println("Response for address is: " +
	 * s);
	 * 
	 * 
	 * 
	 * if(process.isAlive()) { process.destroy();
	 * 
	 * }
	 * 
	 * 
	 * //converting response to json object JSONObject jsonResponse = new
	 * JSONObject(s);
	 * 
	 * JPAUserDAO tempEmail = new JPAUserDAO();
	 * 
	 * for (ObjectError e : bindingResult.getAllErrors())
	 * System.out.println("this is error: " + e.getDefaultMessage());
	 * 
	 * int parentId =
	 * tempEmail.getExistingEmail(registrationModel.getParentId());
	 * System.out.println("Parent email is: " + registrationModel.getParentId()
	 * + " , id is: " + parentId);
	 * 
	 * if(tempEmail.getExistingEmail(registrationModel.getEmailId()) > 0) {
	 * System.out.println("Error in email: " + registrationModel.getDob());
	 * ModelAndView mv1 = new ModelAndView(); mv1.addObject("msg",
	 * "user with this email already exists"); mv1.setViewName("signup");
	 * 
	 * return mv1;
	 * 
	 * } if (bindingResult.hasErrors() ||
	 * ((!registrationModel.getParentId().equals("")) && parentId == 0) ||
	 * passwordDiff == -1) { System.out.println(bindingResult);
	 * System.out.println("Error in form: " + registrationModel.getDob());
	 * //returning the errors on same page if any errors..
	 * 
	 * return new ModelAndView("signup", "userdetails", registrationModel); }
	 * if(jsonResponse.has("error")) { System.out.println("Address is invalid");
	 * ModelAndView mv1 = new ModelAndView(); mv1.addObject("msgAddress",
	 * "Invalid address"); mv1.setViewName("signup");
	 * 
	 * return mv1;
	 * 
	 * } else {
	 * 
	 * //User Table Entry System.out.println("user model details here --"
	 * +registrationModel.getDob());
	 * 
	 * 
	 * registrationModel.setActiveUser(1);
	 * //System.out.println(PlayPP.sha1(registrationModel.getPassword()));
	 * registrationModel.setPassword(PlayPP.sha1(registrationModel.getPassword()
	 * )); //Date d = new Date();
	 * 
	 * user registerUser = new user();
	 * registerUser.setActive(registrationModel.getActiveUser());
	 * registerUser.setCategory(registrationModel.getCategory());
	 * 
	 * DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	 * format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
	 * 
	 * java.util.Date date = format.parse(registrationModel.getDob());
	 * 
	 * java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	 * registerUser.setDob(sqlDate);
	 * registerUser.setEmailId(registrationModel.getEmailId());
	 * registerUser.setName(registrationModel.getName());
	 * registerUser.setPassword(registrationModel.getPassword()); //change here
	 * registerUser.setParentId(parentId);
	 * registerUser.setPhone(registrationModel.getPhone());
	 * 
	 * 
	 * 
	 * JPAUserDAO obj= new JPAUserDAO(); int newUserId
	 * =obj.insert(registerUser); registerUser.setId(newUserId);
	 * 
	 * System.out.println("UserId Added: " + newUserId); //User Table Entry
	 * Ends///////////////////////////////// //System.out.println("Apt#: " +
	 * registrationModel.getAptNo());
	 * 
	 * //address Entry registrationModel.setActive(1); address newUserAddress =
	 * new address();
	 * 
	 * newUserAddress.setUserId(newUserId);
	 * newUserAddress.setStreet(registrationModel.getStreet());
	 * newUserAddress.setAptNo(registrationModel.getAptNo());
	 * newUserAddress.setCity(registrationModel.getCity());
	 * newUserAddress.setState(registrationModel.getState());
	 * newUserAddress.setCountry(registrationModel.getCountry());
	 * newUserAddress.setZip(registrationModel.getZip());
	 * newUserAddress.setAttachmentId(registrationModel.getAttachmentId());
	 * newUserAddress.setActive(registrationModel.getActive());
	 * 
	 * JPAAddressDAO objAddress = new JPAAddressDAO(); int newAddressId
	 * =objAddress.insert(newUserAddress); newUserAddress.setId(newAddressId);
	 * System.out.println("AddressId Added: " + newAddressId); //address Entry
	 * Ends
	 * 
	 * 
	 * //Getting regions in User City , If no found ????? JPARegionDAO jr = new
	 * JPARegionDAO(); List<region> rgnNearUser = new ArrayList<region>();
	 * 
	 * rgnNearUser = jr.getAllRegions(registrationModel.getCity());
	 * 
	 * LoginSamplee loginModel = new LoginSamplee(); ModelAndView model = new
	 * ModelAndView("login");
	 * 
	 * model.addObject("logindetails", loginModel);
	 * 
	 * 
	 * return model; } } catch (Exception e) {
	 * System.out.println("Exception in FirstController "+e.getMessage());
	 * e.printStackTrace(); return new ModelAndView("signup", "userdetails",
	 * registrationModel); }
	 * 
	 * }
	 */

	@RequestMapping(value = "/editprofile", method = RequestMethod.POST)
	public Object editProfile(@ModelAttribute("userdetails") user userModel1, BindingResult bindingResult,
			HttpServletRequest request, HttpServletResponse response) {

		System.out.println("enter to  edit");
		if (!sessionService.checkAuth()) {
			
    		System.out.println("Invalid session");

			return "redirect:/";

		}
		try {
			String msg = null;
			int userId = Integer.parseInt(httpSession.getAttribute("USERID").toString());

			// ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult,"name","name",
			// "name not be empty");

			JPAUserDAO tempEmail = new JPAUserDAO();

			// if (bindingResult.hasErrors())
			// {
			// System.out.println("Edit form has error --"
			// +userModel1.getName()+userModel1.getPhone());
			// //returning the errors on same page if any errors..
			// ModelAndView mv1 = new ModelAndView();
			// mv1.addObject("userdetails", userModel1);
			// mv1.addObject("path", "editprofile");
			// mv1.setViewName("profileedit");
			//
			// return mv1;
			//
			// }
			// else
			// {
			System.out.println("user edit model details here --" + userModel1.getEmailId());
			// insert the record by calling the service
			// userRecordService.insertUser(userModel1);

			// userModel1.setActive(1);
			System.out.println("???? " + userId);
			userModel1.setPassword(PlayPP.sha1(userModel1.getPassword()));
			JPAUserDAO obj = new JPAUserDAO();

			user x = obj.getUser(userId);
			// userModel1.setUserId(l);

			x.setActive(1);
			x.setCategory(userModel1.getCategory());
			x.setDob(userModel1.getDob());
			x.setName(userModel1.getName());
			x.setPassword(userModel1.getPassword());
			x.setPhone(userModel1.getPhone());

			obj = new JPAUserDAO();
			obj.update(x);

			ModelAndView mv = new ModelAndView();
			// getting data
			landingPage = new LandingPage();
			JPALandingPageDAO obj1 = new JPALandingPageDAO();
			// landingPage.setBooks(obj1.getBooks());
			landingPage.setCategories(obj1.getCategories());
			System.out.println(landingPage);
			mv.addObject("pagedetails", landingPage);
			mv.setViewName("home");

			return mv;

			// return new ModelAndView("redirect: /showuser/" + userId);
			// }
		} catch (Exception e) {
			System.out.println("Exception in FirstController " + e.getMessage());
			e.printStackTrace();
			return new ModelAndView("signup", "userdetails", userModel1);
		}
	}

	/*
	 * This method loads the homepage on application startup. Works on "/"
	 * mapping. *
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView initM() {

		ModelAndView mv = new ModelAndView();
		
	/////check for recommendations
		
		if(null == httpSession.getAttribute("USERID")){
			
			
			// Apoorv code for the getting the data if user is not logged In.
			JPALandingPageDAO j = new JPALandingPageDAO();
			List<MongoBook> customerChoice = new ArrayList<MongoBook>();
	        customerChoice=j.getCustomerChoice();
			System.out.println("=======================Customer choice==============");
			for(int i=0 ; i<=customerChoice.size()-1; i++)
			{
				System.out.println("enter" + i);
				System.out.println(customerChoice.get(i).getBookId());
				System.out.println(customerChoice.get(i).getLanguage());
				System.out.println(customerChoice.get(i).getTitle());
			}
			
			httpSession.setAttribute("recommendedForYou", customerChoice);
			
			
		
			
		}else{
			
			InventoryScheduler n = new InventoryScheduler();
			n.checkUserCreditScore();
			JPAUserDAO jp = new JPAUserDAO();
			
		/////check for recommendations
			
			int userid = (int) httpSession.getAttribute("USERID");
			
			user tempUser = jp.getUser(userid);
    		
    		
    		JPABookDAO bookTemp = new JPABookDAO();
    		
    		int orderCount = bookTemp.getOrderCount(userid);
    		
    		//get Top recommendations from user category based on rating
    		String[] categories = tempUser.getCategory().split(",");
    		
    		List<MongoBook> recommCatBooks = new ArrayList<MongoBook>();
    		
    		recommCatBooks = bookTemp.searchTop5CategoryBooks(categories);
    		
    		
    		List<Integer> userbasedRecommBookIds = new ArrayList<Integer>();
    		if(orderCount != 0)
    		{
    			//get Apache Mahout recommendations based on previous selections
    			
    			userbasedRecommBookIds = bookTemp.getMahoutRecomm(980);
    			
    		}
    		
    		List<MongoBook> recommendedForUser = new ArrayList<MongoBook>();
    		
    		for(int i=0;i<userbasedRecommBookIds.size();i++){
    			MongoBook bookDetails =  bookTemp.searchBooksInDBByID(String.valueOf(userbasedRecommBookIds.get(i)));
    			recommendedForUser.add(bookDetails);
    		}
    		
    		httpSession.setAttribute("recommendedForUser", recommendedForUser);
    		httpSession.setAttribute("recommCatBooks", recommCatBooks);
			
			
		}
		mv.setViewName("home");
		return mv;
		
	}

	// method to search
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchResults", method = RequestMethod.GET)
	public Object SearchR(HttpServletRequest request, final RedirectAttributes redirectAttributes) {
		// List<book> list1 = new ArrayList<book>();

		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {

			// list1 = (List<book>) inputFlashMap.get("pagedetails");

		}

		ModelAndView mv = new ModelAndView();
		// mv.addObject("pagedetails", list1);
		mv.setViewName("searchResults");
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Object Search(@RequestParam(value = "searchbox", required = true, defaultValue = "C++") String input,
			HttpServletRequest request, final RedirectAttributes redirectAttributes) {

		// Search for book in MongoDB
		List<MongoBook> searchedBooks = new ArrayList<MongoBook>();
		searchedBooks = searchService.searchBooksInDB(input);

		// End of search in MongoDB
		// Suppose No book is found
		// Google API implementation
		try {
			if (searchedBooks.size() == 0) {
				searchService.getBooksFromGoogle(input);
				searchedBooks = searchService.searchBooksInDB(input);

			}
		} catch (Exception e) {
			searchService.getBooksFromGoogle(input);
			searchedBooks = searchService.searchBooksInDB(input);
		}

		ModelAndView mv = new ModelAndView();
		mv.addObject("pagedetails", searchedBooks);
		mv.setViewName("searchResults");
		return mv;

	}

	@RequestMapping(value = "/advanceSearch", method = RequestMethod.GET)
	public Object SearchA(HttpServletRequest request) {

		System.out.println("in advance search get");

		int len = 0;
		JPACategoryDAO jpaCat = new JPACategoryDAO();
		internalCategory cTemp = new internalCategory();

		List<category> lst = jpaCat.getCategories();
		String[] catToDisplay = new String[lst.size()];
		for (category c : lst) {
			catToDisplay[len] = c.getName();

			len++;
		}

		httpSession.removeAttribute("SEARCHEDBOOKS");
		cTemp.setCatName(catToDisplay);
		ModelAndView mv = new ModelAndView();
		mv.addObject("advanceSearchDetails", cTemp);
		mv.setViewName("advanceSearch");
		return mv;

	}

	@RequestMapping(value = "/advanceSearch", method = RequestMethod.POST)
	public Object SearchAR(@RequestParam(value = "byAuthChkBox", defaultValue = "Def") String byAuthChkBox,
			@RequestParam(value = "byAuthTxt", defaultValue = "Def") String byAuthTxt,

			@RequestParam(value = "byPubChkBox", defaultValue = "Def") String byPubChkBox,
			@RequestParam(value = "byPubTxt", defaultValue = "") String byPubTxt,

			@RequestParam(value = "byDescTxt", defaultValue = "") String byDescTxt,
			@RequestParam(value = "byDescChkBox", defaultValue = "Def") String byDescChkBox,

			@RequestParam(value = "byCategChkBoxP", defaultValue = "Def") String byCategChkBoxP,
			@ModelAttribute("advanceSearchDetails") internalCategory cTemp,

			HttpServletRequest request) {

		String[] catArray = new String[cTemp.getCatName().length];
		String[] condi = new String[2];
		condi[0] = "ALL";
		if (byAuthChkBox.equalsIgnoreCase("Def")) {

			byAuthTxt = "ALL";
			System.out.println(byAuthTxt + "  auth name not picked dude");
		} else
			System.out.println(byAuthTxt + "   picked dude");

		if (byPubChkBox.equalsIgnoreCase("Def")) {

			byPubTxt = "ALL";
			System.out.println(byPubTxt + "  pub name not picked dude");
		} else
			System.out.println(byPubTxt + "   pub name picked dude");

		if (byDescChkBox.equalsIgnoreCase("Def")) {

			byDescTxt = "ALL";
			System.out.println(byDescTxt + "  Desc name not picked dude");
		} else
			System.out.println(byDescTxt + "   Desc name picked dude");

		if (byCategChkBoxP.equalsIgnoreCase("Def")) {
			System.out.println("cat. not checked");
			// catArray[0]=-1;
		} else {
			int len = 0;

			for (String categ : cTemp.getCatName()) {
				System.out.println("cat selected is:" + categ);
				// if(categ.getSelected() == true)
				catArray[len] = categ;
				len++;
			}

		}

		List<MongoBook> lb = searchService.doAdvanceSearch(byAuthTxt, byPubTxt, byDescTxt, catArray);
		// System.out.println("advanceSearch result size " + lb.size());
		try {
			if (lb.size() == 0) {
				searchService.getBooksFromGoogleAdvance(byAuthTxt, byPubTxt, byDescTxt, catArray);
				lb = searchService.doAdvanceSearch(byAuthTxt, byPubTxt, byDescTxt, catArray);

			}
		} catch (Exception e) {
			searchService.getBooksFromGoogleAdvance(byAuthTxt, byPubTxt, byDescTxt, catArray);
			lb = searchService.doAdvanceSearch(byAuthTxt, byPubTxt, byDescTxt, catArray);
		}

		httpSession.setAttribute("SEARCHEDBOOKS", lb);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pagedetails", lb);
		mv.setViewName("searchResults");
		return mv;
	}

	@RequestMapping(value = "/getSearchSuggetion", method = RequestMethod.GET)
	public @ResponseBody List<searchSuggetion> getSearchSuggetion(HttpServletRequest request) {
		System.out.println("searchQuery---->" + request.getParameter("searchTerm"));
		System.out.println("searchQuery---->" + request.getQueryString());
		JPASearchDAO searchDAO = new JPASearchDAO();
		String response = "";
		return searchDAO.getSearchSuggestion(request.getParameter("searchTerm"));

	}

	@RequestMapping(value = "/openAdvancedSearch", method = RequestMethod.GET)
	public ModelAndView loginPage() {

		System.out.println("in advance search get");

		int len = 0;
		JPACategoryDAO jpaCat = new JPACategoryDAO();
		internalCategory cTemp = new internalCategory();

		List<category> lst = jpaCat.getCategories();
		String[] catToDisplay = new String[lst.size()];
		for (category c : lst) {
			catToDisplay[len] = c.getName();

			len++;
		}

		httpSession.removeAttribute("SEARCHEDBOOKS");
		cTemp.setCatName(catToDisplay);
		ModelAndView mv = new ModelAndView();
		mv.addObject("advanceSearchDetails", cTemp);
		mv.setViewName("advanceSearch2");
		return mv;
	}

	@RequestMapping(value = "/advanceSearchAJAX", method = RequestMethod.GET)
	public @ResponseBody List<MongoBook> getAdvSearchAJAX(HttpServletRequest request) {

		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String publisher = request.getParameter("publisher");
		String description = request.getParameter("description");
		String category = request.getParameter("category");
		List<MongoBook> lb = searchService.doAdvanceSearchAJAX(author, publisher, description, category, title);
		try {
			if (lb.size() == 0) {
				searchService.getBooksFromGoogleAJAX(title, author, publisher, description, category);
				lb = searchService.doAdvanceSearchAJAX(author, publisher, description, category, title);
			}
		} catch (Exception e) {
			searchService.getBooksFromGoogleAJAX(title, author, publisher, description, category);
			lb = searchService.doAdvanceSearchAJAX(author, publisher, description, category, title);
		}

		// searchedBooks = searchService.searchBooksInDB(input);
		//
		//
		// List<MongoBook> lb = searchService.doAdvanceSearch(byAuthTxt,
		// byPubTxt, byDescTxt, catArray);
		// // System.out.println("advanceSearch result size " + lb.size());
		// try {
		// if (lb.size() == 0) {
		// searchService.getBooksFromGoogleAdvance(byAuthTxt, byPubTxt,
		// byDescTxt, catArray);
		// lb = searchService.doAdvanceSearch(byAuthTxt, byPubTxt, byDescTxt,
		// catArray);
		//
		// }
		// } catch (Exception e) {
		// searchService.getBooksFromGoogleAdvance(byAuthTxt, byPubTxt,
		// byDescTxt, catArray);
		// lb = searchService.doAdvanceSearch(byAuthTxt, byPubTxt, byDescTxt,
		// catArray);
		// }
		//
		// httpSession.setAttribute("SEARCHEDBOOKS", lb);
		// ModelAndView mv = new ModelAndView();
		// mv.addObject("pagedetails", lb);
		// mv.setViewName("searchResults");
		return lb;
	}

	@RequestMapping(value = "/getAllRegionsAJAX", method = RequestMethod.GET)
	public @ResponseBody List<region> getAllRegionsAJAX(HttpServletRequest request) {

		JPARegionDAO regionDAO = new JPARegionDAO();
		String city = request.getParameter("city");
		List<region> listRegion = regionDAO.getAllRegions(city);
		return listRegion;

	}

	@RequestMapping(value = "/validateAddressAJAX", method = RequestMethod.GET)
	public @ResponseBody String validateAddress(HttpServletRequest request) {
		ProcessBuilder p = new ProcessBuilder("curl", "-X", "POST", "https://api.easypost.com/v2/addresses", "-u",
				"Dp8stkYIT525FJjgvk5bXg:", "-d", "verify_strict[]=delivery", "-d",
				"address[street1]=" + request.getParameter("street1"), "-d",
				"address[street2]=" + request.getParameter("street2"), "-d",
				"address[city]=" + request.getParameter("city"), "-d",
				"address[state]=" + request.getParameter("state"), "-d",
				"address[country]=" + request.getParameter("country"), "-d",
				"address[zip]=" + request.getParameter("zip"));

		Process process;
		try {
			process = p.start();

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			System.out.println("Addess is:");
			String s;
			int count = 0;

			s = stdInput.readLine();
			System.out.println("Response for address is: " + s);

			if (process.isAlive()) {
				process.destroy();
			}

			JSONObject jsonResponse = new JSONObject(s);

			if (jsonResponse.has("error")) {

				return "N";
			} else {
				return "Y";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "N";
	}

}