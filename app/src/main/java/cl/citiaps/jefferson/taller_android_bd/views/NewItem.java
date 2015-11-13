package cl.citiaps.jefferson.taller_android_bd.views;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author:
 */
import cl.citiaps.jefferson.taller_android_bd.R;
import cl.citiaps.jefferson.taller_android_bd.controllers.HttpPost;
import cl.citiaps.jefferson.taller_android_bd.utilities.JsonHandler;
import cl.citiaps.jefferson.taller_android_bd.utilities.SystemUtilities;

public class NewItem extends Fragment implements View.OnClickListener  {
    private EditText etNombre;
    private EditText etLastName;
    private String URL_POST="http://192.168.42.63:8080/sakila-backend/actors";
    private BroadcastReceiver br = null;
    /**
     * Constructor. Obligatorio para Fragmentos!
     */
    public NewItem() {
    }// NewItem()

    /**
     * Método que crea la vista del fragmento
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_item, container, false);
        Button button = (Button) view.findViewById(R.id.botonAgregar);
        etNombre=(EditText) view.findViewById(R.id.etName);
        etLastName=(EditText) view.findViewById(R.id.etLastName);
        button.setOnClickListener(this);
        return view;

    }// onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    @Override
    public void onClick(View v) {

        if(etNombre.getText().toString().compareTo("")==0||etLastName.getText().toString().compareTo("")==0){
            Toast.makeText(this.getActivity(),
                    "Debe llenar los campos", Toast.LENGTH_LONG).show();
            Log.i("TDB_TAG","no lleno todos los campos, no se envia el mensaje.");
        }else{
            String name=etNombre.getText().toString();
            final String apellido=etLastName.getText().toString();
            Log.i("TDB_TAG","EnviarPost("+name+","+apellido+");");
            IntentFilter intentFilter = new IntentFilter("httpPostResponse");

            getActivity().registerReceiver(br, intentFilter);
            SystemUtilities su = new SystemUtilities(getActivity().getApplicationContext());
            if (su.isNetworkAvailable()) {
                Log.i("TBD_TAG","Enviando Nuevo Actor");
                new HttpPost(getActivity().getApplicationContext(),"{\"firstName\":\""+name+"\", \"lastName\":\""+apellido+"\"}",this).execute(URL_POST);
            }else{
                Log.e("TBD_TAG", "error de red");
                Toast.makeText(getActivity().getApplicationContext(),"Error de conexión , intenta de nuevo o más tarde",Toast.LENGTH_LONG).show();
            }

        }


    }
    public void added(String s){
        Log.i("TBD_TAG","data:"+s);
        if(s.compareTo("No Content")==0){

            Log.i("TBD_TAG", "Añadido");
            Toast.makeText(this.getActivity(),
                    "Actor añadido exitosamente", Toast.LENGTH_LONG).show();
            InputMethodManager mgr = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(this.etLastName.getWindowToken(), 0);

        }
    }
}// NewItem extends Fragment