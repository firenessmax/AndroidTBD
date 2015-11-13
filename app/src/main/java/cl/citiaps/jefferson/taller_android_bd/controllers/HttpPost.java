package cl.citiaps.jefferson.taller_android_bd.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import cl.citiaps.jefferson.taller_android_bd.utilities.JsonHandler;
import cl.citiaps.jefferson.taller_android_bd.views.NewItem;

/**
 * @author:
 */
public class HttpPost extends AsyncTask<String, Void, String> {
        private Context context;
        private String data;
        private NewItem ni;

        /**
         * Constructor
         */
        public HttpPost(Context context, String data,NewItem ni) {
            this.context = context;
            this.data=data;
            this.ni=ni;
        }// HttpGet(Context context)

        /**
         * Método que realiza la petición al servidor
         */
        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection connection=null;
            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestProperty("content-type", "application/json; charset=utf-8");
                connection.setRequestProperty("Accept", "application/json");
                JSONObject cred   = new JSONObject();
                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();
                Log.i("TBD_TAG","response code :"+connection.getResponseCode());

            } catch (MalformedURLException e) {
                Log.e("ERROR:50:", this.getClass().toString() + " " + e.toString());
            } catch (ProtocolException e) {
                Log.e("ERROR:52:", this.getClass().toString() + " " + e.toString());
            } catch (IOException e) {
                Log.e("ERROR:54:", this.getClass().toString() + " " + e.toString());
            }finally {
                if(connection!=null) {
                    connection.disconnect();
                    try {
                        return connection.getResponseMessage();
                    } catch (IOException e) {
                        return null;
                    }
                }
            }

            return null;
        }// doInBackground(String... urls)

        /**
         * Método que manipula la respuesta del servidor
         */
        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent("httpPostResponse").putExtra("data", result);

            context.sendBroadcast(intent);
            this.ni.added(result);
            this.ni.getFragmentManager().popBackStackImmediate();
        }// onPostExecute(String result)

}// HttpPost