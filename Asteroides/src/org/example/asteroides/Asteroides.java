package org.example.asteroides;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Asteroides extends Activity {

	// variables para los botones de Asteroides
	private Button bAcercaDe;
	private Button bSalir;
	private Button bConfigurar;
	
	
	
	
	
	/**
	 * Código para añadir onClickListener a los botones de esta actividad
	 * 
	 * Se crean variables privadas para los botones y al sobreescribir la 
	 * función 'OnCreate' de la actividad 'Asteroides' se definen también las 
	 * funciones OnClickListener para cada uno de los botones
	 */

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
				lanzarAcercaDe(null);
			}
		
		});
		
		//Añadimos un Listener Event (oyente de eventos)
		// para el botón 'Salir'
		bSalir = (Button) findViewById(R.id.Button04);
		bSalir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lanzarSalir(null);
			}
		});			

		//Añadimos un Listener Event (oyente de eventos)
		// para el botón 'Configurar'
		bConfigurar = (Button) findViewById(R.id.Button02);
		bConfigurar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					lanzarPreferencias(null);
				}
		});
		
	}
				
	

/** Comento esta parte de código, porque originalmente al crear la 
 *  actividad 'Asteroides" con el Eclipse, la plataforma de desarrollo
 *  Android me definió el método 'OnCreateOptionsMenu' con un menú
 *  que también creó llamado 'main.xml' en la carpeta res/menu	
 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

* aquí termina el código comentado del creado originalmente al crear la activiad 'Asteroides'	
*/	
	
	
	public void lanzarAcercaDe(View view){
		// Arranca la actividad 'Acerca De'
		Intent i = new Intent(this, AcercaDe.class);
		startActivity(i);
	}
	
	
	public void lanzarSalir(View view){
		// Finaliza la actividad 'Asteroides'
		finish();
	}

	public void lanzarPreferencias(View view){
		// Arranca la actividad 'Preferencia'
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}	
	
	
	/**
	 * Código para activar el menú en la actividad 'Asteroides'
	 */
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		/** Valor devuelto por el método
		 * true -> al devolver true queremos que el menú ya está visible
		 */
		return true;	
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()) {
		case R.id.acercaDe:
			lanzarAcercaDe(null);
			break;
		case R.id.config:
			lanzarPreferencias(null);
			break;
		}
		/** Valor devuelto por el método
		 * true ->  indica que hemos capturado el evento y 
		 * 			no queremos que se propague 
		 */
		return true;	
	}
	
	
}
