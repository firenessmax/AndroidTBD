package cl.citiaps.jefferson.taller_android_bd.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author:
 */
import cl.citiaps.jefferson.taller_android_bd.R;

public class NewItem extends Fragment {

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
        return inflater.inflate(R.layout.new_item, container, false);
    }// onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

}// NewItem extends Fragment