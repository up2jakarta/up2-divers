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
@PositionOverride(path = "reference", value = @Position(1))
@PositionOverride(path = "value", value = @Position(2))
public final class Node82 extends Node80 {

    @NotBlank
    @ReferenceId("81")
    private @Position(0) String node1Id;

    @BusinessLink(value = "83", target = Node83.class, bean = @Linker(Node80Linker.class))
    private final List<Node83> nodes = new LinkedList<>();

    public String getNode1Id() {
        return node1Id;
    }

    public void setNode1Id(String node1Id) {
        this.node1Id = node1Id;
    }

    @Override
    public List<Node83> getNodes() {
        return nodes;
    }

}
