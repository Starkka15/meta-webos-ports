FILESEXTRAPATHS:prepend := "${THISDIR}/configd-data:"

SRC_URI:append:genericx86-64 = " file://genericx86-64-com.webos.surfacemanager.json"

do_install:append:genericx86-64() {
    install -m 0644 ${WORKDIR}/genericx86-64-com.webos.surfacemanager.json \
        ${D}${sysconfdir}/configd/layers/base/com.webos.surfacemanager.json
}
