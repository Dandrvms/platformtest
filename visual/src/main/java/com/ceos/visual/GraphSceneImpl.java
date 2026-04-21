package com.ceos.visual;

import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;

public class GraphSceneImpl extends GraphScene {

    public GraphSceneImpl() {
    }

    @Override
    protected Widget attachNodeWidget(Object node) {
        return null;
    }

    @Override
    protected Widget attachEdgeWidget(Object edge) {
        return null;
    }

    @Override
    protected void attachEdgeSourceAnchor(Object edge, Object oldSourceNode, Object newSourceNode) {

    }

    @Override
    protected void attachEdgeTargetAnchor(Object edge, Object oldTargetNode, Object newTargetNode) {

    }

}