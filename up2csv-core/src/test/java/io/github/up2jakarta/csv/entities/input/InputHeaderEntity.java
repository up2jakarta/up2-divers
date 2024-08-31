package io.github.up2jakarta.csv.entities.input;

import io.github.up2jakarta.csv.converters.FluxConverter;
import io.github.up2jakarta.csv.entities.FluxType;
import io.github.up2jakarta.csv.entities.ImmutableEntity;
import io.github.up2jakarta.csv.entities.params.SystemEntity;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "TB_INPUT_HEADERS")
public class InputHeaderEntity extends ImmutableEntity {

    @Id
    @OneToOne(optional = false)
    @JoinColumn(name = "HEAD_LOAD_ID", foreignKey = @ForeignKey(name = "FK_INPUT_HEADER_LOADING"))
    private InputLoadingEntity loading;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "HEAD_SO_ID", referencedColumnName = "SO_ID",
            foreignKey = @ForeignKey(name = "FK_INPUT_HEADER_SO")
    )
    private SystemEntity system;

    @Column(name = "HEAD_REFERENCE", length = 10, nullable = false)
    private String reference;

    @Column(name = "HEAD_ISSUE_DATE")
    private LocalDate issueDate;

    @Column(name = "HEAD_FLUX_TYPE", length = 10)
    @Convert(converter = FluxConverter.class)
    private FluxType fluxType;

    public InputLoadingEntity getLoading() {
        return loading;
    }

    public void setLoading(InputLoadingEntity loading) {
        this.loading = loading;
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

    public FluxType getFluxType() {
        return fluxType;
    }

    public void setFluxType(FluxType fluxType) {
        this.fluxType = fluxType;
    }

}
