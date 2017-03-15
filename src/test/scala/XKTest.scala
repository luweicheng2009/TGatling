import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
  * Created by wei on 2017/2/28.
  */
class XKTest extends Simulation  {
  val httpConf = http.baseURL("http://int.xiaokakeji.com:8091");
  val parts = csv("parts.csv").circular;
  val header_0 = Map("appType"->"9","os"->"android",
    "lat"->"0.0","lng"->"0.0","version"->"1.6.0","Content-Type"->"application/json");
//  val scn = scenario("get index ")
//    .exec(
//      http("home page")
//        .get("/dcm/employee/index/1.6.0")
//          .headers(header_0)
//          .queryParam("empCode","010109")
//          .queryParam("debugAuth",true)
//          .check(status.is(200)));
//  setUp(scn.inject(atOnceUsers(10))).protocols(httpConf);
  val scn = scenario("wlu dcm").feed(parts)
    .exec(http("partsPrice")
        .post("/dcm/partsPrice/index/1.6.0")

          .headers(header_0)
            .body(StringBody("""{"empCode":"${empCode}","plateNum":"${plateNum}"}""")).asJSON
              .queryParam("debugAuth",true)
                  .check(status.is(200))
                    .check(jsonPath("$.data.littleRepairPackage").findAll.saveAs("LittleRepairPackage"))
    );
  setUp(scn.inject(atOnceUsers(1))).protocols(httpConf);


}


