package uy.bunker.deliverylibre;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
	public static SharedPreferences get(Context context){
		return context.getSharedPreferences("DL_prefs", 0);
	}
	
}
