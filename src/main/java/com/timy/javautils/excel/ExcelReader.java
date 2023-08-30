package com.timy.javautils.excel;

import com.aspose.cells.Cell;
import com.aspose.cells.CellValueType;
import com.aspose.cells.Cells;
import com.aspose.cells.Row;
import com.aspose.cells.RowCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class ExcelReader {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param inputStream
     * @param columns     欄位順序
     * @param format      資料格式
     * @param <T>         資料物件
     * @return
     * @throws Exception
     */
    public static <T> List<T> read(InputStream inputStream, String[] columns, Class<T> format)
            throws Exception {
        final Workbook wb = new Workbook(inputStream);
        // 最前面的進入點，記得要用 try-with-resource 關 inputstream
        return read(wb, columns, format);
    }

    /**
     * @param filePath 檔案路徑
     * @param columns  欄位順序
     * @param format   資料格式
     * @param <T>      資料物件
     * @return
     * @throws Exception
     */
    public static <T> List<T> read(String filePath, String[] columns, Class<T> format)
            throws Exception {
        final Workbook wb = new Workbook(filePath);
        return read(wb, columns, format);
    }

    /**
     * 讀取 Excel
     *
     * @param wb      Workbook
     * @param columns 欄位順序
     * @param format  資料格式
     * @param <T>     資料物件
     * @return
     * @throws Exception
     */
    private static <T> List<T> read(Workbook wb, String[] columns, Class<T> format)
            throws Exception {
        try {
            final Worksheet sheet = wb.getWorksheets().get(0);
            final Cells cells = sheet.getCells();
            cells.deleteRows(0, 1, true); // 刪除第一行
            cells.deleteBlankRows(); // 刪除空行
            final RowCollection rows = cells.getRows();
            final List<T> results = new ArrayList();
            for (Object o : rows) {
                final Row row = (Row) o;
//        final T obj = format.newInstance();
                final Map<String, Object> map = new HashMap<>();
                for (int i = 0, columnLength = columns.length; i < columnLength; i++) {
                    final Cell cell = row.get(i);
                    final Object value = removeTime(cell);
                    map.put(columns[i], value);
                }
                final T obj = objectMapper.convertValue(map, format);
//        BeanUtils.copyProperties(map, obj);
                //        BeanUtils.populate(obj, map);
                results.add(obj);
            }
            //      //Create an instance of DeleteOptions class
            //      DeleteOptions options = new DeleteOptions();
            //      //Set UpdateReference property to true;
            //      options.setUpdateReference(true);
            //      //Delete all blank rows and columns
            //      cells.deleteBlankColumns(options);
            //      cells.deleteBlankRows(options);
            return results;
        } catch (Exception e) {
            throw new Exception("Parse Excel error.");
        }
    }

    /**
     * 日期格式，只保留日期，去除時間
     *
     * @param cell
     * @param isRemoveTime
     * @return
     */
    private static Object removeTime(Cell cell, boolean isRemoveTime) {
        final Object value = cell.getValue();
        if (isRemoveTime) {
            switch (cell.getType()) {
                //            case CellValueType.IS_BOOL:
                //              value = row.get(i).getBoolValue();
                //              break;
                case CellValueType.IS_DATE_TIME: // 只取日期，後面時間去掉，否則會解析失敗
                    return StringUtils.substringBefore(value.toString(), "T");
                //            case CellValueType.IS_NULL:
                //              value = "";
                //              break;
                //            case CellValueType.IS_NUMERIC:
                //              value = row.get(i).getIntValue();
                //              break;
                //            case CellValueType.IS_STRING:
                //              value = row.get(i).getStringValue();
                //              break;
            }
        }
        return value;
    }

    private static Object removeTime(Cell cell) {
        return removeTime(cell, true);
    }
}
