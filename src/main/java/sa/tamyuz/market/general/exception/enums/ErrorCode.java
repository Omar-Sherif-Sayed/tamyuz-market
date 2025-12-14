package sa.tamyuz.market.general.exception.enums;


import lombok.Getter;

@Getter
public enum ErrorCode {

    // Not Found
    NOT_FOUND_USER("not.found.user"),
    NOT_FOUND_PRODUCT("not.found.product"),
    NOT_FOUND_ITEMS_IN_CARD("not.found.items.in.card"),

    // Exception
    EXCEPTION_SYSTEM("exception.system"),
    EXCEPTION_UNEXPECTED_TYPE("exception.unexpected.type"),
    EXCEPTION_NO_HANDLER_FOUND("exception.no.handler.found"),
    EXCEPTION_DATA_INTEGRITY_VIOLATION("exception.data.integrity.violation"),
    EXCEPTION_HTTP_MESSAGE_NOT_READABLE("exception.http.message.not.readable"),
    EXCEPTION_METHOD_ARGUMENT_NOT_VALID("exception.method.argument.not.valid"),

    // Exist
    EXIST_USER("exist.user"),

    // Auth
    AUTH_USER_DISABLED("auth.user.disabled"),

    // Other
    NOT_ENOUGH_PRODUCT_QUANTITY("not.enough.product.quantity");

    private final String messageCode;

    ErrorCode(String messageCode) {
        this.messageCode = messageCode;
    }

}
