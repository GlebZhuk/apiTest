package objects.head_hunter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import objects.head_hunter.Salary;

@Data
public class Vacancy {
@Expose
    String name;
    @Expose
    Salary salary;
    @Expose
    //Для записи переменной в кэмал-кейсе
    @SerializedName("alternate_url")
    String alternateUrl;
}
