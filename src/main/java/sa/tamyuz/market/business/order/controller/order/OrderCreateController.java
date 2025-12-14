package sa.tamyuz.market.business.order.controller.order;

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
public class OrderCreateController {

    private final OrderCreateService orderCreateService;

    @PutMapping
    @PreAuthorize("hasAuthority('CREATE_ORDER')")
    public ResponseEntity<BaseResponse<DtoOrder>> create() {
        return ResponseEntity.ok(new BaseResponse<>(orderCreateService.create()));
    }

}
