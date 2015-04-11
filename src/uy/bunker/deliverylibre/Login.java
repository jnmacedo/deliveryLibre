package uy.bunker.deliverylibre;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;


public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Oculto Bar
      	getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
      	getActionBar().hide();
      				
      		
        setContentView(R.layout.activity_login);
        
        AsyncLogin asyncTask = new AsyncLogin(this,getString(R.string.inicio_url), false, this, false);
		asyncTask.execute();	
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
    
	@Override
	public void onBackPressed() {
		finish();
	}
}
