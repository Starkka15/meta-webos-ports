DESCRIPTION = "LuneOS live/installer image for generic x86-64 hardware"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# Only build for genericx86-64 — this image type is not meaningful for
# ARM/Halium devices or QEMU targets.
COMPATIBLE_MACHINE = "genericx86-64"

IMAGE_FEATURES += "${LUNEOS_IMAGE_DEFAULT_FEATURES}"

inherit luneos_image

# EFI disk image (dd to USB or use with Calamares installer)
# 'live'/hddimg dropped — FAT filesystem can't hold a rootfs >= 4GB
IMAGE_FSTYPES = "wic"
WKS_FILE = "luneos-genericx86-64.wks"

# Give the rootfs plenty of room for firmware + kernel modules
IMAGE_ROOTFS_EXTRA_SPACE = "2097152"

# Full firmware + kernel modules for broad hardware support
# These are intentionally NOT in MACHINE_EXTRA_RRECOMMENDS to avoid
# bloating core-image-minimal-initramfs beyond its 128MB limit.
IMAGE_INSTALL:append = " linux-firmware intel-microcode kernel-modules"

# During build the pulse-access group is not available to wam
inherit extrausers
EXTRA_USERS_PARAMS = " \
    usermod -a -G pulse-access wam; \
"

# Remove the QEMU-only eglfs emulator integration — on real hardware it
# crashes immediately because it requires QEMU EGL extensions that don't
# exist.  The KMS/DRM integration (libqeglfs-kms-integration.so) stays.
# Both live in the same qtbase-plugins package so we strip the emu one here.
remove_eglfs_emu_integration() {
    rm -f ${IMAGE_ROOTFS}/usr/lib/plugins/egldeviceintegrations/libqeglfs-emu-integration.so
}
ROOTFS_POSTPROCESS_COMMAND += "remove_eglfs_emu_integration;"

do_image_wic[depends] += "grub-efi:do_deploy grub-efi-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot dosfstools-native:do_populate_sysroot"
