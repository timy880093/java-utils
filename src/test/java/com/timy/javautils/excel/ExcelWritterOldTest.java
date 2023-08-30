//package com.timy.javautils.excel;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.junit.jupiter.api.Test;
//import org.springframework.cglib.core.ReflectUtils;
//import org.springframework.util.ReflectionUtils;
//
//class ExcelWritterOldTest {
//
//    @Test
//    void testsdfds() {
//        final TestObj testObj = new TestObj("123", "456", "789");
//        final List<TestObj> list = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            list.add(testObj);
//        }
//        final Path path = ExcelWritter.create()
//                .writeSheet(list, "123Test", 2, 1, TestObj.headerMap)
//                .writeSheet(list, "TestSheet12345", 0, 0, TestObj.headerMap)
//                .toPath("test.xlsx");
//        System.out.println(path);
//        assertThat(Files.exists(path)).isTrue();
//    }
//
//    @Data
//    @AllArgsConstructor
//    static class TestObj {
//
//        private String test1;
//        private String test2;
//        private String test3;
//
//        public static final Map<String, String> headerMap;
//
//        static {
//            final Map<String, String> map = new LinkedHashMap<>();
//            map.put("test1", "Test1");
//            map.put("test2", "Test2");
//            map.put("test3", "Test3");
//            headerMap = map;
//        }
//    }
//
//}