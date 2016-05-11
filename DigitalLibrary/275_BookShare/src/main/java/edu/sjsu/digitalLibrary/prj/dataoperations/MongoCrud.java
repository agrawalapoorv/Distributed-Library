package edu.sjsu.digitalLibrary.prj.dataoperations;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

//import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import edu.sjsu.digitalLibrary.prj.models.BookId;
import edu.sjsu.digitalLibrary.prj.models.MongoBook;
import edu.sjsu.digitalLibrary.prj.models.category;
import edu.sjsu.digitalLibrary.prj.models.searchSuggetion;
import edu.sjsu.digitalLibrary.prj.models.subbook;

public class MongoCrud {
	public DBCollection dbCollection;

	static final String API_KEY = "AIzaSyAMCWf513E9bF6VpSiOL5U9HfoaqBrLi-I";
	// + MongoCrud.class;
	private static final String APPLICATION_NAME = "bookshare library";
	MongoClient mongoClient;
	DB db;

	@SuppressWarnings("deprecation")
	public MongoCrud(String collectionName) throws UnknownHostException {

		String dbURI = "mongodb://bookUser:9876543210@ds015750.mlab.com:15750/booksharedb";

		if (mongoClient == null) {
			mongoClient = new MongoClient(new MongoClientURI(dbURI));

			db = mongoClient.getDB("booksharedb");
			// boolean auth = ((Object) db).authenticate("sjsuTeam16",
			// "1234".toCharArray());
			// System.out.println("mongoClient: " +
			// mongoClient.getConnectPoint());

			dbCollection = db.getCollection(collectionName);
		}

		if (dbCollection == null) {
			// System.out.println("I am here to create");

			dbCollection = db.createCollection(collectionName,
					new BasicDBObject("capped", true).append("size", 1048576));
		}

		// System.out.println("db name const: " + dbCollection.getName());

	}

	public void GetBooksFromGoogle(String value, int bookId) {
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		String prefix = null;
		String query = "";
		query = value;
		try {
			queryGoogleBooks(jsonFactory, query, bookId);
			System.out.println("Search completed	");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void queryGoogleBooks(JsonFactory jsonFactory, String query, int bookId) throws Exception {

		JSONObject objBooks = new JSONObject();
		JSONArray arrayBooks = new JSONArray();
		java.util.List<String> authors;

		// Set up Books client.
		final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
				.setApplicationName(APPLICATION_NAME)
				.setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY)).build();

		List volumesList = books.volumes().list(query);

		Volumes volumes = volumesList.execute();
		if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {

			return;
		}

		// Output results.
		for (Volume volume : volumes.getItems()) {
			objBooks = new JSONObject();
			Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();

			Volume.SaleInfo saleInfo = volume.getSaleInfo();
			System.out.println("==========");
			// Title.
			objBooks.put("bookId", bookId);
			bookId++;
			objBooks.put("title", volumeInfo.getTitle());
			System.out.println("Title: " + volumeInfo.getTitle());
			// Author(s).

			authors = volumeInfo.getAuthors();

			if (authors != null && !authors.isEmpty()) {
				System.out.print("Author(s): ");
				arrayBooks = new JSONArray();
				for (int i = 0; i < authors.size(); ++i) {
					arrayBooks.add(authors.get(i));
					System.out.print(authors.get(i));
					if (i < authors.size() - 1) {
						System.out.print(", ");
					}
				}
				System.out.println();
			}
			objBooks.put("authors", arrayBooks);
			// Description (if any).
			if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
				objBooks.put("description", volumeInfo.getDescription());
				System.out.println("Description: " + volumeInfo.getDescription());
			}

			if (volumeInfo.getAverageRating() != null)
				objBooks.put("rating", volumeInfo.getAverageRating().doubleValue());
			else
				objBooks.put("rating", 0);

			// Categories
			authors = volumeInfo.getCategories();
			DBCrud<category> dbCrudCt = new DBCrud<category>();
			category cTemp = new category();
			int catCount = 0;
			String ctTmp = "";
			if (authors != null && !authors.isEmpty()) {
				System.out.print("Category(s): ");
				arrayBooks = new JSONArray();
				for (int i = 0; i < authors.size(); ++i) {
					arrayBooks.add(authors.get(i));
					ctTmp = authors.get(i);
					dbCrudCt = new DBCrud<category>();
					catCount = dbCrudCt.getExistingCategory(ctTmp);
					if (catCount == 0) {
						cTemp = new category();
						cTemp.setActive(1);
						cTemp.setName(authors.get(i));
						dbCrudCt.Insert(cTemp);
					}

				}

			}

			if (arrayBooks.size() != 0)
				objBooks.put("categories", arrayBooks);

			if (volumeInfo.getImageLinks() != null)
				objBooks.put("image", volumeInfo.getImageLinks());

			if (volumeInfo.getLanguage() != null)
				objBooks.put("language", volumeInfo.getLanguage());

			if (volumeInfo.getPrintedPageCount() == null)
				objBooks.put("pageCount", 0);
			else
				objBooks.put("pageCount", volumeInfo.getPrintedPageCount());

			if (volumeInfo.getPublisher() != null)
				objBooks.put("publisher", volumeInfo.getPublisher());

			if (volumeInfo.getIndustryIdentifiers() != null) {
				if (volumeInfo.getIndustryIdentifiers().size() > 1)
					objBooks.put("isbn", volumeInfo.getIndustryIdentifiers().get(1).getIdentifier());
				else
					objBooks.put("isbn", volumeInfo.getIndustryIdentifiers().get(0).getIdentifier());
			}
			objBooks.put("price", 0.0);

			Object o = com.mongodb.util.JSON.parse(objBooks.toString());
			DBObject dbObj = (DBObject) o;

			dbCollection = db.getCollection("book");
			// System.out.println("db name func: " + dbCollection.getName());
			dbCollection.insert(dbObj);

		}

		// insert next bookId in SQL DB BookId table
		try {
			DBCrud<BookId> dbCrud = new DBCrud<BookId>();
			BookId bTemp = new BookId();
			bTemp = dbCrud.get(bTemp, 1);
			bTemp.setBookId(bookId);
			dbCrud.update(bTemp);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void GetBooksFromGoogleAdvance(String byAuthTxt, String byPubTxt, String byDescTxt, String[] catArray,
			int bookId) {
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		String prefix = null;

		String query = "author=" + byAuthTxt;
		try {
			queryGoogleBooks(jsonFactory, query, bookId);
			System.out.println("Search completed	");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// private void getBooksFromGoogleAdvance(JsonFactory jsonFactory, String
	// byAuthTxt, String byPubTxt, String byDescTxt, String [] catArray, int
	// bookId) throws Exception {
	//
	// JSONObject objBooks = new JSONObject();
	// JSONArray arrayBooks = new JSONArray();
	// java.util.List<String> authors;
	//
	// // Set up Books client.
	// final Books books = new
	// Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory,
	// null)
	// .setApplicationName(APPLICATION_NAME)
	// .setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY))
	// .build();
	//
	// List volumesList = books.volumes().list(query);
	//
	// Volumes volumes = volumesList.execute();
	// if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
	//
	// return;
	// }
	//
	// // Output results.
	// for (Volume volume : volumes.getItems()) {
	// objBooks = new JSONObject();
	// Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
	//
	// Volume.SaleInfo saleInfo = volume.getSaleInfo();
	// System.out.println("==========");
	// // Title.
	// objBooks.put("bookId", bookId); bookId++;
	// objBooks.put("title", volumeInfo.getTitle());
	// System.out.println("Title: " + volumeInfo.getTitle());
	// // Author(s).
	//
	// authors = volumeInfo.getAuthors();
	//
	// if (authors != null && !authors.isEmpty()) {
	// System.out.print("Author(s): ");
	// arrayBooks = new JSONArray();
	// for (int i = 0; i < authors.size(); ++i) {
	// arrayBooks.add(authors.get(i));
	// System.out.print(authors.get(i));
	// if (i < authors.size() - 1) {
	// System.out.print(", ");
	// }
	// }
	// System.out.println();
	// }
	// objBooks.put("authors", arrayBooks);
	// // Description (if any).
	// if (volumeInfo.getDescription() != null &&
	// volumeInfo.getDescription().length() > 0) {
	// objBooks.put("description", volumeInfo.getDescription());
	// System.out.println("Description: " + volumeInfo.getDescription());
	// }
	//
	// if(volumeInfo.getAverageRating() != null)
	// objBooks.put("rating", volumeInfo.getAverageRating().doubleValue());
	// else
	// objBooks.put("rating",0);
	//
	// //Categories
	// authors = volumeInfo.getCategories();
	// DBCrud<category> dbCrudCt = new DBCrud<category>();
	// category cTemp = new category();
	// int catCount = 0;
	// String ctTmp = "";
	// if (authors != null && !authors.isEmpty()) {
	// System.out.print("Category(s): ");
	// arrayBooks = new JSONArray();
	// for (int i = 0; i < authors.size(); ++i) {
	// arrayBooks.add(authors.get(i));
	// ctTmp =authors.get(i);
	// dbCrudCt = new DBCrud<category>();
	// catCount = dbCrudCt.getExistingCategory(ctTmp);
	// if(catCount == 0)
	// {
	// cTemp = new category();
	// cTemp.setActive(1);
	// cTemp.setName(authors.get(i));
	// dbCrudCt.Insert(cTemp);
	// }
	//
	//
	// }
	//
	// }
	//
	// if(arrayBooks.size() != 0)
	// objBooks.put("categories", arrayBooks);
	//
	// if(volumeInfo.getImageLinks() != null)
	// objBooks.put("image", volumeInfo.getImageLinks());
	//
	// if(volumeInfo.getLanguage() != null)
	// objBooks.put("language", volumeInfo.getLanguage());
	//
	// if(volumeInfo.getPrintedPageCount() == null)
	// objBooks.put("pageCount", 0);
	// else
	// objBooks.put("pageCount", volumeInfo.getPrintedPageCount());
	//
	// if(volumeInfo.getPublisher() != null)
	// objBooks.put("publisher", volumeInfo.getPublisher());
	//
	//
	//
	// objBooks.put("price", 0.0);
	//
	// Object o = com.mongodb.util.JSON.parse(objBooks.toString());
	// DBObject dbObj = (DBObject) o;
	//
	//
	// dbCollection = db.getCollection("book");
	// //System.out.println("db name func: " + dbCollection.getName());
	// dbCollection.insert(dbObj);
	//
	// }
	//
	// //insert next bookId in SQL DB BookId table
	// try {
	// DBCrud<BookId> dbCrud = new DBCrud<BookId>();
	// BookId bTemp = new BookId();
	// bTemp = dbCrud.get(bTemp, 1);
	// bTemp.setBookId(bookId);
	// dbCrud.update(bTemp);
	//
	// } catch (Exception e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	//
	// }

	public java.util.List<MongoBook> searchBooksInDB(String searchString) throws Exception {
		java.util.List<MongoBook> searchedBooks = new java.util.ArrayList<MongoBook>();
		dbCollection = db.getCollection("book");

		Pattern regex = Pattern.compile(searchString,  Pattern.CASE_INSENSITIVE);
		BasicDBObject query = null;
		query = new BasicDBObject("title", regex);
		BasicDBObject image = null;
		DBCursor cursor = dbCollection.find(query);
		BasicDBObject object = null;
		MongoBook mTemp = new MongoBook();
		System.out.println("look this is what i found: " + cursor.count());
		try {
			while (cursor.hasNext()) {
				mTemp = new MongoBook();
				object = (BasicDBObject) cursor.next();
				mTemp.setBookId(Integer.parseInt(object.get("bookId").toString()));
				mTemp.setTitle(object.get("title").toString());

				if (object.containsField("description"))
					mTemp.setDescription(object.get("description").toString());
				else
					mTemp.setDescription("");

				if (object.containsField("description"))
					mTemp.setRating(Double.parseDouble(object.get("rating").toString()));
				else
					mTemp.setRating(0);

				image = (BasicDBObject) object.get("image");
				if (image != null) {
					if (image.containsField("smallThumbnail"))
						mTemp.setImage(image.get("smallThumbnail").toString());
					else
						mTemp.setImage("");
				} else
					mTemp.setImage("");

				if (object.containsField("language"))
					mTemp.setLanguage(object.get("language").toString());
				else
					mTemp.setLanguage("");

				if (object.containsField("pageCount"))
					mTemp.setPageCount(Integer.parseInt(object.get("pageCount").toString()));
				else
					mTemp.setPageCount(0);

				if (object.containsField("publisher"))
					mTemp.setPublisher(object.get("publisher").toString());
				else
					mTemp.setPublisher("");

				if (object.containsField("price"))
					mTemp.setPrice(Double.parseDouble(object.get("price").toString()));
				else
					mTemp.setPrice(0);

				if (object.containsField("isbn"))
					mTemp.setIsbn(object.get("isbn").toString());
				else
					mTemp.setIsbn("");

				if (object.containsField("categories")) {
					java.util.List<String> categories = (java.util.List<String>) object.get("categories");
					mTemp.setCategories(categories);
					if (categories.size() > 0) {
						mTemp.setSingleCategory(categories.get(0));
					} else if (categories.size() == 0) {
						mTemp.setSingleCategory("");
					}
				} else {
					mTemp.setCategories(null);
					mTemp.setSingleCategory("");
				}

				if (object.containsField("authors"))
					mTemp.setAuthors((java.util.List<String>) object.get("authors"));
				else
					mTemp.setAuthors(null);
				
				searchedBooks.add(mTemp);
			}
		} finally {
			cursor.close();

		}
		System.out.println("***"+ mTemp.getDescription());
		return searchedBooks;
	}

	@SuppressWarnings("unchecked")
	public MongoBook searchBooksInDBByID(String searchString) throws Exception {
		// java.util.List<MongoBook> searchedBooks = new
		// java.util.ArrayList<MongoBook>();
		dbCollection = db.getCollection("book");
		int bookId= Integer.parseInt(searchString);
		System.out.println("mongoSearch=====" + bookId);
		BasicDBObject query = null;
		query = new BasicDBObject("bookId", Integer.parseInt(searchString));
		BasicDBObject image = null;
		DBCursor cursor = dbCollection.find(query);
		BasicDBObject object = null;
		MongoBook mTemp = new MongoBook();
		System.out.println("look this is what i found: " + cursor.count());
		try {
			if (cursor.hasNext()) {
				mTemp = new MongoBook();

				object = (BasicDBObject) cursor.next();
				mTemp.setBookId(Integer.parseInt(object.get("bookId").toString()));
				mTemp.setTitle(object.get("title").toString());

				if (object.containsField("description"))
					mTemp.setDescription(object.get("description").toString());
				else
					mTemp.setDescription("");

				if (object.containsField("rating"))
					mTemp.setRating(Double.parseDouble(object.get("rating").toString()));
				else
					mTemp.setRating(0);

				image = (BasicDBObject) object.get("image");
				if (image != null) {
					if (image.containsField("smallThumbnail"))
						mTemp.setImage(image.get("smallThumbnail").toString());
					else
						mTemp.setImage("");
				} else
					mTemp.setImage("");

				if (object.containsField("categories")) {
					java.util.List<String> categories = (java.util.List<String>) object.get("categories");
					mTemp.setCategories(categories);
					if (categories.size() > 0) {
						mTemp.setSingleCategory(categories.get(0));
					} else if (categories.size() == 0) {
						mTemp.setSingleCategory("");
					}
				} else {
					mTemp.setCategories(null);
					mTemp.setSingleCategory("");
				}

				if (object.containsField("authors"))
					mTemp.setAuthors((java.util.List<String>) object.get("authors"));
				else
					mTemp.setAuthors(null);

				if (object.containsField("language"))
					mTemp.setLanguage(object.get("language").toString());
				else
					mTemp.setLanguage("");

				if (object.containsField("pageCount"))
					mTemp.setPageCount(Integer.parseInt(object.get("pageCount").toString()));
				else
					mTemp.setPageCount(0);

				if (object.containsField("publisher"))
					mTemp.setPublisher(object.get("publisher").toString());
				else
					mTemp.setPublisher("");

				if (object.containsField("price"))
					mTemp.setPrice(Double.parseDouble(object.get("price").toString()));
				else
					mTemp.setPrice(0);

				if (object.containsField("isbn"))
					mTemp.setIsbn(object.get("isbn").toString());
				else
					mTemp.setIsbn("");

			}
		} finally {
			cursor.close();

		}
	
		return mTemp;
	}

	public java.util.List<MongoBook> doAdvanceSearch(String auth, String publisher, String desc, String[] categories) {
		java.util.List<MongoBook> searchedBooks = new java.util.ArrayList<MongoBook>();
		dbCollection = db.getCollection("book");
		BasicDBObject image = null;
		System.out.println("Adv search here: " + auth + " -- " + publisher + " -- " + desc + "---" + categories.length);
		Pattern regexAuth = Pattern.compile(auth);
		Pattern regexPublisher = Pattern.compile(publisher);
		Pattern regexDesc = Pattern.compile(desc);
		String categoryPattern = "";
		int c = 0;
		// for (String s : categories){
		// categoryPattern += s;
		// c++;
		// if(c != categories.length)
		// categoryPattern += ",";
		// }
		// Pattern regexCategories = Pattern.compile(auth);
		BasicDBObject query = new BasicDBObject();
		if (auth != "ALL")
			query = new BasicDBObject("authors", regexAuth);

		if (categories.length != 0)
			query.put("categories", new BasicDBObject("$in", categories));

		if (publisher != "ALL")
			query.put("publisher", regexPublisher);

		if (desc != "ALL")
			query.put("description", regexDesc);

		System.out.println("query is:" + query);

		DBCursor cursor = dbCollection.find(query);
		BasicDBObject object = null;
		MongoBook mTemp = new MongoBook();
		System.out.println("Adv search found: " + cursor.count());
		try {
			while (cursor.hasNext()) {
				mTemp = new MongoBook();
				object = (BasicDBObject) cursor.next();
				mTemp.setBookId(Integer.parseInt(object.get("bookId").toString()));
				mTemp.setTitle(object.get("title").toString());
				if (object.containsField("description"))
					mTemp.setDescription(object.get("description").toString());
				else
					mTemp.setDescription("");

				if (object.containsField("description"))
					mTemp.setRating(Double.parseDouble(object.get("rating").toString()));
				else
					mTemp.setRating(0);

				image = (BasicDBObject) object.get("image");
				if (image != null) {
					if (image.containsField("smallThumbnail"))
						mTemp.setImage(image.get("smallThumbnail").toString());
					else
						mTemp.setImage("");
				} else
					mTemp.setImage("");

				if (object.containsField("language"))
					mTemp.setLanguage(object.get("language").toString());
				else
					mTemp.setLanguage("");

				if (object.containsField("pageCount"))
					mTemp.setPageCount(Integer.parseInt(object.get("pageCount").toString()));
				else
					mTemp.setPageCount(0);

				if (object.containsField("publisher"))
					mTemp.setPublisher(object.get("publisher").toString());
				else
					mTemp.setPublisher("");

				if (object.containsField("price"))
					mTemp.setPrice(Double.parseDouble(object.get("price").toString()));
				else
					mTemp.setPrice(0);

				if (object.containsField("isbn"))
					mTemp.setIsbn(object.get("isbn").toString());
				else
					mTemp.setIsbn("");

				if (object.containsField("categories")) {
					java.util.List<String> categories1 = (java.util.List<String>) object.get("categories");
					mTemp.setCategories(categories1);
					if (categories1.size() > 0) {
						mTemp.setSingleCategory(categories1.get(0));
					} else if (categories1.size() == 0) {
						mTemp.setSingleCategory("");
					}
				} else {
					mTemp.setCategories(null);
					mTemp.setSingleCategory("");
				}

				if (object.containsField("authors"))
					mTemp.setAuthors((java.util.List<String>) object.get("authors"));
				else
					mTemp.setAuthors(null);
				
				searchedBooks.add(mTemp);
			}
		} finally {
			cursor.close();

		}
		return searchedBooks;
	}

	public MongoBook searchBooksInDBWithBookId(String bookId) throws Exception {

		dbCollection = db.getCollection("book");
		BasicDBObject query = null;
		query = new BasicDBObject("bookId", bookId);
		BasicDBObject image = null;
		DBCursor cursor = dbCollection.find(query);
		BasicDBObject object = null;
		MongoBook mTemp = new MongoBook();
		System.out.println("Particular found: " + cursor.count());
		try {
			if (cursor.hasNext()) {
				mTemp = new MongoBook();
				object = (BasicDBObject) cursor.next();
				mTemp.setBookId(Integer.parseInt(object.get("bookId").toString()));
				mTemp.setTitle(object.get("title").toString());
				mTemp.setDescription(object.get("description").toString());
				mTemp.setRating(Double.parseDouble(object.get("rating").toString()));
				image = (BasicDBObject) object.get("image");
				if (image.containsField("smallThumbnail"))
					mTemp.setImage(image.get("smallThumbnail").toString());
				else
					mTemp.setImage("");

				mTemp.setLanguage(object.get("language").toString());
				mTemp.setPageCount(Integer.parseInt(object.get("pageCount").toString()));
				mTemp.setPublisher(object.get("publisher").toString());
				mTemp.setPrice(Double.parseDouble(object.get("price").toString()));
				if (object.containsField("isbn"))
					mTemp.setIsbn(object.get("isbn").toString());
				else
					mTemp.setIsbn("");
				if (object.containsField("categories")) {
					java.util.List<String> categories = (java.util.List<String>) object.get("categories");
					mTemp.setCategories(categories);
					if (categories.size() > 0) {
						mTemp.setSingleCategory(categories.get(0));
					} else if (categories.size() == 0) {
						mTemp.setSingleCategory("");
					}
				} else {
					mTemp.setCategories(null);
					mTemp.setSingleCategory("");
				}

				if (object.containsField("authors"))
					mTemp.setAuthors((java.util.List<String>) object.get("authors"));
				else
					mTemp.setAuthors(null);
			}
		} finally {
			cursor.close();

		}
		return mTemp;
	}

	public java.util.List<MongoBook> searchTop5CategoryBooks(String[] categories) throws Exception {

		java.util.List<MongoBook> mBooks = new java.util.ArrayList<MongoBook>();
		mBooks = this.doAdvanceSearch("ALL", "ALL", "ALL", categories);

		Collections.sort(mBooks);

		return mBooks;

	}

	/*
	 * Added by Jinal
	 */
	public MongoBook searchBooksInDBWithBookISBN(String ISBN) throws Exception {

		dbCollection = db.getCollection("book");
		BasicDBObject query = null;
		query = new BasicDBObject("isbn", ISBN);
		BasicDBObject image = null;
		DBCursor cursor = dbCollection.find(query);
		BasicDBObject object = null;
		MongoBook mTemp = new MongoBook();
		System.out.println("Particular found: " + cursor.count());
		try {
			if (cursor.hasNext()) {
				mTemp = new MongoBook();
				object = (BasicDBObject) cursor.next();
				mTemp.setBookId(Integer.parseInt(object.get("bookId").toString()));
				mTemp.setTitle(object.get("title").toString());
				mTemp.setDescription(object.get("description").toString());
				mTemp.setRating(Double.parseDouble(object.get("rating").toString()));
				image = (BasicDBObject) object.get("image");
				if (image.containsField("smallThumbnail"))
					mTemp.setImage(image.get("smallThumbnail").toString());
				else
					mTemp.setImage("");

				mTemp.setLanguage(object.get("language").toString());
				mTemp.setPageCount(Integer.parseInt(object.get("pageCount").toString()));
				mTemp.setPublisher(object.get("publisher").toString());
				mTemp.setPrice(Double.parseDouble(object.get("price").toString()));

			}
		} finally {
			cursor.close();

		}
		return mTemp;
	}

	/*
	 * Added by Jinal for Search suggestions
	 */
	public java.util.List<searchSuggetion> getGoogleBooksSuggetion(JsonFactory jsonFactory, String query)
			throws Exception {

		JSONObject objBooks = new JSONObject();
		JSONArray arrayBooks = new JSONArray();
		java.util.List<String> authors;

		// Set up Books client.
		final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
				.setApplicationName(APPLICATION_NAME)
				.setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY)).build();

		List volumesList = books.volumes().list(query);

		Volumes volumes = volumesList.execute();
		if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {

			return null;
		}
		java.util.List<searchSuggetion> searchlist = new ArrayList<searchSuggetion>();
		// Output results.
		for (Volume volume : volumes.getItems()) {
			searchSuggetion s = new searchSuggetion();
			objBooks = new JSONObject();
			Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
			s.setName(volumeInfo.getTitle());
			if(volumeInfo.getAuthors()!=null)
				s.setAuthor(volumeInfo.getAuthors().get(0));
			else
				s.setAuthor("");
			s.setPublisher(volumeInfo.getPublisher());

			searchlist.add(s);
		}
		return searchlist;
	}

	public void queryGoogleBooksAJAX(String title, String author, String category, String publisher, String description,
			int bookId) throws Exception {
		JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

		JSONObject objBooks = new JSONObject();
		JSONArray arrayBooks = new JSONArray();
		java.util.List<String> authors;

		// Set up Books client.
		final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
				.setApplicationName(APPLICATION_NAME)
				.setGoogleClientRequestInitializer(new BooksRequestInitializer(API_KEY)).build();

		String query = "";
		if (!title.equals("") && title != null) {
			query += title + "+";
		}
		if (!author.equals("") && author != null) {
			query += author + "+";
		}
		if (!category.equals("") && category != null) {
			query += category + "+";
		}
		if (!publisher.equals("") && publisher != null) {
			query += publisher + "+";
		}
		if (!description.equals("") && description != null) {
			query += description + "+";
		}
		if (!query.equals("")) {
			query = query.substring(0, query.length() - 1);
		}
		List volumesList = books.volumes().list(query);

		Volumes volumes = volumesList.execute();
		if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {

			return;
		}

		// Output results.
		for (Volume volume : volumes.getItems()) {
			objBooks = new JSONObject();
			Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();

			Volume.SaleInfo saleInfo = volume.getSaleInfo();
			System.out.println("==========");
			// Title.
			objBooks.put("bookId", bookId);
			bookId++;
			objBooks.put("title", volumeInfo.getTitle());
			System.out.println("Title: " + volumeInfo.getTitle());
			// Author(s).

			authors = volumeInfo.getAuthors();

			if (authors != null && !authors.isEmpty()) {
				System.out.print("Author(s): ");
				arrayBooks = new JSONArray();
				for (int i = 0; i < authors.size(); ++i) {
					arrayBooks.add(authors.get(i));
					System.out.print(authors.get(i));
					if (i < authors.size() - 1) {
						System.out.print(", ");
					}
				}
				System.out.println();
			}
			objBooks.put("authors", arrayBooks);
			// Description (if any).
			if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
				objBooks.put("description", volumeInfo.getDescription());
				System.out.println("Description: " + volumeInfo.getDescription());
			}

			if (volumeInfo.getAverageRating() != null)
				objBooks.put("rating", volumeInfo.getAverageRating().doubleValue());
			else
				objBooks.put("rating", 0);

			// Categories
			authors = volumeInfo.getCategories();
			DBCrud<category> dbCrudCt = new DBCrud<category>();
			category cTemp = new category();
			int catCount = 0;
			String ctTmp = "";
			if (authors != null && !authors.isEmpty()) {
				System.out.print("Category(s): ");
				arrayBooks = new JSONArray();
				for (int i = 0; i < authors.size(); ++i) {
					arrayBooks.add(authors.get(i));
					ctTmp = authors.get(i);
					dbCrudCt = new DBCrud<category>();
					catCount = dbCrudCt.getExistingCategory(ctTmp);
					if (catCount == 0) {
						cTemp = new category();
						cTemp.setActive(1);
						cTemp.setName(authors.get(i));
						dbCrudCt.Insert(cTemp);
					}

				}

			}

			if (arrayBooks.size() != 0)
				objBooks.put("categories", arrayBooks);

			if (volumeInfo.getImageLinks() != null)
				objBooks.put("image", volumeInfo.getImageLinks());

			if (volumeInfo.getLanguage() != null)
				objBooks.put("language", volumeInfo.getLanguage());

			if (volumeInfo.getPrintedPageCount() == null)
				objBooks.put("pageCount", 0);
			else
				objBooks.put("pageCount", volumeInfo.getPrintedPageCount());

			if (volumeInfo.getPublisher() != null)
				objBooks.put("publisher", volumeInfo.getPublisher());

			if (volumeInfo.getIndustryIdentifiers() != null) {
				if (volumeInfo.getIndustryIdentifiers().size() > 1)
					objBooks.put("isbn", volumeInfo.getIndustryIdentifiers().get(1).getIdentifier());
				else
					objBooks.put("isbn", volumeInfo.getIndustryIdentifiers().get(0).getIdentifier());
			}
			objBooks.put("price", 0.0);

			Object o = com.mongodb.util.JSON.parse(objBooks.toString());
			DBObject dbObj = (DBObject) o;

			dbCollection = db.getCollection("book");
			// System.out.println("db name func: " + dbCollection.getName());
			dbCollection.insert(dbObj);

		}

		// insert next bookId in SQL DB BookId table
		try {
			DBCrud<BookId> dbCrud = new DBCrud<BookId>();
			BookId bTemp = new BookId();
			bTemp = dbCrud.get(bTemp, 1);
			bTemp.setBookId(bookId);
			dbCrud.update(bTemp);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public java.util.List<MongoBook> doAdvanceSearchAJAX(String auth, String publisher, String desc, String category,
			String title) {
		java.util.List<MongoBook> searchedBooks = new java.util.ArrayList<MongoBook>();
		dbCollection = db.getCollection("book");
		BasicDBObject image = null;
		System.out.println("Adv search here: " + auth + " -- " + publisher + " -- " + desc + "---" + category);
		Pattern regexAuth = Pattern.compile(auth);
		Pattern regexTitle = Pattern.compile(title);

		Pattern regexPublisher = Pattern.compile(publisher);
		Pattern regexDesc = Pattern.compile(desc);
		String categoryPattern = "";
		int c = 0;
		// for (String s : categories){
		// categoryPattern += s;
		// c++;
		// if(c != categories.length)
		// categoryPattern += ",";
		// }
		// Pattern regexCategories = Pattern.compile(auth);
		BasicDBObject query = new BasicDBObject();
		if (!auth.equals("") &&  auth!=null)
			query = new BasicDBObject("authors", regexAuth);

		if (!publisher.equals("") &&  publisher!=null)
			query.put("publisher", regexPublisher);

		if (!desc.equals("") &&  desc!=null)
			query.put("description", regexDesc);

		if (!title.equals("") &&  title!=null)
			query.put("title", regexTitle);

		System.out.println("query is:" + query);

		DBCursor cursor = dbCollection.find(query);
		BasicDBObject object = null;
		MongoBook mTemp = new MongoBook();
		System.out.println("Adv search found: " + cursor.count());
		try {
			while (cursor.hasNext()) {
				mTemp = new MongoBook();
				object = (BasicDBObject) cursor.next();
				mTemp.setBookId(Integer.parseInt(object.get("bookId").toString()));
				mTemp.setTitle(object.get("title").toString());
				if (object.containsField("description"))
					mTemp.setDescription(object.get("description").toString());
				else
					mTemp.setDescription("");

				if (object.containsField("description"))
					mTemp.setRating(Double.parseDouble(object.get("rating").toString()));
				else
					mTemp.setRating(0);

				image = (BasicDBObject) object.get("image");
				if (image != null) {
					if (image.containsField("smallThumbnail"))
						mTemp.setImage(image.get("smallThumbnail").toString());
					else
						mTemp.setImage("");
				} else
					mTemp.setImage("");

				if (object.containsField("language"))
					mTemp.setLanguage(object.get("language").toString());
				else
					mTemp.setLanguage("");

				if (object.containsField("pageCount"))
					mTemp.setPageCount(Integer.parseInt(object.get("pageCount").toString()));
				else
					mTemp.setPageCount(0);

				if (object.containsField("publisher"))
					mTemp.setPublisher(object.get("publisher").toString());
				else
					mTemp.setPublisher("");

				if (object.containsField("price"))
					mTemp.setPrice(Double.parseDouble(object.get("price").toString()));
				else
					mTemp.setPrice(0);

				if (object.containsField("isbn"))
					mTemp.setIsbn(object.get("isbn").toString());
				else
					mTemp.setIsbn("");

				searchedBooks.add(mTemp);
			}
		} finally {
			cursor.close();

		}
		return searchedBooks;
	}
}
