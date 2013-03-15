package org.example.asteroides;


import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VistaJuego extends View implements SensorEventListener {

	// //// ASTEROIDES //////
	private Vector<Grafico> Asteroides; // Vector con los Asteroides
	private int numAsteroides= 5; // Número inicial de asteroides
	private int numFragmentos= 3; // Fragmentos en que se divide
	
	
	// //// NAVE //////
	private Grafico nave;	// Grafico de la nave
	private int giroNave;	// Incremento de dirección
	private float aceleracionNave;	// aumento de velocidad
	
	// Incremento estándar de giro y aceleración
	private static final int PASO_GIRO_NAVE = 5;
	private static final float PASO_ACELERACION_NAVE = 0.5f;
	
	
	// //// THREAD y TIEMPO //////
	// Thread encargado de procesar el juego
	private ThreadJuego thread = new ThreadJuego();
	// Cada cuanto queremos procesar cambios (ms)
	private static int PERIODO_PROCESO = 50;
	// Cuando se realizó el último proceso.
	private long ultimoProceso = 0;
	
	
	// Para el manejo de la nave con pantalla dactil
	private float mX=0, mY=0;
	private boolean disparo=false;
	
	
	
	/**
	 * Módulo 5: Entradas en Android
	 * Apartado: Introduciendo movimiento en Asteroides
	 * @author impaco
	 *
	 *Creo la clase 'ThreadJuego'
	 */

	class ThreadJuego extends Thread{
		private boolean pausa, corriendo;
		
		public synchronized void pausar(){
			pausa=true;
		}
		
		public synchronized void reanudar(){
			pausa=false;
			notify();
		}
		
		public void detener(){
			corriendo=false;
			if (pausa) reanudar();
		}
		
		@Override 
		public void run(){
			corriendo = true;
			while (corriendo){
				actualizaFisica();
				synchronized (this){
					while(pausa) {
						try {
							wait();
						} catch (Exception e) {
							
						}
						
					}
				}
			}
		}
	}

	public VistaJuego(Context context, AttributeSet attrs) {

         super(context, attrs);

         Drawable drawableNave, drawableAsteroide, drawableMisil;

         drawableAsteroide = context.getResources().getDrawable(R.drawable.asteroide1);
         drawableNave = context.getResources().getDrawable(R.drawable.nave);

         nave = new Grafico(this, drawableNave);
         Asteroides = new Vector<Grafico>();
         
         for (int i = 0; i < numAsteroides; i++) {
        	 Grafico asteroide = new Grafico(this, drawableAsteroide);
        	 asteroide.setIncY(Math.random() * 4 - 2);
        	 asteroide.setIncX(Math.random() * 4 - 2);
        	 asteroide.setAngulo((int) (Math.random() * 360));
        	 asteroide.setRotacion((int) (Math.random() * 8 - 4));
        	 Asteroides.add(asteroide);
         }
         
         
         /**
          * Manejo de la nave con el sensor de orientación
          */
         // Registramos el sensor e indicamos que nuestro objeto
         // recogerá la llamada callback
         // Manejo por el sensor de orientación
//         SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
//         List<Sensor> listSensors = mSensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
//         if (!listSensors.isEmpty()){
//        	 Sensor orientationSensor = listSensors.get(0);
//        	 mSensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
//         }
         
         
         /**
          *  Modifica el ejemplo anterior para utilizar el sensor de aceleración en lugar del de orientación.
          *  Gracias a la fuerza de gravedad que la Tierra ejerce sobre el terminal podremos saber si este 
          *  está horizontal. En caso de que la nave este horizontal (o casi) no ha de girar, pero cuando 
          *  el terminal se incline, la nave a de girar proporcionalmente a esta inclinación. Utiliza los 
          *  programas anteriores para  descubrir que eje (x, y o z) es el que te interesa y el rango de 
          *  valores que proporciona.
          * */
         SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
          List<Sensor> listSensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
         if (!listSensors.isEmpty()){
        	 Sensor orientationSensor = listSensors.get(0);
        	 mSensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_GAME);
         }
   
         
    
         

	}

	@Override protected void onSizeChanged(int ancho, int alto,
                                          int ancho_anter, int alto_anter) {

		super.onSizeChanged(ancho, alto, ancho_anter, alto_anter);

		// Iniciamos la nave en el medio justo de la pantalla
		nave.setPosX((ancho-nave.getAncho())/2);
		nave.setPosY((alto-nave.getAlto())/2);

		// Una vez que conocemos nuestro ancho y alto.
		for (Grafico asteroide: Asteroides) {
			do {
				asteroide.setPosX(Math.random()*(ancho-asteroide.getAncho()));
				asteroide.setPosY(Math.random()*(alto-asteroide.getAlto()));
			} while (asteroide.distancia(nave) < (ancho+alto)/5);
         }
		
		// se llama al método run del hilo de ejecución. Es un método que es un 
		// bucle infinito que continuamente llama a 'actualizaFisica()'
		ultimoProceso = System.currentTimeMillis();
		thread.start();

	}



   @Override protected synchronized void  onDraw(Canvas canvas) {
	   super.onDraw(canvas);
       for (Grafico asteroide: Asteroides) {
    	   asteroide.dibujaGrafico(canvas);
         }
       nave.dibujaGrafico(canvas);
	}

	
   // Código introducido en Módulo 5: Entradas en Android
   // Apartado: Introduciendo movimiento en Android.
   protected void actualizaFisica() {
       long ahora = System.currentTimeMillis();
       // No hagas nada si el período de proceso no se ha cumplido.
       if (ultimoProceso + PERIODO_PROCESO > ahora) {
             return;
       }
       // Para una ejecución en tiempo real calculamos retardo           
       double retardo = (ahora - ultimoProceso) / PERIODO_PROCESO;
       ultimoProceso = ahora; // Para la próxima vez
       // Actualizamos velocidad y dirección de la nave a partir de 
       // giroNave y aceleracionNave (según la entrada del jugador)
       nave.setAngulo((int) (nave.getAngulo() + giroNave * retardo));
       double nIncX = nave.getIncX() + aceleracionNave *
                            Math.cos(Math.toRadians(nave.getAngulo())) * retardo;
       double nIncY = nave.getIncY() + aceleracionNave * 
                           Math.sin(Math.toRadians(nave.getAngulo())) * retardo;
       // Actualizamos si el módulo de la velocidad no excede el máximo
       if (Math.hypot(nIncX,nIncY) <= Grafico.getMaxVelocidad()){
             nave.setIncX(nIncX);
             nave.setIncY(nIncY);
       }
       
       // Actualizamos posiciones X e Y
       nave.incrementaPos(retardo);
       for (Grafico asteroide : Asteroides) {
             asteroide.incrementaPos(retardo);
       }
   }
   
   // función para el control de los eventos de la pantalla tactil
   // añadidos en el módulo 5 del curso
   @Override
   public boolean onTouchEvent(MotionEvent event){
	   super.onTouchEvent(event);
	   float x = event.getX();
	   float y = event.getY();
	   switch (event.getAction()){
	   case MotionEvent.ACTION_DOWN:	
		   disparo=true;
		   break;
	   case MotionEvent.ACTION_MOVE:
		   float dx = Math.abs(x - mX);
		   float dy = Math.abs(y - mY);
		   if(dy<12 && dx>12){
			   giroNave = Math.round((x - mX) /4);
			   disparo = false;
		   } else if (dx<12 && dy>12){
			   aceleracionNave = Math.round((mY - y) / 50);
			   disparo = false;
		   }
		   break;
	   case MotionEvent.ACTION_UP:
		   giroNave = 0;
		   aceleracionNave = 0;
		   if (disparo){
//			   ActivaMisil(); //Función todavía no implementada
		   }
		   break;
	   } // end switch (event.getAction())
	   
	   mX = x; mY = y;
	   return true;
   
   }
   
   /** MÓDULO 5 - Utilización de Sensores en Asteroides
    * Métodos 'onAccuracyChanged' y 'onSensorChange' que implementan
    * la interfaz 'SensorEventListener'
    */
   
   @Override
   public void onAccuracyChanged(Sensor sensor, int accuracy) {}
   
   private boolean hayValorInicial = false;
   private float valorInicial;
   
   @Override
   public void onSensorChanged(SensorEvent event){
	   float valor = event.values[1];
	   if (!hayValorInicial){
		   valorInicial = valor;
		   hayValorInicial = true;
	   }
	   giroNave = (int) (valor - valorInicial)/3;
   }

public ThreadJuego getThread() {
	return thread;
}
	
}
