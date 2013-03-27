package org.example.asteroides;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore.Audio;
import android.widget.Toast;

public class ServicioMusica extends Service {
	
	private NotificationManager nm;
	private static final int ID_NOTIFICACION_CREAR = 1;
	
	MediaPlayer reproductor;
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "ServicioCreado", Toast.LENGTH_SHORT).show();
		reproductor = MediaPlayer.create(this, R.raw.ser_music);
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}
	
	/**
	 * 	El método onStartCommand() aparece a partir del "nivel de API 5"
	 * 	en sustitución del método onStart(). 
	 * 
	 * 	Para versiones inferiores a la 2.0 hay que crear un método onStart()
	 * 	y comentar el método onStartCommand()
	 */
	
	@Override
	public int onStartCommand(Intent intenc, int flags, int idArranque){
		Toast.makeText(this, "Servicio arrancado - onStartCommand() " + idArranque, Toast.LENGTH_SHORT).show();
		reproductor.start();
		Notification notificacion = new Notification (R.drawable.ic_launcher, "Creando Servicio de Musica", System.currentTimeMillis());
		PendingIntent intencionPendiente = PendingIntent.getActivity(this, 0, new Intent(this,Asteroides.class), 0);
		notificacion.setLatestEventInfo(this, "Reproduciendo música en Asteroides", "información adicional", intencionPendiente);
		// sonido de la notifación
		//notificacion.sound = Uri.parse("file://path//file.mp3");
		notificacion.sound = Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
		//notificacion.defaults |= Notification.DEFAULT_SOUND;
		notificacion.defaults |= Notification.DEFAULT_VIBRATE;  // necesita permiso en el fichero MANIFEST
		
		nm.notify(ID_NOTIFICACION_CREAR, notificacion);
		return START_STICKY;
	}
	
//	@Override
//	public void onStart(Intent intent, int startId){
//		Toast.makeText(this, "Servicio arrancado - onStart " + startId, Toast.LENGTH_SHORT).show();
//		reproductor.start();
//	}
	
	@Override
	public void onDestroy(){
		Toast.makeText(this, "Servicio detenido", Toast.LENGTH_SHORT).show();
		reproductor.stop();
		nm.cancel(ID_NOTIFICACION_CREAR);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}


