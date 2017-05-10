package com.home.service;


import static java.util.Objects.nonNull;

import com.home.domain.Employee;
import com.home.domain.Ot;
import com.home.domain.Pt;
import com.itextpdf.barcodes.BarcodeEAN;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.Set;

@Component
public class PdfService {

    public void createOtPdf(Ot ot){

        try {
            FileOutputStream fos = new FileOutputStream(ot.getName()+"_Ot.pdf");
            PdfDocument pdf = new PdfDocument(new PdfWriter(fos));
            //pdf.setDefaultPageSize(PageSize.);
            Document document = new Document(pdf);

            Table table = new Table(new float[]{13, 11, 2, 3});
            table.setWidthPercent(100);
            Cell cell;
            cell = new Cell(1, 3).add("PRZYJECIE SRODKA TRWALEGO").setFont(PdfFontFactory.createFont(FontConstants.HELVETICA)).setBold().setFontSize(11).setMarginLeft(20f).setMarginRight(20f).setTextAlignment(TextAlignment.CENTER);;
            table.addCell(cell);
            cell = new Cell(3, 1).add("OT").setMarginLeft(5f).setMarginRight(5f).setBold().setFontSize(38).setTextAlignment(TextAlignment.CENTER);;
            table.addCell(cell);
            table.addCell("Nr inwentarzowy: " + (nonNull(ot.getAsset().getInventoryCode()) ? ot.getAsset().getInventoryCode() : ""));
            table.addCell("Data");
            table.addCell("Pieczec i podpis").setMarginLeft(30f).setMarginRight(30f);
            table.addCell("Symbol OT: "+ (nonNull(ot.getAsset().getSymbol()) ? ot.getAsset().getSymbol(): ""));
            table.addCell(nonNull(ot.getDate()) ? ot.getDate().toString() : "");
            table.addCell("").setMinHeight(40f);
            cell = new Cell(1, 3).add("Nazwa\n"+(nonNull(ot.getAsset().getName()) ? ot.getAsset().getName() : ""));
            table.addCell(cell);
            table.addCell(createBarcode(String.format("%08d", createCode(ot.getAsset().getId(), ot.getDate())), pdf));
            cell = new Cell(1, 4).add("Charakterystyka\n"+(nonNull(ot.getAsset().getDescription()) ? ot.getAsset().getDescription() : ""));
            table.addCell(cell);
            cell = new Cell(1, 1).add("Dostawca lub wykonawca\n"+(nonNull(ot.getProvider()) ? ot.getProvider() : ""));
            table.addCell(cell);
            cell = new Cell(1, 3).add("Wartosc (zl)\n"+(nonNull(ot.getAsset().getValue()) ? ot.getAsset().getValue(): ""));
            table.addCell(cell);
            table.addCell("Miejsce uzytkowania \n"+(nonNull(ot.getPlace()) ? ot.getPlace() : ""));
            cell = new Cell(1, 3).add("Umorzenie (zl)\n"+(nonNull(ot.getAsset().getRemission()) ? ot.getAsset().getRemission(): ""));
            table.addCell(cell);

            document.add(table);
            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createPtPdf(Pt pt){


        try {
            FileOutputStream fos = new FileOutputStream(pt.getName() + "_Pt.pdf");
            PdfDocument pdf = new PdfDocument(new PdfWriter(fos));
            Document document = new Document(pdf);

            Table table = new Table(new float[]{33, 33, 16, 16});
            table.setWidthPercent(100);
            Cell cell;

            table.addCell(createBarcode(String.format("%08d", createCode(pt.getAsset().getId(), pt.getDate())), pdf));
            cell = new Cell(1, 1).add("PROTOKOL\nprzekazania-przejecia\nsrodka trwalego").setFont(PdfFontFactory.createFont(FontConstants.HELVETICA)).setBold().setFontSize(11).setMarginLeft(20f).setMarginRight(20f).setTextAlignment(TextAlignment.CENTER);
            table.addCell(cell);
            cell = new Cell(1, 1).add("PT").setMarginLeft(10f).setMarginRight(10f).setBold().setFontSize(38).setTextAlignment(TextAlignment.CENTER);
            table.addCell(cell);
            cell = new Cell(1,1).add("Numer\n"+(nonNull(pt.getName()) ? pt.getName() : "")).setFontSize(10).setTextAlignment(TextAlignment.LEFT);
            table.addCell(cell);
            cell = new Cell(1,4).add("Na podstawie... znak... z dnia "+(nonNull(pt.getDate()) ? pt.getDate() : " ") +" przekazuje sie ... srodek trwaly o nizej okreslonych cechach").setFontSize(10);
            table.addCell(cell);
            cell = new Cell(2, 1).add("Nazwa "+ (nonNull(pt.getAsset().getName()) ? pt.getAsset().getName() : "")).setFontSize(10);
            table.addCell(cell);
            cell = new Cell(1, 1).add("Symbok klasy rodzajowej\n"+(nonNull(pt.getAsset().getSymbol()) ? pt.getAsset().getSymbol() : "")).setFontSize(10);
            table.addCell(cell);
            cell = new Cell(1, 2).add("Wartosc poczatkowa (zl)\n"+ (nonNull(pt.getAsset().getValue()) ? pt.getAsset().getValue() : "")).setFontSize(10);
            table.addCell(cell);
            cell = new Cell(1, 1).add("Nr inwentarzowy\n"+ (nonNull(pt.getAsset().getInventoryCode()) ? pt.getAsset().getInventoryCode() : "")).setFontSize(10);
            table.addCell(cell);
            cell = new Cell(1, 2).add("Umorzenie (zl)\n"+ (nonNull(pt.getAsset().getRemission()) ? pt.getAsset().getRemission() : "")).setFontSize(10);
            table.addCell(cell);
            cell = new Cell(1, 4).add("Uwagi: "+ (nonNull(pt.getNote()) ? pt.getNote() : "")).setFontSize(10).setMinHeight(40f);
            table.addCell(cell);
            cell = new Cell(1, 1).add("Przekazujacy: \n"+ pt.getForwarder().getFirstName()+" "+pt.getForwarder().getSurname()).setFontSize(10).setMinHeight(60f);
            table.addCell(cell);
            cell = new Cell(1, 1).add("Data "+ (nonNull(pt.getDate()) ? pt.getDate() : "")).setFontSize(10);
            table.addCell(cell);
            cell = new Cell(1, 2).add("Przyjmujacy: "+printRecipients(pt.getRecipients())).setFontSize(10);
            table.addCell(cell);

            document.add(table);
            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String printRecipients(Set<Employee> recipients){
        String recipientList = "";
        for(Employee employee : recipients)
            recipientList+=("\n"+employee.getFirstName()+" "+employee.getSurname());

        return recipientList;
    }

    private Cell createBarcode(String code, PdfDocument pdfDoc) {
        BarcodeEAN barcode = new BarcodeEAN(pdfDoc);
        barcode.setCodeType(BarcodeEAN.EAN8);
        barcode.setCode(code);
        Cell cell = new Cell().add(new Image(barcode.createFormXObject(null, null, pdfDoc)));
        cell.setPaddingTop(10);
        cell.setPaddingRight(10);
        cell.setPaddingBottom(10);
        cell.setPaddingLeft(10);
        return cell;
    }

    private Integer createCode(Long id, LocalDate date){
        String code = Long.toString(id) + Integer.toString(date.getYear()) + Integer.toString(date.getMonthValue()) + Integer.toString(date.getDayOfMonth());
        return Integer.parseInt(code);
    }
}

