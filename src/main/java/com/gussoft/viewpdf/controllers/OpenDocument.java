package com.gussoft.viewpdf.controllers;

import org.icepdf.core.pobjects.ViewerPreferences;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.ViewerPropertiesManager;
import org.icepdf.ri.viewer.Launcher;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class OpenDocument {

    public static void main(String[] args) {
        final String filePath = "C:\\Users\\GusMa\\Desktop\\demoS.pdf";//demoS.pdf";//
        VerPdf(filePath);



/*
        //SwingUtilities.invokeLater(new Runnable() {
          //  public void run() {
                // build a component controller
                SwingController controller = new SwingController();
                controller.setIsEmbeddedComponent(true);

                // read stored system font properties.
                //FontPropertiesManager.getInstance().loadOrReadSystemFonts();

                //PropertiesManager properties = PropertiesManager.getInstance();
                //properties.getPreferences().putFloat(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, 1.25f);

                SwingViewBuilder factory = new SwingViewBuilder(controller);

                // add interactive mouse link annotation support via callback
                controller.getDocumentViewController().setAnnotationCallback(
                        new org.icepdf.ri.common.MyAnnotationCallback(controller.getDocumentViewController()));
                JPanel viewerComponentPanel = factory.buildViewerPanel();
                JFrame applicationFrame = new JFrame("Ver documento");
                applicationFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                applicationFrame.add(viewerComponentPanel, BorderLayout.CENTER);
                applicationFrame.getContentPane().add(viewerComponentPanel);
                // Now that the GUI is all in place, we can try openning a PDF
                controller.openDocument(filePath);

                // add the window event callback to dispose the controller and
                // currently open document.
                applicationFrame.addWindowListener(controller);

                // show the component
                applicationFrame.pack();
                applicationFrame.setVisible(true);
           // }
       // });*/
    }

    public static void VerPdf(String filePath) {

        SwingController controller = new SwingController();
        ViewerPropertiesManager properties = ViewerPropertiesManager.getInstance();
                //ViewerPropertiesManager.getInstance();
        //new ViewerPropertiesManager(System.getProperties(),
                //ResourceBundle.getBundle(ViewerPropertiesManager.DEFAULT_MESSAGE_BUNDLE));
        ViewerPropertiesManager.printAllProperties();
        //if (PropertiesManager.checkAndStoreBooleanProperty(properties, ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_UTILITY))
        properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, Boolean.FALSE);
        properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, Boolean.FALSE);
        properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, Boolean.FALSE);
        //properties.set(ViewerPropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.25");
        properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_STATUSBAR_VIEWMODE, Boolean.FALSE);
        properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV, Boolean.TRUE);

        properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_ANNOTATION, false);
        properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, false);
        properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_ZOOM, false);
        properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_UTILITYPANE_SEARCH, false);
        properties.setBoolean(ViewerPropertiesManager.PROPERTY_SHOW_TOOLBAR_SEARCH, false);
        //ResourceBundle messageBundle = ResourceBundle.getBundle(ViewerPropertiesManager.DEFAULT_MESSAGE_BUNDLE);
        //new FontPropertiesManager(properties, System.getProperties(), messageBundle);
        SwingViewBuilder factory = new SwingViewBuilder(controller, properties);


        JPanel viewerComponentPanel = factory.buildViewerPanel();
        //viewerComponentPanel.
        viewerComponentPanel.setPreferredSize(new Dimension(700, 700));
        viewerComponentPanel.setMaximumSize(new Dimension(700, 700));
        //viewerComponentPanel.setBorder(BorderFactory.createTitledBorder(
        //        BorderFactory.createEtchedBorder(), "BigPrime"));
        //setBackground(Color.green);
        controller.getDocumentViewController().setAnnotationCallback(
                new org.icepdf.ri.common.MyAnnotationCallback(
                        controller.getDocumentViewController()));

        JFrame applicationFrame = new JFrame("Gussoft :: " + filePath);
        applicationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        applicationFrame.add(viewerComponentPanel, BorderLayout.CENTER);
        //applicationFrame.getContentPane().add(viewerComponentPanel);
        //applicationFrame.add(viewerComponentPanel);
        applicationFrame.invalidate();

        controller.openDocument(filePath);

        applicationFrame.pack();
        applicationFrame.setVisible(true);
    }
}
