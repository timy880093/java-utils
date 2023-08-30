package com.timy.javautils.excel;//package com.gateweb.commonsdk.config;
//
//import com.aspose.cells.License;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import javax.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//public class AsposeConfig {
//
//  @PostConstruct
//  public void init() throws IOException {
//    String licenseString =
//        "<License>\n"
//            + "  <Data>\n"
//            + "    <LicensedTo>Gate Web</LicensedTo>\n"
//            + "    <EmailTo>se03@gateweb.com.tw</EmailTo>\n"
//            + "    <LicenseType>Developer OEM</LicenseType>\n"
//            + "    <LicenseNote>Limited to 1 developer, unlimited physical locations</LicenseNote>\n"
//            + "    <OrderID>170518042408</OrderID>\n"
//            + "    <UserID>401727</UserID>\n"
//            + "    <OEM>This is a redistributable license</OEM>\n"
//            + "    <Products>\n"
//            + "    <Product>Aspose.Cells for Java</Product>\n"
//            + "    </Products>\n"
//            + "    <EditionType>Enterprise</EditionType>\n"
//            + "    <SerialNumber>202e4a19-c0f2-4bf8-8130-21cc1cc58570</SerialNumber>\n"
//            + "    <SubscriptionExpiry>20180518</SubscriptionExpiry>\n"
//            + "    <LicenseVersion>3.0</LicenseVersion>\n"
//            + "    <LicenseInstructions>http://www.aspose.com/corporate/purchase/license-instructions.aspx</LicenseInstructions>\n"
//            + "  </Data>\n"
//            + "  <Signature>e6KsjBs2Ya3QO83MR5Bevgyam8DZAn1H+qRmxXotM/HIOtDBiB1Fe5r/Db4VwbiAgPwEqOKaUK26Y9J1w8kYSYhOrlvYXFSI0U1S/CzJSs75i3U18hD9FpE4cRCAnMj8XpYIk/IAd40e/5ovfWHVNMuVIoxZAMhRqRBav7Imd6k=</Signature>\n"
//            + "</License>";
//
//    try (InputStream inputStream = new ByteArrayInputStream(licenseString.getBytes())) {
//      License license = new License();
//      license.setLicense(inputStream);
//    }
//    if (!License.isLicenseSet()) {
//      log.error("Aspose License is not valid!!");
//    } else {
//      log.info("Aspose License is still valid");
//    }
//  }
//}
