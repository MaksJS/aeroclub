import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class ApplicationSpec extends Specification {
  
  "Application" should {
   
    "go back to index page without credentials" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val result = route(FakeRequest(GET, "/flights")).get
        status(result) must equalTo(303)
      }
    }
    "go the flights page with credentials" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val result = route(FakeRequest(GET, "/flights").withSession("user" -> "foo")).get
        status(result) must equalTo(200)
      }
    }
    "can't add a plane without being admin" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val result = route(FakeRequest(GET, "/planes/new").withSession("user" -> "foo")).get
        status(result) must equalTo(403)
      }
    }
    "can add a plane type when admin" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase())) {
        val result = route(FakeRequest(GET, "/planetypes/new").withSession("user" -> "admin")).get
        status(result) must equalTo(200)
      }
    }
  }
}