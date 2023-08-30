package com.timy.javautils.model;

import com.timy.javautils.excel.ExcelWritter;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.util.ReflectionUtils;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class InvoiceExternalTrackEntity implements Serializable {

    private Integer id;
    private String yearMonth;
    private String typeCode;
    private String track;

    public Integer getId() {
        return id;
    }

    public InvoiceExternalTrackEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public InvoiceExternalTrackEntity setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
        return this;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public InvoiceExternalTrackEntity setTypeCode(String typeCode) {
        this.typeCode = typeCode;
        return this;
    }

    public String getTrack() {
        return track;
    }

    public InvoiceExternalTrackEntity setTrack(String track) {
        this.track = track;
        return this;
    }

    public String combine() {
        return StringUtils.join("_", yearMonth, track, typeCode);
    }
}
