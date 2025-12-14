package sa.tamyuz.market.business.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.tamyuz.market.general.schema.response.BaseResponse;
import sa.tamyuz.market.business.product.schema.request.ReqProductSearch;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;
import sa.tamyuz.market.business.product.service.ProductRetrievalService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/products")
public class ProductRetrievalController {

    private final ProductRetrievalService productRetrievalService;

    /**
     * Search/filter products by name, price range, and availability
     *
     * @param reqProductSearch ReqProductSearch
     * @param pageable         Pageable
     * @return ResponseEntity<BaseResponse<Page<DtoProduct>>>
     */
    @PutMapping("/search")
    @Operation(parameters = {
            @Parameter(name = "page", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)),
            @Parameter(name = "size", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)),
            @Parameter(name = "sort", in = ParameterIn.QUERY, schema = @Schema(implementation = Sort.class)),
            @Parameter(name = "direction", in = ParameterIn.QUERY, schema = @Schema(implementation = Sort.Order.class))})
    @PreAuthorize("hasAuthority('RETRIEVE_PRODUCT')")
    public ResponseEntity<BaseResponse<Page<DtoProduct>>> search(@RequestBody ReqProductSearch reqProductSearch,
                                                                 @SortDefault(sort = "id", direction = Sort.Direction.DESC)
                                                                 @PageableDefault Pageable pageable) {
        return ResponseEntity.ok(new BaseResponse<>(productRetrievalService.search(reqProductSearch, pageable)));
    }

}
