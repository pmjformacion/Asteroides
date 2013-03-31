package org.example.asteroides;

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
		SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferencias.edit();
		editor.putString("puntuaciones", puntos + " " + nombre);
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
