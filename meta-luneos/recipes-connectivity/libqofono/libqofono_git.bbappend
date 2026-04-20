SRC_URI:append = " file://0001-libqofono.pro-Skip-test-subdirs-on-no_tests.patch"

EXTRA_QMAKEVARS_PRE += "CONFIG+=no_tests"
