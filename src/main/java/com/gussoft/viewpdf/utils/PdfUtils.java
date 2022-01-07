package com.gussoft.viewpdf.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import javax.swing.text.StyleConstants;

public class PdfUtils {

    public static final String out = "C:\\Users\\LENOVO\\Desktop\\crearNew.pdf";
    public static final String FOX = "C:\\Users\\LENOVO\\Desktop\\fox.png";
    public static final String DOG = "C:\\Users\\LENOVO\\Desktop\\dog.png";
    public static final String DATA = "C:\\Users\\LENOVO\\Desktop\\client.csv";
    public static final String content = "Welcome to the Jungle... aqui debemos insertar un lorem ipsun para verificar el " +
            "contenido centrado con ciertas caracteristicas, asi que vamos a escribir un poco para ver el resultado.";

    public static void main(String[] args) {
        System.out.println("Welcome to the Jungle!");
        createPdf(out);

    }

    private static void createPdf(String out){
        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);

            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(20, 20, 20, 20);
            Image fox = new Image(ImageDataFactory.create(FOX));
            Image dog = new Image(ImageDataFactory.create(DOG));
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont currier = PdfFontFactory.createFont(StandardFonts.COURIER);
            ImageData data = ImageDataFactory.create(DOG);
            Image image = new Image(data);
            image.setPadding(20);
            image.setMarginTop(20);
            image.setWidth(200);
            image.setMaxHeight(250);
            image.setAutoScale(false);
            image.setTextAlignment(TextAlignment.CENTER);

            document.add(image);

            Paragraph paragraph = new Paragraph(content);
            paragraph.setFontSize(14);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            paragraph.setBorder(Border.NO_BORDER);
            paragraph.setFirstLineIndent(20);
            paragraph.setItalic();
            paragraph.setBold();
            paragraph.setBackgroundColor(new DeviceRgb(245, 245, 245));
            paragraph.setMargin(10);
            paragraph.setPaddingLeft(10);
            paragraph.setPaddingRight(10);
            paragraph.setWidth(1000);
            paragraph.setHeight(100);
            document.add(paragraph);
            /*Paragraph p = new Paragraph("The quick brown ")
                    .add(fox)
                    .add(" jumps over the lazy ")
                    .add(dog);*/
            // Add Paragraph to document

            com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List();
            list.add("Java");
            list.add("Go");
            list.add("React");
            list.add("Apache Kafka");
            list.add("Jenkins");
            list.add("Elastic Search");
            list.setTextAlignment(TextAlignment.CENTER);
            document.add(list);

            document.add(new Paragraph("Welcome to the Secret!").setFontSize(24));
            document.add(new Paragraph("Welcome to the Jungle...").setFont(currier).setFontSize(18));
            //document.add(p);
            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 1, 3, 4, 3, 3, 3}))
                    .useAllAvailableWidth();
            BufferedReader br = new BufferedReader(new FileReader(DATA));// llenar la tabla
            String line = br.readLine();
            process(table, line, bold, true);
            while ((line = br.readLine()) != null) {
                process(table, line, font, false);
            }
            br.close();
            document.add(table);
            document.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }
}
