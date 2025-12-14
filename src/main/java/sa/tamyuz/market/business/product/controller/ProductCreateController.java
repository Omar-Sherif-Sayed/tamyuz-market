package sa.tamyuz.market.business.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.tamyuz.market.general.schema.response.BaseResponse;
import sa.tamyuz.market.business.product.schema.request.ReqProductCreate;
import sa.tamyuz.market.business.product.schema.response.DtoProduct;
import sa.tamyuz.market.business.product.service.ProductCreateService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/products")
public class ProductCreateController {

    private final ProductCreateService productCreateService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PRODUCT')")
    public ResponseEntity<BaseResponse<DtoProduct>> create(@RequestBody @Valid ReqProductCreate reqProductCreate) {
        return ResponseEntity.ok(new BaseResponse<>(productCreateService.create(reqProductCreate)));
    }

}
