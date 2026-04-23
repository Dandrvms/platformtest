/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/templateTopComponent637.java to edit this template
 */
package com.ceos.visual;

import java.awt.BorderLayout;
import java.awt.Desktop.Action;
import java.awt.Point;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import org.netbeans.api.settings.ConvertAsProperties;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
//import org.openide.util.lookup.Lookups;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.ceos.visual//Visual//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "VisualTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "com.ceos.visual.VisualTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_VisualAction",
        preferredID = "VisualTopComponent"
)
@Messages({
    "CTL_VisualAction=Visual",
    "CTL_VisualTopComponent=Visual Window",
    "HINT_VisualTopComponent=This is a Visual window"
})
public final class VisualTopComponent extends TopComponent {

    private static VisualTopComponent instance;

    // Singleton — NetBeans no crea dos instancias
    public static synchronized VisualTopComponent findInstance() throws IOException {
        if (instance == null) {
            instance = new VisualTopComponent();
        }
        return instance;
    }
    private PaletteController palette;
    private final MyScene scene = new MyScene();
    private final InstanceContent content = new InstanceContent();

    public VisualTopComponent() throws IOException {
        initComponents();

        setName(Bundle.CTL_VisualTopComponent());
        setToolTipText(Bundle.HINT_VisualTopComponent());

        setLayout(new BorderLayout());
        JComponent view = scene.createView();
        JScrollPane scroll = new JScrollPane(view);
        add(scroll, BorderLayout.CENTER);
        add(scene.createSatelliteView(), BorderLayout.WEST);

        // ── 2. Crear la paleta desde la carpeta "MyPalette" en layer.xml ──
        palette = PaletteFactory.createPalette(
                "MyPalette", // carpeta raíz en layer.xml
                new PaletteActions() {  // acciones opcionales (pueden estar vacías)

            @Override
            public javax.swing.Action[] getCustomCategoryActions(org.openide.util.Lookup lkp) {
                return new javax.swing.Action[0];
            }

            @Override
            public javax.swing.Action[] getCustomItemActions(org.openide.util.Lookup lkp) {
                return new javax.swing.Action[0];
            }

            @Override
            public javax.swing.Action getPreferredAction(org.openide.util.Lookup lkp) {
                return null;
            }

            @Override
            public javax.swing.Action[] getImportActions() {
                return new javax.swing.Action[0];
            }

            @Override
            public javax.swing.Action[] getCustomPaletteActions() {
                return new javax.swing.Action[0];
            }
        }
        );

        // ── 3. Exponer la paleta en el Lookup del TopComponent ──────
        content.add(palette);
        associateLookup(new AbstractLookup(content));

        // ── 4. Registrar DropTarget en la vista de la escena ────────
        // registerDropTarget(view);
    }

    private void registerDropTarget(JComponent view) {
        new DropTarget(view, DnDConstants.ACTION_COPY, new DropTargetAdapter() {

            @Override
            public void dragEnter(DropTargetDragEvent e) {
                // Es vital aceptar el drag aquí también
                if (e.isDataFlavorSupported(ShapeItems.SHAPE_FLAVOR)) {
                    e.acceptDrag(DnDConstants.ACTION_COPY);
                } else {
                    e.rejectDrag();
                }
            }

            @Override
            public void dragOver(DropTargetDragEvent e) {
                System.out.println("Flavors disponibles: " + Arrays.toString(e.getCurrentDataFlavors()));
                if (e.isDataFlavorSupported(ShapeItems.SHAPE_FLAVOR)) {
                    e.acceptDrag(DnDConstants.ACTION_COPY);
                } else {
                    e.rejectDrag();
                }
            }

            @Override
            public void drop(DropTargetDropEvent e) {
                try {
                    e.acceptDrop(DnDConstants.ACTION_COPY);
                    String shapeType = (String) e.getTransferable().getTransferData(ShapeItems.SHAPE_FLAVOR);

                    // Convertir coordenadas del drop a coordenadas de escena
                    Point dropPt = e.getLocation();
                    Point scenePt = scene.convertViewToScene(dropPt);

                    scene.addShape(shapeType, scenePt);
                    e.dropComplete(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    e.dropComplete(false);
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

}
