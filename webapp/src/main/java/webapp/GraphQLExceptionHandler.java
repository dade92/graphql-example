package webapp;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Controller;
import service.ExistingAuthorException;

@Controller
public class GraphQLExceptionHandler {

    @GraphQlExceptionHandler
    public GraphQLError handle(ExistingAuthorException exception) {
        return GraphqlErrorBuilder.newError()
            .message(exception.getMessage())
            .errorType(ErrorType.BAD_REQUEST) // or define a custom one
            .build();
    }
}