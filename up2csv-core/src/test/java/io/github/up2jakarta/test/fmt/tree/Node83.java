package io.github.up2jakarta.test.fmt.tree;

import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.ReferenceId;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.csv.cfg.Position;
import io.github.up2jakarta.csv.cfg.PositionOverride;
import io.github.up2jakarta.test.fmt.sln.Node80Linker;
import jakarta.validation.constraints.NotBlank;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
@PositionOverride(path = "reference", value = @Position(2))
@PositionOverride(path = "value", value = @Position(3))
public final class Node83 extends Node80 {

    @NotBlank
    @ReferenceId("81")
    private @Position(0) String node1Id;

    @NotBlank
    @ReferenceId("82")
    private @Position(1) String node2Id;

    @BusinessLink(value = "84", target = Leaf84.class, bean = @Linker(Node80Linker.class))
    private final List<Leaf84> nodes = new LinkedList<>();

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

    @Override
    public List<Leaf84> getNodes() {
        return nodes;
    }

}
