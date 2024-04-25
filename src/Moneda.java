import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Moneda {
    //Esta se las debo, mis amores!
    @SerializedName("conversion_rate")
    String conversion_rate;
    String valor;

    public Moneda(){};

    public Moneda (String conversion_rate){
        this.conversion_rate = conversion_rate;
    }

    public void setConversion_rate(){

    }


}
