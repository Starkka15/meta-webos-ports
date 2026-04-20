FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"
SRC_URI += "file://squashfs.cfg \
    file://video.cfg \
    file://virtio.cfg \
    file://crypto.cfg \
    file://waydroid-anbox.cfg \
"

# we have our own version of virtio.cfg
KERNEL_FEATURES:remove:qemuall = "cfg/virtio.scc"

# genericx86-64: real-hardware BSP + extra driver coverage
# Extend COMPATIBLE_MACHINE regex to include our new machine
COMPATIBLE_MACHINE:append = "|genericx86-64"
KMACHINE:genericx86-64 = "common-pc-64"
KBRANCH:genericx86-64 = "v6.6/standard/base"

SRC_URI:append:genericx86-64 = " file://genericx86-64-extra.cfg"
