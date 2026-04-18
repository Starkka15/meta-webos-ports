DESCRIPTION = "LuneOS live/installer image for generic x86-64 hardware"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

# Only build for genericx86-64 — this image type is not meaningful for
# ARM/Halium devices or QEMU targets.
COMPATIBLE_MACHINE = "genericx86-64"

IMAGE_FEATURES += "${LUNEOS_IMAGE_DEFAULT_FEATURES}"

inherit luneos_image
inherit image-live

# Live ISO + EFI disk image
IMAGE_FSTYPES = "live wic"
WKS_FILE = "luneos-genericx86-64.wks"

# Full firmware set for broad hardware support
IMAGE_INSTALL:append = " linux-firmware intel-microcode"

# During build the pulse-access group is not available to wam
inherit extrausers
EXTRA_USERS_PARAMS = " \
    usermod -a -G pulse-access wam; \
"

do_image_wic[depends] += "grub-efi:do_deploy grub-efi-native:do_populate_sysroot \
    mtools-native:do_populate_sysroot dosfstools-native:do_populate_sysroot"
