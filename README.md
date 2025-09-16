## java-utils

輕量級的 Java 工具庫，基於 Spring Boot 2.7.x (Java 8)，提供常用的檔案、日期、Excel、HMAC 與 HTTP client (Retrofit/OkHttp) 等輔助程式。

## 快速摘要
- Spring Boot 2.7.5 (starter)
- Java 1.8 相容
- 主要第三方相依：Retrofit, OkHttp, Jackson, Aspose.Cells（商業元件）, Apache Commons Lang3, Lombok（optional）
- 支援將應用 build 為 native image（GraalVM） via `native` profile

## 目標與用途
此倉庫聚焦於「常用工具方法」的集中管理，包含：

- 檔案存取與資源讀取：`FileUtils`
- 時間/日期格式解析與轉換（含民國年）: `DateTimeConverter`
- Excel 讀取/寫入（採 Aspose.Cells）：`excel.ExcelReader`, `excel.ExcelWritterOld`
- HMAC 處理（Hex / Base64 / 初始化 Mac）: `HmacUtils`
- Retrofit + 自訂 OkHttp client（包含寬鬆 SSL 信任）：`RetrofitProvider`

## 專案結構 (重點)

- `src/main/java/com/timy/javautils/` - 工具類別集合
  - `DateTimeConverter.java` - 多格式字串 -> `LocalDate` 解析、民國/西元相互轉換
  - `FileUtils.java` - 讀取 classpath 下的資源檔
  - `HmacUtils.java` - HMAC 計算、Base64/Hex 輸出
  - `RetrofitProvider.java` - 產生 Retrofit client，內含自訂 OkHttp 設定
  - `excel/` - Excel 相關的讀寫工具（使用 Aspose.Cells）

- `src/test/java/...` - 單元測試範例（包含 `DateTimeConverterTest` 與 `ExcelWritterOldTest`）
- `pom.xml` - Maven 設定；包含 `native` profile 與 Aspose repository

## 重要注意事項

- Aspose.Cells 為商業授權套件，pom 已加入 Aspose 的 repository；實際執行時需確保可取得對應 artifact。
- `RetrofitProvider` 建立的 OkHttpClient 對所有主機名稱/憑證採取寬鬆信任（hostname verifier return true、custom TrustManager）。僅建議在內部測試或受控環境使用，生產環境請改用嚴格的 TLS 驗證與憑證管理。
- Lombok 在 pom 中為 optional，IDE 若要無警告請安裝 Lombok 外掛。

## 建置與測試

在專案根目錄下使用 wrapper：

```bash
# 建置
./mvnw clean package

# 執行測試
./mvnw test
```

要產生 native binary（需要 GraalVM 與相容環境）：

```bash
# 使用 native profile
./mvnw -Pnative package
```

執行 jar：

```bash
java -jar target/java-utils-0.0.1-SNAPSHOT.jar
```

## 範例使用

1) 使用 `DateTimeConverter` 解析日期字串：

```java
LocalDate d = DateTimeConverter.toLocalDate("2023-08-01");
String ymd = DateTimeConverter.toYYYYMMDD(d);
```

2) 使用 `HmacUtils` 計算 HMAC-SHA256（示意）：

```java
Mac mac = HmacUtils.initMac("secret-key", "HmacSHA256");
String hex = HmacUtils.toHmac(mac, "payload");
```

3) 建立 Retrofit client：

```java
MyApi api = RetrofitProvider.createClient("https://api.example.com/", MyApi.class);
```

4) 讀取 Excel（Aspose）：

```java
List<MyDto> rows = com.timy.javautils.excel.ExcelReader.read(inputStream, new String[]{"col1","col2"}, MyDto.class);
```

## 設計規範（小結）

- 輸入：主要方法多接受 Java 原生型別（String、InputStream、Collections、POJO 等）
- 輸出：POJO list、String (hex/base64)、`Retrofit` client instance
- 錯誤模式：多數工具在遇到解析錯誤會擲出 Exception 或回傳 null（需呼叫端判斷），部分工具會記錄錯誤但吞掉例外（例如 `FileUtils.readResourceFile` 會回傳空的 Properties 並紀錄錯誤）

## 常見 edge cases

- 空字串 / null 輸入（`DateTimeConverter` 已處理會回傳 null）
- 非法日期格式（會記錄錯誤並返回 null）
- Excel 中含空行或不同欄位型別（`ExcelReader` 已嘗試刪除空列並將 cell.getValue() 交由 mapper 處理）
- TLS 驗證風險（`RetrofitProvider` 目前接受任意憑證）

