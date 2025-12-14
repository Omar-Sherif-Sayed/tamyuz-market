package sa.tamyuz.market.business.order.controller.item;

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
public class ItemUpdateController {

    private final ItemUpdateService itemUpdateService;

    @PutMapping
    @PreAuthorize("hasAuthority('UPDATE_ITEM')")
    public ResponseEntity<BaseResponse<List<DtoItem>>> update(@RequestBody @Valid ReqItemUpdate reqItemUpdate) {
        return ResponseEntity.ok(new BaseResponse<>(itemUpdateService.update(reqItemUpdate)));
    }

}
