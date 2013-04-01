package org.example.asteroides;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Asteroides extends Activity {

	// variables para los botones de Asteroides
	private Button bAcercaDe;
	private Button bSalir;
	private Button bConfigurar;
	
	private MediaPlayer mp;
	
	public static AlmacenPuntuaciones almacen = new AlmacenPuntuacionesArray();
	
	
	
	/**
	 * C�digo para a�adir onClickListener a los botones de esta actividad
	 * 
	 * Se crean variables privadas para los botones y al sobreescribir la 
	 * funci�n 'OnCreate' de la actividad 'Asteroides' se definen tambi�n las 
	 * funciones OnClickListener para cada uno de los botones
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// A�adimos un Listener Event (oyente de eventos)
		// para el bot�n 'Acerca de'
		bAcercaDe = (Button) findViewById(R.id.Button03);
		bAcercaDe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lanzarAcercaDe(null);
			}
		
		});
		
		//A�adimos un Listener Event (oyente de eventos)
		// para el bot�n 'Salir'
		bSalir = (Button) findViewById(R.id.Button05);
		bSalir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lanzarSalir(null);
			}
		});			

		//A�adimos un Listener Event (oyente de eventos)
		// para el bot�n 'Configurar'
		bConfigurar = (Button) findViewById(R.id.Button02);
		bConfigurar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					lanzarPreferencias(null);
				}
		});
		
		//A�adimos un Listener Event (oyente de eventos)
		// para el bot�n 'Puntuaciones'
		bSalir = (Button) findViewById(R.id.Button04);
		bSalir.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				lanzarPuntuaciones(null);
			}
		});	
		
		Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
		
		// Se a�ade m�sica a la aplicaci�n (m�dulo 6)
		mp = MediaPlayer.create(this, R.raw.audio);
		mp.start();
		
		almacen = new AlmacenPuntuacionesPreferencias(this);
	}
				

	
	public void lanzarAcercaDe(View view){
		// Arranca la actividad 'Acerca De'
		Intent i = new Intent(this, AcercaDe.class);
		startActivity(i);
	}
	
	
	public void lanzarSalir(View view){
		// Finaliza la actividad 'Asteroides'
		finish();
		//lanzarPuntuaciones(null);
	}

	public void lanzarPreferencias(View view){
		// Arranca la actividad 'Preferencia'
		Intent i = new Intent(this, Preferencias.class);
		startActivity(i);
	}	
	
	
	public void lanzarPuntuaciones (View view){
		Intent i = new Intent(this, Puntuaciones.class);
		startActivity(i);
	}
	
	
	public void lanzarJuego(View view){
		Intent i = new Intent(this, Juego.class);
		startActivity(i);
	}
	
	// Modulo 9: para que nos devuelva la puntuaci�n del juego
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1234 & resultCode == RESULT_OK & data != null){
			int puntuacion = data.getExtras().getInt("puntuacion");
			String nombre = "Yo";
			// Mejor leerlo desde un Dialog o una nueva actividad AlterDialog.Builder
			almacen.guardaPuntuaciones(puntuacion, nombre, System.currentTimeMillis());
			lanzarPuntuaciones(null);		
		}
	}
	
	
	/**
	 * C�digo para activar el men� en la actividad 'Asteroides'
	 */
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		/** Valor devuelto por el m�todo
		 * true -> al devolver true queremos que el men� ya est� visible
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
		/** Valor devuelto por el m�todo
		 * true ->  indica que hemos capturado el evento y 
		 * 			no queremos que se propague 
		 */
		return true;	
	}
	
	
	@Override
	protected void onStart(){
		super.onStart();
		Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
		mp.start();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
	}	
	
	@Override
	protected void onStop(){
		super.onStop();
		Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
		mp.pause();
	}	
	
	@Override
	protected void onRestart(){
		super.onRestart();
		Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
		mp.start();
	}	
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
		mp.stop();
	}	
	
	
	/** BEGIN SAVE STATE
	 * M�dulo 6 - Funciones para guardar el estado de Asteroides.
	 * 
	 * Guardo donde estaba el sonido de audio y luego reinicio
	 * el audio donde se hab�a quedado
	 * 
	 * Implemento la soluci�n que dan ellos en el curso
	 * 
	 */
	
	
	@Override
	protected void onSaveInstanceState(Bundle estadoGuardado) {
		super.onSaveInstanceState(estadoGuardado);
		if (mp != null){
			int pos = mp.getCurrentPosition();
			estadoGuardado.putInt("posicion", pos);
		}
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle estadoGuardado){
		super.onRestoreInstanceState(estadoGuardado);
		if (estadoGuardado != null && mp != null){
			int pos = estadoGuardado.getInt("posicion");
			mp.seekTo(pos);
		}
	}
	
	/** END SAVE STATE */
	
	
	
}
