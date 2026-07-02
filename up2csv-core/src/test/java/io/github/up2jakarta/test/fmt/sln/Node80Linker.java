package io.github.up2jakarta.test.fmt.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.fmt.tree.Node80;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@SuppressWarnings("unchecked")
public class Node80Linker implements ILinker<Node80, Node80> {

    @Override
    public List<Node80> from(Node80 parent) {
        return (List<Node80>) parent.getNodes();
    }

    @Override
    public void link(Node80 parent, Node80 child) {
        ((List<Node80>) parent.getNodes()).add(child);
    }
}
