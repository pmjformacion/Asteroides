package org.example.asteroides;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

public class AlmacenPuntuacionesFicheroInterno implements AlmacenPuntuaciones {

	private static String FICHERO = "puntuaciones.txt";
	private Context context;
	
	
	public AlmacenPuntuacionesFicheroInterno(Context context) {
		this.context = context;
		
	}
	@Override
	public void guardaPuntuaciones(int puntos, String nombre, long fecha) {
		try {
			// para poner la fecha en formato diasemana, mes dia, año
			SimpleDateFormat sdf = new SimpleDateFormat("E, MMM dd, yyyy");
			String dateString = sdf.format(new Date(fecha));
			
			FileOutputStream f = context.openFileOutput(FICHERO, Context.MODE_APPEND);
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
		try {
			FileInputStream f = context.openFileInput(FICHERO);
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
