package io.github.up2jakarta.csv.entities.input;

import io.github.up2jakarta.csv.entities.ImmutableEntity;
import io.github.up2jakarta.csv.entities.Status;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_INPUT_FOOTERS")
public class InputFooterEntity extends ImmutableEntity {

    @Id
    @OneToOne(optional = false)
    @JoinColumn(name = "FOOT_LOAD_ID", foreignKey = @ForeignKey(name = "FK_INPUT_FOOTER_LOADING"))
    private InputLoadingEntity loading;

    @Column(name = "FOOT_INVOICE_COUNT", nullable = false)
    private Integer invoiceCount;

    @Column(name = "FOOT_ROW_COUNT", nullable = false)
    private Integer rowCount;

    @Column(name = "FOOT_STATUS", length = 2, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    public InputLoadingEntity getLoading() {
        return loading;
    }

    public void setLoading(InputLoadingEntity loading) {
        this.loading = loading;
    }

    public Integer getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(Integer invoiceCount) {
        this.invoiceCount = invoiceCount;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
