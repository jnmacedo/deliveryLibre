package uy.bunker.deliverylibre;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Conversacion extends Activity {
	
	WebView wbConversacion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        //Oculto Bar
      	getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
      	getActionBar().hide();
      	
      	
		setContentView(R.layout.activity_conversacion);
		
		wbConversacion = (WebView) findViewById(R.id.wbConversacion);
		
		
		WebSettings webSettings = wbConversacion.getSettings();
		webSettings.setJavaScriptEnabled(true); // enable javascript
		webSettings.setDomStorageEnabled(true);
		wbConversacion.getSettings().setJavaScriptCanOpenWindowsAutomatically(
				true);
		wbConversacion.setWebChromeClient(new WebChromeClient());

		wbConversacion.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {

			}
			
			@Override
			public void onPageFinished(WebView view, String url) {

			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				
			}
		});
		
		SharedPreferences prefs = Prefs.get(this);
		String id_compra = prefs.getString("id_compra", ""); 
		String buyer = prefs.getString("buyer", "");
		String seller = prefs.getString("seller", "");
		String type = prefs.getString("type", "");
		
		String product = prefs.getString("producto", "");
		String envio="";
		TextView producto = (TextView) findViewById(R.id.producto);
		TextView nickname = (TextView) findViewById(R.id.nickname);
		producto.setText(product);
		if(type.equals("buyer")){
			envio = buyer;
			Log.i("prueba", envio);
			nickname.setText(envio);
		}
		else if(type.equals("seller")){
			envio = seller;
			Log.i("prueba", envio);
			nickname.setText(envio);
		}
		
		
		
		
		
		wbConversacion.loadUrl("http://deliverylibre.herokuapp.com/chat?nickname="+envio+"&idcompra="+id_compra);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conversacion, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
