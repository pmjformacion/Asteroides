package org.example.asteroides;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class AlmacenPuntuacionesFicheroExterno implements AlmacenPuntuaciones {

	private static String FICHERO = Environment.getExternalStorageDirectory() + "/puntuaciones.txt";
	private Context context;
	
	
	public AlmacenPuntuacionesFicheroExterno(Context context) {
		this.context = context;
		
	}
	@Override
	public void guardaPuntuaciones(int puntos, String nombre, long fecha) {
		// Comprueba si la memoria externa est� protegida contra escritura
		String stadoSD = Environment.getExternalStorageState();
		if (!stadoSD.equals(Environment.MEDIA_MOUNTED)){
			Toast.makeText(context, "No puedo escribir en la memoria externa", Toast.LENGTH_LONG).show();
			return;
		}
		
		try {
			// para poner la fecha en formato diasemana, mes dia, a�o
			SimpleDateFormat sdf = new SimpleDateFormat("E, MMM dd, yyyy");
			String dateString = sdf.format(new Date(fecha));
			
			FileOutputStream f = new FileOutputStream(FICHERO, true);
			String texto = puntos + " " + " " + nombre + " " + dateString + "\n";
			f.write(texto.getBytes());
			f.close();
		} catch (Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}

	}

	@Override
	public Vector<String> listaPuntuaciones(int cantidad) {
		Vector <String> result = new Vector<String>();
		
		// Compruebo si tengo acceso a la memoria externa
		String stadoSD = Environment.getExternalStorageState();
		if (!stadoSD.equals(Environment.MEDIA_MOUNTED) && !stadoSD.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			Toast.makeText(context, "No puedo leer en la memoria externa", Toast.LENGTH_LONG).show();
			return result;
			
		}
		
		
		try {
			FileInputStream f = new FileInputStream(FICHERO);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(f));
			int n = 0;
			String linea;
			do {
				linea = entrada.readLine();
				if (linea != null) {
					result.add(linea);
					n++;
				}
			} while (n < cantidad && linea != null);
			f.close();
		} catch (Exception e) {
			Log.e("Asteroides", e.getMessage(), e);
		}
		return result;
	}

}
