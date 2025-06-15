package com.gymapp.financeservice.client.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapp.common.exceptions.custom.BadRequestException;
import com.gymapp.common.exceptions.custom.BusinessValidationException;
import com.gymapp.common.exceptions.custom.ExternalServiceException;
import com.gymapp.common.exceptions.custom.ForbiddenException;
import com.gymapp.common.exceptions.custom.ResourceNotFoundException;
import com.gymapp.common.exceptions.custom.UnauthorizedException;
import com.gymapp.common.exceptions.error.ErrorResponse;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

  private final ErrorDecoder defaultDecoder = new Default();
  private final ObjectMapper objectMapper = new ObjectMapper();


  @Override
  public Exception decode(String methodKey, Response response) {

    if (response.body() == null) return null;
    try {
      String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
      ErrorResponse errorResponse = objectMapper.readValue(body, ErrorResponse.class);
      log.error("[finance-service] [FeignErrorDecoder] Feign error - method={}, status={}, body={}", methodKey, response.status(), body);
      String message = Optional.ofNullable(errorResponse)
          .map(ErrorResponse::getMessage)
          .orElse("Unknown error in external service");
      return switch (response.status()) {
        case 400 -> throw new BadRequestException(message, errorResponse);
        case 401 -> throw new UnauthorizedException(message, errorResponse);
        case 403 -> throw new ForbiddenException(message, errorResponse);
        case 404 -> throw new ResourceNotFoundException(message, errorResponse);
        case 422 -> throw new BusinessValidationException(message, errorResponse);
        case 500 -> throw new ExternalServiceException(message, errorResponse);
        default -> defaultDecoder.decode(methodKey, response);
      };
    } catch (IOException e) {
      log.warn("[finance-service] [FeignErrorDecoder] Failed to read response body - method={}, status={}", methodKey, response.status());
    }
    return null;
  }
}
