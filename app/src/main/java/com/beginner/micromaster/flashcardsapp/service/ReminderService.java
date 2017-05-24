package com.beginner.micromaster.flashcardsapp.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.beginner.micromaster.flashcardsapp.R;
import com.beginner.micromaster.flashcardsapp.activities.MainActivity;

/**
 * Created by praxis on 23/05/17.
 */

public class ReminderService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        int NOTIFICATION_ID = 2;

        int icon = android.R.drawable.ic_lock_idle_alarm;
        Uri uriNotification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(pendingIntent)
                .setSmallIcon(icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), icon))
                .setSound(uriNotification)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
