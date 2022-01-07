package com.gussoft.viewpdf.controllers;

import org.icepdf.core.util.Defs;
import org.icepdf.core.util.SystemProperties;
import org.icepdf.ri.common.ViewModel;
import org.icepdf.ri.util.FontPropertiesManager;
import org.icepdf.ri.util.URLAccess;
import org.icepdf.ri.util.ViewerPropertiesManager;
import org.icepdf.ri.viewer.Launcher;
import org.icepdf.ri.viewer.WindowManager;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

public class Run {
    private static final Logger logger = Logger.getLogger(Launcher.class.toString());
    public static final String APPLICATION_LOOK_AND_FEEL = "application.lookandfeel";
    private static ViewerPropertiesManager propertiesManager;

    public Run() {
    }

    public static void main(String[] argv) {
        boolean brokenUsage = false;
        String contentURL = "";
        String contentFile = "C:\\Users\\LENOVO\\Desktop\\businessIT.pdf";//demoS.pdf";//
        String printer = null;

        for(int i = 0; i < argv.length; ++i) {
            if (i == argv.length - 1) {
                brokenUsage = true;
                break;
            }

            String arg = argv[i];
            byte var8 = -1;
            switch(arg.hashCode()) {
                case 1395246752:
                    if (arg.equals("-print")) {
                        var8 = 2;
                    }
                    break;
                case 1453715964:
                    if (arg.equals("-loadurl")) {
                        var8 = 1;
                    }
                    break;
                case 2115066511:
                    if (arg.equals("-loadfile")) {
                        var8 = 0;
                    }
            }

            switch(var8) {
                case 0:
                    ++i;
                    contentFile = argv[i].trim();
                    break;
                case 1:
                    ++i;
                    contentURL = argv[i].trim();
                    break;
                case 2:
                    ++i;
                    printer = argv[i].trim();
                    break;
                default:
                    brokenUsage = true;
            }
        }

        ResourceBundle messageBundle = ResourceBundle.getBundle("org.icepdf.ri.resources.MessageBundle");
        if (brokenUsage) {
            System.out.println(messageBundle.getString("viewer.commandLin.error"));
            System.exit(1);
        }

        run(contentFile, contentURL, printer, messageBundle);
    }

    private static void run(String contentFile, String contentURL, String printer, ResourceBundle messageBundle) {
        propertiesManager = ViewerPropertiesManager.getInstance();
        FontPropertiesManager.getInstance().loadOrReadSystemFonts();
        setupLookAndFeel(messageBundle);
        ViewModel.setDefaultFilePath(propertiesManager.getPreferences().get("application.default.filepath", (String)null));
        ViewModel.setDefaultURL(propertiesManager.getPreferences().get("application.default.url", (String)null));
        WindowManager windowManager = WindowManager.createInstance(propertiesManager, messageBundle);
        if (contentFile != null && !contentFile.isEmpty()) {
            if (printer != null) {
                windowManager.newWindow(contentFile, printer);
            } else {
                windowManager.newWindow(contentFile);
            }

            ViewModel.setDefaultFilePath(contentFile);
        }

        if (contentURL != null && !contentURL.isEmpty()) {
            URLAccess urlAccess = URLAccess.doURLAccess(contentURL);
            urlAccess.closeConnection();
            if (urlAccess.errorMessage != null) {
                Object[] messageArguments = new Object[]{urlAccess.errorMessage, urlAccess.urlLocation};
                MessageFormat formatter = new MessageFormat(messageBundle.getString("viewer.launcher.URLError.dialog.message"));
                JOptionPane.showMessageDialog((Component)null, formatter.format(messageArguments), messageBundle.getString("viewer.launcher.URLError.dialog.title"), 1);
            } else if (printer != null) {
                windowManager.newWindow(urlAccess.url, printer);
            } else {
                windowManager.newWindow(urlAccess.url);
            }

            ViewModel.setDefaultURL(urlAccess.urlLocation);
            urlAccess.dispose();
        }

        if ((contentFile == null || contentFile.isEmpty()) && (contentURL == null || contentURL.isEmpty()) || windowManager.getNumberOfWindows() == 0L) {
            windowManager.newWindow("");
        }

    }

    private static void setupLookAndFeel(ResourceBundle messageBundle) {
        if (SystemProperties.OS_NAME.contains("OS X")) {
            Defs.setSystemProperty("apple.laf.useScreenMenuBar", "true");
            String appName = messageBundle.getString("viewer.window.title.default");
            Defs.setSystemProperty("com.apple.mrj.application.apple.menu.about.name", appName);
        }

        Preferences preferences = propertiesManager.getPreferences();
        String className = propertiesManager.getLookAndFeel("application.lookandfeel", (String)null, messageBundle);
        if (className != null) {
            try {
                UIManager.setLookAndFeel(className);
                return;
            } catch (Exception var7) {
                Object[] messageArguments = new Object[]{preferences.get("application.lookandfeel", (String)null)};
                MessageFormat formatter = new MessageFormat(messageBundle.getString("viewer.launcher.URLError.dialog.message"));
                JOptionPane.showMessageDialog((Component)null, formatter.format(messageArguments), messageBundle.getString("viewer.launcher.lookAndFeel.error.message"), 0);
            }
        }

        try {
            String defaultLF = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(defaultLF);
        } catch (Exception var6) {
            logger.log(Level.FINE, "Error setting Swing Look and Feel.", var6);
        }

    }
}
