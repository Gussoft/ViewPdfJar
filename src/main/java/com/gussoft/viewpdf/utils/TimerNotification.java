package com.gussoft.viewpdf.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimerNotification {
    public static void main(String[] args) {
        Date horaDespertar = new Date(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        c.setTime(horaDespertar);
        System.out.println(c.get(Calendar.DAY_OF_WEEK));
        // Si la hora es posterior a las 8am se programa la alarma para el dia siguiente
        if (c.get(Calendar.HOUR_OF_DAY) >= 22) {
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
        }

        c.set(Calendar.HOUR_OF_DAY, 16);
        c.set(Calendar.MINUTE, 6);
        c.set(Calendar.SECOND, 0);

        horaDespertar = c.getTime();
        System.out.println(horaDespertar);
        System.out.println(c.get(Calendar.DAY_OF_WEEK));
        // El despertador suena cada 24h (una vez al dia)
        int tiempoRepeticion = 86400000;

        // Programamos el despertador para que "suene" a las 8am todos los dias
        Timer temporizador = new Timer();
        temporizador.schedule(new Temporizador(), horaDespertar, tiempoRepeticion);
    }
}
