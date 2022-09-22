package hexagonal.bank.context.common.domain.failure;

public record ResourceNotFoundFailure(String message) implements BusinessFailure {}
