/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ceos.palette;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.lookup.Lookups;

public class ShapeNode extends AbstractNode {
    private Shape shape;
    
    public ShapeNode(Shape key) {
        super(Children.LEAF, Lookups.fixed( new Object[] {key} ) );
        this.shape = key;
        setIconBaseWithExtension(key.getImage());
    }
}