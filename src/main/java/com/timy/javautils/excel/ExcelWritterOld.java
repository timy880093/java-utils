//package com.timy.javautils.excel;
//
//import com.aspose.cells.Cells;
//import com.aspose.cells.Font;
//import com.aspose.cells.Range;
//import com.aspose.cells.Style;
//import com.aspose.cells.Workbook;
//import com.aspose.cells.Worksheet;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//@Slf4j
//public class ExcelWritterOld {
//
//    private final ExcelWritterConfiguration configuration;
//
//    public ExcelWritterOld() {
//        this.configuration = new ExcelWritterConfiguration();
//    }
//
//    protected ExcelWritterOld(ExcelWritterConfiguration configuration) {
//        this.configuration = configuration;
//    }
//
//    public <T> List<Path> generate(LinkedHashMap<String, String> headerMap,
//            Collection<T> collection, String filename) throws Exception {
//        return generate(headerMap, collection.stream(), filename);
//    }
//
//    public <T> List<Path> generate(LinkedHashMap<String, String> headerMap,
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
//                if (row.getAndIncrement() < configuration.getLimit()) {
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
//
//    Workbook initWithHeaders(String[] headers) {
//        final Workbook wb = new Workbook();
//        final Worksheet sheet = wb.getWorksheets().get(0);
//        final Cells cells = sheet.getCells();
//        // write header column
//        cells.importArray(headers, configuration.getStartRow(), configuration.getStartColumn(),
//                false);
//        // set header style
//        setBoldHeader(wb, headers.length);
//        return wb;
//    }
//
//    Path parseFileName(String filename, int idx) throws IOException {
//        String filePrefix = StringUtils.substringBeforeLast(filename, ".");
//        String extension = StringUtils.substringAfterLast(filename, ".");
//        filePrefix = idx == 0 ? filePrefix + "_" : filePrefix + "_" + idx + "_";
//        extension = StringUtils.startsWith(extension, ".") ? extension : "." + extension;
//        return configuration.useDefaultRoot()
//                ? Files.createTempFile(filePrefix, extension)
//                : Files.createTempFile(Paths.get(configuration.getRoot()), filePrefix, extension);
//    }
//
//    public <T> void writeRow(Workbook workbook, int row,
//            String[] headers, T dto) {
//        final List<String> values = Arrays.stream(headers)
//                .map(it -> ReflectUtils.invokeGetter(dto, it).toString())
//                .collect(Collectors.toList());
//        writeRow(workbook, 0, row, values);
//    }
//
//    public void writeRow(Workbook workbook, int sheetIndex, int row, List<String> values) {
//        workbook.getWorksheets().get(sheetIndex).getCells()
//                .importArrayList((ArrayList<?>) values, row, 0, false);
//    }
//
//    private Path saveFileAndAddList(String filename, int index, Workbook wb)
//            throws Exception {
//        final Path tempFile = parseFileName(filename, index);
//        wb.save(tempFile.toString());
//        log.debug("Write temp to {}", tempFile);
//        return tempFile;
//    }
//
//    public void setBoldHeader(Workbook wb, int columnSize) {
//        setBoldHeader(wb, columnSize, 0);
//    }
//
//    public void setBoldHeader(Workbook wb, int columnSize, int sheetIndex) {
//        final Worksheet sheet = wb.getWorksheets().get(sheetIndex);
//        final Cells cells = sheet.getCells();
//        final Range headerRange = cells.createRange(0, 0, 1, columnSize + 1);
//        final Style defaultStyle = wb.getDefaultStyle();
//        final Font font = defaultStyle.getFont();
//        // 粗體
//        font.setBold(true);
//        // 微軟正黑體
//        font.setName(configuration.getFont());
//        // 字體大小
//        font.setSize(configuration.getFontSize());
//        headerRange.setStyle(defaultStyle);
//        try {
//            // 自動適應列寬 & 高，必須先設定字體 (必須先寫入資料，再使用此方法)
//            sheet.autoFitColumns();
//            sheet.autoFitRows();
//        } catch (Exception e) {
//            log.warn("autoFitColumns error : {}", e.getMessage());
//        }
//    }
//
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
//
//    @Data
//    static class ExcelWritterConfiguration {
//
//        private String root;
//        private String font = "Microsoft JhengHei";
//        private int fontSize = 14;
//        private int limit = 1000000;
//        private int sheet = 0;
//        private int startRow = 0;
//        private int startColumn = 0;
//
//        public boolean useDefaultRoot() {
//            return root == null || root.trim().isEmpty();
//        }
//    }
//}
