package uy.bunker.deliverylibre;

import uy.bunker.deliverylibre.R.color;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Compras extends Activity {

	
	//Defino variables a utilizar
	SharedPreferences prefs;
	Button compras;
	Button ventas;
	Button recientes;
	Button ajustes;
	String token;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Oculto Bar
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		
				
		setContentView(R.layout.activity_compras);
		
		// Instancia de SharedPreferences
		prefs = Prefs.get(this);
				
		Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
		compras = (Button) findViewById(R.id.BtnCompras);
		ventas = (Button) findViewById(R.id.BtnVentas);
		recientes = (Button) findViewById(R.id.BtnReciente);
		ajustes = (Button) findViewById(R.id.BtnConfig);
		
		compras.setTypeface(font);
		ventas.setTypeface(font);
		recientes.setTypeface(font);
		ajustes.setTypeface(font);
		
		compras.setTextColor(color.blue);
		ventas.setTextColor(color.white);
		recientes.setTextColor(color.white);
		ajustes.setTextColor(color.white);
		
		ajustes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoAjustes();
			}
		});
		
		recientes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoRecientes();
			}
		});
		
		ventas.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoVentas();
			}
		});
		
		token = prefs.getString("access_token", "");
		 
		//Voy a obtener los chats recientes
		AsyncListConver asyncTask = new AsyncListConver(this,"https://deliverylibre.herokuapp.com/convBuyer?token="+token, this);
		asyncTask.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compras, menu);
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
	
	protected void gotoAjustes() {
		Intent intent = new Intent(getApplicationContext(), Ajustes.class);
		startActivity(intent);
		finish();
	}
	
	protected void gotoRecientes() {
		Intent intent = new Intent(getApplicationContext(), Recientes.class);
		startActivity(intent);
		finish();
	}
	
	protected void gotoVentas() {
		Intent intent = new Intent(getApplicationContext(), Ventas.class);
		startActivity(intent);
		finish();
	}
	
	
}
