package io.github.up2jakarta.csv.core.ops;

import io.github.up2jakarta.csv.api.IType;
import io.github.up2jakarta.csv.core.BeanException;
import io.github.up2jakarta.csv.data.BusinessObject;

import java.util.function.Supplier;

public final class BusinessType<T extends BusinessObject> {

    final Filler filler;
    final ModeType builder;
    private final Class<T> type;

    BusinessType(ModeType builder, Class<T> type, Filler filler) {
        this.builder = builder;
        this.filler = filler;
        this.type = type;
    }

    void check(IType<?, ?> type, int offset) throws BeanException {
        if (!this.type.equals(type.getClassType())) {
            throw new BeanException(this.type, "Invalid business typing");
        }
        if (offset != builder.length) {
            throw new BeanException(type.getClassType(), "@Truncated[value] must be  equals to " + builder.length);
        }
    }

    public int getTypeIdIndex() {
        return builder.typeIdIndex;
    }

    public int getBeanIdIndex() {
        return builder.beanIdIndex;
    }

    public ModeType getModeType() {
        return builder;
    }

    @FunctionalInterface
    interface Filler {
        void accept(String[] target, Supplier<String> rowId, IType<?, ?> type, BusinessObject source);
    }

}
