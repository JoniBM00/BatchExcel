package com.viewnext.batchexcel.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.viewnext.batchexcel.model.Producto;

@Component
public class Reader {

	private static final String path = "terminales.xlsx";
	private static final Logger log = LoggerFactory.getLogger(Reader.class);

	public ArrayList<Producto> readExcelFile() throws IOException {

		log.info(" ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		log.info("LEYENDO XLSX CONVERGENTES");
		File excelFile = new File(this.path);
		FileInputStream fis = new FileInputStream(excelFile);
		ArrayList<Producto> lProductos = new ArrayList<>();

		// we create an XSSF Workbook object for our XLSX Excel File
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		XSSFSheet sheet = workbook.getSheetAt(0);

		// we iterate on rows
		Iterator<Row> rowIt = sheet.iterator();
		Producto producto = new Producto();
		int numColumn = 0;

		Row row = rowIt.next();// Saltar Cabecera
		while (rowIt.hasNext()) {
			row = rowIt.next();

			// iterate on cells for the current row
			Iterator<Cell> cellIterator = row.cellIterator();
			numColumn = 0;
			producto = new Producto();

			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				switch (numColumn) {
				case 0: {
					producto.setId(cell.toString());
					break;
				}
				case 1: {
					producto.setName(cell.toString());
					break;
				}
				case 2: {
					producto.setDescripcion(cell.toString());
					break;
				}
				case 3: {
					producto.setCode(Long.valueOf(cell.toString().replace(".0", "")));
					break;
				}
				default: {
					break;
				}

				}
				numColumn++;

			}
			lProductos.add(producto);

		}

		workbook.close();
		fis.close();

		log.info("Promos Leidas del fichero: " + lProductos.size());

		log.info("Fin Lector XLSX");
		return lProductos;
	}

	@Bean
	public ItemReader<Producto> excelItemReader() throws IOException {
		return new ItemReader<>() {
			private List<Producto> lProductos = readExcelFile();

			private int currentIndex = 0;

			@Override
			public Producto read() {
				if (currentIndex < lProductos.size()) {
					return lProductos.get(currentIndex++);
				} else {
					return null;
				}
			}
		};

	}

}
