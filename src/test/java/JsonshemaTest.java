import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import jdk.nashorn.internal.runtime.regexp.joni.Matcher;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.response.Response;
import org.junit.Before;
import  java.io.File;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;



public class JsonshemaTest {

    public static String  xqtoken;
    public static Long   xquid ;
    public static RequestSpecification requestSpecification;

    @BeforeClass
    public static void XQ_Login() {

        requestSpecification = new RequestSpecBuilder().build();
        responseSpecification = new ResponseSpecBuilder().build();
        requestSpecification.header("User-Agent", "Xueqiu Android 10.5");
        requestSpecification.cookie("xq_a_token", "e21aa3546a97a6ed8d16ff5951a65e4b9afa2252");
        responseSpecification.statusCode(200);
        useRelaxedHTTPSValidation();
        Response response =
                given()
                        .spec(requestSpecification)
                        .cookie("xq_a_token", "e21aa3546a97a6ed8d16ff5951a65e4b9afa2252")
                        .cookie("u", "4905196289")
                        .queryParam("-t", "1UNKNOWN3bcce00378569ae98abe46e0a1d52f13.4905196289.1524724308456.1524725062240")
                        .queryParam("-s", "00033e")
                        .formParam("grant_type", "password")
                        .formParam("areacode", "86")
                        .formParam("client_id", "JtXbaMn7eP")
                        .formParam("client_secret", "txsDfr9FphRSPov5oQou74")
                        .formParam("telephone", "15921570877")
                        .formParam("password", "b3d0bd2f91c7ac0f40fe7d042910c331")
                        .when().post("https://api.xueqiu.com/provider/oauth/token")
                        .then().log().all()
                        .spec(responseSpecification)
                        .extract().response();

        xqtoken = response.path("access_token");
        xquid = response.path("uid");
    }

    @Test
    public void Testshema1(){
          given()
                .header("User-Agent","Xueqiu Android 10.5")
                .cookie("xq_a_token",xqtoken)
                .cookie("u",xquid)
                .when().get("https://xueqiu.com/v4/stock/quote.json?code=SOGO")
                .then()
                  .log().all()
                  .statusCode(200)
                  .body(matchesJsonSchema(new File("/Users/raymen/temp/json.schema")));
    }

    @Test
    public void Testshema2(){
        given()
                .header("User-Agent","Xueqiu Android 10.5")
                .cookie("xq_a_token",xqtoken)
                .cookie("u",xquid)
                .when().get("https://xueqiu.com/v4/stock/quote.json?code=SOGO")
                .then().statusCode(200)
                .body(matchesJsonSchemaInClasspath("shema/json.schema"));

    }
}
