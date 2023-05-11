package co.com.example.api;

import co.com.example.api.request.HeaderDTO;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Commons {
    default Mono<Errors> validatorHeaders(Validator validator, HeaderDTO headerDTO) {
        Errors errorHeader = new BeanPropertyBindingResult(headerDTO, HeaderDTO.class.getName());
        validator.validate(headerDTO, errorHeader);
        return Mono.just(errorHeader);
    }

    default Mono<ServerResponse> getErrorsResponse(Errors errorHeader, HeaderDTO headerDTO) {
        return Flux.fromIterable(errorHeader.getFieldErrors())
                .map(fieldError -> "El campo " + errorHeader.getObjectName().replace("co.com.example.api.request.", "").replace("DTO", "") + " " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collectList()
                .flatMap(list -> getServerResponse(list, headerDTO, 400));
    }

    default Mono<HeaderDTO> getHeaders(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .map(request -> HeaderDTO.builder()
                        .consumerId(serverRequest.headers().firstHeader("consumidor_id"))
                        .invocationIp(serverRequest.headers().firstHeader("ip_invocacion"))
                        .company(serverRequest.headers().firstHeader("empresa"))
                        .business(serverRequest.headers().firstHeader("negocio"))
                        .build());
    }

    default Mono<ServerResponse> getServerResponse(Object body, HeaderDTO header, Integer status) {
        return ServerResponse.status(status)
                .header("consumidor_id", header.getConsumerId())
                .header("ip_invocacion", header.getInvocationIp())
                .header("empresa", header.getCompany())
                .header("negocio", header.getBusiness())
                .bodyValue(body);
    }
}
