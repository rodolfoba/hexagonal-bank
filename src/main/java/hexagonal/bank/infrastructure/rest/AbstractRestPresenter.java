package hexagonal.bank.infrastructure.rest;

import hexagonal.bank.context.common.domain.failure.BusinessFailure;
import hexagonal.bank.context.common.domain.failure.ResourceNotFoundFailure;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public abstract class AbstractRestPresenter {

    @Getter
    private ResponseEntity<Object> responseEntity;

    protected void setResponseEntity(ResponseEntity<Object> responseEntity) {
        if (this.responseEntity != null) {
            throw new IllegalStateException("Response entity cannot be set multiple times");
        }

        this.responseEntity = responseEntity;
    }

    public void present(BusinessFailure failure) {
        setResponseEntity(ResponseEntity.badRequest().body(failure.message()));
    }

    public void present(ResourceNotFoundFailure failure) {
        setResponseEntity(ResponseEntity.status(NOT_FOUND).body(failure.message()));
    }

}
