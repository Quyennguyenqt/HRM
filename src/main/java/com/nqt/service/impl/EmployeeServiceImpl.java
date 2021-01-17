package com.nqt.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nqt.dao.EmployeeDAO;
import com.nqt.entities.ContracEmp;
import com.nqt.entities.Employee;
import com.nqt.entities.TrainningEmployee;
import com.nqt.service.EmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDAO empDAO;

	@Override
	public void addEmployee(Employee employee) {
		Calendar c = Calendar.getInstance();
		List<TrainningEmployee> trainEmp = new ArrayList<TrainningEmployee>(employee.getTrainEmp());
		List<ContracEmp> contracEmp = new ArrayList<ContracEmp>(employee.getContracEmp());

		Long id = empDAO.addEmployee(employee);
		employee.setEmpId(id);

		ContracEmp ctemp = new ContracEmp();
		ctemp.setContractEmpStartDate(c.getTime());
		c.add(Calendar.MONTH, 6);
		ctemp.setContractEmpEndDate(c.getTime());
		ctemp.setContractEmpStatus(contracEmp.get(0).getContractEmpStatus());
		ctemp.setEmpConId(employee);
		ctemp.setContractId(contracEmp.get(0).getContractId());
		empDAO.addContractEmp(ctemp);

		TrainningEmployee trainEmpl = new TrainningEmployee();
		trainEmpl.setTrainingEmpStartDate(c.getTime());
		c.add(Calendar.MONTH, 2);
		trainEmpl.setTrainingEmpStartEnd(c.getTime());
		trainEmpl.setTrainingEmpStatus(trainEmp.get(0).getTrainingEmpStatus());
		trainEmpl.setTrainingEmpResult(trainEmp.get(0).getTrainingEmpResult());
		trainEmpl.setEmTrainId(employee);
		trainEmpl.setTrainingId(trainEmp.get(0).getTrainingId());
		empDAO.addTrainEmp(trainEmpl);

	}

	@Override
	public void updateEmployee(Employee employee) {
		Employee empCurr = getEmployeeById(employee.getEmpId());
		if (empCurr != null) {
			empDAO.updateEmployee(employee);
			Calendar c = Calendar.getInstance();
			List<TrainningEmployee> trainEmpList = new ArrayList<TrainningEmployee>(employee.getTrainEmp());
			List<ContracEmp> contracEmpList = new ArrayList<ContracEmp>(employee.getContracEmp());

			ContracEmp ctemp = new ContracEmp();
			ctemp.setContractEmpStartDate(c.getTime());
			c.add(Calendar.MONTH, 6);
			ctemp.setContractEmpEndDate(c.getTime());
			ctemp.setContractEmpStatus(contracEmpList.get(0).getContractEmpStatus());
			ctemp.setEmpConId(employee);
			ctemp.setContractId(contracEmpList.get(0).getContractId());
			empDAO.updateContractEmp(ctemp);

			TrainningEmployee trainEmpl = new TrainningEmployee();
			trainEmpl.setTrainingEmpStartDate(c.getTime());
			c.add(Calendar.MONTH, 2);
			trainEmpl.setTrainingEmpStartEnd(c.getTime());
			trainEmpl.setTrainingEmpStatus(trainEmpList.get(0).getTrainingEmpStatus());
			trainEmpl.setTrainingEmpResult(trainEmpList.get(0).getTrainingEmpResult());
			trainEmpl.setEmTrainId(employee);
			trainEmpl.setTrainingId(trainEmpList.get(0).getTrainingId());
			empDAO.updateEmpTrain(trainEmpl);
		}

	}

	@Override
	public List<Employee> listEmployee() {
		return empDAO.listEmployee();
	}

	@Override
	public Employee getEmployeeById(Long id) {
		return empDAO.getEmployeeById(id);
	}

	@Override
	public void removeEmployee(Employee employee) {
		// empDAO.removeContractEmp(employee.getEmpId());
		empDAO.removeEmployee(employee);

	}

	@Override
	public boolean save(Employee employee, boolean b) {
		if (checkId(employee, b)) {
			return true;
		}
		return false;
	}

	private boolean checkId(Employee employee, boolean b) {
		Employee rl = getEmployeeById(employee.getEmpId());
		if (rl == null && b == true) {
			addEmployee(employee);
			return true;
		} else if (rl != null && b == false) {
			updateEmployee(employee);
			return true;
		}
		return false;

	}

	@Override
	public void removeContracEmp(Long id) {
		empDAO.removeContractEmp(id);

	}

	public ByteArrayInputStream productPDFReport(List<Employee> employees) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, out);
			document.open();

			// add text pdf file
			@SuppressWarnings("unused")
			Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
			Paragraph para = new Paragraph();
			para.setAlignment(Element.ALIGN_CENTER);
			document.add(para);
			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(2);
			// make column title
			Stream.of("Ten nhan vien", "Gioi tinh").forEach(headerTitle -> {
				PdfPCell header = new PdfPCell();
				Font headfont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
				header.setBackgroundColor(BaseColor.LIGHT_GRAY);
				header.setHorizontalAlignment(Element.ALIGN_CENTER);
				header.setBorderWidth(1);
				header.setPhrase(new Phrase(headerTitle, headfont));
				table.addCell(header);
			});

			for (Employee employ : employees) {
				PdfPCell nameCell = new PdfPCell(new Phrase(employ.getEmpName()));
				nameCell.setPadding(1);
				nameCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				nameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(nameCell);

				PdfPCell sexCell = new PdfPCell(new Phrase(employ.getEmpSex()));
				sexCell.setPadding(1);
				sexCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				sexCell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(sexCell);
			}

			document.add(table);
			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	public ByteArrayInputStream productExcelReport(List<Employee> employees) throws IOException {
		String[] columns = { "Ten nhan vien", "Gioi tinh" };
		try (Workbook wordbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CreationHelper creationHelper = wordbook.getCreationHelper();
			Sheet sheet = wordbook.createSheet("Employees");
			sheet.autoSizeColumn(columns.length);

			org.apache.poi.ss.usermodel.Font headerFont = wordbook.createFont();
			headerFont.setBold(true);
			headerFont.setColor(IndexedColors.BLUE.getIndex());

			CellStyle cellStyle = wordbook.createCellStyle();
			cellStyle.setFont(headerFont);

			// Row for header
			Row headerRow = sheet.createRow(0);
			// header

			for (int col = 0; col < columns.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(columns[col]);
				cell.setCellStyle(cellStyle);
			}

			CellStyle cellStyle1 = wordbook.createCellStyle();
			cellStyle1.setDataFormat(creationHelper.createDataFormat().getFormat("#"));

			int rowIndex = 1;
			for (Employee emp : employees) {
				Row row = sheet.createRow(rowIndex++);
				row.createCell(0).setCellValue(emp.getEmpName());
				row.createCell(1).setCellValue(emp.getEmpSex());
			}
			wordbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		}
	}


}
