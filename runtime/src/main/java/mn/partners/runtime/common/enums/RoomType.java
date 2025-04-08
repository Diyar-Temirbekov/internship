package mn.partners.runtime.common.enums;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum RoomType {
    @XmlEnumValue("STANDARD")
    STANDARD,

    @XmlEnumValue("SUPERIOR")
    SUPERIOR,

    @XmlEnumValue("STUDIO")
    STUDIO,

    @XmlEnumValue("FAMILY")
    FAMILY,

    @XmlEnumValue("DELUXE")
    DELUXE,

    @XmlEnumValue("SUITE")
    SUITE
}