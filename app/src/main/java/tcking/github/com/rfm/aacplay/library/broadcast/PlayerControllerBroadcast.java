package tcking.github.com.rfm.aacplay.library.broadcast;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import tcking.github.com.rfm.aacplay.MainActivity;
import tcking.github.com.rfm.aacplay.MyApplication;
import tcking.github.com.rfm.aacplay.library.media.MediaManager;
import tcking.github.com.rfm.aacplay.library.media.MediaPlayerService;
import tcking.github.com.rfm.aacplay.library.radio.RadioManager;
import tcking.github.com.rfm.aacplay.library.radio.RadioPlayerService;
import tcking.github.com.rfm.aacplay.pojos.Finnish;

/**
 * Created by mertsimsek on 04/11/15.
 */
public class PlayerControllerBroadcast extends BroadcastReceiver{

    /**
     * This receiver receives STOP controlling between player services
     * For instances, If MediaPlayerService is running and playing stream
     * and then RadioPlayerService requested play radio stream, Service sends broadcast
     * that stop MediaPlayerService. And If RadioPlayerService is running and
     * playing radio stream, RadioPlayerService will be stopped here when MediaPlayerService
     * requested play media. This is the way we communicate between services to stop
     * each other.
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isRadioServiceBinded = RadioManager.getService() == null ? false : true;
        boolean isMediaServiceBinded = MediaManager.getService() == null ? false : true;

        String action = intent.getAction();

        if(action.equals(MediaPlayerService.ACTION_RADIOPLAYER_STOP)
                && isRadioServiceBinded
                && RadioManager.getService().isPlaying()){
            RadioManager.getService().stop();
        }
        else if(action.equals(RadioPlayerService.ACTION_MEDIAPLAYER_STOP)
                && isMediaServiceBinded
                && MediaManager.getService().isPlaying()){
            MediaManager.getService().stop();
        }






        else if(action.equals(RadioPlayerService.NOTIFICATION_INTENT_PLAY_PAUSE)
                && isRadioServiceBinded){
          /*  if(RadioManager.getService().isPlaying())
                RadioManager.getService().stop();
            else
                RadioManager.getService().resume();*/

            EventBus.getDefault().post(new Finnish());


          if(MyApplication.isActivityVisible()) {
              Log.d("aaaaaaaa","aaaaaaaa");
              EventBus.getDefault().post("notiClicked");
          }else{
              Log.d("aaaaaaaa","bbbbbbbb");
              if(RadioManager.getService().isPlaying())
                  RadioManager.getService().stop();
              else
                  RadioManager.getService().resume();
          }

        }else if(action.equals(RadioPlayerService.NOTIFICATION_INTENT_CANCEL)
                && isRadioServiceBinded){
            RadioManager.getService().stopFromNotification();
        }


        else if(action.equals(RadioPlayerService.NOTIFICATION_INTENT_OPEN_PLAYER)
                && isRadioServiceBinded){

       //     Log.d("imageclicked","imageclicked ");

            Intent it = new Intent("intent.my.action");
            it.setComponent(new ComponentName(context.getPackageName(), MainActivity.class.getName()));
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(it);


        }

        else if(action.equals(MediaPlayerService.NOTIFICATION_INTENT_PLAY_PAUSE)
                && isMediaServiceBinded){
            if(MediaManager.getService().isPlaying())
                MediaManager.getService().pause();
            else
                MediaManager.getService().resume();
        }else if(action.equals(MediaPlayerService.NOTIFICATION_INTENT_CANCEL)
                && isMediaServiceBinded){
            MediaManager.getService().stopFromNotification();
        }

    }
}
