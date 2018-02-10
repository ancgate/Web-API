package valandur.webapi.swagger;

import io.swagger.jersey.SwaggerJersey2Jaxrs;
import io.swagger.models.Operation;
import io.swagger.models.Response;
import io.swagger.models.properties.IntegerProperty;
import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.StringProperty;
import valandur.webapi.api.exceptions.NotImplementedException;
import valandur.webapi.api.servlet.Permission;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class SwaggerExtension extends SwaggerJersey2Jaxrs {

    private static Property schema400 = constructProperty(400, "Bad request");
    private static Property schema401 = constructProperty(401, "Unauthorized");
    private static Property schema403 = constructProperty(403, "Access denied");
    private static Property schema404 = constructProperty(404, "Not found");
    private static Property schema500 = constructProperty(500, "Internal server error");
    private static Property schema501 = constructProperty(501, "Not implemented");

    private static Property constructProperty(int status, String error) {
        Property statusProp = new IntegerProperty()
                .description("The status code of the error (also provided in the HTTP header)");
        statusProp.setExample(status);

        Property errorProp = new StringProperty()
                .description("The error message describing the error");
        errorProp.setExample(error);

        return new ObjectProperty().property("status", statusProp).property("error", errorProp);
    }


    @Override
    public void decorateOperation(Operation operation, Method method,
                                  Iterator<io.swagger.jaxrs.ext.SwaggerExtension> chain) {
        // Automatically add 500 as a possible response
        operation.addResponse("500", new Response().description("Internal server error").schema(schema500));

        // Automatically add error codes depending on thrown exceptions
        for (Class<?> execClass : method.getExceptionTypes()) {
            if (BadRequestException.class.isAssignableFrom(execClass)) {
                operation.addResponse("400", new Response().description("Bad request").schema(schema400));
            }
            if (NotFoundException.class.isAssignableFrom(execClass)) {
                operation.addResponse("404", new Response().description("Not found").schema(schema404));
            }
            if (NotImplementedException.class.isAssignableFrom(execClass)) {
                operation.addResponse("501", new Response().description("Not implemented").schema(schema501));
            }
        }

        Permission[] perms = method.getAnnotationsByType(Permission.class);
        if (perms.length > 0) {
            // Automatically add 401 & 403 as a possible response
            operation.addResponse("401", new Response().description("Unauthorized").schema(schema401));
            operation.addResponse("403", new Response().description("Access denied").schema(schema403));

            // Automatically add required permission notes if we have a @Permission annotation
            Path path = method.getDeclaringClass().getAnnotation(Path.class);
            String prefix = path != null ? path.value() + "." : "";

            StringBuilder permStr = new StringBuilder("  \n\n **Required permissions:**  \n\n");
            for (Permission perm : perms) {
                permStr.append("- **").append(prefix).append(String.join(".", perm.value())).append("**  \n");
            }

            operation.setDescription(operation.getDescription() + permStr.toString());

            // Add security definitions
            operation.addSecurity("ApiKeyHeader", new ArrayList<>());
            operation.addSecurity("ApiKeyQuery", new ArrayList<>());
        }
        super.decorateOperation(operation, method, chain);
    }
}
