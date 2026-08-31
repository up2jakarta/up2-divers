package io.github.up2jakarta.csv;

import io.github.up2jakarta.csv.api.Overlink;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Up2J Business Annotation specifies that the class is a business-object (root segment).
 * <p>
 * This annotation is applied to the {@link Segment} class.
 */
@Inherited
@Documented
@Target({TYPE})
@Retention(RUNTIME)
public @interface BusinessObject {

    /**
     * @return the code of input type.
     * @see io.github.up2jakarta.csv.api.IType#getCode()
     */
    String value();

    /**
     * Returns the overrides for declared {@link BusinessLink} at property level with exclusions
     * and {@link BusinessLink#value()} replacements abilities for sub-links.
     * <ul>
     *     <b>Here is a business case of how annotations can be used to realize developer dreams:</b>
     *     <li>The mapping of complex structure like hierarchical {@code Tree}</li>
     *     <li>Within less-code approach: single class for {@code Tree} structure</li>
     * </u>
     * {@snippet lang = "java":
     *       import io.github.up2jakarta.csv.*;
     *       import io.github.up2jakarta.csv.api.*;
     *       import io.github.up2jakarta.csv.cfg.*;
     *       import jakarta.inject.Singleton;
     *       import jakarta.validation.Valid;
     *       import jakarta.validation.constraints.*;
     *       import java.util.*;
     *
     *       @Valid
     *       @Error(value = "CSV-TREE")
     *       @BusinessObject(value = "0", overrides = {
     *            @Overlink(value = @BusinessLink("1"), replaces = @Sublink(value = "1", with = "2")),
     *            @Overlink(value = @BusinessLink("2"), replaces = @Sublink(value = "1", with = "3")),
     *            @Overlink(value = @BusinessLink("3"), replaces = @Sublink(value = "1", with = "4")),
     *            @Overlink(value = @BusinessLink("4"), excludes = "1")
     *       })
     *       public final class Tree90 implements Segment {
     *
     *            @NotNull
     *            @Up2Token
     *            @BusinessId
     *            @Position(0)
     *            private final String key;
     *
     *            @NotEmpty
     *            @Position(1)
     *            @BusinessType(NONE)
     *            private final String value;
     *
     *            @BusinessLink(value = "1", automatic = true, bean = @Linker(TreeLinker.class))
     *            private final List<Tree90> nodes = new LinkedList<>();
     *
     *            public Tree90(String key, String value) {
     *                 this.value = value;
     *                  this.key = key;
     *            }
     *
     *            public String getKey() {
     *                 return key;
     *            }
     *
     *            public String getValue() {
     *                 return value;
     *            }
     *
     *            public List<Tree90> getNodes() {
     *                 return nodes;
     *            }
     *       }
     *
     *       public enum TreeType implements IType<TreeType> {
     *
     *            D0("0", "1st depth (Tree)"),
     *            D1("1", "2nd depth (Node)"),
     *            D2("2", "3rd depth (Node)"),
     *            D3("3", "4th depth (Node)"),
     *            D4("4", "5th depth (Leaf)");
     *
     *            private final String code;
     *            private final String name;
     *
     *            TreeType(String code, String name) {
     *                 this.code = code;
     *                  this.name = name;
     *            }
     *
     *            @Override
     *            public String getCode() {
     *                 return code;
     *            }
     *
     *            @Override
     *            public String getName() {
     *                 return name;
     *            }
     *
     *            @Override
     *            public String toString() {
     *                 return name;
     *            }
     *       }
     *
     *       @Singleton
     *       public class TreeLinker implements ILinker<Tree, Tree> {
     *
     *            @Override
     *            public List<Tree> from(Tree node) {
     *                 return parent.getNodes();
     *            }
     *
     *            @Override
     *            public void link(Tree parent, Tree child) {
     *                 parent.getNodes().add(child);
     *            }
     *       }
     *}
     *
     * @return the overrides for existing links defined at property level.
     */
    Overlink[] overrides() default {};

}
