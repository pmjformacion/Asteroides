package org.example.asteroides;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Asteroides extends Activity {

	private Button bAcercaDe;
	private Button bSalir;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Añadimos un Listener Event (oyente de eventos)
		// para el botón 'Acerca de'
		bAcercaDe = (Button) findViewById(R.id.Button03);
		bAcercaDe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lanzarAcercaDe(null);
			}
		
		});
		
		//Añadimos un Listener Event (oyente de eventos)
		// para el botón 'Salir'
		bSalir = (Button) findViewById(R.id.Button04);
		bSalir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				lanzarSalir(null);
			}
		
		});
				
				
	}

	
	// 

	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void lanzarAcercaDe(View view){
		Intent i = new Intent(this, AcercaDe.class);
		startActivity(i);
	}
	
	
	public void lanzarSalir(View view){
		finish();
	}

}
