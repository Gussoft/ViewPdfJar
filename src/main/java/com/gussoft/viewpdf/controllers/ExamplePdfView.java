package com.gussoft.viewpdf.controllers;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.ViewerPropertiesManager;

import javax.swing.*;
import java.io.File;

public class ExamplePdfView {
    public static void main(String[] args) {
        // Get a file from the command line to open
        final String filePath = "C:\\Users\\LENOVO\\Desktop\\demoS.pdf";//args[0];
        File file = new File(filePath);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // build a component controller
                SwingController controller = new SwingController();
                controller.setIsEmbeddedComponent(true);

                // read stored system font properties.
                FontPropertiesManager.getInstance().loadOrReadSystemFonts();

                ViewerPropertiesManager properties = ViewerPropertiesManager.getInstance();
                //properties.getPreferences().putFloat(ViewerPropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 1.25f);
                properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_UTILITY, Boolean.FALSE);
                properties.getPreferences().putBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION, Boolean.TRUE);
                properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_UTILITYPANE_SEARCH, Boolean.FALSE);
                properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, Boolean.FALSE);
                //properties.getPreferences().putBoolean(ViewerPropertiesManager.PROPERTY_SHOW_UTILITY_SAVE, false);
                //properties.getPreferences().putInt(DocumentViewControllerImpl.FULL_SCREEN_VIEW, 0);
                //properties.getPreferences().put(ViewerPropertiesManager.PROPERTY_TOKEN_SEPARATOR, String.valueOf(true));
                //properties.getPreferences().remove(ViewerPropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL);
                properties.getPreferences().putBoolean("application.showLocalStorageDialogs", false);

                SwingViewBuilder factory = new SwingViewBuilder(controller, properties);

                // add interactive mouse link annotation support via callback
                controller.getDocumentViewController().setAnnotationCallback(
                        new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
                JPanel viewerComponentPanel = factory.buildViewerPanel();
                JFrame applicationFrame = new JFrame("Gussoft :: [ " + file.getName() + " ]");
                applicationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                applicationFrame.getContentPane().add(viewerComponentPanel);
                // Now that the GUI is all in place, we can try openning a PDF
                controller.openDocument(filePath);

                // add the window event callback to dispose the controller and
                // currently open document.
                applicationFrame.addWindowListener(controller);

                // show the component
                applicationFrame.pack();
                applicationFrame.setVisible(true);
            }
        });


    }
}
