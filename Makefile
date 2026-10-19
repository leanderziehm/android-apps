build_and_run:
	podman build -t my-android-app . && podman run -dit --replace --name abc my-android-app /bin/bash

a attach:
	podman attach abc

s start:
	podman start abc

c copy:
	podman cp abc:/root/hello-world .


e escape:
	flatpak-spawn --host --env=TERM=xterm-256color bash