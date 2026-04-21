/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ceos.palette;

import org.openide.nodes.AbstractNode;
import org.openide.util.lookup.Lookups;

public class CategoryNode extends AbstractNode {
    public CategoryNode( Category category ) {
        super( new ShapeChildren(category), Lookups.singleton(category) );
        setDisplayName(category.getName());
    }
}
