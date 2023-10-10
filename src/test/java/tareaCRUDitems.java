import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class tareaCRUDitems {

    JSONObject bodyItem;
    int itemID = 0;
    RequestSpecification request;
    String itemPath;
    String regPath = "https://todo.ly/api/items.json";

    public void setItem(int id){
        itemID = id;
        itemPath = "https://todo.ly/api/items/"+itemID+".json";
    }

    public static ValidatableResponse compareDetails(Response response, JSONObject bodyItem){
        return response.then()
                .log()
                .all()
                .statusCode(200)
                .body("Content", equalTo(bodyItem.get("Content")))
                .body("Priority", equalTo(bodyItem.get("Priority")));
    }

    public static ValidatableResponse compareDetails(Response response, JSONObject bodyItem, boolean isDeleted){
        return compareDetails(response, bodyItem).body("Deleted", equalTo(isDeleted));
    }

    @BeforeEach
    public void setRequest(){
        bodyItem = new JSONObject();
        bodyItem.put("Content", "que_pro");
        bodyItem.put("Priority", 1);
        bodyItem.put("ProjectId", 4129100);
    }

    public RequestSpecification initQuery(){
        return given()
                .auth()
                .preemptive()
                .basic("upbapi@upbapi.com", "12345")
                .log()
                .all()
                ;
    }

    public RequestSpecification initQueryWithBody(){
        return initQuery()
                .body(bodyItem.toString())
                .log()
                .all();
    }

    @Test
    public void testCRUDItem(){
        //CREATE
        Response response = initQueryWithBody().when().post(regPath);
        compareDetails(response, bodyItem);
        setItem(response.then().extract().path("Id"));

        //READ
        response = initQuery().when().get(itemPath);
        compareDetails(response, bodyItem);

        //UPDATE
        bodyItem.put("Content", "JSON Update");
        response = initQueryWithBody().when().put(itemPath);
        compareDetails(response, bodyItem);

        //DELETE
        response = initQuery().when().delete(itemPath);
        compareDetails(response, bodyItem, true);
    }

}
