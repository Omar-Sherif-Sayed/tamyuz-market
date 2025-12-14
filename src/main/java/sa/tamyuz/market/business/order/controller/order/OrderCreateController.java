package sa.tamyuz.market.business.order.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.tamyuz.market.business.order.schema.response.DtoOrder;
import sa.tamyuz.market.business.order.service.order.OrderCreateService;
import sa.tamyuz.market.general.schema.response.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/orders")
@Tag(name = "Orders", description = "Order management endpoints")
public class OrderCreateController {

    private final OrderCreateService orderCreateService;

    @PutMapping
    @PreAuthorize("hasAuthority('CREATE_ORDER')")
    @Operation(
            summary = "Create a new order",
            description = "Creates a new order for the authenticated user. Requires CREATE_ORDER authority."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully"),
            @ApiResponse(responseCode = "406", description = "Unauthorized - Invalid or missing authentication"),
            @ApiResponse(responseCode = "406", description = "Forbidden - User lacks CREATE_ORDER authority")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BaseResponse<DtoOrder>> create() {
        return ResponseEntity.ok(new BaseResponse<>(orderCreateService.create()));
    }

}
