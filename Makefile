# PROECT_NAME = one

ba build_apk:
	toolbox enter && cd one && ./gradlew assembleDebug
# 	podman exec abc /bin/sh -c "cd /workdir/android-apps/one && ./gradlew assembleDebug"

i ia install install_apk adb serve run start start_app:
# 	opt/platform-tools/adb uninstall com.leanderziehm.one
	opt/platform-tools/adb install -r ./one/app/build/outputs/apk/debug/app-debug.apk

ad:
	opt/platform-tools/adb devices 

e escape:
	flatpak-spawn --host --env=TERM=xterm-256color bash


































# ca copy_apk:
# 	podman cp abc:/workdir/android-apps/one/app/build/outputs/apk/debug/app-debug.apk ./apks/
# 	podman cp abc:/opt .
# ---

bac build_apk_with_container:
	podman exec abc /bin/sh -c "cd /workdir/android-apps/one && ./gradlew assembleDebug"

b build:
	make build_and_run_container_abc && make build_apk_in_container && make copy_apk_from_container
# 	 workdir/android-apps/$PROECT_NAME/gradlew assembleDebug 
# 	app/build/outputs/apk/debug/app-debug.apk

bc build_container:
	podman build -t my-android-app . && podman run -dit --replace --name abc -v /home/user/dev/android-apps:/workdir/android-apps:Z my-android-app /bin/bash
# 	podman build -t my-android-app . && podman run -it --replace --name abc -v /home/user/dev/android-apps:/workdir/android-apps:Z my-android-app /bin/bash


ac attach_container:
	podman attach abc

sc start_container:
	podman start abc
