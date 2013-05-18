import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;

import play.data.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

public class ApplicationTest {

    @Test
    public void renderTemplate() {
        Content html = views.html.index.render(Form.form(controllers.Application.Login.class));
        assertThat(contentType(html)).isEqualTo("text/html");
        //assertThat(contentAsString(html)).contains("Your new application is ready.");
    }
}
