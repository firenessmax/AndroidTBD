package cl.citiaps.jefferson.taller_android_bd.views;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import cl.citiaps.jefferson.taller_android_bd.R;
import cl.citiaps.jefferson.taller_android_bd.controllers.HttpGet;
import cl.citiaps.jefferson.taller_android_bd.utilities.JsonHandler;
import cl.citiaps.jefferson.taller_android_bd.utilities.SystemUtilities;

/**
 * @author: Jefferson Morales De la Parra
 * Clase Fragmento (Lista) que se utiliza para mostrar una lista de items
 */
public class ItemList extends ListFragment {

    private BroadcastReceiver br = null;
    private final String URL_GET = "http://192.168.42.63:8080/sakila-backend/actors";
    private String jsonData;

    /**
     * Constructor. Obligatorio para Fragmentos!
     */
    public ItemList() {
    }// ItemList()

    /**
     * Método que se llama una vez que se ha creado la actividad que contiene al fragmento
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }// onActivityCreated(Bundle savedInstanceState)

    /**
     * Método que escucha las pulsaciones en los items de la lista
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = l.getItemAtPosition(position).toString();
        Fragment itemDetail = new ItemDetail();
        Bundle arguments = new Bundle();
        arguments.putString("item", item);
        String[] faltantes=(new JsonHandler()).getDetail(this.jsonData,position);
        arguments.putString("id", faltantes[0]);
        arguments.putString("lastUpdate", faltantes[1]);
        itemDetail.setArguments(arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, itemDetail);
        transaction.addToBackStack(null);
        transaction.commit();
    }// onListItemClick(ListView l, View v, int position, long id)

    /**
     * Método que se ejecuta luego que el fragmento es creado o restaurado
     */
    @Override
    public void onResume() {
        IntentFilter intentFilter = new IntentFilter("httpData");
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                JsonHandler jh = new JsonHandler();
                String s = intent.getStringExtra("data");
                try{
                    String[] actorsList = jh.getActors(s);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity()
                            , android.R.layout.simple_list_item_1, actorsList);
                    setListAdapter(adapter);
                }catch (java.lang.RuntimeException e) {
                    Log.i("TAG", "Error al recibir el mensaje");
                }

            }
        };
        getActivity().registerReceiver(br, intentFilter);
        SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
        if (su.isNetworkAvailable()) {
            Log.i("TBD_TAG","Conectado a la red");
            new HttpGet(getActivity().getApplicationContext(),this).execute(URL_GET);
        }else{
            Log.e("TBD_TAG","error de red");
            Toast.makeText(getActivity().getApplicationContext(),"Error de conexión , intenta de nuevo o más tarde",Toast.LENGTH_LONG).show();
        }
        super.onResume();
    }// onResume()

    /**
     * Método que se ejecuta luego que el fragmento se detiene
     */
    @Override
    public void onPause() {
        if (br != null) {
            getActivity().unregisterReceiver(br);
        }
        super.onPause();
    }// onPause()
    public void setJsonData(String s){
        this.jsonData=s;
    }
}// ItemList extends ListFragment