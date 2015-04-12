package uy.bunker.deliverylibre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//Debo hacer una tarea asincronica para consumir el servicio rest correctamente
public class AsyncLogin extends AsyncTask<String, String, JSONObject>{
	
	//Defino variables a utilizar por el constructor
	private Activity activity;
	private String url;
	private boolean checkCode;
	private Context context;
	private boolean logueado;
	
	//Defino variables a utilizar por getJSONFromUrl
	private InputStream is = null;
	private JSONObject jObj = null;
	private String json = "";
	
	//Shared preferences
	// Instancia de SharedPreferences
	private SharedPreferences prefs;
			
	//Defino variables a utilizar para hacer el addUser
	String user_id;
	String access_token;
	
	//Defino variables objetos y urls
	WebView wblogin;
	String code = "https://deliverylibre.herokuapp.com/code";

	/**
	 * 
	 * @param ac
	 * 
	 *            Constructor AsyncLogin(Activity, String, boolean, Context, boolean)
	 */
	public AsyncLogin(Activity ac, String url, boolean codehere, Context context1, boolean logueado) {
		activity = ac;
		this.url = url;
		this.checkCode = codehere;
		this.context = context1;
		this.logueado = logueado;
	}
	
	
	/**
	 * 
	 * @param ac
	 * 
	 *            Constructor por defecto.
	 */
	public AsyncLogin() {
	
	}
	
	
	/**
	 * 
	 * @param url
	 * @return JSONObject
	 * 
	 *         Obtiene y parsea el json.
	 */
	@SuppressWarnings("deprecation")
	private JSONObject getJSONFromUrl(String url) {
		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line + "\n");

			is.close();
			json = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// return JSON String
		return jObj;
		
	}
	
	
	/**
	 * 
	 * @param url
	 * @return JSONObject
	 * 
	 *         Obtiene y parsea el json.
	 */
	@SuppressWarnings("deprecation")
	private JSONObject postJSONToUrl(String url, String clientId, String token, String deviceId) {
		// Making HTTP request
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			
			//Set params
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    nameValuePairs.add(new BasicNameValuePair("clientId", clientId));
		    nameValuePairs.add(new BasicNameValuePair("token", token));
		    nameValuePairs.add(new BasicNameValuePair("deviceId", deviceId));
		    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();

		} catch (ClientProtocolException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line + "\n");

			is.close();
			json = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// try parse the string to a JSON object
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// return JSON String
		return jObj;
	}
	
	
	
	
	@Override
	protected JSONObject doInBackground(String... arg0) {
		AsyncLogin AsLogin = new AsyncLogin();
		
		if(logueado){
			
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
				  
			String postUrl = "https://deliverylibre.herokuapp.com/addUser";//context.getString(R.string.add_user);
			
			 prefs = Prefs.get(context);
			 this.user_id = prefs.getString("user_id", "");
			 this.access_token = prefs.getString("access_token", "");
			// Getting JSON from URL
			JSONObject  json = AsLogin.postJSONToUrl(postUrl, this.user_id, this.access_token, "pruebadiego");
			return json;
		}
		else{
			// Getting JSON from URL
			JSONObject  json = AsLogin.getJSONFromUrl(url);
			return json;
		}
	}

	
	@Override
	protected void onPostExecute(JSONObject json) {
		super.onPostExecute(json);
		// Do stuff
		if(!logueado){
			if (checkCode) {
				SharedPreferences prefs = Prefs.get(context);
				SharedPreferences.Editor editor = prefs.edit();
	
				try {
					user_id = json.getString("user_id");
					access_token = json.getString("access_token");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				editor.putString("user_id", user_id);
				editor.putString("access_token", access_token);
	
				editor.commit();
				
				//hacer el post de add user
				String urlLogueado = "https://deliverylibre.herokuapp.com/addUser";
				AsyncLogin asyncTask = new AsyncLogin(
						new Login(), urlLogueado, true, context, true);
				asyncTask.execute();
	
				
	
			} else {
	
				String url2;
				try {
					url2 = json.getString("url");
	
					wblogin = (WebView) activity.findViewById(R.id.wblogin);
					
				
					WebSettings webSettings = wblogin.getSettings();
					webSettings.setJavaScriptEnabled(true); // enable javascript
					webSettings.setDomStorageEnabled(true);
					wblogin.getSettings().setJavaScriptCanOpenWindowsAutomatically(
							true);
					wblogin.setWebChromeClient(new WebChromeClient());
	
					wblogin.setWebViewClient(new WebViewClient() {
						public void onReceivedError(WebView view, int errorCode,
								String description, String failingUrl) {
	
						}
						
						@Override
						public void onPageFinished(WebView view, String url) {
							String current = "";
							current = getUrl();
							if (current != null) {
								if (current.contains(code)) {
									checkCode = true;
									AsyncLogin asyncTask = new AsyncLogin(
											new Login(), current, true, context, false);
									asyncTask.execute();
								}
							}
						}
	
						@Override
						public void onPageStarted(WebView view, String url, Bitmap favicon) {
							
						}
					});
	
					if (!url2.equals("")) {
						wblogin.loadUrl(url2);
					}
	
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
			}
		}else{
			activity.finish();
			
			Intent intent = new Intent(context,
					Recientes.class);
			context.startActivity(intent);
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}
	
	public String getUrl() {
		return wblogin.getUrl();
	}
	
	
	public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
