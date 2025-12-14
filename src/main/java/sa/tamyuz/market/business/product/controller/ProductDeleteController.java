package sa.tamyuz.market.business.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.tamyuz.market.general.schema.response.BaseResponse;
import sa.tamyuz.market.business.product.service.ProductDeleteService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/products")
public class ProductDeleteController {

    private final ProductDeleteService productDeleteService;

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    public ResponseEntity<BaseResponse<Boolean>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(new BaseResponse<>(productDeleteService.delete(id)));
    }

}
