package com.khan366kos.styles

import org.apache.poi.ss.usermodel.*

fun expiredStyle(workbook: Workbook, cellStyle: CellStyle): CellStyle =
    workbook.createCellStyle()
        .apply {
            this.cloneStyleFrom(cellStyle)
            this.fillForegroundColor = IndexedColors.RED.index
            this.fillPattern = FillPatternType.SOLID_FOREGROUND
        }

fun expiredScanStyle(workbook: Workbook, cellStyle: CellStyle): CellStyle =
    workbook.createCellStyle()
        .apply {
            this.cloneStyleFrom(cellStyle)
            this.fillForegroundColor = IndexedColors.YELLOW.index
            this.fillPattern = FillPatternType.SOLID_FOREGROUND
        }

fun okStyle(workbook: Workbook, cellStyle: CellStyle): CellStyle =
    workbook.createCellStyle()
        .apply {
            this.cloneStyleFrom(cellStyle)
            this.fillForegroundColor = IndexedColors.AQUA.index
            this.fillPattern = FillPatternType.SOLID_FOREGROUND
        }

fun mergeStyle(workbook: Workbook) = workbook.createCellStyle().apply {
    this.alignment = HorizontalAlignment.CENTER
    this.verticalAlignment = VerticalAlignment.CENTER
    this.wrapText = true
    this.setFont(workbook.createFont().apply {
        this.fontHeightInPoints = 14
        this.bold = true
    })
}
