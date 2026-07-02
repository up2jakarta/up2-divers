package io.github.up2jakarta.test.fmt.tree;

import io.github.up2jakarta.csv.ReferenceId;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@SuppressWarnings("unused")
@PositionOverride(path = "reference", value = @Position(3))
@PositionOverride(path = "value", value = @Position(4))
public final class Leaf84 extends Node80 {

    @NotBlank
    @ReferenceId("81")
    private @Position(0) String node1Id;

    @NotBlank
    @ReferenceId("82")
    private @Position(1) String node2Id;

    @NotBlank
    @ReferenceId("83")
    private @Position(2) String node3Id;

    public String getNode1Id() {
        return node1Id;
    }

    public void setNode1Id(String node1Id) {
        this.node1Id = node1Id;
    }

    public String getNode2Id() {
        return node2Id;
    }

    public void setNode2Id(String node2Id) {
        this.node2Id = node2Id;
    }

    public String getNode3Id() {
        return node3Id;
    }

    public void setNode3Id(String node3Id) {
        this.node3Id = node3Id;
    }

    @Override
    public List<? extends Node80> getNodes() {
        return List.of();
    }

}
