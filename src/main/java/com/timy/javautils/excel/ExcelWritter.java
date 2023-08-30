package com.timy.javautils.excel;

import com.aspose.cells.BackgroundType;
import com.aspose.cells.BorderCollection;
import com.aspose.cells.BorderType;
import com.aspose.cells.CellBorderType;
import com.aspose.cells.CellsFactory;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Range;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Data
public class ExcelWritter {

    private final Workbook wb;
    private final String root;
    private String fontName = "Microsoft JhengHei";
    private int fontSize = 14;
    private int limit = 1000000;
    private int style = 0;
    private int borderType = CellBorderType.THIN;
    private Worksheet sheet;
    private int startRow = 0;
    private int startColumn = 0;
    private Map<String, String> headerMap;

    public static ExcelWritter create() {
        return create(null, null);
    }

    public static ExcelWritter create(Workbook wb, String root) {
        wb = wb == null ? new Workbook() : wb;
        return new ExcelWritter(wb, root);
    }

    public ExcelWritter writeToSheet(List<?> list, String sheetName, int startRow, int startColumn,
            Map<String, String> headerMap) {
        addSheet(sheetName, startRow, startColumn, headerMap);
        int tmp = 0;
        int row = 0;
        for (int i = 0; i < list.size(); i++) {
            row = i % limit;
            if (i > 0 && row == 0) {
                // apply preview sheet style
                applyDataBorder(sheet, limit);
                applyHeaderStyleAndBorder(sheet);
                // add new sheet
                final String newName = tmp++ == 0 ? sheetName : sheetName + "-" + tmp;
                addSheet(newName, startRow, startColumn, headerMap);
            }
            writeRow(list.get(i), row);
        }
        applyDataBorder(sheet, row + 1);
        applyHeaderStyleAndBorder(sheet);
        return this;
    }

    public InputStream toInputStream() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            wb.getWorksheets().removeAt(0);
            wb.save(out, SaveFormat.XLSX);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }

    public Path toPath(String filename) {
        Path path = null;
        try {
            path = parseFileName(root, filename, 0);
            wb.getWorksheets().removeAt(0);
            wb.save(path.toString(), SaveFormat.XLSX);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    void addSheet(String sheetName, int startRow, int startColumn,
            Map<String, String> headerMap) {
        this.sheet = wb.getWorksheets().add(sheetName);
        this.startRow = startRow;
        this.startColumn = startColumn;
        this.headerMap = headerMap;
        writeHeaders();
    }

    String[] getHeaders() {
        return headerMap.keySet().toArray(new String[0]);
    }

    String[] getDisplayHeaders() {
        return headerMap.values().toArray(new String[0]);
    }


    void writeHeaders() {
        sheet.getCells().importArray(getDisplayHeaders(), startRow, startColumn, false);
    }

    void writeRow(Object o, int i) {
        final List<Object> values = Arrays.stream(getHeaders())
                .map(prop -> ReflectUtils.invokeGetter(o, prop))
                .collect(Collectors.toList());
        sheet.getCells()
                .importArrayList(new ArrayList<>(values), startRow + 1 + i, startColumn, false);
    }

    void applyDataBorder(Worksheet sheet, int row) {
        final Range range = sheet.getCells()
                .createRange(startRow + 1, startColumn, row, headerMap.size());
        applyBorder(range);
    }

    void applyHeaderStyleAndBorder(Worksheet sheet) {
        final Range range = sheet.getCells()
                .createRange(startRow, startColumn, 1, headerMap.size());
        applyStyle(range, Color.getOrange(), null, true, fontName,
                fontSize, CellBorderType.THIN);
        try {
            // 自動適應列寬 & 高，必須先設定字體 (必須先寫入資料，再使用此方法)
            sheet.autoFitColumns();
            sheet.autoFitRows();
        } catch (Exception e) {
            log.warn("autoFitColumns error : {}", e.getMessage());
        }
    }

    static void applyStyle(Range range, Color backgroundColor, Color textColor,
            boolean isBold, String fontName, int fontSize, int borderType) {
        final Style style = new CellsFactory().createStyle();
        // 必填這行，否則顏色不生效
        style.setPattern(BackgroundType.SOLID);
        if (backgroundColor != null) {
            style.setForegroundColor(backgroundColor);
        }
        setFont(style, isBold, fontName, fontSize, textColor);
        setBolder(style, borderType);
        range.setStyle(style);
    }

    static void applyBorder(Range range) {
        final Style style = new CellsFactory().createStyle();
        style.setPattern(BackgroundType.SOLID);
        setBolder(style, CellBorderType.THIN);
        range.setStyle(style);
    }

    static void setFont(Style style, boolean isBold, String fontName, int fontSize,
            Color textColor) {
        final Font font = style.getFont();
        font.setBold(isBold);
        font.setName(fontName);
        font.setSize(fontSize);
        if (textColor != null) {
            font.setColor(textColor);
        }
    }

    static void setBolder(Style style, int borderType) {
        final BorderCollection borders = style.getBorders();
        borders.getByBorderType(BorderType.TOP_BORDER).setLineStyle(borderType);
        borders.getByBorderType(BorderType.BOTTOM_BORDER).setLineStyle(borderType);
        borders.getByBorderType(BorderType.LEFT_BORDER).setLineStyle(borderType);
        borders.getByBorderType(BorderType.RIGHT_BORDER).setLineStyle(borderType);
    }

    static Path parseFileName(String root, String filename, int idx) throws IOException {
        String filePrefix = StringUtils.substringBeforeLast(filename, ".");
        String extension = StringUtils.substringAfterLast(filename, ".");
        filePrefix = idx == 0 ? filePrefix + "_" : filePrefix + "_" + idx + "_";
        extension = StringUtils.startsWith(extension, ".") ? extension : "." + extension;
        return StringUtils.isBlank(root)
                ? Files.createTempFile(filePrefix, extension)
                : Files.createTempFile(Paths.get(root), filePrefix, extension);
    }

//    public <T> List<Path> generate(Map<String, String> headerMap,
//            List<T> collection, String filename) throws Exception {
//        return generate(headerMap, collection.stream(), filename);
//    }
//
//    public <T> List<Path> generate(Map<String, String> headerMap,
//            Stream<T> stream, String filename) throws Exception {
//        final List<Path> paths = new ArrayList<>();
//        final String[] headers = headerMap.keySet().toArray(new String[0]);
//        final String[] displayHeaders = headerMap.values().toArray(new String[0]);
//        final AtomicReference<Workbook> wb = new AtomicReference<>(initWithHeaders(displayHeaders));
//        final AtomicInteger row = new AtomicInteger(1);
//        final AtomicInteger fileIndex = new AtomicInteger();
//        try (Stream<T> tStream = stream) {
//            tStream.forEach(dto -> {
//                writeRow(wb.get(), row.get(), headers, dto);
//                if (row.getAndIncrement() < limit) {
//                    return;
//                }
//                try {
//                    paths.add(saveFileAndAddList(filename, fileIndex.getAndIncrement(), wb.get()));
//                    wb.set(initWithHeaders(displayHeaders));
//                    row.set(1);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            });
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        if (row.get() > 1) {
//            paths.add(saveFileAndAddList(filename, fileIndex.getAndIncrement(), wb.get()));
//        }
//        return paths;
//    }

//        private Path saveFileAndAddList(String filename, int index, Workbook wb)
//            throws Exception {
//        final Path tempFile = parseFileName(filename, index);
//        wb.save(tempFile.toString());
//        log.debug("Write temp to {}", tempFile);
//        return tempFile;
//    }

//    public <T> HashMap<String, List<Object>> toVerticalMap(List<T> list) {
//        final Field[] fields = list.get(0).getClass().getDeclaredFields();
//        final HashMap<String, List<Object>> map = new HashMap<>();
//        for (Field f : fields) {
//            final String property = f.getName();
//            final List<Object> collect =
//                    list.stream()
//                            .map(it -> ReflectUtils.invokeGetter(it, property))
//                            .collect(Collectors.toList());
//            map.put(property, collect);
//        }
//        return map;
//    }

}