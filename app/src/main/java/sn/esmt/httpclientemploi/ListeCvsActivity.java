package sn.esmt.httpclientemploi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sn.esmt.httpclientemploi.httpconfig.Api;
import sn.esmt.httpclientemploi.httpconfig.CvsResponses;
import sn.esmt.httpclientemploi.tools.MyAdapterCvs;
 /*Activité liée layout activity_liste_cvs. Elle permettra d'afficher la liste de tous les cvs.*/
public class ListeCvsActivity extends AppCompatActivity {
    // Création de la ListView
    private ListView list;
    private TextView selection;
    private ArrayList<CvsResponses> cvs = new ArrayList<CvsResponses>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_cvs);

        // Initialisation de la ListView
        list = (ListView) findViewById(R.id.listCvs);
        selection = (TextView) findViewById(R.id.selection);

        // Chargement de la liste des utilisateurs depuis l'API
        chargerListe();

        // Gestion de l'évènement sélection d'un élément dans la listView
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Get the selected item from the ArrayList
                CvsResponses selectedItem = cvs.get(position);

                // Update the TextView with the selected item's information
                selection.setText("************************************\n" +
                        "\t\t\t\t\t\t Détails du CV \n" +
                        "************************************\n\n" +
                        "Id : " + selectedItem.getId() + "\n"
                        + "Nom : " + selectedItem.getNom() + "\n"
                        + "Prenom : " + selectedItem.getPrenom()+ "\n"
                        + "Age : " + selectedItem.getAge()+ "\n"
                        + "Adresse : " + selectedItem.getAdresse()+ "\n"
                        + "Email : " + selectedItem.getEmail()+ "\n"
                        + "Telephone : " + selectedItem.getTelephone()+ "\n"
                        + "Spécialité : " + selectedItem.getSpecialite()+ "\n"
                        + "Niveau d'étude : " + selectedItem.getNiveauEtude()+ "\n"
                        + "Expérience professionnelle: " + selectedItem.getExperienceProfessionnelle()
                        + "\n\n************************************\n\n");
            }
        });
    }

    public void chargerListe(){

        //Création de l'objet Retrofit pour accéder à l'API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.11:8083") //URL de base de l'API
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Crée une instance de l'interface Api,
        //qui définit les méthodes pour les appels HTTP.
        Api api = retrofit.create(Api.class);

        //creation d'un objet Call pour l'appel à la méthode all() de l'interface Api.
        Call<ArrayList<CvsResponses>> call = api.all();

        call.enqueue(new Callback<ArrayList<CvsResponses>>() {
            @Override
            public void onResponse(Call<ArrayList<CvsResponses>> call, Response<ArrayList<CvsResponses>> response) {
                if (response.isSuccessful()) {
                    Log.d("Response :", response.body().get(0).getEmail());
                    response.body().stream().forEach(cvsResponse -> cvs.add(cvsResponse));
                    MyAdapterCvs adpt = new MyAdapterCvs(ListeCvsActivity.this,cvs);
                    Log.d("Debbugage : " , cvs.get(0).getNom());
                    list.setAdapter(adpt);

                } else {
                    Log.d("error message exception", response.toString());

                }
            }

            @Override
            public void onFailure(Call<ArrayList<CvsResponses>> call, Throwable t) {
                Log.d("Error : ", t.getMessage());
                //D/Error :: CLEARTEXT communication to 192.168.90.167 not permitted by network security policy
                Toast.makeText(ListeCvsActivity.this, "Impossible d'acceder au serveur !", Toast.LENGTH_LONG).show();
            }

        });


    }

}