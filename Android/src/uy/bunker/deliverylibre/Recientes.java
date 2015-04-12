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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class Recientes extends Activity {

	//Defino variables a utilizar
	SharedPreferences prefs;
	Button recent;
	Button ventas;
	Button compras;
	Button ajustes;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Oculto Bar
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		
				
		setContentView(R.layout.activity_recientes);
		
		// Instancia de SharedPreferences
		prefs = Prefs.get(this);
				
		Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
		recent = (Button) findViewById(R.id.BtnRecent);
		ventas = (Button) findViewById(R.id.BtnVentas);
		compras = (Button) findViewById(R.id.BtnCompras);
		ajustes = (Button) findViewById(R.id.BtnConfig);
		
		recent.setTypeface(font);
		ventas.setTypeface(font);
		compras.setTypeface(font);
		ajustes.setTypeface(font);
		
		recent.setTextColor(color.blue);
		ventas.setTextColor(color.white);
		compras.setTextColor(color.white);
		ajustes.setTextColor(color.white);
		
		ajustes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoAjustes();
			}
		});
		
		compras.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoCompras();
			}
		});
		
		ventas.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoVentas();
			}
		});
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recientes, menu);
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
	
	protected void gotoCompras() {
		Intent intent = new Intent(getApplicationContext(), Compras.class);
		startActivity(intent);
		finish();
	}
	
	protected void gotoVentas() {
		Intent intent = new Intent(getApplicationContext(), Ventas.class);
		startActivity(intent);
		finish();
	}
}
