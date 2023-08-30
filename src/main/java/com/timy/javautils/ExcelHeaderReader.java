package com.timy.javautils;

import com.aspose.cells.Cell;
import com.aspose.cells.Row;
import com.aspose.cells.RowCollection;
import com.timy.javautils.excel.ReflectUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelHeaderReader<T> extends ExcelReader<T> {

  @Override
  public Object parseCellValue(Cell cell) {
    return cell.getValue();
  }

  @Override
  public List<T> toList(RowCollection rows) throws InstantiationException, IllegalAccessException {
    final List<T> results = new ArrayList<>();
    for (int i = 0; i < rows.getCount(); i++) {
      final Row row = rows.get(i);
      if (i == 0) {
        final Iterator iterator = row.iterator();
        final List<String> list = new ArrayList<>();
        if (iterator.hasNext()) {
          list.add((String) iterator.next());
        }
        headers = list.toArray(new String[] {});
        continue;
      }
      final T obj = classT.newInstance();
      final Map<String, Integer> headerIndexMap = new HashMap<>();
      for (int c = 0; c < headers.length; c++) {
        headerIndexMap.put(headers[c], c);
      }
      for (String header : headerIndexMap.keySet()) {
        ReflectUtils.invokeSetter(obj, header, row.get(headerIndexMap.get(header)));
      }
      results.add(obj);
    }
    return results;
  }
}
