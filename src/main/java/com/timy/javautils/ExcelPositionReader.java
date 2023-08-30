package com.timy.javautils;

import com.aspose.cells.Cell;
import com.aspose.cells.Row;
import com.aspose.cells.RowCollection;
import com.timy.javautils.excel.ReflectUtils;
import java.util.ArrayList;
import java.util.List;

public class ExcelPositionReader<T> extends ExcelReader<T> {

  public ExcelPositionReader(String[] positionHeaders) {
    headers = positionHeaders;
  }

  @Override
  public Object parseCellValue(Cell cell) {
    return cell.getValue();
  }

  @Override
  public List<T> toList(RowCollection rows) throws InstantiationException, IllegalAccessException {
    final List<T> results = new ArrayList<>();
    for (int i = 0; i < rows.getCount(); i++) {
      if(i==0) continue;
      final Row row = rows.get(i);
      final T obj = classT.newInstance();
      for (int h = 0; h < headers.length; h++) {
        ReflectUtils.invokeSetter(obj, headers[h], parseCellValue(row.get(h)));
      }
      results.add(obj);
    }
    return results;
  }

}
