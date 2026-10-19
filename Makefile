# PROECT_NAME = one

b build:
	make build_and_run_container_abc && make build_apk_in_container && make copy_apk_from_container
# 	 workdir/android-apps/$PROECT_NAME/gradlew assembleDebug 
# 	app/build/outputs/apk/debug/app-debug.apk

bc build_container:
	podman build -t my-android-app . && podman run -dit --replace --name abc -v /home/user/dev/android-apps:/workdir/android-apps:Z my-android-app /bin/bash
# 	podman build -t my-android-app . && podman run -it --replace --name abc -v /home/user/dev/android-apps:/workdir/android-apps:Z my-android-app /bin/bash

ba build_apk:
	podman exec abc /bin/sh -c "cd /workdir/android-apps/one && ./gradlew assembleDebug"

ca copy_apk:
	podman cp abc:/workdir/android-apps/one/app/build/outputs/apk/debug/app-debug.apk ./apks/
# 	podman cp abc:/opt .

i ia install install_apk adb serve run start start_app:
	opt/platform-tools/adb install -r apks/app-debug.apk
# 	opt/platform-tools/adb devices 
#### ----

e escape:
	flatpak-spawn --host --env=TERM=xterm-256color bash




























# ---
ac attach_container:
	podman attach abc

sc start_container:
	podman start abc
