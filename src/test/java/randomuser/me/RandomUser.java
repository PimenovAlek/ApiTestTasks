package randomuser.me;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import org.junit.Test;
import randomuser.me.responseobject.ResponseData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class RandomUser {
    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("https://randomuser.me/api/1.3/")
            .setContentType(ContentType.JSON)
            .build();

    @Test
    public void requestWithTwoParameters() {
        Response response = given().spec(requestSpecification)
                .param("gender", "male").param("results", "2")
                .get();


        ResponseData resultData = new Gson().fromJson(response.getBody().asString(), ResponseData.class);

        Assert.assertEquals("male", resultData.getResults().get(0).getGender());
        Assert.assertEquals(2, resultData.getInfo().getResults());
    }

    @Test
    public void pageThatDoesNotExistTest(){
        Response response = given().spec(requestSpecification)
                .param("page", 999999999)
                .get();
        ResponseData responseData = new Gson().fromJson(response.getBody().asString(), ResponseData.class);

        Assert.assertEquals(1, responseData.getInfo().getPage());
    }

    @Test
    public void invalidGender(){
        given().spec(requestSpecification)
                .param("gender", "")
                .get().then().statusCode(200);
    }

    @Test
    public void foobarTest(){
        Response response = given().spec(requestSpecification)
                .param("seed","foobar")
                .get();
        ResponseData responseData = new Gson().fromJson(response.getBody().asString(), ResponseData.class);

        Assert.assertEquals("Miss", responseData.getResults().get(0).getName().getTitle());
        Assert.assertEquals("Britney",responseData.getResults().get(0).getName().getFirst());
        Assert.assertEquals("foobar", responseData.getInfo().getSeed());
        Assert.assertEquals("female",responseData.getResults().get(0).getGender());
    }
    @Test
    public void nationalityTest(){
        Response response = given().spec(requestSpecification)
                .param("nat","gb")
                .get();

        ResponseData responseData = new Gson().fromJson(response.getBody().asString(), ResponseData.class);

        Assert.assertEquals("GB", responseData.getResults().get(0).getNat());
    }
    @Test
    public void invalidNationalityTest(){
        given().spec(requestSpecification)
                .param("nat","RU")
                .get().then().statusCode(200);
    }

    @Test
    public void specialPasswordTest(){
        Response response = given().spec(requestSpecification)
                .param("password","special")
                .get();

        ResponseData responseData = new Gson().fromJson(response.getBody().asString(), ResponseData.class);
        Pattern p = Pattern.compile("[^A-Za-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(responseData.getResults().get(0).getLogin().getPassword());
        Assert.assertTrue(m.find());
    }



}
