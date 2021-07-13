package com.example.tourismfyp.DahBoard.OrginazerPanel.PostHotels;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Geolocation {
    public static void getAdress(String locationAdress, Context context, Handler handler) {
        Thread thread = new Thread() {
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String Latitude = null;
                String Longitude = null;
                try {
                    List addressresult =  geocoder.getFromLocationName(locationAdress, 1);

                    if (addressresult!=null  && addressresult.size() > 0) {
                        Address address = (Address) addressresult.get(0);
                        Latitude = String.valueOf(address.getLatitude());
                        Longitude = String.valueOf(address.getLongitude());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally
                {
                    Message message = Message.obtain();
                message.setTarget(handler);
                if (Latitude!=null && Longitude!=null)
                {
                    message.what=1;
                    Bundle bundle= new Bundle();
                    bundle.putString("lati",Latitude);
                    bundle.putString("longi",Longitude  );
                    message.setData(bundle);
                }
                message.sendToTarget();
                }

            }
        };
        thread.start();
    }
}
