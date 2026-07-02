package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.api.Overlink;

@BusinessObject(value = "71", overrides = @Overlink(@BusinessLink(value = "73", target = Virtual2Item.class)))
public final class Virtual2Reference extends VirtualReference<Virtual2Item> {

    public Virtual2Reference(String reference) {
        super(reference);
    }

}
