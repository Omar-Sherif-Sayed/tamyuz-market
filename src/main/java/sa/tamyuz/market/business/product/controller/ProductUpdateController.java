package sa.tamyuz.market.business.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.tamyuz.market.general.schema.response.BaseResponse;
import sa.tamyuz.market.business.product.schema.request.ReqProductUpdate;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;
import sa.tamyuz.market.business.product.service.ProductUpdateService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/products")
public class ProductUpdateController {

    private final ProductUpdateService productUpdateService;

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_PRODUCT')")
    public ResponseEntity<BaseResponse<DtoProduct>> update(@RequestBody @Valid ReqProductUpdate reqProductUpdate) {
        return ResponseEntity.ok(new BaseResponse<>(productUpdateService.update(reqProductUpdate)));
    }

}
