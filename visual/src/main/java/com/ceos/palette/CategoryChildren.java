/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ceos.palette;


import org.openide.nodes.Children;
import org.openide.nodes.Node;

public class CategoryChildren extends Children.Keys {
    private String[] Categories = new String[]{ "Shapes" };

    public CategoryChildren() {
    }

    protected Node[] createNodes(Object key) {
        Category obj = (Category) key;
        return new Node[] { new CategoryNode(obj) };
    }

    protected void addNotify() {
        super.addNotify();
        Category[] objs = new Category[Categories.length];
        for (int i = 0; i < objs.length; i++) {
            Category cat = new Category();
            cat.setName(Categories[i]);
            objs[i] = cat;
        }
        setKeys(objs);
    }
}
