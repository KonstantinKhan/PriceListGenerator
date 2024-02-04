package com.khan366kos

import com.khan366kos.handler.CellParser
import com.khan366kos.handler.DocumentHandler
import com.khan366kos.status.Status
import com.khan366kos.styles.expiredScanStyle
import com.khan366kos.styles.expiredStyle
import com.khan366kos.styles.mergeStyle
import com.khan366kos.styles.okStyle
import org.apache.poi.openxml4j.util.ZipSecureFile
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.Instant

fun main() {
//    val path = "C:\\Users\\han\\Desktop\\Реестр акты Консалтинг 15.01.xlsx"
    ZipSecureFile.setMinInflateRatio(0.0)
    val path =
        "/home/khan/MyProjects/PriceListGenerator/excel-file-processing-service/src/main/resources/Реестр акты Консалтинг 15.01.xlsx"
    val inputStream = FileInputStream(path)
    val workbook = XSSFWorkbook(inputStream)
    inputStream.close()
    val sheet = workbook.getSheetAt(0)
    val parser = CellParser()
    val handler = DocumentHandler(30)
    val regionMerge = CellRangeAddress(9, 44, 12, 14)
    val mergeMap = mutableMapOf<Int, Pair<Status, Short>>()
    val pairsFirst = mutableListOf<Pair<Int, Int>>()
    val pairsLast = mutableListOf<Pair<Int, Int>>()
    val regions = mutableListOf<RegionExcel>()

    val deadline = run {
        sheet.forEach { row ->
            row.forEach { cell ->
                parser.findDeadline(cell.toString())?.let {
                    return@run parser.sdf.parse(it).toInstant()
                }
            }
        }
    } as Instant

    sheet.forEach { row ->
        val result = run {
            row.forEach { cell ->
                with(parser) {
                    findCell(cell.toString())?.let {
                        if (handler.isExpired(deadline, sdf.parse(it).toInstant())) {
                            val exist = run {
                                row.forEach { cellInto ->
                                    findScan(cellInto.toString())?.let {
                                        return@run true
                                    }
                                }
                                return@run false
                            }
                            if (exist) return@run Status.SCAN
                            else return@run Status.EXPIRED
                        } else return@run Status.OK


                    }
                }
            }
        }
        when (result) {
            Status.EXPIRED -> {
                mergeMap[row.rowNum] = Pair(Status.EXPIRED, row.lastCellNum)
                row.forEach {
                    it.cellStyle = expiredStyle(workbook, it.cellStyle)
                }
            }

            Status.SCAN -> {
                mergeMap[row.rowNum] = Pair(Status.SCAN, row.lastCellNum)
                row.forEach {
                    it.cellStyle = expiredScanStyle(workbook, it.cellStyle)
                }
            }

            Status.OK -> {
                mergeMap[row.rowNum] = Pair(Status.OK, row.lastCellNum)
                row.forEach {
                    it.cellStyle = okStyle(workbook, it.cellStyle)
                }
            }
        }
    }

    mergeMap.forEach { t, u ->
        if (mergeMap[t - 1] == null ||
            ((u.first == Status.EXPIRED || u.first == Status.SCAN) && mergeMap[t - 1]?.first == Status.OK) ||
            (u.first == Status.OK && (mergeMap[t - 1]?.first == Status.EXPIRED || mergeMap[t - 1]?.first == Status.SCAN))
        ) {
            pairsFirst.add(Pair(t, u.second.toInt()))
        }

        if (mergeMap[t + 1] == null ||
            ((u.first == Status.EXPIRED || u.first == Status.SCAN) && mergeMap[t + 1]?.first == Status.OK) ||
            (u.first == Status.OK && (mergeMap[t + 1]?.first == Status.EXPIRED || mergeMap[t + 1]?.first == Status.SCAN))
        ) {
            pairsLast.add(Pair(t, u.second.toInt()))
        }
    }

    pairsFirst.forEachIndexed { index, pair ->
        regions.add(RegionExcel(pair.first, pairsLast[index].first, pair.second, pair.second + 2))
    }

    regions.forEach {
        val newCell = sheet.getRow(it.firstRow)
            .createCell(it.firstColumn)
        newCell.cellStyle = mergeStyle(workbook) as XSSFCellStyle
        newCell.setCellValue("Вернуть до... а то наказывать будут")
        sheet.addMergedRegion(CellRangeAddress(it.firstRow, it.lastRow, it.firstColumn, it.lastColumn))
    }

    workbook.write(FileOutputStream(path))
    workbook.close()
}