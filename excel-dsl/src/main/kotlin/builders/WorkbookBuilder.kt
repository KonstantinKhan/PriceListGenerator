package builders

import models.ExcelDSL
import models.Path
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream

@ExcelDSL
class WorkbookBuilder : Builder<XSSFWorkbook> {
    var path: Path = Path.NONE

    override fun build(): XSSFWorkbook {
        return FileInputStream(path.value).use { inputStream ->
            XSSFWorkbook(inputStream)
        }
    }
}