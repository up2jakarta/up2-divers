package io.github.up2jakarta.csv.entities.invoincing;

import io.github.up2jakarta.csv.annotation.*;
import io.github.up2jakarta.csv.entities.ParsedEntity;
import io.github.up2jakarta.csv.entities.params.SystemEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "TB_INVOICES",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_INVOICE",
                columnNames = {"NVC_SYSTEM_ID", "NVC_REFERENCE"}
        )
)
@AssociationOverride(name = "loading", foreignKey = @ForeignKey(name = "FK_INVOICE_INPUT_LOADING"),
        joinColumns = @JoinColumn(insertable = false, updatable = false,
                name = "NVC_LOAD_ID", referencedColumnName = "LOAD_ID"
        )
)
@AssociationOverride(name = "row", foreignKey = @ForeignKey(name = "FK_INVOICE_INPUT_ROW"),
        joinColumns = {
                @JoinColumn(name = "NVC_LOAD_ID", referencedColumnName = "ROW_LOAD_ID"),
                @JoinColumn(name = "NVC_ROW_ORDER", referencedColumnName = "ROW_ORDER")
        }
)
@Validated
@Up2EnableJPA
@SuppressWarnings("unused")
public class InvoiceEntity extends ParsedEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_INVOICE_ID")
    @SequenceGenerator(name = "SQ_INVOICE_ID", sequenceName = "SQ_INVOICE_ID", allocationSize = 1)
    @Column(name = "NVC_ID")
    private Long key;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "NVC_SYSTEM_ID", referencedColumnName = "SYS_ID",
            foreignKey = @ForeignKey(name = "FK_INVOICE_SYSTEM")
    )
    private SystemEntity system;

    @Column(name = "NVC_REFERENCE", length = 20, nullable = false)
    private String reference;

    @Position(0)
    @Up2Temporal
    @NotNull
    @Column(name = "NVC_ISSUE_DATE", nullable = false)
    private LocalDate issueDate;

    @Position(4)
    @Up2Temporal
    @Column(name = "NVC_DUE_DATE")
    private LocalDate dueDate;

    @Size(max = 50)
    @Position(5)
    @Column(name = "NVC_CONTRACT_REFERENCE", length = 50)
    private String contractReference;

    @Size(max = 50)
    @Position(7)
    @Column(name = "NVC_SHIP_REFERENCE", length = 50)
    private String shippingReference;

    @Size(max = 20)
    @Position(10)
    @Up2Token
    @Column(name = "NVC_PREV_REFERENCE", length = 20)
    private String previousReference;

    @Position(11)
    @Up2Temporal
    @Column(name = "NVC_PREV_ISSUE_DATE")
    private LocalDate previousIssueDate;

    @Position(12)
    @Up2Temporal
    @Column(name = "NVC_BILL_START_DATE")
    private LocalDate billingStartDate;

    @Position(13)
    @Up2Temporal
    @Column(name = "NVC_BILL_END_DATE")
    private LocalDate billingEndDate;

    @OneToMany(mappedBy = "key.invoice", cascade = CascadeType.PERSIST)
    private final List<InvoiceNoteEntity> notes = new LinkedList<>();

    @OneToMany(mappedBy = "key.invoice", cascade = CascadeType.PERSIST)
    @MapKey(name = "key.order")
    private Map<Integer, InvoiceNoteEntity> noteMap = new LinkedHashMap<>();

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public SystemEntity getSystem() {
        return system;
    }

    public void setSystem(SystemEntity system) {
        this.system = system;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getContractReference() {
        return contractReference;
    }

    public void setContractReference(String contractReference) {
        this.contractReference = contractReference;
    }

    public String getShippingReference() {
        return shippingReference;
    }

    public void setShippingReference(String shippingReference) {
        this.shippingReference = shippingReference;
    }

    public String getPreviousReference() {
        return previousReference;
    }

    public void setPreviousReference(String previousReference) {
        this.previousReference = previousReference;
    }

    public LocalDate getPreviousIssueDate() {
        return previousIssueDate;
    }

    public void setPreviousIssueDate(LocalDate previousIssueDate) {
        this.previousIssueDate = previousIssueDate;
    }

    public LocalDate getBillingStartDate() {
        return billingStartDate;
    }

    public void setBillingStartDate(LocalDate billingStartDate) {
        this.billingStartDate = billingStartDate;
    }

    public LocalDate getBillingEndDate() {
        return billingEndDate;
    }

    public void setBillingEndDate(LocalDate billingEndDate) {
        this.billingEndDate = billingEndDate;
    }

    public List<InvoiceNoteEntity> getNotes() {
        return notes;
    }

    public Map<Integer, InvoiceNoteEntity> getNoteMap() {
        return noteMap;
    }

    public void setNoteMap(Map<Integer, InvoiceNoteEntity> noteMap) {
        this.noteMap = noteMap;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InvoiceEntity that)) {
            return false;
        }
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

}
