package edu.sjsu.digitalLibrary.prj.dao;

import java.util.List;

import edu.sjsu.digitalLibrary.prj.Recommendations.Recommendations;
import edu.sjsu.digitalLibrary.prj.dataoperations.DBCrud;
import edu.sjsu.digitalLibrary.prj.dataoperations.MongoCrud;
import edu.sjsu.digitalLibrary.prj.models.MongoBook;
import edu.sjsu.digitalLibrary.prj.models.order;
import edu.sjsu.digitalLibrary.prj.models.region;


/*
 * Class to perform business functions
 * it implements the DAO of address
 */
@SuppressWarnings("unused")
public class JPARegionDAO {

	
	
	

				public List<region> getAllRegions(String s)
				{
					
					try {
						
						
						DBCrud<region> db = new DBCrud<region>();
						
					    return db.getAllRegions(s);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return null;
				}
				
				
				
				
				
				public int getOrderCount(int userId)
				{
					try {
						
						DBCrud<order> db = new DBCrud<order>();
						 return db.getOrderCount(userId);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return 0;
				}
				
				public List<MongoBook> searchTop5CategoryBooks(String[] categories) {
					
					try {
						MongoCrud db = new MongoCrud("book");
						return db.searchTop5CategoryBooks(categories);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					return null	;
				}
				
				
				public List<Integer> getMahoutRecomm(int userId) {
					
					try {
						Recommendations r = new Recommendations();
						return r.getBookRecommendationsMahout(userId, 10);
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					return null	;
				}
				
				public region getRegion(int regionID) {
					try {

						DBCrud<region> db = new DBCrud<region>();

						return db.getRegionInfo(regionID);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return null;
				}
				
				
				public List<region> getAllRegionsAJAX()
				{
					
					try {
						
						
						DBCrud<region> db = new DBCrud<region>();
						
					    return db.getAllRegionsAJAX();
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return null;
				}
				
}