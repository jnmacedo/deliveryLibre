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

public class Ajustes extends Activity {

	//Defino variables a utilizar
	SharedPreferences prefs;
	Button ajustes;
	Button compras;
	Button recientes;
	Button ventas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Oculto Bar
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		
				
		setContentView(R.layout.activity_ajustes);
		
		// Instancia de SharedPreferences
		prefs = Prefs.get(this);
				
		Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );
		ajustes = (Button) findViewById(R.id.BtnConfig);
		compras = (Button) findViewById(R.id.BtnCompras);
		recientes = (Button) findViewById(R.id.BtnReciente);
		ventas = (Button) findViewById(R.id.BtnVentas);
		
		ajustes.setTypeface(font);
		compras.setTypeface(font);
		recientes.setTypeface(font);
		ventas.setTypeface(font);
		
		ajustes.setTextColor(color.blue);
		compras.setTextColor(color.white);
		recientes.setTextColor(color.white);
		ventas.setTextColor(color.white);
		
		ventas.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoVentas();
			}
		});
		
		recientes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoRecientes();
			}
		});
		
		compras.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoCompras();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ajustes, menu);
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
	
	protected void gotoVentas() {
		Intent intent = new Intent(getApplicationContext(), Ventas.class);
		startActivity(intent);
		finish();
	}
	
	protected void gotoRecientes() {
		Intent intent = new Intent(getApplicationContext(), Recientes.class);
		startActivity(intent);
		finish();
	}
	
	protected void gotoCompras() {
		Intent intent = new Intent(getApplicationContext(), Compras.class);
		startActivity(intent);
		finish();
	}
}
