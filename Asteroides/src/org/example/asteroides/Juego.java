package org.example.asteroides;

import android.app.Activity;
import android.os.Bundle;

public class Juego extends Activity {
	
	
	// variable creada para correguir el consumo de CPU 
	// cuando la aplicación está en pausa
	
	private VistaJuego vistaJuego;
	
	  @Override public void onCreate(Bundle savedInstanceState) {

          super.onCreate(savedInstanceState);

          setContentView(R.layout.juego);
          vistaJuego = (VistaJuego) findViewById(R.id.VistaJuego);

   }
	  
	  @Override
	  protected void onPause() {
		  super.onPause();
		  vistaJuego.getmSensorManager().unregisterListener(vistaJuego);
		  vistaJuego.getThread().pausar();
	  }
	  
	  @Override
	  protected void onResume() {
		  super.onResume();
		  vistaJuego.registerSensorManager(getApplicationContext());
		  vistaJuego.getThread().reanudar();
	  }

	  @Override
	  protected void onDestroy() {
		  vistaJuego.getThread().detener();
		  super.onDestroy();
	  }
	  
}
