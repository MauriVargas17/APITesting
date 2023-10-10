import io.restassured.specification.Argument;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class BasicRestAssured {


    @Test
    public void createProjectByAPI(){
        given().
                auth().
                preemptive().
                basic("upbapi@upbapi.com", "12345").
                body("{\n" +
                        " \"Content\":\"JAJAJA\",\n" +
                        " \"Icon\":20,\n" +

                        "}").
                log().
                all().
                when().post("https://todo.ly/api/projects.json").
                then().
                log().
                all().
                statusCode(200).
                body("Content", equalTo("JAJAJA")).
                body("Icon", equalTo(20));
    }

    @Test
    public void createProjectByAPIAndJSON(){
        JSONObject bodyProject = new JSONObject();
        bodyProject.put("Content","a");
        bodyProject.put("Icon",2);


        given()
                .auth()
                .preemptive()
                .basic("upbapi@upbapi.com","12345")
                .body(bodyProject.toString())
                .log()
                .all().
                when()
                .post("https://todo.ly/api/projects.json").
                then()
                .log()
                .all()
                .statusCode(200)
                .body("Content",equalTo(bodyProject.get("Content")))
                .body("Icon",equalTo(2));
    }



}
