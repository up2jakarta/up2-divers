package io.github.up2jakarta.test.core.bs;

import io.github.up2jakarta.csv.BusinessId;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.Segment;
import io.github.up2jakarta.csv.cfg.Error;
import io.github.up2jakarta.csv.cfg.Fragment;
import io.github.up2jakarta.csv.cfg.Up2Number;
import io.github.up2jakarta.test.impl.BusinessType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.function.Supplier;

import static io.github.up2jakarta.test.core.bs.ComplexBId.OFragment;
import static io.github.up2jakarta.test.impl.TermType.UUID;

@Valid
@Error("CSV-S00")
@BusinessObject("00")
public final class ComplexBId implements Segment, Supplier<OFragment> {

    @Fragment(0)
    private @Valid OFragment fragment;

    @Override
    public OFragment get() {
        return fragment;
    }

    public static final class OFragment implements Segment, Supplier<TFragment> {
        @Fragment(0)
        private @Valid TFragment fragment;

        @Override
        public TFragment get() {
            return fragment;
        }
    }

    public static final class TFragment implements Segment {
        @NotNull
        @Up2Number
        @BusinessId
        @BusinessType(UUID)
        private Integer id;

        public Integer getId() {
            return id;
        }
    }
}