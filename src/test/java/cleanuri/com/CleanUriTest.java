package cleanuri.com;

import com.google.gson.JsonObject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CleanUriTest {

    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("https://cleanuri.com/api/v1/shorten")
            .setContentType(ContentType.JSON)
            .build();

    public String jsonString(String url, String value){
        JsonObject json = new JsonObject();
        json.addProperty(url, value);
        return json.toString();
    }

    @Test
    public void positiveTest() throws FileNotFoundException {
        FileReader file = new FileReader("validData.txt");
        FileReader valid = new FileReader("response.txt");
        Scanner scanValid = new Scanner(valid);
        Scanner scanFile = new Scanner(file);

        while (scanFile.hasNextLine()){
            String body = scanFile.nextLine();
            given()
                    .spec(requestSpecification)
                    .body(jsonString("url", body))
                    .post()
                    .then().statusCode(200)
                    .and()
                    .assertThat().body("result_url", equalTo(scanValid.nextLine()));
        }
    }
    @Test
    public void emptyUrlTest(){
        given()
                .spec(requestSpecification)
                .body(jsonString("url", ""))
                .post()
                .then().statusCode(400)
                .and()
                .body("error", equalTo("API Error: After sanitization URL is empty"));
    }
    @Test
    public void encodedUrl(){
        given().spec(requestSpecification)
                .body(jsonString("url", "https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D5abamRO41fE%26ab_channel%3DSlipknot"))
                .post()
                .then().statusCode(400)
                .and()
                .body("error", equalTo("API Error: URL is invalid (check #1)"));
    }

}
