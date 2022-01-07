package com.gussoft.viewpdf.controllers;

import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;
import org.icepdf.ri.viewer.Launcher;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class OpenDocument {

    public static void main(String[] args) {
        final String filePath = "C:\\Users\\LENOVO\\Desktop\\businessIT.pdf";//demoS.pdf";//
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

                SwingViewBuilder factory = new SwingViewBuilder(controller);
        /*PropertiesManager properties = new PropertiesManager(System.getProperties(),
                ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE));
        properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_FIT, "false");
        properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_ROTATE, "false");
        properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_TOOL, "false");
        properties.set(PropertiesManager.PROPERTY_DEFAULT_ZOOM_LEVEL, "1.25");
        properties.setBoolean(PropertiesManager.PROPERTY_SHOW_STATUSBAR_VIEWMODE, Boolean.FALSE);
        properties.set(PropertiesManager.PROPERTY_SHOW_TOOLBAR_PAGENAV, "false");
        ResourceBundle messageBundle = ResourceBundle.getBundle(PropertiesManager.DEFAULT_MESSAGE_BUNDLE);
        new FontPropertiesManager(properties, System.getProperties(), messageBundle);*/

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

                JFrame applicationFrame = new JFrame();
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
