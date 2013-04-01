package org.example.asteroides;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import android.content.Context;
import android.content.SharedPreferences;

public class AlmacenPuntuacionesPreferencias implements AlmacenPuntuaciones {
	
	// Mod9: Almacenamiento de datos - Preferencias
	private static String PREFERENCIAS = "puntuaciones";
	private Context context;
	
	public AlmacenPuntuacionesPreferencias(Context context){
		this.context = context;
	}
	

	@Override
	public void guardaPuntuaciones(int puntos, String nombre, long fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("E, MMM dd, yyyy");
		String dateString = sdf.format(new Date(fecha));
		
		SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferencias.edit();
		editor.putString("puntuaciones", puntos + " " + nombre + " " + dateString);
		editor.commit();
		

	}

	@Override
	public Vector<String> listaPuntuaciones(int cantidad) {
		Vector<String> result = new Vector<String>();
		SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
		String s = preferencias.getString("puntuaciones", "");
		if (s != ""){
			result.add(s);
		}
		return result;
	}

}