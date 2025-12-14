package sa.tamyuz.market.business.order.controller.item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.tamyuz.market.business.order.schema.request.ReqItemUpdate;
import sa.tamyuz.market.business.order.schema.response.DtoItem;
import sa.tamyuz.market.business.order.service.item.ItemUpdateService;
import sa.tamyuz.market.general.schema.response.BaseResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/items")
@Tag(name = "Item Management", description = "APIs for managing order items")
public class ItemUpdateController {

    private final ItemUpdateService itemUpdateService;

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_ITEM')")
    @Operation(
            summary = "Update order items",
            description = "Updates existing order items. Requires UPDATE_ITEM authority."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Items updated successfully",
                    content = @Content(schema = @Schema(implementation = BaseResponse.class))
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "Invalid request data",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "Unauthorized - Authentication required",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "406",
                    description = "Forbidden - Insufficient permissions",
                    content = @Content
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BaseResponse<List<DtoItem>>> update(@RequestBody @Valid ReqItemUpdate reqItemUpdate) {
        return ResponseEntity.ok(new BaseResponse<>(itemUpdateService.update(reqItemUpdate)));
    }

}
