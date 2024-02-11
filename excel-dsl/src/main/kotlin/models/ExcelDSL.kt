package models

import builders.WorkbookBuilder
import org.apache.poi.xssf.usermodel.XSSFWorkbook

fun workbook(block: WorkbookBuilder.() -> Unit): XSSFWorkbook = WorkbookBuilder().apply(block).build()

