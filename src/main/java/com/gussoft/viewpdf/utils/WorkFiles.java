package com.gussoft.viewpdf.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import java.util.Objects;
import java.util.zip.*;

public class WorkFiles {
    private static final Logger logger = LogManager.getLogger(WorkFiles.class.getName());

    private static final int BUFFER_SIZE = 2 * 1024;

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Jungle!");
        String entry = "C:\\Users\\LENOVO\\Desktop\\sinfirma.zip";
        String unzip = "C:\\Users\\LENOVO\\Desktop\\sinfirma\\";
        //System.out.println(copyFile("https://www.traffic.org/site/assets/files/5332/traffic_pub_gen19.pdf",//"https://download.support.xerox.com/pub/docs/FlowPort2/userdocs/any-os/en/fp_dc_setup_guide.pdf",
        //        "C:\\Users\\LENOVO\\Desktop\\well\\guide.pdf"));
        //System.out.println(copyFile("C:\\Users\\LENOVO\\Desktop\\demoS.pdf",
        //        "C:\\Users\\LENOVO\\Desktop\\well\\guide.pdf"));
        //listZip(entry);
        //unZip(entry, unzip);
       // doZip(entry, unzip);
        //readDirectoryToZip(unzip,entry);// mas rapido
        //String filePath = "C:\\Users\\LENOVO\\Desktop\\well\\"; // representa una carpeta
        //String zipPath = "C:\\Users\\LENOVO\\Desktop\\well.zip"; // representa un archivo comprimido
        //zipMultiFile(filePath, zipPath); // muy lento
        cleanDirectory(new File("C:\\Users\\Lenovo\\Desktop\\pruba"));
    }

    public static boolean copyFile(String in, String out) {
        boolean res;
        try {
            res = copyFile(new URL(in),new File(out));
        }catch (MalformedURLException e){
            res = copyFile(new File(in), new File(out));
        }
       return res;
    }

    private static boolean copyFile(File source, File destination){
        BufferedInputStream fis = null;
        BufferedOutputStream fout = null;
        logger.trace("Inciando el copiado.");
        try {
            int bufSize = 8 * 1024;
            fis = new BufferedInputStream(new FileInputStream(source), bufSize);
            fout = new BufferedOutputStream(new FileOutputStream(destination), bufSize);
            copyPipe(fis, fout, bufSize);
        } catch (FileNotFoundException e){
            System.out.println("El archivo no existe! " + e.getMessage());
            logger.error("Error... No se encontro el archivo : " + e.getMessage());
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fis != null){
                try {
                    fis.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (fout != null){
                try {
                    fout.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            logger.trace("Cerrando Copiado");
        }
        return true;
    }

    private static boolean copyFile(URL from, File to){
        BufferedInputStream urlIn = null;
        BufferedOutputStream fout = null;
        try {
            int bufSize = 8 * 1024;
            urlIn = new BufferedInputStream(from.openConnection()
                        .getInputStream(), bufSize);
            fout = new BufferedOutputStream(new FileOutputStream(to), bufSize);
            copyPipe(urlIn,fout,bufSize);
        } catch (FileNotFoundException e) {
            System.out.println("El archivo de la Url no existe :: " + e.getMessage());
            return false;
        }catch (UnknownHostException e){
            System.out.println("Host no encontrado! "+ e.getMessage());
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }finally {
            if (urlIn != null){
                try {
                    urlIn.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (fout != null){
                try {
                    fout.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private static void copyPipe(InputStream fis, OutputStream fout, int bufSize) throws IOException {

        int read = -1;
        byte[] buf = new byte[bufSize];
        while ((read = fis.read(buf, 0, bufSize)) >= 0){
            fout.write(buf, 0, read);
        }
        fout.flush();
    }

    private static void listZip(String zip) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zip);
            Enumeration zipEntries = zipFile.entries();

            while (zipEntries.hasMoreElements()) {
                System.out.println(((ZipEntry) zipEntries.nextElement()).getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unZip(String zipname, String out) {

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipname);

            Enumeration enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enumeration.nextElement();
                //System.out.println("Unzipping: " + zipEntry.getName());
                String Salida = out +"//"+zipEntry.getName();
                BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                int size;
                byte[] buffer = new byte[2048];
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream(Salida), buffer.length);
                while ((size = bis.read(buffer, 0, buffer.length)) != -1) {
                    bos.write(buffer, 0, size);
                }
                bos.flush();
                bos.close();
                bis.close();
            }
        } catch(IOException e){
                e.printStackTrace();

        }
    }

    private static void doZip(String zipname, String files){

        try {
            byte[] buf = new byte[1024];
            FileInputStream fis = new FileInputStream(files);
            fis.read(buf, 0, buf.length);

            CRC32 crc = new CRC32();
            ZipOutputStream s = new ZipOutputStream((OutputStream) new FileOutputStream(zipname));
            s.setLevel(6);

            ZipEntry entry = new ZipEntry(files);
            entry.setSize((long) buf.length);
            crc.reset();
            crc.update(buf);
            entry.setCrc(crc.getValue());
            s.putNextEntry(entry);
            s.write(buf, 0, buf.length);
            s.finish();
            s.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void readDirectoryToZip(String dir, String out){
        File op = new File(dir);
        List<File> list = new ArrayList<>();
        String[] children = op.list();
        if (children == null) {
            System.out.println("does not exist or is not a directory");
        } else {
            System.err.println("Files in Directory " + op.getName());
            for (int i = 0; i < children.length; i++) {
                String filename = children[i];
                System.out.println(dir+"\\"+filename);
                File file = new File(dir+"\\"+filename);
                list.add(file);
            }
            toZip(list, out);
        }
    }

    private static void createFileTemp(){
        try {
            File temp = File.createTempFile("texto-", ".txt");

            //temp.deleteOnExit();
            System.out.println("temp = " + temp);
            BufferedWriter out = new BufferedWriter(new FileWriter(temp));
            out.write("Acm1pt");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File directory = new File("C:/temp/carpetacreada");
        if (directory.mkdir()) {
            System.out.println("Success mkdir");
        } else {
            if (directory.mkdirs()) {
                System.out.println("Success mkdirs");
            } else {
                System.out.println("Failed");
            }
        }
    }

    private static void toZip(List<File> srcFiles, String output) {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            OutputStream out = new FileOutputStream(output);
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
                System.out.println("Add to Zip File : " + srcFile.getName());
            }
            long end = System.currentTimeMillis();
            System.out.println("Finalización de la compresión, requiere mucho tiempo: " + (end-start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Excepción del sistema:" + e.getMessage());
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    private static void zipMultiFile(String filePath, String zipPath){
        if (Utils.isEmpty(filePath)){
            System.err.println("filePath is Null");
            return;
        }
        if (Utils.isEmpty(zipPath)) {
            System.err.println("zipPath is null");
            return;
        }
        InputStream input = null;
        File file = new File(filePath);
        File zip = new File(zipPath);
        ZipOutputStream zipOut = null;
       try {
            zipOut = new ZipOutputStream(FileUtils.openOutputStream(zip));
            if (file.isDirectory()){
                File[] files = file.listFiles();
                if (files != null){
                    for (File tempFile: files){
                        input = FileUtils.openInputStream(tempFile);
                        //System.out.println(file.getName() + File.separator + tempFile.getName());
                        zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + tempFile.getName()));

                        int temp;
                        while ((temp = input.read()) != -1){
                            //System.out.println(temp);
                            zipOut.write(temp);
                        }
                        try {
                            //System.out.println ("Cerrar 2 recursos FileInputStream");
                            input.close();
                            input = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
       }catch (Exception e){
           e.printStackTrace();
       }finally {
           try {
               if (input != null) {
                   System.out.println ("Este lugar es para proteger y cerrar los recursos FileInputStream en 2 lugares, generalmente no vaya aquí");
                   input.close();
               }
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               if (zipOut != null) {
                   try {
                       zipOut.close();
                       System.out.println ("Cerrar 1 recurso zipOut");
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }

       }

    }

    //elimina todos los archivo dentro de la raiz, no carpetas
    public static void cleanDirectory(File directory) throws IOException {
        Objects.requireNonNull(directory, "Directory cannot be null");
        if (!directory.exists() || !directory.isDirectory()) {
            throw new FileNotFoundException("Directory '" + directory.getAbsolutePath() + "' not found");
        } else if (directory.isDirectory()) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        cleanDirectory(file);
                    } else if (file.isFile()) {
                        if (!file.delete()) {
                            throw new IOException("Unable to delete file " + file.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }
}
