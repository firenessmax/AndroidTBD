package cl.citiaps.jefferson.taller_android_bd;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import cl.citiaps.jefferson.taller_android_bd.views.ItemList;
import cl.citiaps.jefferson.taller_android_bd.views.NewItem;

/**
 * @author: Jefferson Morales De la Parra
 * Clase principal de la aplicación
 */
public class MainActivity extends AppCompatActivity {

    FragmentTransaction transaction;

    /**
     * Método que se ejecuta al momento de crear la actividad, llama al fragmento que muestra una lista de elementos
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ItemList());
        transaction.commit();
    }// onCreate(Bundle savedInstanceState)

    /**
     * Método que se ejecuta al momento de presionar el botón regresar del teclado
     */
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }// onBackPressed()

    /**
     * Método que crea el menú superior
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }// onCreateOptionsMenu(Menu menu)

    /**
     * Método que escucha los elementos presionados en el menú superior
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_activity_add:
                if (!(getFragmentManager().findFragmentByTag("isActiveNewItem") != null && getFragmentManager().findFragmentByTag("isActiveNewItem").isVisible())) {
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, new NewItem(), "isActiveNewItem");
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
                break;
            case R.id.menu_main_activity_exit:
                finish();
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }// onOptionsItemSelected(MenuItem item)

}// MainActivity extends AppCompatActivity