SUMMARY = "Waydroid uses a container-based approach to boot a full Android system"
DESCRIPTION = "Android image file for Waydroid"
# this isn't very clear, there is no information in build.anbox.io and it surely doesn't
# cover all components included in this built image, e.g.
# https://aur.archlinux.org/packages/waydroid-image says Apache license
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

# works only for following 4 archs
COMPATIBLE_MACHINE ?= "(^$)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"
COMPATIBLE_MACHINE:armv7a = "(.*)"
COMPATIBLE_MACHINE:armv7ve = "(.*)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"

WAYDROID_ARCH:x86-64 = "waydroid_x86_64"
WAYDROID_ARCH:aarch64 = "waydroid_arm64"
WAYDROID_SYSTEM_IMAGE = "lineage-20.0-20260403-VANILLA-${WAYDROID_ARCH}-system.zip"
WAYDROID_VENDOR_IMAGE = "lineage-20.0-20260403-MAINLINE-${WAYDROID_ARCH}-vendor.zip"

SHA256SUM_SYSTEM:x86-64 = "2e343b14c649a685853e7957ac34feb1ca110425d78a47ebad764caae24116a8"
SHA256SUM_VENDOR:x86-64 = "24cc7e0d9e28b4ff32b4f097f2fc816a4552ac2070b2422afb28d39ac78d426b"

SHA256SUM_SYSTEM:aarch64 = "c4b45fad36bee7c0db8a1d9315a5be0035520c53d3d005a807735ae9b7ee79cf"
SHA256SUM_VENDOR:aarch64 = "1e6d33d464277ea3964e4658001c8882f21325616d6bcc66d473bc9ee1e246c7"

SRC_URI = "https://sourceforge.net/projects/waydroid/files/images/system/lineage/${WAYDROID_ARCH}/${WAYDROID_SYSTEM_IMAGE};name=system \
           https://sourceforge.net/projects/waydroid/files/images/vendor/${WAYDROID_ARCH}/${WAYDROID_VENDOR_IMAGE};name=vendor \
           "

SRC_URI[system.sha256sum] = "${SHA256SUM_SYSTEM}"
SRC_URI[vendor.sha256sum] = "${SHA256SUM_VENDOR}"

do_install() {
    install -dm755 "${D}/usr/share/waydroid-extra/images"

    # makepkg have extracted the zips
    install -m 0644 "${WORKDIR}/system.img" "${D}/usr/share/waydroid-extra/images"
    install -m 0644 "${WORKDIR}/vendor.img" "${D}/usr/share/waydroid-extra/images"
}

FILES:${PN} += "/usr/share/waydroid-extra/images"
