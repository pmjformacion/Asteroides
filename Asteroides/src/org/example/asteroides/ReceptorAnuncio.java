package org.example.asteroides;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.*;


public class ReceptorAnuncio extends BroadcastReceiver {

	@Override
	public void onReceive(Context contexto, Intent intencion) {
		Intent i = new Intent(contexto, AcercaDe.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		contexto.startActivity(i);
	}

}
