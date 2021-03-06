package com.example.gitjob;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gitjob.fragment.Fragment0;

public class MainActivity extends FragmentActivity {
	// listing all necessary items to declare
	public ArrayAdapter<JobInfoItems> adapter; // arrayList and adapter
    public static MainActivity instance = new MainActivity(); // MainActivity instance
    public static List<JobInfoItems> lastResultList; // last result
	public static String ENCODE_jPlace_initial;//search by city name
	//public static String ENCODE_jPlace_last;
	public static String url;
	public static int listNumber;
	public ViewPager viewPager;
	public static boolean pageControl;
	public static MyFragmentStatePagerAdapter pageLock;
	//TextView noResult;
	TextView pleaseInput;
		
    // MainActivity instance returns
    public static MainActivity getInstance() {
        return instance;
    }
    
    // method --creating list of jobInfoitems 
   public List<JobInfoItems> getSearchResult(String keyword) {
		return lastResultList;
    }  
    // method --last result list 
    public List<JobInfoItems> getLastResutList() {
        return lastResultList;
    } 
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager_set);
		lastResultList = new ArrayList<JobInfoItems>();
		viewPager = (ViewPager) findViewById(R.id.pager);
		//list1.setBackgroundColor(0xFFFF0000);
		// set adapter for resultList
		adapter = new JobListAdapter(this, 0, lastResultList);
		
	        // search result to arraylist 
	        lastResultList = new ArrayList<JobInfoItems>();
		
		// set adapter for viewPager
	        pageLock = new MyFragmentStatePagerAdapter(
					getSupportFragmentManager());
		viewPager.setAdapter(pageLock);
	}
	
	
	// set parseJson method 
	public static void parseJson() {

		try {
			// city name with space 
			for(int v= 0; v < ENCODE_jPlace_initial.length(); v++){
					char c;			
					c=ENCODE_jPlace_initial.charAt(v);
					//look for space and add + 
					if(c==' '){
					ENCODE_jPlace_initial = ENCODE_jPlace_initial.replace(" ", "+");
					 //url = "http://jobs.github.com/positions.json?description=" + "&location=" + ENCODE_jPlace_last;
					//}else{
					 //url = "http://jobs.github.com/positions.json?description=" + ".&location=" + ENCODE_jPlace_initial;
					}
			}

			url = "http://jobs.github.com/positions.json?description=" + "&location=" + ENCODE_jPlace_initial;
			

			// creating POST instance
			HttpUriRequest httpGet = new HttpGet(url);

			// setting variables/parameters as site requires
			DefaultHttpClient defaultHttpClient = new DefaultHttpClient();

			// creating client instance
			HttpResponse httpResponse = defaultHttpClient.execute(httpGet);

			// if HTTP info is not working normal
			 if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return; // set return
			}

			// set HTTP parameter entity
			 HttpEntity entity = httpResponse.getEntity();

			// JSON info stores to entity
			JSONArray json = new JSONArray(EntityUtils.toString(entity));
			
            //items inside of arrayList 
            // String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
            // String icon = json.getJSONArray("weather").getJSONObject(0).getString("icon");
            for(int n=0; n<json.length(); n++){
            	JSONObject setJson = json.getJSONObject(n);
          
           //setting up selected objects and array to display
			String createdAt = setJson.getString("created_at");
			String jobTitle = setJson.getString("title");
			String location = setJson.getString("location");
			String type = setJson.getString("type");
			String description = setJson.getString("description");
			String howToApply = setJson.getString("how_to_apply");
			String company = setJson.getString("company");
			String companyUrl = setJson.getString("company_url");
			String logo = setJson.getString("company_logo");
									
                //create result list individually 
                JobInfoItems item = new JobInfoItems(jobTitle, location, type, company, createdAt, description,
            			 howToApply,companyUrl,logo);
                
                // Display class member as lists
                lastResultList.add(item);
            }
            if (lastResultList.size()==0){
    		TextView noResult  = (TextView)Fragment0.view.findViewById(R.id.tv_pleaseInput);
     	       noResult.setText("oops! no results found");
     	       //noResult.setTextSize(25);
     	       //noResult.setTypeface(tf);
            }
		}catch(Exception e){
			e.printStackTrace();

	        
	}
		return; // in case exception happens, go back to the beginning of parseJson method.
	}
		/**
	
		// json data transfers to listview and view
		public class JobListAdapter extends ArrayAdapter<JobInfoItems> {
			private LayoutInflater inflater;

			public JobListAdapter(Context context, int resource,
					List<JobInfoItems> items) {
				super(context, resource, items);
				inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view;

				if (convertView != null) {
					view = convertView;
				} else {
					// setting up list_row
					view = inflater.inflate(R.layout.list_row, null);
				}
				JobInfoItems info = getItem(position);
				
				 //getting icon from url 
				 URL iurl; 
				 * String iconurl =
				 * "http://openweathermap.org/img/w/" + info.getIcon() + ".png";
				 * Bitmap iconDisplay = null; try { iurl = new URL(iconurl);
				 * iconDisplay = BitmapFactory.decodeStream(iurl.openStream());
				 * } catch (MalformedURLException e) {
				 * Log.w(getClass().getSimpleName(), e); } catch (IOException e)
				 * { Log.w(getClass().getSimpleName(), e); }
				 * 
				 //icon set up needs to be declared here //ImageView icon =
				 (ImageView)view.findViewById(R.id.iv_icon); // icon setting
				 up as imagebitmap // icon.setImageBitmap(iconDisplay);
			}

		}*/

}	
