package com.ceos.visual;

import java.awt.datatransfer.*;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.datatransfer.ExTransferable;

public class ShapeItems {

    // DataFlavor compartido para identificar el tipo de dato
    public static final DataFlavor SHAPE_FLAVOR =
        new DataFlavor(String.class, "ShapeType");

    /** Fábrica para el rectángulo (llamada desde layer.xml) */
    public static ShapeNode createRectangle() {
        return new ShapeNode("RECTANGLE", "Rectángulo");
    }

    /** Fábrica para la elipse */
    public static ShapeNode createEllipse() {
        return new ShapeNode("ELLIPSE", "Elipse");
    }

    // -------------------------------------------------------
    // Nodo interno que representa un ítem de la paleta
    // -------------------------------------------------------
    public static class ShapeNode extends AbstractNode {

        private final String shapeType; // dato que viajará en el drag

        public ShapeNode(String shapeType, String displayName) {
            super(Children.LEAF);
            this.shapeType = shapeType;
            setDisplayName(displayName);
        }

        @Override
        public Transferable drag() {
            ExTransferable t = ExTransferable.create(
                new ExTransferable.Single(SHAPE_FLAVOR) {
                    @Override
                    protected Object getData() {
                        return shapeType; // "RECTANGLE" o "ELLIPSE"
                    }
                }
            );
            return t;
        }
    }
}