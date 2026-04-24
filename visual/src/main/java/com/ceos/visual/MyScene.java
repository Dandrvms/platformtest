package com.ceos.visual;

import java.awt.*;
import java.awt.datatransfer.Transferable;
import javax.swing.BorderFactory;
import org.netbeans.api.visual.widget.*;
import org.netbeans.api.visual.action.*;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

public class MyScene extends GraphScene<String, String> {

    private final LayerWidget nodeLayer = new LayerWidget(this);
    private final LayerWidget connLayer = new LayerWidget(this);
    private final LayerWidget interactLayer = new LayerWidget(this);
    private int nodeCount = 0;
    private int edgeCount = 0;

    public MyScene() {
        addChild(connLayer);  // conexiones debajo
        addChild(nodeLayer);  // nodos encima

        // Acción de mover la escena con el ratón (scroll/pan)
        getActions().addAction(ActionFactory.createPanAction());
        getActions().addAction(ActionFactory.createZoomAction());

        // En MyScene.java
        this.getActions().addAction(ActionFactory.createAcceptAction(new AcceptProvider() {
            @Override
            public ConnectorState isAcceptable(Widget widget, Point point, Transferable transferable) {
                return transferable.isDataFlavorSupported(ShapeItems.SHAPE_FLAVOR)
                        ? ConnectorState.ACCEPT : ConnectorState.REJECT;
            }

            @Override
            public void accept(Widget widget, Point point, Transferable transferable) {
                try {
                    String shapeType = (String) transferable.getTransferData(ShapeItems.SHAPE_FLAVOR);
                    addShape(shapeType, point); // 'point' ya viene en coordenadas de escena
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }));

        getActions().addAction(ActionFactory.createPanAction());
    }

    /**
     * Crea y agrega un widget a la escena en la posición indicada.
     *
     * @param shapeType "RECTANGLE" | "ELLIPSE"
     * @param location punto en coordenadas de escena
     */
    public void addShape(String shapeType, Point location) {
        String nodeId = shapeType + (nodeCount++);
        Widget w = addNode(nodeId);
        w.setPreferredLocation(location);

        validate(); // redibuja
        this.getView().repaint();
    }

    private Widget buildWidget(String shapeType) {
        // Contenedor principal (el que se mueve)
        Widget container = new Widget(this);
        container.setLayout(LayoutFactory.createVerticalFlowLayout());
        container.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // La parte visual
        IconNodeWidget lw = new IconNodeWidget(this, IconNodeWidget.TextOrientation.RIGHT_CENTER);
        lw.setLabel("FASE " + nodeCount);
        lw.setBackground(Color.LIGHT_GRAY);
        lw.setOpaque(true);
        

        // Pin exclusivo para conexiones (igual que en el código que funciona)
        Widget pin = new Widget(this);
        pin.setPreferredSize(new java.awt.Dimension(12, 12));
        pin.setBackground(Color.LIGHT_GRAY);
        pin.setOpaque(true);
        pin.getActions().addAction(
                ActionFactory.createConnectAction(interactLayer, new MyConnectProvider()));

        // El movimiento va en el contenedor, NO en el pin
        container.getActions().addAction(
                ActionFactory.createAlignWithMoveAction(
                        nodeLayer, connLayer,
                        ActionFactory.createDefaultAlignWithMoveDecorator()));
        container.getActions().addAction(
                ActionFactory.createSelectAction(new SingleSelectProvider()));

        container.addChild(lw);
        container.addChild(pin);  // pin debajo del label

        return container;
    }

    @Override
    protected Widget attachNodeWidget(String node) {
        String shapeType = node.contains("ELLIPSE") ? "ELLIPSE" : "RECTANGLE";
        Widget w = buildWidget(shapeType);
        nodeLayer.addChild(w);
        return w;
    }

    @Override
    protected Widget attachEdgeWidget(String e) {
        ConnectionWidget conn = new ConnectionWidget(this);
        conn.setTargetAnchorShape(AnchorShape.NONE);
        conn.setStroke(new BasicStroke(2));
        
        conn.setRouter(RouterFactory.createOrthogonalSearchRouter(nodeLayer));
        connLayer.addChild(conn);
        return conn;
    }

    @Override
    protected void attachEdgeSourceAnchor(String edge, String oldSrc, String newSrc) {
        Widget w = findWidget(newSrc);
        ((ConnectionWidget) findWidget(edge)).setSourceAnchor(AnchorFactory.createRectangularAnchor(w));
    }

    @Override
    protected void attachEdgeTargetAnchor(String edge, String oldSrc, String newSrc) {
        Widget w = findWidget(newSrc);
        ((ConnectionWidget) findWidget(edge)).setTargetAnchor(AnchorFactory.createRectangularAnchor(w));
    }

    // Proveedor de selección simple (mínimo requerido)
    private static class SingleSelectProvider implements SelectProvider {

        @Override
        public boolean isAimingAllowed(Widget w, Point l, boolean inv) {
            return false;
        }

        @Override
        public boolean isSelectionAllowed(Widget w, Point l, boolean inv) {
            return true;
        }

        @Override
        public void select(Widget w, Point l, boolean inv) {
            w.getScene().setFocusedWidget(w);
        }
    }

    private class MyConnectProvider implements ConnectProvider {

        @Override
        public boolean isSourceWidget(Widget source) {
            return true;  // el pin solo está en nodos, siempre es válido
        }

        @Override
        public ConnectorState isTargetWidget(Widget src, Widget trg) {
            Object trgObj = findObject(trg);
            // findObject devuelve null si trg no es un nodo registrado
            return (trgObj != null && trg != src)
                    ? ConnectorState.ACCEPT : ConnectorState.REJECT;
        }

        @Override
        public boolean hasCustomTargetWidgetResolver(Scene s) {
            return false;
        }

        @Override
        public Widget resolveTargetWidget(Scene s, Point p) {
            return null;
        }

        @Override
        public void createConnection(Widget source, Widget target) {
            String sourceNode = (String) findObject(source);
            String targetNode = (String) findObject(target);

            if (sourceNode == null || targetNode == null) {
                return;
            }

            String edgeId = "edge" + (edgeCount++);
            addEdge(edgeId);
            setEdgeSource(edgeId, sourceNode);
            setEdgeTarget(edgeId, targetNode);
            validate();
        }
    }
}
