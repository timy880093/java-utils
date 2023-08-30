package com.timy.javautils;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.RowCollection;
import com.aspose.cells.Workbook;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class ExcelReader<T> {
  protected final Class<T> classT =
      (Class<T>)
          ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

  public static String[] headers;

  public abstract Object parseCellValue(Cell cell);

  public abstract List<T> toList(RowCollection rows)
          throws InstantiationException, IllegalAccessException;

  public List<T> read(InputStream originStream, int sheet, int deleteRow)
      throws Exception {
    try (InputStream inputStream = originStream) {
      final Workbook wb = new Workbook(inputStream);
      final Cells cells = wb.getWorksheets().get(sheet).getCells();
      final RowCollection rows = cells.getRows();
//      cells.deleteRows(0, deleteRow, true); // 刪除第一行
      cells.deleteBlankRows(); // 刪除空行
      final List<T> results = toList(rows);
//      final List<T> results = new ArrayList<>();
//      for (int i = 0; i < rows.getCount(); i++) {
//        final T obj = toObject(rows.get(i));
//        if (obj == null) continue;
//        results.add(obj);
//      }
      //      for (Object o : rows) {
      //        final Row row = (Row) o;
      //        final T obj = classT.newInstance();
      //        for (int i = 0; i < columns.length; i++) {
      //          ReflectUtils.invokeSetter(obj, columns[i], parseCellValue(row.get(i)));
      //        }
      //        results.add(obj);
      //      }
      return results;
    }
  }
}
