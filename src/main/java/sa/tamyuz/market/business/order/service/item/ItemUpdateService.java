package sa.tamyuz.market.business.order.service.item;

import sa.tamyuz.market.business.order.schema.request.ReqItemUpdate;
import sa.tamyuz.market.business.order.schema.response.DtoItem;

import java.util.List;

public interface ItemUpdateService {

    List<DtoItem> update(ReqItemUpdate reqItemUpdate);

}
