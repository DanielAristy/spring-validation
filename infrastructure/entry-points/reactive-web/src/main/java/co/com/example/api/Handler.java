package co.com.example.api;

import co.com.example.api.request.HeaderDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler implements Commons {

    private final Validator validator;

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        return getHeaders(serverRequest)
                .flatMap(headerDTO -> validatorHeaders(validator, headerDTO)
                        .filter(Errors::hasErrors)
                        .flatMap(errors -> getErrorsResponse(errors, headerDTO))
                );
    }
}
