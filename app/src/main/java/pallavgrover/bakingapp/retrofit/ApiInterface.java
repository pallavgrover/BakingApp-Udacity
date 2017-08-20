package pallavgrover.bakingapp.retrofit;

import java.util.ArrayList;

import pallavgrover.bakingapp.models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET(" ")
    Call<ArrayList<Recipe>> getRecipes();

}
