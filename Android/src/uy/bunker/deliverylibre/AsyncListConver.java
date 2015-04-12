package uy.bunker.deliverylibre;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class AsyncListConver extends AsyncTask<String, String, JSONObject>{
	
	//Defino variables de objetos del layout
	private LinearLayout linearConv;
	private WebView wblogin;
	
	//Defino variables para http get
	private InputStream is = null;
	private JSONObject jObj = null;
	private String json = "";

	//Variables de cosas que me da json
    String buyer;
    String seller;
    String product;
    String date_updated;
    String id_buyer;
    String id_seller;
    String total;
    String currency;
    String precio;
    String id_compra;
    String type;
	
	private Activity activity;
	private String url;
	
	boolean codeHere;

	String user_id;
	String access_token;
	
	Context context;
	
	
	
	public AsyncListConver (Activity ac, String url, Context context1) {
		activity = ac;
		this.url = url;
		this.context = context1;

	}
	
	
	public AsyncListConver() {
		
	}
	
	
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
			//jArr = new JSONArray(json);
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// return JSON String
		return jObj;
	}
	
	
	
	@Override
	protected JSONObject doInBackground(String... params) {
		AsyncListConver listConver = new AsyncListConver();

		// Getting JSON from URL
		JSONObject json = listConver.getJSONFromUrl(url);
		return json;
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onPostExecute(JSONObject json) {
		super.onPostExecute(json);
		// Do stuff
		
		
		try {
			JSONArray cast;
			cast = json.getJSONArray("list");
			for (int i=0; i<cast.length(); i++) {
			    JSONObject conv = cast.getJSONObject(i);
			    
			   /*EJEMPLO DE LO QUE RECIBO
			    *  "product_name": "Cubre Mochila, Reflectivo,impermeable,p. Moto,bici O A Pie",
	            "nickname_seller": "RYR CHOICE",
	            "id_seller": 85197057,
	            "nickname_buyer": "DIEGOMAIDANA4",
	            "id_buyer": 151780312,
	            "total_amount": 150,
	            "currency_id": "UYU",
	            "status_compra": "confirmed",
	            "id_compra": 941380218,
	            "__v": 0,
	            "date_updated": "2015-04-12T02:18:09.803Z",
	            "date_created": "2015-04-06T17:45:34.000Z"*/
	            Log.i("compra", conv+"");
	             
			     buyer = conv.getString("nickname_buyer");
			     seller = conv.getString("nickname_seller");
			     product = conv.getString("product_name");
			    if(product.length()>30){
			    	product = product.substring(0, 30);
			    	product = product+"...";
			    }
			     date_updated = conv.getString("date_updated");
			     id_buyer = conv.getString("id_buyer");
			     id_seller = conv.getString("id_seller");
			     total = conv.getString("total_amount");
			     currency = conv.getString("currency_id");
			     precio = currency + " " + total;
			     id_compra = conv.getString("id_compra");
			     type = conv.getString("type");
			    
			    //ARMO LA PANTALLA
			    linearConv =(LinearLayout) activity.findViewById(R.id.linearConv);
			    
			    RelativeLayout rel = new RelativeLayout(context);
			    

			    //texto
			    TextView txtProducto = new TextView(context);
			    txtProducto.setText(product);
			    txtProducto.setId(R.id.chatVentas+i);
			    txtProducto.setTextColor(context.getResources().getColor(R.color.blue));
			    txtProducto.setTextSize(18);
			    
			    //fecha
			    TextView txtSeller = new TextView(context);
			    txtSeller.setText(seller);
			    txtSeller.setId(R.id.chatReciente+i);
			    txtSeller.setTextColor(context.getResources().getColor(R.color.blue));
			    txtSeller.setTextSize(13);
			    
			    TextView txtPrecio =  new TextView(context);
			    txtPrecio.setText(precio);
			    txtPrecio.setTextColor(context.getResources().getColor(R.color.blue));
			    txtPrecio.setTextSize(10);
			    
			  //view
			    LinearLayout linea = new LinearLayout(context);
			    RelativeLayout.LayoutParams vparams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, 1);
			    //vparams.height = 1;
			    //vparams.width = LayoutParams.MATCH_PARENT;
			    vparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			    linea.setLayoutParams(vparams);
			    linea.setPadding(10, 0, 10, 0);
			    linea.setBackgroundResource(R.color.linea);
			    
			    
			    RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams
		                ((int)LayoutParams.MATCH_PARENT,(int)LayoutParams.WRAP_CONTENT);
			    
			    RelativeLayout.LayoutParams dparams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			    dparams.addRule(RelativeLayout.BELOW, R.id.chatVentas+i);
			    txtSeller.setLayoutParams(dparams);
			    
			    RelativeLayout.LayoutParams Precioparams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			    Precioparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			    Precioparams.addRule(RelativeLayout.BELOW, R.id.chatReciente+i);
			    txtPrecio.setLayoutParams(Precioparams);
			    
			    rel.setLayoutParams(params);
			    rel.setPadding(25, 40, 25, 40);
			    rel.addView(txtProducto);
			    rel.addView(txtSeller);
			    rel.addView(txtPrecio);

			    rel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						SharedPreferences prefs = Prefs.get(context);
						SharedPreferences.Editor editor = prefs.edit();
						editor.putString("id_buyer", id_buyer);
						editor.putString("id_seller", id_seller);
						editor.putString("id_compra", id_compra);
						editor.putString("producto", product);
						editor.putString("type", type);
						editor.putString("buyer", buyer);
						editor.putString("seller", seller);
						editor.commit();
						
						gotoConversacion(context);
						
					}
				});
			  
			    
			    linearConv.addView(rel);
			    linearConv.addView(linea);
			    
			    
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
	
	protected void gotoConversacion(Context context) {
		Intent intent = new Intent(context.getApplicationContext(), Conversacion.class);
		context.startActivity(intent);
		cancel(true);
	}

}
