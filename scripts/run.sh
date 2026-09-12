#!/bin/bash
set -e
source "$HOME/.sdkman/bin/sdkman-init.sh" 2>/dev/null || true
export PATH="$HOME/.sdkman/candidates/java/current/bin:$PATH"

java -cp bin fr.univ.boutique.app.Main
