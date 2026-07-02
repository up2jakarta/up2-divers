package io.github.up2jakarta.test.fmt.sln;

import io.github.up2jakarta.csv.api.ILinker;
import io.github.up2jakarta.test.fmt.tree.Tree90;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Tree90Linker implements ILinker<Tree90, Tree90> {

    @Override
    public List<Tree90> from(Tree90 parent) {
        return parent.getNodes();
    }

    @Override
    public void link(Tree90 parent, Tree90 child) {
        parent.getNodes().add(child);
    }
}
