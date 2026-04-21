/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ceos.palette;

import java.util.ArrayList;
import org.openide.nodes.Index;
import org.openide.nodes.Node;

public class ShapeChildren extends Index.ArrayChildren {

    private Category category;
    private String[][] items = new String[][]{
        {"0", "Shapes", "org/netbeans/shapesample/palette/image1.png"},
        {"1", "Shapes", "org/netbeans/shapesample/palette/image2.png"},
        {"2", "Shapes", "org/netbeans/shapesample/palette/image3.png"},};

    public ShapeChildren(Category Category) {
        this.category = Category;
    }

    @Override
    protected java.util.List initCollection() {
        ArrayList childrenNodes = new ArrayList(items.length);
        for (int i = 0; i < items.length; i++) {
            // Corregido: Usamos el índice 1 para comparar la categoría ("Shapes")
            if (category.getName().equals(items[i][1])) {
                Shape item = new Shape();

                // Corregido: Pasamos solo el String de la posición 0 ("0", "1", "2")
                // Nota: new Integer() está deprecado, mejor usa Integer.valueOf()
                item.setNumber(Integer.valueOf(items[i][0]));

                // Corregido: Índices ajustados a la estructura de tu arreglo
                item.setCategory(items[i][1]);
                item.setImage(items[i][2]);

                childrenNodes.add(new ShapeNode(item));
            }
        }
        return childrenNodes;
    }
}
