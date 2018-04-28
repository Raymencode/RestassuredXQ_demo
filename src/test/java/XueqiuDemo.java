import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.response.Response;
import org.junit.Before;


//import javax.xml.ws.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class XueqiuDemo {

    public static String  xqtoken;
    public static Long   xquid ;
    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;

    @BeforeClass
    public static void XQ_Login(){

        requestSpecification = new RequestSpecBuilder().build();
        responseSpecification = new ResponseSpecBuilder().build();
        requestSpecification.header("User-Agent","Xueqiu Android 10.5");
        requestSpecification.cookie("xq_a_token","e21aa3546a97a6ed8d16ff5951a65e4b9afa2252");
        responseSpecification.statusCode(200);
        useRelaxedHTTPSValidation();
        Response response  =
                 given()
                .spec(requestSpecification)
                .cookie("xq_a_token","e21aa3546a97a6ed8d16ff5951a65e4b9afa2252")
                .cookie("u","4905196289")
                .queryParam("-t","1UNKNOWN3bcce00378569ae98abe46e0a1d52f13.4905196289.1524724308456.1524725062240")
                .queryParam("-s","00033e")
                .formParam("grant_type","password")
                .formParam("areacode","86")
                .formParam("client_id","JtXbaMn7eP")
                .formParam("client_secret","txsDfr9FphRSPov5oQou74")
                .formParam("telephone","15921570877")
                .formParam("password","b3d0bd2f91c7ac0f40fe7d042910c331")
        .when().post("https://api.xueqiu.com/provider/oauth/token")
        .then().log().all()
                .spec(responseSpecification)
        .extract().response();

         xqtoken = response.path("access_token");
         xquid = response.path("uid");

        System.out.println(xqtoken);
        System.out.println(xquid);

    }

    @Test
    public void XQ_MyTrader(){
        useRelaxedHTTPSValidation();
                given()
                        .spec(requestSpecification)
                        .queryParam("-t","1UNKNOWN3bcce00378569ae98abe46e0a1d52f13.8198956618.1524737221262.1524739880241")
                        .queryParam("-s","251497")
                        .queryParam("code","SH600120,SH600637")
                        .when().get("https://stock.xueqiu.com/v4/stock/quotec.json")
                        .then().log().all()
                        .spec(responseSpecification);
    }

/*    @Test
    public  void XQ_CommentReply(){
        useRelaxedHTTPSValidation();

        System.out.println(xqtoken);
        System.out.println(xquid);

        given()
                .header("User-Agent","Xueqiu Android 10.5")
                .cookie("xq_a_token",xqtoken)
                .cookie("u",xquid)
                .queryParam("-t","1UNKNOWN3bcce00378569ae98abe46e0a1d52f13.8198956618.1524737221262.1524739880241")
                .queryParam("-s","251497")
                .formParam("comment","持保留意见")
                .formParam("forward","0")
                .formParam("id","104904715")
                .formParam("x","0.46")
                .formParam("split","true")
                .when().post("https://api.xueqiu.com/statuses/reply.json")
                .then().log().all()
                .statusCode(200);
    }*/




/*
    @Test
    public void XQ_Requst() {
        given().log().all()
                .header("Cookie","xq_a_token=e21aa3546a97a6ed8d16ff5951a65e4b9afa2252;u=4905196289")
                .when().get("https://api.xueqiu.com/v4/stock/portfolio/stock_list.json?_t=1UNKNOWN3bcce00378569ae98abe46e0a1d52f13.4905196289.1523895483837.1523895542151&_s=5cb300&size=10000&x=0.348&type=6&category=2")
                .then().log().all().statusCode(200)
                .body("stocks.stockName",hasItem("耐克"))
                .body("stocks.find{it.code == 'AAPL'}.stockName",equalTo("苹果"));

    }
*/


    @Test
    public void hashmap(){
        HashMap<String,Object>map = new HashMap<String,Object>();
        map.put("a",1);
        map.put("b","zuihubao");
        given().contentType(ContentType.JSON)
                .when().post("http://www.baidu.com")
                .then().log().all()
                .statusCode(200);

    }

}
