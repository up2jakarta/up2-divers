package io.github.up2jakarta.test.fmt.tree;

import io.github.up2jakarta.csv.BusinessLink;
import io.github.up2jakarta.csv.BusinessObject;
import io.github.up2jakarta.csv.api.Linker;
import io.github.up2jakarta.test.fmt.sln.Node80Linker;

import java.util.LinkedList;
import java.util.List;

@BusinessObject("80")
public final class Tree80 extends Node80 {

    @BusinessLink(value = "81", target = Node81.class, bean = @Linker(Node80Linker.class))
    private final List<Node81> nodes = new LinkedList<>();

    @Override
    public List<Node81> getNodes() {
        return nodes;
    }

}
