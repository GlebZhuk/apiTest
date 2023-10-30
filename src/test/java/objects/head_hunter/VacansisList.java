package objects.head_hunter;

import com.google.gson.annotations.Expose;
import lombok.Data;
import objects.head_hunter.Vacancy;

import java.util.ArrayList;
@Data
public class VacansisList {
@Expose
    ArrayList<Vacancy> items;
}
