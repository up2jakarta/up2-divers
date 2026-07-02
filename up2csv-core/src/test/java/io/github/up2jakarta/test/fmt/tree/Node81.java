package io.github.up2jakarta.test.fmt.tree;

import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.test.fmt.sln.Node80Linker;

import java.util.LinkedList;
import java.util.List;

public final class Node81 extends Node80 {

    @BusinessLink(value = "82", target = Node82.class, bean = @Linker(Node80Linker.class))
    private final List<Node82> nodes = new LinkedList<>();

    @Override
    public List<Node82> getNodes() {
        return nodes;
    }

}
